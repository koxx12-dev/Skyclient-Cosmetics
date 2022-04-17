package io.github.koxx12dev.gui.greeting

import club.sk1er.patcher.config.PatcherConfig
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.universal.ChatColor
import gg.essential.vigilance.gui.settings.ButtonComponent
import io.github.koxx12dev.gui.greeting.components.CorrectOutsidePixelConstraint
import io.github.koxx12dev.scc.SkyclientCosmetics

class HUDChachySlide : GreetingSlide<EndSlide>(EndSlide::class.java) {
    init {
        if (!SkyclientCosmetics.isPatcher) {
            displayNextScreen()
        }
        hideNextButton()
    }

    val text by UIWrappedText("""
        Would you like to turn on HUDCaching?
    """.trimIndent(), centered = true) constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 75.percent()
        textScale = 3.pixels()
    } childOf window

    val secondaryText by UIWrappedText("""
        HUDCaching reuses frames from the HUD instead of constantly recreating them every frame, as most HUD elements will stay the same for a long amount of time.
        ${ChatColor.BOLD}This improves FPS drastically.${ChatColor.RESET}
        However, this may cause stuff with animations to feel "choppy".
    """.trimIndent(), centered = true) constrain {
        x = CenterConstraint()
        y = SiblingConstraint(5f).also { it.constrainTo = text }
        width = 100.percent()
    } childOf window

    val yesButton by ButtonComponent("${ChatColor.GREEN}Yes") {
        if (SkyclientCosmetics.isPatcher) {
            PatcherConfig.hudCaching = true
            PatcherConfig.INSTANCE.markDirty()
            PatcherConfig.INSTANCE.writeData()
        }
        displayNextScreen()
    } constrain {
        y = CenterConstraint()
        x = CorrectOutsidePixelConstraint(window.getWidth() / 2 - 2)
    } childOf blackbar

    val noButton by ButtonComponent("${ChatColor.RED}No") {
        displayNextScreen()
    } constrain {
        y = CenterConstraint()
        x = (window.getWidth() / 2 + 2).pixels()
    } childOf blackbar

    override fun setButtonFloat() {
        yesButton.setFloating(true)
        noButton.setFloating(true)
    }
}
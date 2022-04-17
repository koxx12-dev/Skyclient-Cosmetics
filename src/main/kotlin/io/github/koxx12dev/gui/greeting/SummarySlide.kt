package io.github.koxx12dev.gui.greeting

import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.percent
import gg.essential.elementa.dsl.provideDelegate
import gg.essential.universal.ChatColor

class SummarySlide : GreetingSlide<DiscordSlide>(DiscordSlide::class.java) {
    val text by UIWrappedText("""
        ${ChatColor.BOLD}SkyClient${ChatColor.RESET} is a mod and pack installer that lets you ${ChatColor.BOLD}choose${ChatColor.RESET} and ${ChatColor.BOLD}update${ChatColor.RESET} your own mods and packs with ease.
        By default, SkyClient provides some built-in mods, such as ${ChatColor.BOLD}SkyClient${ChatColor.RESET} ${ChatColor.BOLD}Updater${ChatColor.RESET} and ${ChatColor.BOLD}SkyClient${ChatColor.RESET} ${ChatColor.BOLD}Cosmetics${ChatColor.RESET} which enhances the SkyClient experience.
        All of the mods and packs included were added ${ChatColor.BOLD}with permission${ChatColor.RESET} from the original developers.
    """.trimIndent(), centered = true) constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 75.percent()
    } childOf window
}
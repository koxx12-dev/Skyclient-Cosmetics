package io.github.koxx12dev.gui.greeting

import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.universal.UGraphics
import io.github.koxx12dev.gui.greeting.components.GreetingSlide
import java.awt.Color

class IntroductionGreetingSlide : GreetingSlide<DiscordSlide>(DiscordSlide::class.java) {
    val title by UIText("SkyClient") constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        textScale = 5.pixels()
        color = Color.CYAN.toConstraint()
    } childOf window

    val subtitle by UIText("Welcome to...") constrain {
        x = CenterConstraint()
        y = SiblingConstraint(2f, alignOpposite = true)
    } childOf window

    override fun initScreen(width: Int, height: Int) {
        @Suppress("DEPRECATION") // we need to run this to force reenable opengl stuff (also this wouldn't run in 1.17+)
        UGraphics.disableTexture2D()
        UGraphics.enableBlend()
        UGraphics.disableAlpha()
        UGraphics.shadeModel(7425)
        UGraphics.shadeModel(7424)
        UGraphics.disableBlend()
        UGraphics.enableAlpha()
        @Suppress("DEPRECATION")
        UGraphics.enableTexture2D()
        super.initScreen(width, height)
    }
}
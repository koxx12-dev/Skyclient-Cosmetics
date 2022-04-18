package io.github.koxx12dev.gui.greeting

import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import io.github.koxx12dev.gui.greeting.components.GreetingSlide
import io.github.koxx12dev.scc.utils.Files
import net.minecraft.client.gui.GuiMainMenu
import java.awt.Color

class EndSlide : GreetingSlide<GuiMainMenu>(GuiMainMenu::class.java, {
    Files.greetingFile.createNewFile()
    Files.greetingFile.writeText("Deleting this file will cause the SKyClient Greeting slides to show again. If you want to do that, go ahead, if you don't, don't.")
    Thread.sleep(1000)
}) {
    val title by UIText("That's it!") constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        textScale = 5.pixels()
        color = Color.GREEN.darker().toConstraint()
    } childOf window

    val subtitle by UIText("Have fun using SkyClient!") constrain {
        x = CenterConstraint()
        y = SiblingConstraint(2f)
    } childOf window

    override fun onScreenClose() {
        super.onScreenClose()
        if (previousScale != Int.MIN_VALUE) {
            mc.gameSettings.guiScale = previousScale
            mc.gameSettings.saveOptions()
        }
    }
}
package io.github.koxx12dev.gui.greeting

import cc.woverflow.onecore.utils.fetchJsonElement
import gg.essential.api.utils.Multithreading
import gg.essential.api.utils.WebUtil
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.*
import gg.essential.universal.ChatColor
import gg.essential.vigilance.gui.settings.ButtonComponent
import net.minecraft.client.Minecraft
import net.minecraft.launchwrapper.Launch
import java.awt.Color
import java.io.File

class ImportSlide : GreetingSlide<OptimizationSlide>(OptimizationSlide::class.java) {
    private val parentConfig: File? = run {
        val parent = Minecraft.getMinecraft().mcDataDir.parentFile
        if (parent?.name != ".minecraft" || !parent.isDirectory) {
            val parentParent = parent?.parentFile
            if (parentParent?.name != ".minecraft" || !parentParent.isDirectory) {
                null
            } else {
                parentParent
            }
        } else {
            parent
        }
    }
    init {
        if (parentConfig == null) {
            displayNextScreen()
        }
        hideNextButton()
    }

    val text by UIWrappedText("""
        ${ChatColor.BOLD}Would you like to import your config from .minecraft?${ChatColor.RESET}
        -
        ${ChatColor.ITALIC}You will still need to import resource packs manually. Please contact SkyClient discord for more information.${ChatColor.RESET}
    """.trimIndent(), centered = true) constrain {
        x = CenterConstraint()
        y = CenterConstraint() //todo find better wording for this
        width = 75.percent()
        textScale = 3.pixels()
    } childOf window

    val progressText by UIText() constrain {
        color = Color.GREEN.darker().toConstraint()
        x = CenterConstraint()
        y = 2.pixels(alignOpposite = true)
    } childOf window

    val yesButton by ButtonComponent("${ChatColor.GREEN}Yes") {
        hideButtons()
        progressText.setFloating(true)
        Multithreading.runAsync {
            progressText.setText("Downloading config locations...")
            val configLocations = arrayListOf<String>()
            try {
                WebUtil.fetchJsonElement("https://raw.githubusercontent.com/Wyvest/SkyblockClient-REPO/main/files/config_locations.json").asJsonArray.forEach {
                    configLocations.add(it.asString)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                configLocations.add("config")
                configLocations.add("W-OVERFLOW")
                configLocations.add("options.txt")
                configLocations.add("optionsof.txt")
            }
            for (location in configLocations) {
                try {
                    progressText.setText("Finding \"$location\"...")
                    val file = File(parentConfig, location)
                    if (file.exists()) {
                        progressText.setText("Moving \"$location\"...")
                        file.copyRecursively(File(Launch.minecraftHome, location), overwrite = true, onError = { _, _ -> OnErrorAction.SKIP })
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Window.enqueueRenderOperation {
                displayNextScreen()
            }
        }
    } constrain {
        y = CenterConstraint()
        x = CenterConstraint() - 2.pixels(alignOutside = true)
    } childOf blackbar

    private fun hideButtons() {
        yesButton.hide(true)
        noButton.hide(true)
    }

    val noButton by ButtonComponent("${ChatColor.RED}No") {
        displayNextScreen()
    } constrain {
        y = CenterConstraint()
        x = CenterConstraint() + 2.pixels()
    } childOf blackbar

    override fun setButtonFloat() {
        yesButton.setFloating(true)
        noButton.setFloating(true)
    }
}
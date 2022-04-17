package io.github.koxx12dev.gui.greeting

import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.*
import gg.essential.universal.ChatColor
import gg.essential.vigilance.gui.settings.ButtonComponent
import net.minecraft.client.settings.GameSettings

class OptimizationSlide : GreetingSlide<EndSlide>(EndSlide::class.java) {
    init {
        hideNextButton()
    }

    val text by UIWrappedText("""
        Would you like to apply pre-optimized settings?
    """.trimIndent(), centered = true) constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 75.percent()
        textScale = 3.pixels()
    } childOf window

    val yesButton by ButtonComponent("${ChatColor.GREEN}Yes") {
        if (isOptifineLoaded()) {
            val settingsClass = mc.gameSettings.javaClass
            settingsClass.getDeclaredField("ofFastRender").setBoolean(mc.gameSettings, false)
            settingsClass.getDeclaredField("ofSmartAnimations").setBoolean(mc.gameSettings, false)
            settingsClass.getDeclaredField("ofSmartAnimations").setBoolean(mc.gameSettings, true)
            settingsClass.getDeclaredField("ofRenderRegions").setBoolean(mc.gameSettings, true)
            settingsClass.getDeclaredField("ofSmoothFps").setBoolean(mc.gameSettings, false)
            settingsClass.getDeclaredField("ofSmoothWorld").setBoolean(mc.gameSettings, false)
            settingsClass.getDeclaredField("ofClouds").setInt(mc.gameSettings, 0)
            settingsClass.getDeclaredField("ofFogType").setInt(mc.gameSettings, 0)
            settingsClass.getDeclaredField("ofConnectedTextures").setInt(mc.gameSettings, 1)
            settingsClass.getDeclaredField("ofTranslucentBlocks").setInt(mc.gameSettings, 1)
            settingsClass.getDeclaredField("ofDroppedItems").setInt(mc.gameSettings, 1)
            settingsClass.getDeclaredField("ofVignette").setInt(mc.gameSettings, 1)
            settingsClass.getDeclaredField("ofSwampColors").setBoolean(mc.gameSettings, false)
            settingsClass.getDeclaredField("ofRain").setInt(mc.gameSettings, 1)
        }
        mc.gameSettings.saveOptions()
        if (mc.gameSettings.entityShadows) {
            mc.gameSettings.setOptionValue(GameSettings.Options.ENTITY_SHADOWS, 0)
        }
        if (!mc.gameSettings.useVbo) {
            mc.gameSettings.setOptionValue(GameSettings.Options.USE_VBO, 1)
        }
        if (mc.gameSettings.enableVsync) {
            mc.gameSettings.setOptionValue(GameSettings.Options.ENABLE_VSYNC, 0)
        }
        mc.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, 10)
        displayNextScreen()
    } constrain {
        y = CenterConstraint()
        x = CenterConstraint() - 2.pixels(alignOutside = true)
    } childOf blackbar

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

    private fun isOptifineLoaded(): Boolean {
        return try {
            val clazz = Class.forName("Config")
            clazz.getDeclaredField("OF_RELEASE")
            true
        } catch (_: Exception) {
            false
        }
    }
}
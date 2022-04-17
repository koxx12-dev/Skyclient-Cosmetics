package io.github.koxx12dev.gui.greeting

import club.sk1er.patcher.config.PatcherConfig
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.*
import gg.essential.universal.ChatColor
import gg.essential.vigilance.gui.settings.ButtonComponent
import io.github.koxx12dev.gui.greeting.components.CorrectOutsidePixelConstraint
import io.github.koxx12dev.scc.SkyclientCosmetics
import io.github.koxx12dev.scc.utils.TickDelay
import net.minecraft.client.settings.GameSettings
import net.minecraft.util.MathHelper
import java.awt.Color

class OptimizationSlide : GreetingSlide<HUDChachySlide>(HUDChachySlide::class.java) {
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

    val progressText by UIText() constrain {
        color = Color.GREEN.darker().toConstraint()
        x = CenterConstraint()
        y = 2.pixels(alignOpposite = true)
    } childOf window

    val yesButton by ButtonComponent("${ChatColor.GREEN}Yes") {
        progressText.setText("Applying fixes... This might take a while...")
        TickDelay(2) {
            if (isOptifineLoaded()) {
                try {
                    val settingsClass: Class<GameSettings> = mc.gameSettings.javaClass
                    val configClass = Class.forName("Config")
                    settingsClass.getDeclaredField("ofFastRender").setBoolean(mc.gameSettings, false)
                    configClass.getDeclaredMethod("updateFramebufferSize").invoke(null)

                    settingsClass.getDeclaredField("ofFastMath").setBoolean(mc.gameSettings, false)
                    MathHelper::class.java.getDeclaredField("fastMath").setBoolean(null, false)

                    settingsClass.getDeclaredField("ofSmartAnimations").setBoolean(mc.gameSettings, true)
                    settingsClass.getDeclaredField("ofRenderRegions").setBoolean(mc.gameSettings, true)
                    settingsClass.getDeclaredField("ofSmoothFps").setBoolean(mc.gameSettings, false)
                    settingsClass.getDeclaredField("ofSmoothWorld").setBoolean(mc.gameSettings, false)
                    configClass.getDeclaredMethod("updateThreadPriorities").invoke(null)

                    settingsClass.getDeclaredField("ofClouds").setInt(mc.gameSettings, 0)
                    settingsClass.getDeclaredMethod("updateRenderClouds").invoke(mc.gameSettings)
                    mc.renderGlobal.javaClass.getDeclaredMethod("resetClouds").invoke(mc.renderGlobal)

                    settingsClass.getDeclaredField("ofFogType").setInt(mc.gameSettings, 0)
                    settingsClass.getDeclaredField("ofConnectedTextures").setInt(mc.gameSettings, 1)
                    settingsClass.getDeclaredField("ofTranslucentBlocks").setInt(mc.gameSettings, 1)
                    settingsClass.getDeclaredField("ofDroppedItems").setInt(mc.gameSettings, 1)
                    settingsClass.getDeclaredField("ofVignette").setInt(mc.gameSettings, 1)
                    settingsClass.getDeclaredField("ofSwampColors").setBoolean(mc.gameSettings, false)
                    Class.forName("net.optifine.CustomColors").getDeclaredMethod("updateUseDefaultGrassFoliageColors").invoke(null)

                    settingsClass.getDeclaredField("ofRain").setInt(mc.gameSettings, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (SkyclientCosmetics.isPatcher) {
                PatcherConfig.cullingFix = true
                PatcherConfig.separateResourceLoading = true
                PatcherConfig.disableAchievements = true
                PatcherConfig.autoTitleScale = true
                PatcherConfig.unfocusedFPS = true
                PatcherConfig.cleanProjectiles = true
                PatcherConfig.numericalEnchants = true
                PatcherConfig.staticItems = true
                PatcherConfig.limitChunks = true
                PatcherConfig.playerBackFaceCulling = true
                PatcherConfig.openToLanReplacement = 1
                PatcherConfig.INSTANCE.markDirty()
                PatcherConfig.INSTANCE.writeData()
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
            mc.gameSettings.setOptionFloatValue(GameSettings.Options.FRAMERATE_LIMIT, 260f)
            mc.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, 10)
            mc.gameSettings.saveOptions()
            mc.gameSettings.loadOptions()
            mc.refreshResources()
            mc.renderGlobal.loadRenderers()
            TickDelay(2) {
                Window.enqueueRenderOperation { displayNextScreen() }
            }
        }
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
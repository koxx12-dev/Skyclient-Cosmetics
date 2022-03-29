/*
 * SkyclientCosmetics - Cool cosmetics for a mod installer Skyclient!
 * Copyright (C) koxx12-dev [2021 - 2021]
 *
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found under this url
 * https://github.com/koxx12-dev/Skyclient-Cosmetics
 *
 * If you have a private concern, please contact me on
 * Discord: Koxx12#8061
 */

package io.github.koxx12dev.scc.listeners;

import cc.woverflow.onecore.utils.GuiUtils;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.SkyClientMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiListeners {

    @SubscribeEvent
    public void onGuiInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiIngameMenu) {
            int x = event.gui.width - 132;
            int y = 3;
            event.buttonList.add(new GuiButton(2666487, x, y, 130, 20, "SkyClient Cosmetics"));
        }
    }

    @SubscribeEvent
    public void onGuiAction(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.gui instanceof GuiIngameMenu && event.button.id == 2666487) {
            GuiUtils.openScreen(SkyclientCosmetics.config);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && !(Minecraft.getMinecraft().currentScreen instanceof SkyClientMainMenu)) {
                Minecraft.getMinecraft().displayGuiScreen(new SkyClientMainMenu());
            }
        }
    }
}
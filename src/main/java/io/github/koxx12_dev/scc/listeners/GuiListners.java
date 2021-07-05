package io.github.koxx12_dev.scc.listeners;

import io.github.koxx12_dev.scc.SCC;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiListners {

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
            SCC.displayScreen = SCC.config.gui();
        }
    }

}
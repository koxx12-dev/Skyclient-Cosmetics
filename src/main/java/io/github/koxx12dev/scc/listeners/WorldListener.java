package io.github.koxx12dev.scc.listeners;

import gg.essential.api.EssentialAPI;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class WorldListener {
    @SubscribeEvent
    public void onWorldJoin(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if (Settings.joinMessage) {
            EssentialAPI.getNotifications().push(SkyclientCosmetics.MOD_NAME, "Looks like you are using Skyclient Cosmetics for the first time!\n" + "Use /scc to get started!\n" + "Use \"/api new\" to set hypixel API key!", 10f);
            Settings.joinMessage = false;
            SkyclientCosmetics.config.markDirty();
            SkyclientCosmetics.config.writeData();
        }
    }
}

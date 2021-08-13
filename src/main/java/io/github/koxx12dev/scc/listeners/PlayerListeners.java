package io.github.koxx12dev.scc.listeners;

import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.threads.JoinThread;
import io.github.koxx12dev.scc.utils.managers.CosmeticsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerListeners {

    @SubscribeEvent
    public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {

        Thread joinThread = new JoinThread();
        joinThread.start();

    }

    @SubscribeEvent
    public void onNameFormat(PlayerEvent.NameFormat event) {
        if (Settings.debugDisplayTags) {
            event.displayname = CosmeticsManager.getUser(Minecraft.getMinecraft().getSession().getUsername()).getTag();
        }

        if (Settings.displayTags) {
            if (CosmeticsManager.isUserAdded(event.displayname)) {
                String tag;

                tag = CosmeticsManager.getUser(event.displayname).getTag();

                if (Minecraft.getMinecraft().getSession().getUsername().equals(event.displayname) && Settings.displayNameFix) {
                    event.displayname = tag + SkyclientCosmetics.rankColor + " " + event.displayname;
                } else {
                    event.displayname = tag + " " + event.displayname;
                }
            }
        }
    }
}

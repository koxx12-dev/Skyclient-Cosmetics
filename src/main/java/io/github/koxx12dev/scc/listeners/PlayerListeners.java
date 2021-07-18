package io.github.koxx12dev.scc.listeners;

import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.Utils.Tag;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.threads.JoinThread;
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
            event.displayname = SkyclientCosmetics.uuidTags.get(Minecraft.getMinecraft().getSession().getUsername()).get(0);
        }

        if (Settings.displayTags) {
            if (SkyclientCosmetics.uuidTags.containsKey(event.displayname)) {
                String tag;

                tag = SkyclientCosmetics.uuidTags.get(event.displayname).get(Tag.isShortTagsOnInt());

                if (Minecraft.getMinecraft().getSession().getUsername().equals(event.displayname) && Settings.displanameFix) {
                    event.displayname = tag + SkyclientCosmetics.rankColor + " " + event.displayname;
                } else {
                    event.displayname = tag + " " + event.displayname;
                }
            }
        }
    }

}

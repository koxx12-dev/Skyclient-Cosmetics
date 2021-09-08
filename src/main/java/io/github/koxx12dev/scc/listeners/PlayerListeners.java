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

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

import gg.essential.api.EssentialAPI;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.cosmetics.Tag;
import io.github.koxx12dev.scc.cosmetics.TagCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerListeners {

    @SubscribeEvent
    public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (Settings.joinMessage) {
            EssentialAPI.getNotifications().push(SkyclientCosmetics.MOD_NAME, "Welcome to SkyClient Cosmetics!\nType /scc in chat to get started!\nType /api new in chat to set your Hypixel API Key!");
            Settings.joinMessage = false;
            SkyclientCosmetics.config.markDirty();
            SkyclientCosmetics.config.writeData();
        }
    }

    @SubscribeEvent
    public void onNameFormat(PlayerEvent.NameFormat event) {
        if (Settings.displayTags) {
            Tag tag = TagCosmetics.getInstance().getTag(event.displayname);
            if (tag != null) {
                event.displayname = tag.getFullTag() + " " + event.displayname;
            }
        }
    }
}

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

package io.github.koxx12dev.scc.utils;

import gg.essential.universal.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Chat {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void sendSystemMessage(String message) {
        if (mc != null && mc.theWorld != null ) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "SkyClien" + ChatColor.DARK_AQUA + "'" + ChatColor.AQUA + "t Cosmetics" + ChatColor.DARK_GREEN + "]" + ChatColor.WHITE + ": " + ChatColor.RESET + message));
        }
    }

    public static void sendClientMessage(String message) {
        if (mc != null && mc.theWorld != null ) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
        }
    }

}

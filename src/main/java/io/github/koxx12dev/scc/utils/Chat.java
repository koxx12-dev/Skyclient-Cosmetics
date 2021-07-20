package io.github.koxx12dev.scc.utils;

import gg.essential.universal.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Chat {

    public static void sendMessagePrivate(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColor.DARK_GREEN+"["+ChatColor.AQUA+"SkyClien"+ChatColor.DARK_AQUA+"'"+ChatColor.AQUA+"t Cosmetics"+ChatColor.DARK_GREEN+"]"+ChatColor.WHITE+": "+ChatColor.RESET+message));
    }

}

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

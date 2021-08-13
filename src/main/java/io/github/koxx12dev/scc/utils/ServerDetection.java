package io.github.koxx12dev.scc.utils;

import net.minecraft.client.Minecraft;

import java.util.Objects;

public class ServerDetection {

    /*
        Code stolen from skytils
        @Author My-Name-Is-Jeff
    */

    public static boolean isOnHypixel() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }
        return false;
    }

    public static boolean isPlayingSkyblock() {
        if (isOnHypixel() && Objects.equals(Sidebar.getSidebarTitle(), "Skyblock")) {
            return true;
        }
        return true;
    }

}

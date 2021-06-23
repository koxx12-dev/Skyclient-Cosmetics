package io.github.koxx12_dev.scc.Utils;

import net.minecraft.client.Minecraft;

public class DetectionStuff {

    /*
        Code stolen from dsm
        @Author bowser0000
    */

    public static boolean isOnHypixel() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }
        return false;
    }
}

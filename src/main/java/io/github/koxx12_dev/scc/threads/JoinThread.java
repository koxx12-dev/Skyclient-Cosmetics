package io.github.koxx12_dev.scc.threads;

import io.github.koxx12_dev.scc.GUI.SCCConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class JoinThread extends Thread {

    public void run() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (SCCConfig.JoinMessage) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA +"Looks like you are using Skyclient Cosmetics for the first time\n"+ EnumChatFormatting.AQUA +"Use /scc to get started!"));
            SCCConfig.JoinMessage = false;
        }

    }

}

package io.github.koxx12dev.scc.threads;

import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.Chat;
import net.minecraft.util.EnumChatFormatting;

public class JoinThread extends Thread {

    public void run() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Settings.joinMessage) {
            Chat.sendSystemMessage(EnumChatFormatting.AQUA +"Looks like you are using Skyclient Cosmetics for the first time\n"+ EnumChatFormatting.AQUA +"Use /scc to get started!\n"+ EnumChatFormatting.AQUA +"Use \"/api new\" to set hypixel API key!");
            Settings.joinMessage = false;
        }

    }

}

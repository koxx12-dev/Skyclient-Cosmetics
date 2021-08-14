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

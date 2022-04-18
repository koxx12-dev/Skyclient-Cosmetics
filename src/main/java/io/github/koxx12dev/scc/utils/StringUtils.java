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

import io.github.koxx12dev.scc.cosmetics.Tag;
import io.github.koxx12dev.scc.cosmetics.TagCosmetics;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static String cleanMessage(String msg) {
        return msg.replaceAll("\u00A7[a-f0-9kmolnr]", "");
    }

    public static String cleanEmojis(String msg) {
        return msg.replaceAll("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]", "");
    }

    public static String discordPlaceholder(String text) {
        List<String> txt = new ArrayList<>(Arrays.asList(text.split("%")));

        for (int i = 0; i < txt.toArray().length; i++) {
            switch (txt.get(i)) {
                case "player":
                    try {
                        txt.set(i, Minecraft.getMinecraft().getSession().getUsername());
                    } catch (Exception ignored) {
                    }
                    break;
                case "fps":
                    try {
                        txt.set(i, String.valueOf(Minecraft.getDebugFPS()));
                    } catch (Exception ignored) {
                        txt.set(i, "0");
                    }
                    break;
                case "hand":
                    try {
                        txt.set(i, cleanMessage(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName()));
                    } catch (Exception ignored) {
                        txt.set(i, "Nothing");
                    }
                    break;
                case "tag":
                    try {
                        Tag tag = TagCosmetics.getInstance().getTag(Minecraft.getMinecraft().getSession().getUsername());
                        if (tag != null) {
                            txt.set(i, TagCosmetics.getInstance().isInitialized() ? cleanMessage(tag.getFullTag()) : "");
                        } else {
                            txt.set(i, "");
                        }
                    } catch (Exception ignored) {
                    }
                    break;
                case "shorttag":
                    try {
                        Tag tag = TagCosmetics.getInstance().getTag(Minecraft.getMinecraft().getSession().getUsername());
                        if (tag != null) {
                            txt.set(i, TagCosmetics.getInstance().isInitialized() ? cleanMessage(tag.getShortTag()) : "");
                        } else {
                            txt.set(i, "");
                        }
                    } catch (Exception ignored) {
                    }
                    break;
                case "bits":
                    try {
                        txt.set(i, SidebarUtils.getBits());
                    } catch (Exception ignored) {
                    }
                    break;
                case "time":
                    try {
                        txt.set(i, SidebarUtils.getSBTime());
                    } catch (Exception ignored) {
                    }
                    break;
                case "date":
                    try {
                        txt.set(i, SidebarUtils.getSBDate());
                    } catch (Exception ignored) {
                    }
                    break;
                case "loc":
                    try {
                        txt.set(i, SidebarUtils.getSBLoc());
                    } catch (Exception ignored) {
                    }
                    break;
                case "server":
                    try {
                        txt.set(i, SidebarUtils.getServer());
                    } catch (Exception ignored) {
                    }
                    break;
                case "objective":
                    try {
                        txt.set(i, SidebarUtils.getObjective());
                    } catch (Exception ignored) {
                    }
                    break;
                case "purse":
                    try {
                        txt.set(i, SidebarUtils.getPurse());
                    } catch (Exception ignored) {
                    }
                    break;
            }
        }
        return String.join("", txt);
    }
}

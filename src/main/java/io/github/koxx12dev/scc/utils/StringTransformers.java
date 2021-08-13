package io.github.koxx12dev.scc.utils;

import io.github.koxx12dev.scc.utils.managers.CosmeticsManager;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringTransformers {

    public static String cleanMessage(String msg) {
        return msg.replaceAll("\u00A7[a-f0-9kmolnr]","");
    }

    public static String cleanEmojis(String msg) {
        return msg.replaceAll("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]","");
    }

    public static String discordPlaceholder(String text) {
        List<String> txt = new ArrayList<>(Arrays.asList(text.split("%")));

        for (int i = 0; i < txt.toArray().length; i++) {
            switch (txt.get(i)) {
                case "player":
                    try {
                        txt.set(i,Minecraft.getMinecraft().getSession().getUsername());
                    } catch (Exception ignored) {}
                    break;
                case "fps":
                    try {
                        txt.set(i,String.valueOf(Minecraft.getDebugFPS()));
                    } catch (Exception ignored) {
                        txt.set(i,"0");
                    }
                    break;
                case "hand" :
                    try {
                        txt.set(i,cleanMessage(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName()));
                    } catch (Exception ignored) {
                        txt.set(i,"Nothing");
                    }
                    break;
                case "shorttag":
                    try {
                        txt.set(i,cleanMessage(CosmeticsManager.getUser(Minecraft.getMinecraft().getSession().getUsername()).getLongTag()));
                    } catch (Exception ignored) {}
                    break;
                case "tag":
                    try {
                        txt.set(i,cleanMessage(CosmeticsManager.getUser(Minecraft.getMinecraft().getSession().getUsername()).getShortTag()));
                    } catch (Exception ignored) {}
                    break;
                case "bits":
                    try {
                        txt.set(i,Sidebar.getBits());
                    } catch (Exception ignored) {}
                    break;
                case "time":
                    try {
                        txt.set(i,Sidebar.getSBTime());
                    } catch (Exception ignored) {}
                    break;
                case "date":
                    try {
                        txt.set(i,Sidebar.getSBDate());
                    } catch (Exception ignored) {}
                    break;
                case "loc":
                    try {
                        txt.set(i,Sidebar.getSBLoc());
                    } catch (Exception ignored) {}
                    break;
                case "server":
                    try {
                        txt.set(i,Sidebar.getServer());
                    } catch (Exception ignored) {}
                    break;
                case "objective":
                    try {
                        txt.set(i,Sidebar.getObjective());
                    } catch (Exception ignored) {}
                    break;
                case "purse":
                    try {
                        txt.set(i,Sidebar.getPurse());
                    } catch (Exception ignored) {}
                    break;
            }
        }
        return String.join("", txt);
    }
}

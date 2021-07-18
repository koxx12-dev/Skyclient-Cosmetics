package io.github.koxx12dev.scc.Utils;

import io.github.koxx12dev.scc.SkyclientCosmetics;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transformers {

    public static String cleanMessage(String msg) {
        //if null, keep null (no gain or loss of safety)
        if (msg == null)
            return null;

        List<Character> chars = new ArrayList<>(Arrays.asList(ArrayUtils.toObject(msg.toCharArray())));
        // For each character in the String
        // add it to the List
        for (int i = 0; i < chars.toArray().length; i++) {
            if (chars.get(i) == '\u00A7') {
                chars.remove(i);
                chars.remove(i);
            }
        }

        StringBuilder builder = new StringBuilder(chars.size());

        for(Character ch: chars)
        {
            builder.append(ch);
        }

        return builder.toString().replaceAll("\u00A7b","");
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
                        txt.set(i,cleanMessage(SkyclientCosmetics.uuidTags.get(Minecraft.getMinecraft().getSession().getUsername()).get(1)));
                    } catch (Exception ignored) {}
                    break;
                case "tag":
                    try {
                        txt.set(i,cleanMessage(SkyclientCosmetics.uuidTags.get(Minecraft.getMinecraft().getSession().getUsername()).get(0)));
                    } catch (Exception ignored) {}
                    break;
            }
        }

        return String.join("", txt);
    }
}

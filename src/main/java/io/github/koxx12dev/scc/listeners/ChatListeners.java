package io.github.koxx12dev.scc.listeners;

import gg.essential.universal.ChatColor;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.Utils.*;
import io.github.koxx12dev.scc.gui.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatListeners {

    @SubscribeEvent
    public void onChatMsgTags(ClientChatReceivedEvent event) {

        if (Settings.showTags && ServerDetection.isOnHypixel()) {
            try {
                JSONObject msg = new JSONObject(IChatComponent.Serializer.componentToJson(event.message));

                String name = msg.getJSONArray("extra").getJSONObject(0).get("text").toString();

                List<String> nameParsed = new ArrayList<>(Arrays.asList(name.split(" ")));

                String[] nameParsedClean = Transformers.cleanMessage(name).split(" ");
                if (Settings.debugLogs) {
                    SkyclientCosmetics.LOGGER.debug(msg);
                }
                for (int i = 0; i < nameParsedClean.length; i++) {
                    String current = (String) Array.get(nameParsedClean, i);
                    if (Settings.debugLogs) {
                        SkyclientCosmetics.LOGGER.debug(current + " , " + i);
                    }
                    int j = i;
                    if (SkyclientCosmetics.uuidTags.containsKey(current.replace(":", ""))) {
                        if (current.contains(":")) {

                            if (i != 0) {
                                if (SkyclientCosmetics.hypixelRanks.contains(Array.get(nameParsedClean, i - 1))) {
                                    j = i - 1;
                                }
                            }
                            nameParsed.add(j, SkyclientCosmetics.uuidTags.get(current.replace(":", "")).get(Tag.isShortTagsOnInt()));

                            msg.getJSONArray("extra").getJSONObject(0).put("text", String.join(" ", nameParsed) + " ");
                            event.message = IChatComponent.Serializer.jsonToComponent(msg.toString());
                        } else if (msg.getJSONArray("extra").getJSONObject(1).get("text").toString().contains(":")) {

                            String color = ChatColor.valueOf(msg.getJSONArray("extra").getJSONObject(0).get("color").toString().toUpperCase()).toString();

                            if (i != 0) {
                                if (SkyclientCosmetics.hypixelRanks.contains(Array.get(nameParsedClean, i - 1))) {
                                    j = i - 1;
                                }
                            }
                            nameParsed.add(j, SkyclientCosmetics.uuidTags.get(current.replace(":", "")).get(Tag.isShortTagsOnInt()) + color);

                            msg.getJSONArray("extra").getJSONObject(0).put("text", String.join(" ", nameParsed));
                            event.message = IChatComponent.Serializer.jsonToComponent(msg.toString());
                        }
                    }
                }
            } catch (Exception ignored) {}
        }

    }

    @SubscribeEvent
    public void onChatMsgHpApi(ClientChatReceivedEvent event) {
        String msg = Transformers.cleanMessage(event.message.getUnformattedText());
        if (msg.contains("Your new API key is ")) {
            String key = msg.replace("Your new API key is ","");
            SkyclientCosmetics.LOGGER.info(key);
            Chat.sendMessagePrivate(ChatColor.GREEN+"Checking API key");
            try {
                JSONObject response = new JSONObject(Requests.request("https://api.hypixel.net/key?key=" + key));

                if (response.getBoolean("success") && response.getJSONObject("record").get("owner").toString().replaceAll("-", "").equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
                    Chat.sendMessagePrivate(ChatColor.GREEN+"Verified API key!");
                    Settings.hpApiKey = key;
                } else {
                    Chat.sendMessagePrivate(ChatColor.RED+"Couldn't verify \""+key+"\" as a API key");
                }

            } catch (Exception e) {
                Chat.sendMessagePrivate(ChatColor.RED+"\""+key+"\" is not a valid API key");
            }

        }

    }

}

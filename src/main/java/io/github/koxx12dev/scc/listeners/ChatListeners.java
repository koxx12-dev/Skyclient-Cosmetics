package io.github.koxx12dev.scc.listeners;

import gg.essential.universal.ChatColor;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.Chat;
import io.github.koxx12dev.scc.utils.Requests;
import io.github.koxx12dev.scc.utils.Tag;
import io.github.koxx12dev.scc.utils.Transformers;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: Add support for dg tags
//TODO: Show tags in guild/party list

public class ChatListeners {

    @SubscribeEvent
    public void onChatMsgTags(ClientChatReceivedEvent event) {

        if (Settings.showTags) {
            try {
                if (event.type == 0) {

                    JSONObject original = new JSONObject(IChatComponent.Serializer.componentToJson(event.message));

                    String textValue = original.get("text").toString();

                    JSONArray extras = new JSONArray();

                    try {
                        extras = original.getJSONArray("extra");
                    } catch (Exception ignored) {
                    }

                    //tags for other ppl

                    if (textValue.length() != 0) {

                        final String tempSubstring = textValue.substring(textValue.length() - 2);

                        if (tempSubstring.contains("\u00A7f") || tempSubstring.contains("\u00A77")) {

                            List<String> rawName = Arrays.asList(Transformers.cleanMessage(textValue).split(" "));

                            System.out.println(rawName.size() + " , " + rawName);

                            String playerName = rawName.get(rawName.size() - 1);

                            if (SkyclientCosmetics.uuidTags.containsKey(playerName)) {
                                original.put("text", SkyclientCosmetics.uuidTags.get(playerName).get(Tag.isShortTagsOnInt())+" " + textValue);
                                event.message = IChatComponent.Serializer.jsonToComponent(original.toString());
                            }
                        } else {
                            System.out.println(original);
                        }
                    }
                    if (extras.length() != 0) {
                        try {
                            if (extras.getJSONObject(0).get("text").toString().contains("\u00A72Guild >") || extras.getJSONObject(0).get("text").toString().contains("\u00A7bCo-op >") || extras.getJSONObject(0).get("text").toString().contains("\u00A79Party >")) {

                                List<String> rawName = new ArrayList<>(Arrays.asList(extras.getJSONObject(0).get("text").toString().split(" ")));

                                String playerName = Transformers.cleanMessage(rawName.get(rawName.size() - 1)).replace(":", "");

                                if (SkyclientCosmetics.uuidTags.containsKey(playerName)) {

                                    System.out.println("works?");

                                    rawName.add(2, SkyclientCosmetics.uuidTags.get(playerName).get(Tag.isShortTagsOnInt()));

                                    System.out.println("works?");

                                    original.getJSONArray("extra").getJSONObject(0).put("text", String.join(" ", rawName) + " ");

                                    event.message = IChatComponent.Serializer.jsonToComponent(original.toString());

                                }


                            } else {

                                List<String> rawName = new ArrayList<>(Arrays.asList(extras.getJSONObject(0).get("text").toString().split(" ")));

                                String playerName = Transformers.cleanMessage(rawName.get(rawName.size() - 1)).replace(":", "");

                                if (SkyclientCosmetics.uuidTags.containsKey(playerName)) {

                                    String color = ChatColor.valueOf(extras.getJSONObject(0).get("color").toString().toUpperCase()).toString();

                                    rawName.add(0, SkyclientCosmetics.uuidTags.get(playerName).get(Tag.isShortTagsOnInt())+color);

                                    original.getJSONArray("extra").getJSONObject(0).put("text", String.join(" ", rawName));

                                    event.message = IChatComponent.Serializer.jsonToComponent(original.toString());

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

    }

    @SubscribeEvent
    public void onChatMsgHpApi(ClientChatReceivedEvent event) {
        String msg = Transformers.cleanMessage(event.message.getUnformattedText());
        if (msg.contains("Your new API key is ")) {
            String key = msg.replace("Your new API key is ", "");
            SkyclientCosmetics.LOGGER.info(key);
            Chat.sendMessagePrivate(ChatColor.GREEN + "Checking API key");
            try {
                JSONObject response = new JSONObject(Requests.request("https://api.hypixel.net/key?key=" + key));

                if (response.getBoolean("success") && response.getJSONObject("record").get("owner").toString().replaceAll("-", "").equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
                    Chat.sendMessagePrivate(ChatColor.GREEN + "Verified API key!");
                    Settings.hpApiKey = key;
                } else {
                    Chat.sendMessagePrivate(ChatColor.RED + "Couldn't verify \"" + key + "\" as a API key");
                }

            } catch (Exception e) {
                Chat.sendMessagePrivate(ChatColor.RED + "\"" + key + "\" is not a valid API key");
            }

        }

    }

}

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

package io.github.koxx12dev.scc.listeners;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.essential.universal.ChatColor;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.Chat;
import io.github.koxx12dev.scc.utils.Requests;
import io.github.koxx12dev.scc.utils.StringTransformers;
import io.github.koxx12dev.scc.utils.managers.CosmeticsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: Add support for dg tags
//TODO: Show tags in guild/party list

public class ChatListeners {

    public static Pattern chatRegex = Pattern.compile("((To|From)\\s)?((Guild|Co-op|Officer|Party)\\s\\>\\s)?(\\[(MVP|VIP|PIG|YOUTUBE|MOD|HELPER|ADMIN|OWNER|MOJANG|SLOTH|EVENTS|MCP)([\\+]{1,2})?\\]\\s)?[\\w]+:\\s.*");

    public static Pattern dmRegex = Pattern.compile("((To|From)\\s)(\\[(MVP|VIP|PIG|YOUTUBE|MOD|HELPER|ADMIN|OWNER|MOJANG|SLOTH|EVENTS|MCP)([\\+]{1,2})?\\]\\s)?[\\w]+:\\s.*");

    public static Pattern groupRegex = Pattern.compile("((Guild|Co-op|Officer|Party)\\s\\>\\s)(\\[(MVP|VIP|PIG|YOUTUBE|MOD|HELPER|ADMIN|OWNER|MOJANG|SLOTH|EVENTS|MCP)([\\+]{1,2})?\\]\\s)?[\\w]+:\\s.*");

    public static Pattern rankRegex = Pattern.compile("\\[(MVP|VIP|PIG|YOUTUBE|MOD|HELPER|ADMIN|OWNER|MOJANG|SLOTH|EVENTS|MCP)([\\+]{1,2})?\\]");

    public static Pattern groupRankRegex = Pattern.compile("((\u00A7[a-f0-9kmolnr](To|From))|(\u00A7[a-f0-9kmolnr](Guild|Co-op|Officer|Party)\\s(\u00A7[a-f0-9kmolnr])?\\\\u003e))\\s((\u00A7[a-f0-9kmolnr])?(\\[(MVP|VIP|PIG|YOUTUBE|MOD|HELPER|ADMIN|OWNER|MOJANG|SLOTH|EVENTS|MCP)([\\+]{1,2})?\\]\\s)?)?[\\w]+(\u00A7[a-f0-9kmolnr])?:");

    public static Pattern hypixelAPIKeyMsgRegex = Pattern.compile("Your new API key is [0-9a-z]{8}-([0-9a-z]{4}-){3}[0-9a-z]{12}");

    @SubscribeEvent
    public void onChatMsgTags(ClientChatReceivedEvent event) {
        if (Settings.showTags) {
            try {
                if (event.type == 0) {
                    String cleanMessage = StringTransformers.cleanMessage(event.message.getUnformattedText());
                    List<String> splitMessage = Arrays.asList(cleanMessage.split("\\s"));
                    String parsedMessage = IChatComponent.Serializer.componentToJson(event.message);
                    Matcher parsedMatcher = groupRankRegex.matcher(parsedMessage);
                    String playerName;
                    if (chatRegex.matcher(cleanMessage).matches()) {
                        if (dmRegex.matcher(cleanMessage).matches()) {
                            if (Settings.debugRegexChat) {
                                event.message.appendText(ChatColor.GRAY + " (dmRegex)");
                                SkyclientCosmetics.LOGGER.debug(cleanMessage + " (dmRegex)");
                            }
                            if (Settings.debugLogs) {
                                SkyclientCosmetics.LOGGER.info(parsedMessage);
                            }
                            JsonObject jsonParsedMsg = JsonParser.parseString(parsedMessage).getAsJsonObject();
                            String playerText = jsonParsedMsg.get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
                            String playerColor;
                            try {
                                playerColor = ChatColor.valueOf(jsonParsedMsg.get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()).toString();
                            } catch (Exception e) {
                                playerColor = ChatColor.GRAY.toString();
                            }
                            
                            playerName = playerText.replaceAll(rankRegex.pattern(),"").trim();

                            if (Settings.debugRegexChat){
                                Chat.sendSystemMessage(playerName);
                            }
                            if (CosmeticsManager.isUserAdded(playerName)) {
                                String tag = CosmeticsManager.getUser(playerName).getTag();
                                String newVal = tag+" "+playerColor+playerText;
                                jsonParsedMsg.get("extra").getAsJsonArray().get(0).getAsJsonObject().addProperty("text",newVal);
                                if (Settings.debugRegexChat) {
                                    Chat.sendSystemMessage(newVal);
                                }
                                event.message = IChatComponent.Serializer.jsonToComponent(jsonParsedMsg.toString());
                            }
                        } else if (groupRegex.matcher(cleanMessage).matches()) {
                            if (Settings.debugRegexChat) {
                                event.message.appendText(ChatColor.GRAY + " (groupRegex)");
                                SkyclientCosmetics.LOGGER.debug(cleanMessage + " (groupRegex)");
                            }
                            if (Settings.debugLogs) {
                                SkyclientCosmetics.LOGGER.info(parsedMessage);
                            }
                            if (parsedMatcher.find()) {
                                String msg = parsedMatcher.group(0);
                                List<String> msgList = new ArrayList<>(Arrays.asList(msg.split(" ")));
                                List<String> cleanMsg = Arrays.asList(StringTransformers.cleanMessage(msg).split(" "));
                                playerName = cleanMsg.get(cleanMsg.size()-1).replaceAll(":","");
                                if (Settings.debugRegexChat) {
                                    Chat.sendSystemMessage(playerName);
                                }
                                if (CosmeticsManager.isUserAdded(playerName)) {
                                    String tag = CosmeticsManager.getUser(playerName).getTag();
                                    msgList.add(2,tag);
                                    String newVal = String.join(" ", msgList);
                                    if (Settings.debugRegexChat) {
                                        Chat.sendSystemMessage(newVal);
                                    }
                                    event.message = IChatComponent.Serializer.jsonToComponent(parsedMessage.replace(msg,newVal));
                                }
                            } else {
                                if (Settings.debugRegexChat) {
                                    Chat.sendSystemMessage("Match Failed!");
                                }
                            }
                        } else {
                            if (Settings.debugRegexChat) {
                                event.message.appendText(ChatColor.GRAY + " (msgRegex)");
                                SkyclientCosmetics.LOGGER.debug(cleanMessage + " (msgRegex)");
                            }
                            if (rankRegex.matcher(splitMessage.get(0)).matches()) {
                                playerName = splitMessage.get(1);
                            } else {
                                playerName = splitMessage.get(0);
                            }
                            playerName = playerName.replaceAll(":","");
                            if (CosmeticsManager.isUserAdded(playerName)) {
                                event.message = new ChatComponentText(CosmeticsManager.getUser(playerName).getTag()+" ").appendSibling(event.message);
                            }
                        }
                    } else {
                        if (Settings.debugRegexChat) {
                            event.message.appendText(ChatColor.GRAY + " (Nothing)");
                            SkyclientCosmetics.LOGGER.debug(cleanMessage + " (Nothing)");
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
        if (event.type == 0) {
            String msg = StringTransformers.cleanMessage(event.message.getUnformattedText());
            if (msg.contains("Your new API key is ")) {
                Matcher match = hypixelAPIKeyMsgRegex.matcher(msg);
                if (match.find()) {
                    String cleanMsg = match.group();
                    String key = cleanMsg.replace("Your new API key is ", "");
                    SkyclientCosmetics.LOGGER.info(key);
                    Chat.sendSystemMessage(ChatColor.GREEN + "Checking API key");
                    try {
                        JsonObject response = JsonParser.parseString(Requests.request("https://api.hypixel.net/key?key=" + key)).getAsJsonObject();

                        if (response.get("success").getAsBoolean() && response.get("record").getAsJsonObject().get("owner").getAsString().replaceAll("-", "").equals(Minecraft.getMinecraft().getSession().getPlayerID())) {
                            Chat.sendSystemMessage(ChatColor.GREEN + "Verified API key!");
                            Settings.hpApiKey = key;
                        } else {
                            Chat.sendSystemMessage(ChatColor.RED + "Couldn't verify \"" + key + "\" as a API key");
                        }

                    } catch (Exception e) {
                        Chat.sendSystemMessage(ChatColor.RED + "\"" + key + "\" is not a valid API key");
                    }
                }
            }
        }
    }
}

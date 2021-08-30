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

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.essential.universal.ChatColor;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.cache.UserCache;
import io.github.koxx12dev.scc.utils.exceptions.APIException;
import io.github.koxx12dev.scc.utils.exceptions.CacheException;
import io.github.koxx12dev.scc.utils.managers.CacheManager;
import io.github.koxx12dev.scc.utils.managers.CosmeticsManager;
import io.github.koxx12dev.scc.utils.types.User;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

public class Requests {

    public static String request(String URL) throws IOException {

        java.net.URL url = new URL(URL);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";

    }

    public static void reloadTags() throws IOException, CacheException {

        CosmeticsManager.clearUsers();

        JsonObject api = SkyclientCosmetics.api;

        try {

            JsonObject tags = api.getAsJsonObject("tags");
            JsonObject perms = api.getAsJsonObject("perms");

            List<String> tagsIDList = Lists.newArrayList(tags.keySet().iterator());
            List<String> permsIDList = Lists.newArrayList(perms.keySet().iterator());

            if (permsIDList.size() != tagsIDList.size()) {
                throw new Error("Someone broke the repo\nwait for staff to fix it");
            }

            for (String s : tagsIDList) {
                JsonElement tag = tags.get(s);

                String tagLong = tag.getAsJsonArray().get(0).getAsString().replace("&", "\u00A7");

                String tagShort = tag.getAsJsonArray().get(1).getAsString().replace("&", "\u00A7");

                JsonArray users = perms.get(s).getAsJsonArray();

                for (int j = 0; j < users.size(); j++) {

                    String uuid = users.get(j).getAsString();

                    String name;

                    if (UserCache.isUUIDDataOutdated() && UserCache.isUUIDDataCached(uuid) || !UserCache.isUUIDDataCached(uuid)) {
                        name = getUserNameFromUUID(uuid);
                        UserCache.addName(uuid, name);
                    } else {
                        name = UserCache.getName(users.get(j).getAsString());
                    }

                    UserCache.addUser(uuid, name, tagShort, tagLong);
                    CosmeticsManager.addUser(uuid, name, tagShort, tagLong);

                }

            }

        } catch (IOException | CacheException ignored) {
        }

        UserCache.updateOutdated();
        UserCache.save();
    }

    public static JsonObject getApiData() throws IOException, CacheException, APIException {
        try {
            SkyclientCosmetics.apiConnectionSuccess = true;
            return JsonParser.parseString(Requests.request("https://koxx12-dev.github.io/api/scc/tags.json")).getAsJsonObject();
        } catch (Exception e) {
            List<String> keys = Lists.newArrayList(CacheManager.getCache("userCache").getRawAsJsonObject().keySet().iterator());

            for (String s : keys) {
               User user = UserCache.getUser(s);
               CosmeticsManager.addUser(user.getUUID(),user.getName(),user.getLongTag(),user.getShortTag());
            }
            SkyclientCosmetics.apiConnectionSuccess = false;
            Chat.sendSystemMessage(ChatColor.RED+"Failed to connect to the api, Loaded cache");
            SkyclientCosmetics.LOGGER.error("Failed to connect to the api, Loaded cache");
            return null;
        }
    }

    public static String getUserNameFromUUID(String uuid) throws IOException {
        String name = Requests.request("https://api.mojang.com/user/profiles/" + uuid + "/names");
        JsonArray nameJson = JsonParser.parseString(name).getAsJsonArray();
        return nameJson.get(nameJson.size() - 1).getAsJsonObject().get("name").getAsString();
    }

    public static void setRankColor() {
        try {
                JsonObject response = JsonParser.parseString(request("https://api.hypixel.net/player?key=" + Settings.hpApiKey + "&uuid=" + Minecraft.getMinecraft().getSession().getPlayerID())).getAsJsonObject();
                String rank;
                if (response.get("success").getAsBoolean()) {
                    try {
                        rank = response.get("player").getAsJsonObject().get("rank").getAsString();
                    } catch (Exception e) {
                        try {
                            try {
                                rank = response.get("player").getAsJsonObject().get("newPackageRank").getAsString();
                            } catch (Exception ee) {
                                rank = response.get("player").getAsJsonObject().get("packageRank").getAsString();
                            }
                        } catch (Exception eee) {
                            rank = response.get("player").getAsJsonObject().get("monthlyPackageRank").getAsString();
                        }
                    }

                    if (rank.equals("NONE") || rank.equals("NORMAL")) {
                        SkyclientCosmetics.rankColor = "";
                    } else {
                        System.out.println("TEST: " + rank);
                        switch (rank) {
                            case "YOUTUBER":
                                SkyclientCosmetics.rankColor = ChatColor.RED.toString();
                                break;
                            case "VIP":
                            case "VIP_PLUS":
                                SkyclientCosmetics.rankColor = ChatColor.GREEN.toString();
                                break;
                            case "MVP":
                            case "MVP_PLUS":
                                SkyclientCosmetics.rankColor = ChatColor.AQUA.toString();
                                break;
                            case "SUPERSTAR":
                                SkyclientCosmetics.rankColor = ChatColor.GOLD.toString();
                                break;
                        }
                    }
                } else {
                    throw new IOException("Getting rank color failed");
                }
        } catch (Exception e) {
            SkyclientCosmetics.rankColor = "";
        }
    }
}

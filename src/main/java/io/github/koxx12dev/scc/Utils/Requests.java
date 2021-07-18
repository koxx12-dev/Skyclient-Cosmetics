package io.github.koxx12dev.scc.Utils;

import gg.essential.universal.ChatColor;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.gui.Settings;
import net.minecraft.client.Minecraft;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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

    public static void reloadTags() throws IOException {

        try {
            SkyclientCosmetics.hypixelRanks.clear();
            SkyclientCosmetics.uuidTags.clear();
            SkyclientCosmetics.api = Requests.getApiData();
        } catch (Exception ignored) {
        }

        JSONObject api = SkyclientCosmetics.api;

        try {
            JSONArray tagsIdList = api.getJSONObject("tags").names();
            JSONArray permsIdList = api.getJSONObject("perms").names();
            JSONArray HPRanks = api.getJSONArray("HpRanks");
            if (permsIdList.length() != tagsIdList.length()) {
                throw new Error("Someone broke the repo\nwait for staff to fix it");
            }
            for (int i = 0; i < tagsIdList.length(); i++) {
                List<Object> tag = api.getJSONObject("tags").getJSONArray((String) tagsIdList.get(i)).toList();
                JSONArray perms = api.getJSONObject("perms").getJSONArray((String) tagsIdList.get(i));
                for (int j = 0; j < perms.length(); j++) {
                    String uuid = perms.getString(j).replaceAll("-", "");

                    String nme = Cache.getData(uuid);

                    List<String> tmp = new ArrayList<>();

                    tmp.add(tag.get(0).toString().replaceAll("&", "\u00A7"));
                    tmp.add(tag.get(1).toString().replaceAll("&", "\u00A7"));

                    SkyclientCosmetics.uuidTags.put(nme, tmp);
                }
            }
            for (int i = 0; i < HPRanks.length(); i++) {
                SkyclientCosmetics.hypixelRanks.add(HPRanks.get(i).toString());
            }
        } catch (IOException ignored) {
        }

        SkyclientCosmetics.LOGGER.debug(SkyclientCosmetics.uuidTags);
        SkyclientCosmetics.LOGGER.debug(SkyclientCosmetics.hypixelRanks);
        Cache.updateTimestamp();
        Cache.saveCache();
    }

    public static JSONObject getApiData() throws IOException {
        return new JSONObject(Requests.request("https://koxx12-dev.github.io/api/scc/tags.json"));
    }

    public static void setRankColor() {
        try {
                JSONObject response = new JSONObject(request("https://api.hypixel.net/player?key=" + Settings.hpApiKey + "&uuid=" + Minecraft.getMinecraft().getSession().getPlayerID()));
                String rank;
                if (response.getBoolean("success")) {
                    try {
                        rank = response.getJSONObject("player").get("rank").toString();
                    } catch (Exception e) {
                        try {
                            try {
                                rank = response.getJSONObject("player").get("newPackageRank").toString();
                            } catch (Exception ee) {
                                rank = response.getJSONObject("player").get("packageRank").toString();
                            }
                        } catch (Exception eee) {
                            rank = response.getJSONObject("player").get("monthlyPackageRank").toString();
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

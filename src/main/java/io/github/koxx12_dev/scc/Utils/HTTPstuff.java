package io.github.koxx12_dev.scc.Utils;

import io.github.koxx12_dev.scc.SCC;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HTTPstuff {

    public static String request(String URL) throws IOException {

        java.net.URL url = new URL(URL);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";

    }

    public static void reloadTags(){

        try {
            SCC.HypixelRanks.clear();
            SCC.UUIDtags.clear();
            SCC.api = HTTPstuff.getApiData();
        } catch (Exception ignored) {}

        JSONObject api = SCC.api;

        try {
            JSONArray tagsIdList = api.getJSONObject("tags").names();
            JSONArray permsIdList = api.getJSONObject("perms").names();
            JSONArray HPRanks = api.getJSONArray("HpRanks");
            if (permsIdList.length() != tagsIdList.length()) {
                throw new Error("Someone broke the repo\nwait for staff to fix it");
            }
            for (int i = 0; i < tagsIdList.length(); i++) {
                String tag = (String) api.getJSONObject("tags").get((String) tagsIdList.get(i));
                JSONArray perms = api.getJSONObject("perms").getJSONArray((String) tagsIdList.get(i));
                for (int j = 0; j < perms.length(); j++) {
                    String uuid = perms.getString(j).replaceAll("-","");

                    String name = HTTPstuff.request("https://api.mojang.com/user/profiles/"+uuid+"/names");
                    JSONArray nameJson = new JSONArray(name);
                    String nme = (String) nameJson.getJSONObject(nameJson.length()-1).get("name");

                    SCC.UUIDtags.put(nme,tag.replaceAll("&","\u00A7"));
                }
            }
            for (int i = 0; i < HPRanks.length(); i++) {
                SCC.HypixelRanks.add(HPRanks.get(i).toString());
            }
        } catch (IOException ignored) {
        }

        System.out.println(SCC.UUIDtags);
        System.out.println(SCC.HypixelRanks);
    }

    public static JSONObject getApiData() throws IOException {
        return new JSONObject(HTTPstuff.request("https://koxx12-dev.github.io/api/scc/tags.json"));
    }

}

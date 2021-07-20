package io.github.koxx12dev.scc.utils;

import net.minecraft.client.Minecraft;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Cache {

    public static File mcConfigDir = new File(Minecraft.getMinecraft().mcDataDir,"config");

    public static File sccConfigDir = new File(mcConfigDir,"SkyClientCosmetics");

    public static File sccCacheFile = new File(sccConfigDir,"SCC-Cache.txt");

    public static JSONObject tempCache = new JSONObject();

    public static void setup() throws IOException {

        if (!sccConfigDir.exists()) {
            sccConfigDir.mkdir();
        }

        if (!sccCacheFile.exists()) {
            sccCacheFile.createNewFile();
        } else {
            loadCache();
        }

    }

    public static String getData(String uuid) throws IOException {

        if (!tempCache.has("timestamp")) {
            updateTimestamp(true);
        }

        if (!tempCache.has(uuid) || tempCache.getLong("timestamp") > Instant.now().toEpochMilli()) {

            String name = Requests.request("https://api.mojang.com/user/profiles/" + uuid + "/names");
            JSONArray nameJson = new JSONArray(name);
            String nme = (String) nameJson.getJSONObject(nameJson.length() - 1).get("name");

            tempCache.put(uuid,nme);
            //SkyclientCosmetics.LOGGER.info("PUT {} AS {} TO CACHE",uuid,nme);
        }

        return tempCache.get(uuid).toString();
    }

    public static void saveCache() throws IOException {

        String b64json = Base64.getEncoder().encodeToString(tempCache.toString().getBytes());

        FileWriter fileWriter = new FileWriter(sccCacheFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(b64json);
        printWriter.close();

    }

    public static void loadCache() throws FileNotFoundException {
        Scanner reader = new Scanner(sccCacheFile);
        if (reader.hasNextLine()) {
            String data = reader.nextLine();
            reader.close();
            byte[] bytes = Base64.getDecoder().decode(data);
            tempCache = new JSONObject(new String(bytes));
        }

    }

    public static void updateTimestamp() {

        if (tempCache.getLong("timestamp") > Instant.now().toEpochMilli()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(Instant.now()));
            calendar.add(Calendar.HOUR_OF_DAY, 48);
            long timestamp = calendar.getTimeInMillis() / 1000;

            tempCache.put("timestamp", timestamp);

            //SkyclientCosmetics.LOGGER.info("UPDATED CACHE TIMESTAMP");

        }
    }

    public static void updateTimestamp(boolean force) {
        if (force) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(Instant.now()));
            calendar.add(Calendar.HOUR_OF_DAY, 48);
            long timestamp = calendar.getTimeInMillis() / 1000;

            tempCache.put("timestamp", timestamp);

            //SkyclientCosmetics.LOGGER.info("UPDATED CACHE TIMESTAMP");
        }
    }

}

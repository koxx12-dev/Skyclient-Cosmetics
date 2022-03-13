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

package io.github.koxx12dev.scc.utils.managers;

import cc.woverflow.onecore.utils.JsonUtils;
import com.google.gson.JsonObject;
import io.github.koxx12dev.scc.SkyclientCosmetics;
import io.github.koxx12dev.scc.utils.cache.UserCache;
import io.github.koxx12dev.scc.utils.exceptions.CacheException;
import io.github.koxx12dev.scc.utils.types.Cache;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CacheManager {


    private static final String cacheVersion = "1v";
    public static File sccFolder = new File(Minecraft.getMinecraft().mcDataDir, "SkyclientCosmetics");
    private static final File sccCache = new File(sccFolder, "Cache.json");
    private static JsonObject cache = new JsonObject();

    public static void setupCache() throws IOException, CacheException {

        if (!sccFolder.exists()) {
            sccFolder.mkdir();
        }

        if (!sccCache.exists()) {
            sccCache.createNewFile();
        } else {
            loadCache();
        }

        if (cache.has("cacheVersion")) {
            if (!Objects.equals(cache.get("cacheVersion").getAsString(), cacheVersion)) {
                cache = new JsonObject();
                cache.addProperty("cacheVersion", cacheVersion);
            }
        } else {
            cache.addProperty("cacheVersion", cacheVersion);
        }

        UserCache.setup();

    }

    public static void saveCache() throws IOException {
        FileUtils.writeStringToFile(sccCache, cache.toString(), StandardCharsets.UTF_8);
    }

    public static void loadCache() throws IOException {
        cache = JsonUtils.asJsonElement(FileUtils.readFileToString(sccCache, StandardCharsets.UTF_8)).getAsJsonObject();
    }

    public static void addCache(String id, int time) throws CacheException {

        if (!cache.has(id)) {
            cache.add(id, new JsonObject());
            cache.addProperty(id + "_timestamp", generateTimestamp(time));
        } else {
            throw new CacheException("Cache with id \"" + id + "\" already exists");
        }

    }

    public static void updateCache(String id, Cache cacheObj) throws CacheException {

        if (cache.has(id)) {
            cache.add(id, cacheObj.getRawAsJsonObject());
            SkyclientCosmetics.LOGGER.info(cacheObj.getRawAsJsonObject().toString());
        } else {
            throw new CacheException("Cache with id \"" + id + "\" does not exist");
        }

    }

    public static void removeCache(String id) throws CacheException {

        if (cache.has(id)) {
            cache.remove(id);
            cache.remove(id + "_timestamp");
        } else {
            throw new CacheException("Cache with id \"" + id + "\" does not exist");
        }

    }

    public static Cache getCache(String id) throws CacheException {

        if (cache.has(id)) {
            return new Cache(cache.get(id).toString());
        } else {
            throw new CacheException("Cache with id \"" + id + "\" does not exist");
        }

    }

    public static Boolean doesCacheExist(String id) {

        return cache.has(id);

    }

    public static Long getTimestamp(String id) {

        return cache.get(id + "_timestamp").getAsLong();

    }

    public static void updateTimestamp(String id, int time) {

        cache.addProperty(id + "_timestamp", generateTimestamp(time));

    }

    public static Boolean isCacheOutdated(String id) {

        return getTimestamp(id) > Instant.now().toEpochMilli();

    }

    public static void invalidateCache(String id) {

        cache.addProperty(id + "_timestamp", 0);

    }

    public static Boolean isCacheEmpty(String id) {

        return cache.get(id).getAsJsonObject().entrySet().isEmpty();

    }

    private static Long generateTimestamp(int days) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(Instant.now()));
        calendar.add(Calendar.HOUR_OF_DAY, 24 * days);

        return calendar.getTimeInMillis() / 1000;

    }

}

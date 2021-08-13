package io.github.koxx12dev.scc.utils.cache;

import com.google.gson.JsonObject;
import io.github.koxx12dev.scc.utils.exceptions.CacheException;
import io.github.koxx12dev.scc.utils.managers.CacheManager;
import io.github.koxx12dev.scc.utils.types.Cache;
import io.github.koxx12dev.scc.utils.types.User;

import java.io.IOException;

public class UserCache extends CacheManager {

    public static void setup() throws CacheException {

        if (!doesCacheExist("userCache")) {
            addCache("userCache",1);
        }

        if (!doesCacheExist("userUUIDCache")) {
            addCache("userUUIDCache",2);
        }

    }

    public static void save() throws IOException {

        saveCache();

    }

    public static User getUser(String uuid) throws CacheException {

        if (isUserDataCached(uuid)) {
            JsonObject cache = getCache("userCache").getAsJsonObject(uuid);
            return new User(cache.get("uuid").getAsString(),cache.get("name").getAsString(),cache.get("tagShort").getAsString(),cache.get("tagLong").getAsString());
        } else {
            throw new CacheException("User data \""+uuid+"\" is not cached");
        }

    }

    public static String getName(String uuid) throws CacheException {

        if (isUUIDDataCached(uuid)) {
            return getCache("userUUIDCache").getAsString(uuid);
        } else {
            throw new CacheException("User UUID \""+uuid+"\" is not cached");
        }

    }

    public static void addName(String uuid, String name) throws CacheException {

        updateCache("userUUIDCache",uuid,name);

    }

    public static void addUser(String uuid, String name, String tagShort, String tagLong) throws CacheException {

        JsonObject json = new JsonObject();

        json.addProperty("uuid",uuid);
        json.addProperty("name",name);
        json.addProperty("tagShort",tagShort);
        json.addProperty("tagLong",tagLong);

        updateCache("userCache",uuid,json);

    }

    public static Boolean isUserDataCached(String uuid) throws CacheException {

        return getCache("userCache").getRawAsJsonObject().has(uuid);

    }

    public static Boolean isUUIDDataCached(String uuid) throws CacheException {

        return getCache("userUUIDCache").getRawAsJsonObject().has(uuid);

    }

    public static Boolean isUserDataOutdated() {

        return isCacheOutdated("userCache");

    }

    public static Boolean isUUIDDataOutdated() {

        return isCacheOutdated("userUUIDCache");

    }

    public static void updateUserDataTimestamp() {

        updateTimestamp("userCache",1);

    }

    public static void updateUUIDDataTimestamp() {

        updateTimestamp("userUUIDCache",2);

    }

    public static void updateOutdated() {
        if (isUserDataOutdated()) {
            updateUserDataTimestamp();
        }

        if (isUUIDDataOutdated()) {
            updateUUIDDataTimestamp();
        }

    }

    public static void invalidateCache() {

        invalidateCache("userCache");
        invalidateCache("userUUIDCache");

    }


    private static void updateCache(String id,String key,JsonObject cache) throws CacheException {

        if (!isCacheEmpty(id)) {
            JsonObject currentCache = getCache(id).getRawAsJsonObject();

            currentCache.add(key,cache);

            updateCache(id,new Cache(currentCache.toString()));
        } else {
            JsonObject json = new JsonObject();

            json.add(key,cache);

            updateCache(id,new Cache(json.toString()));
        }

    }

    private static void updateCache(String id,String key,String cache) throws CacheException {

        if (!isCacheEmpty(id)) {
            JsonObject currentCache = getCache(id).getRawAsJsonObject();

            currentCache.addProperty(key,cache);

            updateCache(id,new Cache(currentCache.toString()));
        } else {
            JsonObject json = new JsonObject();

            json.addProperty(key,cache);

            updateCache(id,new Cache(json.toString()));
        }

    }

}

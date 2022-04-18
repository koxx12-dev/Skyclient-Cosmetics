package io.github.koxx12dev.scc.cosmetics;

import cc.woverflow.onecore.utils.InternetUtils;
import cc.woverflow.onecore.utils.JsonUtils;
import com.google.gson.*;
import gg.essential.api.utils.Multithreading;
import gg.essential.api.utils.WebUtil;
import io.github.koxx12dev.scc.utils.Files;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TagCosmetics {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final TagCosmetics INSTANCE = new TagCosmetics();
    private boolean initialized;
    private JsonObject rawData;
    private JsonObject tagJson;
    private JsonObject permsJson;
    private final File cacheFile = new File(Files.sccFolder, "cache.json");
    private final HashMap<String, Tag> tags = new HashMap<>();

    public static TagCosmetics getInstance() {
        return INSTANCE;
    }

    public void reInitialize() {
        tags.clear();
        permsJson = null;
        tagJson = null;
        rawData = null;
        initialize();
    }

    public void initialize() {
        initialized = false;
        Multithreading.runAsync(() -> {
            try {
                rawData = InternetUtils.fetchJsonElement(WebUtil.INSTANCE, "https://koxx12-dev.github.io/api/scc/tags.json").getAsJsonObject();
                Multithreading.runAsync(() -> {
                    try {
                        FileUtils.writeStringToFile(cacheFile, GSON.toJson(rawData), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (cacheFile.exists()) {
                    try {
                        rawData = JsonUtils.asJsonElement(FileUtils.readFileToString(cacheFile, StandardCharsets.UTF_8)).getAsJsonObject();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return;
                    }
                } else {
                    return;
                }
            }
            tagJson = rawData.getAsJsonObject("tags");
            permsJson = rawData.getAsJsonObject("perms");
            ArrayList<Tag> tagsToBeAdded = new ArrayList<>();
            ArrayList<TagPerm> permsToBeAdded = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry : tagJson.entrySet()) {
                JsonArray array = entry.getValue().getAsJsonArray();
                tagsToBeAdded.add(new Tag(array.get(0).getAsString(), (array.size() == 2 ? array.get(1).getAsString() : array.get(0).getAsString()), entry.getKey()));
            }
            for (Map.Entry<String, JsonElement> entry : permsJson.entrySet()) {
                permsToBeAdded.add(new TagPerm(entry.getValue().getAsJsonArray(), entry.getKey()));
            }
            for (TagPerm perm : permsToBeAdded) {
                for (Tag tag : tagsToBeAdded) {
                    if (tag.getIdentifier().equals(perm.getIdentifier())) {
                        for (String user : perm.getUsers()) {
                            tags.put(user.toLowerCase(Locale.ENGLISH), tag);
                        }
                        break;
                    }
                }
            }
            initialized = true;
        });
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Nullable
    public Tag getTag(@NotNull String username) {
        if (!isInitialized()) return null;
        return tags.getOrDefault(username.toLowerCase(Locale.ENGLISH), null);
    }

    public HashMap<String, Tag> getTags() {
        return tags;
    }
}

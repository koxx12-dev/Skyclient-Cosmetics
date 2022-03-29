package io.github.koxx12dev.scc.cosmetics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import gg.essential.api.EssentialAPI;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TagPerm {
    private final String identifier;
    private final ArrayList<String> users;

    TagPerm(JsonArray users, String identifier) {
        ArrayList<String> profiles = new ArrayList<>();
        for (JsonElement element : users) {
            String uuid = element.getAsString();
            try {
                CompletableFuture<String> profile = EssentialAPI.getMojangAPI().getName(UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20)));
                if (profile != null) profiles.add(profile.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.users = profiles;
        this.identifier = identifier;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "TagPerm{" +
                "identifier='" + identifier + '\'' +
                ", users=" + users +
                '}';
    }
}

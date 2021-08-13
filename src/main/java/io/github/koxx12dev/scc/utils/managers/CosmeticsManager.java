package io.github.koxx12dev.scc.utils.managers;

import io.github.koxx12dev.scc.utils.types.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CosmeticsManager {

    private static final HashMap<String, User> userData = new HashMap<>();

    public static void clearUsers() {

        userData.clear();

    }

    public static boolean isUserAdded(String name)  {

        return userData.containsKey(name);

    }

    public static void addUser(String uuid,String name,String tagShort,String tagLong) {

        userData.put(name,new User(uuid,name,tagShort,tagLong));

    }

    public static User getUser(String name) {

        return userData.get(name);

    }

    public static List<String> listUsersUUIDs() {

        return new ArrayList<>(userData.keySet());

    }

    public static List<String> listUsersNames() {

        List<String> keys = new ArrayList<>(userData.keySet());
        List<String> names = new ArrayList<>();

        for (String key : keys) {

            names.add(userData.get(key).getName());

        }

        return names;

    }

}

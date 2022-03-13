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

import io.github.koxx12dev.scc.utils.types.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CosmeticsManager {

    private static final HashMap<String, User> userData = new HashMap<>();

    public static void clearUsers() {

        userData.clear();

    }

    public static boolean isUserAdded(String name) {

        return userData.containsKey(name);

    }

    public static void addUser(String uuid, String name, String tagShort, String tagLong) {

        userData.put(name, new User(uuid, name, tagShort, tagLong));

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

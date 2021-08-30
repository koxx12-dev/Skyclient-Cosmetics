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

import io.github.koxx12dev.scc.utils.managers.CacheManager;

import java.io.File;

public class Files {
    public static void setup() {

        if (!CacheManager.sccFolder.exists()) {
            CacheManager.sccFolder.mkdir();
        }

    }
    public static boolean hidePetLis() {
        return new File(CacheManager.sccFolder,"HIDEPETLIS").exists();
    }
}

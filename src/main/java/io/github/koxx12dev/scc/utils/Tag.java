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

import io.github.koxx12dev.scc.gui.Settings;

public class Tag {

    public static boolean isShortTagsOnBoolean() {
        return Settings.shortenTags;
    }

    public static int isShortTagsOnInt() {
        if (Settings.shortenTags) {
            return 1;
        } else {
            return 0;
        }
    }

}

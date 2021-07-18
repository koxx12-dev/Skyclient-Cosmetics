package io.github.koxx12dev.scc.Utils;

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

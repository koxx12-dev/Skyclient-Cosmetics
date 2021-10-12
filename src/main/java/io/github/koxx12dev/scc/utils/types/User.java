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

package io.github.koxx12dev.scc.utils.types;

import io.github.koxx12dev.scc.utils.Tag;

public class User {

    String uuid = "";
    String name = "";
    String tagShort = "";
    String tagLong = "";

    public User() {

    }

    public User(String uuid, String name, String tagShort, String tagLong) {

        this.uuid = uuid;
        this.name = name;
        this.tagShort = tagShort;
        this.tagLong = tagLong;

    }


    public String getShortTag() {

        return tagShort;

    }


    public String getLongTag() {

        return tagLong;

    }

    public String getTag() {

        if (Tag.isShortTagsOnBoolean()) {
            return tagShort;
        } else {
            return tagLong;
        }

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {

        return uuid;

    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setTagLong(String tagLong) {
        this.tagLong = tagLong;
    }

    public void setTagShort(String tagShort) {
        this.tagShort = tagShort;
    }
}

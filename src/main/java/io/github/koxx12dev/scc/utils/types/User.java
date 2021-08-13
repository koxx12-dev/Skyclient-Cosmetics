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

    public String getUUID() {

        return uuid;

    }

    public void setName(String name) {
        this.name = name;
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

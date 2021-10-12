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

import com.google.gson.*;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Cache {

    private static JsonObject value;

    public Cache() {
    }

    public Cache(String jsonObejct) {

        value = JsonParser.parseString(jsonObejct).getAsJsonObject();

    }

    public JsonObject getRawAsJsonObject() {

        return value.getAsJsonObject();

    }

    public JsonArray getRawAsJsonArray() {

        return value.getAsJsonArray();

    }

    public JsonArray getAsJsonArray(String key) {

        return value.get(key).getAsJsonArray();

    }

    public JsonObject getAsJsonObject(String key) {

        return value.get(key).getAsJsonObject();

    }

    public JsonPrimitive getAsJsonPrimitive(String key) {

        return value.get(key).getAsJsonPrimitive();

    }

    public JsonNull getAsJsonNull(String key) {

        return value.get(key).getAsJsonNull();

    }

    public BigDecimal getAsBigDecimal(String key) {

        return value.get(key).getAsBigDecimal();

    }

    public BigInteger getAsBigInteger(String key) {

        return value.get(key).getAsBigInteger();

    }

    public Boolean getAsBoolean(String key) {

        return value.get(key).getAsBoolean();

    }

    public Double getAsDouble(String key) {

        return value.get(key).getAsDouble();

    }

    public Float getAsFloat(String key) {

        return value.get(key).getAsFloat();

    }

    public int getAsInt(String key) {

        return value.get(key).getAsInt();

    }

    public Long getAsLong(String key) {

        return value.get(key).getAsLong();

    }

    public Short getAsShort(String key) {

        return value.get(key).getAsShort();

    }

    public Number getAsNumber(String key) {

        return value.get(key).getAsNumber();

    }

    public String getAsString(String key) {

        return value.get(key).getAsString();

    }


}

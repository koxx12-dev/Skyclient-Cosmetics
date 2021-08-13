package io.github.koxx12dev.scc.utils;

import io.github.koxx12dev.scc.utils.managers.CacheManager;

import java.io.File;

public class Files {
    public static boolean hidePetZord() {
        return new File(CacheManager.sccFolder,"HIDEPETZORD").exists();
    }
}

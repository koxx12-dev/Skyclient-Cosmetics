package io.github.koxx12dev.scc.rpc;

import net.minecraft.client.Minecraft;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadSDK {

    public static File downloadDiscordLibrary() throws IOException {
        String name = "discord_game_sdk";
        String suffix;

        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);

        if (osName.contains("windows")) {
            suffix = ".dll";
        } else if (osName.contains("linux")) {
            suffix = ".so";
        } else if (osName.contains("mac os")) {
            suffix = ".dylib";
        } else {
            throw new RuntimeException("cannot determine OS type: " + osName);
        }


        if (arch.equals("amd64"))
            arch = "x86_64";


        String zipPath = "lib/" + arch + "/" + name + suffix;


        URL downloadUrl = new URL("https://dl-game-sdk.discordapp.net/2.5.6/discord_game_sdk.zip");
        URLConnection urlConnection = downloadUrl.openConnection();
        urlConnection.setRequestProperty("User-Agent", "NING/1.0");
        BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        ZipInputStream zin = new ZipInputStream(inputStream);


        ZipEntry entry;
        while ((entry = zin.getNextEntry()) != null) {

            if (entry.getName().equals(zipPath)) {

                File gameSDK = new File(Minecraft.getMinecraft().mcDataDir, name + suffix);

                if (!new File(Minecraft.getMinecraft().mcDataDir, name + suffix).exists()) {

                    Files.copy(zin, gameSDK.toPath());

                }

                zin.close();

                return gameSDK;

            }

            zin.closeEntry();

        }

        zin.close();

        return null;
    }

}

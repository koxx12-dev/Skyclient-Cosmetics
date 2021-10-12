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

package io.github.koxx12dev.scc;

import com.google.gson.JsonObject;
import de.jcm.discordgamesdk.Core;
import io.github.koxx12dev.scc.commands.SccCommand;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.listeners.ChatListeners;
import io.github.koxx12dev.scc.listeners.GuiListeners;
import io.github.koxx12dev.scc.listeners.PlayerListeners;
import io.github.koxx12dev.scc.listeners.WorldListener;
import io.github.koxx12dev.scc.rpc.RPC;
import io.github.koxx12dev.scc.utils.FileUtils;
import io.github.koxx12dev.scc.utils.Requests;
import io.github.koxx12dev.scc.utils.exceptions.APIException;
import io.github.koxx12dev.scc.utils.exceptions.CacheException;
import io.github.koxx12dev.scc.utils.managers.CacheManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = SkyclientCosmetics.MOD_ID, name = SkyclientCosmetics.MOD_NAME, version = SkyclientCosmetics.MOD_VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class SkyclientCosmetics {

    public static final String MOD_NAME = "${GRADLE_MOD_NAME}";
    public static final String MOD_ID = "${GRADLE_MOD_ID}";
    public static final String MOD_VERSION = "${GRADLE_MOD_VERSION}";

    public static boolean rpcRunning = false;

    public static boolean rpcOn = false;

    public static Settings config;

    public static boolean apiConnectionSuccess = true;

    public static JsonObject api;

    public static Core rpcCore;

    public static String partyID = RPC.generateID();

    public static Logger LOGGER;

    public static String rankColor;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) throws IOException, CacheException, APIException {

        ProgressManager.ProgressBar progress = ProgressManager.push("Pre Init Setup", 5);

        progress.step("Setting up Files");

        FileUtils.setup();

        progress.step("Loading Vigilance");

        config = new Settings();
        config.preload();

        progress.step("Setting up Cache");

        CacheManager.setupCache();

        progress.step("Getting log4j logger");

        LOGGER = event.getModLog();

        progress.step("Getting api data");

        api = Requests.getApiData();

        ProgressManager.pop(progress);
    /*
    if ((boolean)api.get("whitelist")) {
        String UUID = Minecraft.getMinecraft().getSession().getPlayerID();
        List<Object> whitelisted = api.getJSONArray("whitelisted").toList();
        if (!whitelisted.contains(UUID)){
            throw new Error("You are not whitelisted LMAO");
        }
    }
    */
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {

        ProgressManager.ProgressBar progress = ProgressManager.push("Init Setup", 3);

        progress.step("Registering Listeners");

        MinecraftForge.EVENT_BUS.register(new ChatListeners());
        MinecraftForge.EVENT_BUS.register(new PlayerListeners());
        MinecraftForge.EVENT_BUS.register(new GuiListeners());
        MinecraftForge.EVENT_BUS.register(new WorldListener());

        progress.step("Starting RPC");

        RPC.INSTANCE.rpcManager();

        MinecraftForge.EVENT_BUS.register(RPC.INSTANCE);

        progress.step("Registering Commands");

        new SccCommand().register();

        ProgressManager.pop(progress);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) throws IOException, CacheException {

        ProgressManager.ProgressBar progress = ProgressManager.push("Post Init Setup", 2);

        progress.step("Loading tags");

        if (apiConnectionSuccess) {
            Requests.reloadTags();
        }

        progress.step("Setting rank color");

        Requests.setRankColor();

        ProgressManager.pop(progress);

    }
}

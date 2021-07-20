package io.github.koxx12dev.scc;

import de.jcm.discordgamesdk.Core;
import io.github.koxx12dev.scc.utils.Cache;
import io.github.koxx12dev.scc.commands.MainCommand;
import io.github.koxx12dev.scc.gui.Settings;
import io.github.koxx12dev.scc.utils.Requests;
import io.github.koxx12dev.scc.utils.RPC;
import io.github.koxx12dev.scc.listeners.ChatListeners;
import io.github.koxx12dev.scc.listeners.GuiListners;
import io.github.koxx12dev.scc.listeners.PlayerListeners;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod(modid = SkyclientCosmetics.MOD_ID, name = SkyclientCosmetics.MOD_NAME, version = SkyclientCosmetics.MOD_VERSION, clientSideOnly = true)
public class SkyclientCosmetics {

public static final String MOD_NAME = "${GRADLE_MOD_NAME}";
public static final String MOD_ID = "${GRADLE_MOD_ID}";
public static final String MOD_VERSION = "${GRADLE_MOD_VERSION}";

public static boolean rpcRunning = false;

public static boolean rpcOn = false;

public static GuiScreen displayScreen;

public static List<String> hypixelRanks = new ArrayList<>();

public static Settings config;

public static HashMap<String,List<String>> uuidTags = new HashMap<>();

public static JSONObject api;

public static Core rpcCore;

public static String partyID = RPC.generateID();

public static Logger LOGGER;

public static String rankColor;

@Mod.EventHandler
public void onPreInit(FMLPreInitializationEvent event) throws IOException {

    Cache.setup();

    LOGGER = event.getModLog();

    api = Requests.getApiData();
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

    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(new ChatListeners());
    MinecraftForge.EVENT_BUS.register(new PlayerListeners());
    MinecraftForge.EVENT_BUS.register(new GuiListners());

    RPC.INSTANCE.rpcManager();

    MinecraftForge.EVENT_BUS.register(RPC.INSTANCE);

    ClientCommandHandler.instance.registerCommand(new MainCommand());

}

@Mod.EventHandler
public void onPostInit(FMLPostInitializationEvent event) throws IOException {
    // $USER = The username of the currently logged in user.
    // Simply prints out Hello, $USER.
    /*
    if (Loader.isModLoaded("SkyblockExtras")) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {


        }));

        SkyclientCosmetics.LOGGER.info(Arrays.toString(new File(Minecraft.getMinecraft().mcDataDir, "mods").list()));

        //throw new Error("fuck sbe https://github.com/MicrocontrollersDev/Alternatives/blob/1e409e056e3e14ca874a2368c045de96787e8cbd/SkyblockExtras.md");
    }
    */
    Requests.reloadTags();
    config = new Settings();
    config.preload();
    Requests.setRankColor();
}

@SubscribeEvent
public void onTick(TickEvent.ClientTickEvent event) throws IOException {
        if (event.phase != TickEvent.Phase.START)
            return;
        if (displayScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(displayScreen);
            displayScreen = null;
        }
        if (Settings.reloadTags) {
            Requests.reloadTags();
            Settings.reloadTags = false;
        }
    }
}

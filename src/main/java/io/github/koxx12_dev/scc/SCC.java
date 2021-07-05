package io.github.koxx12_dev.scc;

import de.jcm.discordgamesdk.Core;
import io.github.koxx12_dev.scc.Commands.MainCommand;
import io.github.koxx12_dev.scc.GUI.SCCConfig;
import io.github.koxx12_dev.scc.Utils.HTTPstuff;
import io.github.koxx12_dev.scc.Utils.RPC;
import io.github.koxx12_dev.scc.listeners.ChatListeners;
import io.github.koxx12_dev.scc.listeners.GuiListners;
import io.github.koxx12_dev.scc.listeners.PlayerListeners;
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

@Mod(modid = SCC.MOD_ID, name = SCC.MOD_NAME, version = SCC.MOD_VERSION, clientSideOnly = true)
public class SCC {

public static final String MOD_NAME = "${GRADLE_MOD_NAME}";
public static final String MOD_ID = "${GRADLE_MOD_ID}";
public static final String MOD_VERSION = "${GRADLE_MOD_VERSION}";

public static boolean RPCRunning = false;

public static boolean RPCon = false;

public static GuiScreen displayScreen;

public static List<String> HypixelRanks = new ArrayList<>();

public static SCCConfig config;

public static HashMap<String,String> UUIDtags = new HashMap<>();

public static HashMap<String,String> UUIDtagsShort = new HashMap<>();

public static JSONObject api;

public static Core RPCcore;

public static String PartyID = RPC.generateID();

public static Logger LOGGER;

@Mod.EventHandler
public void onPreInit(FMLPreInitializationEvent event) throws IOException {

    LOGGER = event.getModLog();

    api = HTTPstuff.getApiData();
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

    RPC.INSTANCE.RPCManager();

    MinecraftForge.EVENT_BUS.register(RPC.INSTANCE);

    ClientCommandHandler.instance.registerCommand(new MainCommand());

}

@Mod.EventHandler
public void onPostInit(FMLPostInitializationEvent event) {
    // $USER = The username of the currently logged in user.
    // Simply prints out Hello, $USER.
    /*
    if (Loader.isModLoaded("SkyblockExtras")) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {


        }));

        SCC.LOGGER.info(Arrays.toString(new File(Minecraft.getMinecraft().mcDataDir, "mods").list()));

        //throw new Error("fuck sbe https://github.com/MicrocontrollersDev/Alternatives/blob/1e409e056e3e14ca874a2368c045de96787e8cbd/SkyblockExtras.md");
    }
    */
    
    HTTPstuff.reloadTags();
    config = new SCCConfig();
    config.preload();
}

@SubscribeEvent
public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        if (displayScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(displayScreen);
            displayScreen = null;
        }
        if (SCCConfig.reloadTags) {
            HTTPstuff.reloadTags();
            SCCConfig.reloadTags = false;
        }
    }
}

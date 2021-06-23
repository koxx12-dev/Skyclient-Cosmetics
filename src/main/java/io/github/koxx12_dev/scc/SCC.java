package io.github.koxx12_dev.scc;

import io.github.koxx12_dev.scc.Commands.MainCommand;
import io.github.koxx12_dev.scc.GUI.SCCConfig;
import io.github.koxx12_dev.scc.Utils.HTTPstuff;
import io.github.koxx12_dev.scc.listeners.ChatListeners;
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

    public static GuiScreen displayScreen;

    public static List<String> HypixelRanks = new ArrayList<>();

    public static SCCConfig config = new SCCConfig();

    public static HashMap<String,String> UUIDtags = new HashMap<>();

    public static JSONObject api;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) throws IOException {
        api = HTTPstuff.getApiData();
        if ((boolean)api.get("whitelist")) {
            String UUID = Minecraft.getMinecraft().getSession().getPlayerID();
            List<Object> whitelisted = api.getJSONArray("whitelisted").toList();
            if (!whitelisted.contains(UUID)){
                throw new Error("You are not whitelisted LMAO");
            }
        }
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config.preload();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ChatListeners());
        ClientCommandHandler.instance.registerCommand(new MainCommand());

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        // $USER = The username of the currently logged in user.
        // Simply prints out Hello, $USER.

        HTTPstuff.reloadTags();

    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.START)
                return;
            if (displayScreen != null) {
                Minecraft.getMinecraft().displayGuiScreen(displayScreen);
                displayScreen = null;
            }
        }
    }

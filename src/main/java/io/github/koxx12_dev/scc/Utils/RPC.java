package io.github.koxx12_dev.scc.Utils;

import io.github.koxx12_dev.scc.GUI.SCCConfig;
import io.github.koxx12_dev.scc.SCC;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

import java.time.Instant;

public class RPC implements Runnable {

    public static RPC INSTANCE = new RPC();

    private Thread trd = new Thread(this);

    private long timestamp = Instant.now().getEpochSecond();

    public void RPCManager() {
        trd.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing Discord hook.");
            DiscordRPC.discordShutdown();
        }));
    }

    public void initRPC() {

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Discord user: " + user.username);
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder(SCCConfig.RPCText);
            presence.setDetails(Minecraft.getMinecraft().getSession().getUsername()+" is very cool");
            presence.setBigImage("skyclienticon","skyclient is very cool");
            presence.setStartTimestamps(timestamp);
            DiscordRPC.discordUpdatePresence(presence.build());
            SCC.RPCon = true;
        }).build();
        DiscordRPC.discordInitialize("857240025288802356", handlers, true);
    }

    public void updateRPC() {

        if (!SCCConfig.RPC) {
            DiscordRPC.discordClearPresence();
        } else {
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder(SCCConfig.RPCText);
            presence.setDetails(Minecraft.getMinecraft().getSession().getUsername()+" is very cool");
            presence.setBigImage("skyclienticon","skyclient is very cool");
            presence.setStartTimestamps(timestamp);
            DiscordRPC.discordUpdatePresence(presence.build());
        }

    }

    @Override
    public void run() {
        try {
            Thread.sleep(300L);
            initRPC();
            while(!Thread.interrupted()) {
                DiscordRPC.discordRunCallbacks();
                Thread.sleep(300L);
                updateRPC();
            }
        } catch (Exception e) {e.printStackTrace();}
    }
}

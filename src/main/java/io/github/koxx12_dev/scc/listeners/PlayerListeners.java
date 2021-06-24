package io.github.koxx12_dev.scc.listeners;

import io.github.koxx12_dev.scc.GUI.SCCConfig;
import io.github.koxx12_dev.scc.SCC;
import io.github.koxx12_dev.scc.threads.JoinThread;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerListeners {

    @SubscribeEvent
    public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {

        Thread joinThread = new JoinThread();
        joinThread.start();

    }

    @SubscribeEvent
    public void onNameFormat(PlayerEvent.NameFormat event) {
        if (SCCConfig.DebugDisplayTags) {
            event.displayname = SCC.UUIDtags.get(Minecraft.getMinecraft().getSession().getUsername());
        }

        if (SCCConfig.DisplayTags) {
            System.out.println(event.displayname);
            if (SCC.UUIDtags.containsKey(event.displayname)) {
                System.out.println(event.displayname);
                String tag;

                if (SCCConfig.ShortenTags) {
                    tag = SCC.UUIDtagsShort.get(event.displayname);
                } else {
                    tag = SCC.UUIDtags.get(event.displayname);
                }
                event.displayname = tag + " " + event.displayname;
            }
        }
    }

}

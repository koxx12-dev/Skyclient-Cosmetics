package io.github.koxx12_dev.scc.listeners;

import io.github.koxx12_dev.scc.threads.JoinThread;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerListeners {

    @SubscribeEvent
    public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {

        Thread joinThread = new JoinThread();
        joinThread.start();

    }

}

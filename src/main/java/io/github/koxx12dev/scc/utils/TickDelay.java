package io.github.koxx12dev.scc.utils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickDelay {
    private int ticks;
    private final Runnable runnable;

    public TickDelay(int ticks, Runnable runnable) {
        MinecraftForge.EVENT_BUS.register(this);
        this.ticks = ticks;
        this.runnable = runnable;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (ticks < 1) {
                runnable.run();
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            ticks--;
        }
    }
}

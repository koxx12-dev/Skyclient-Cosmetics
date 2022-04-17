package io.github.koxx12dev.scc.mixins.forge;

import io.github.koxx12dev.scc.gui.CustomSplashProgress;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.Semaphore;

@Mixin(value = FMLClientHandler.class, remap = false)
public class FMLClientHandlerMixin {
    @Redirect(method = "processWindowMessages", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/Semaphore;release()V"))
    private void redirectRelease(Semaphore instance) {
        CustomSplashProgress.mutex.release();
    }

    @Redirect(method = "processWindowMessages", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/Semaphore;tryAcquire()Z"))
    private boolean redirectTry(Semaphore instance) {
        return CustomSplashProgress.mutex.tryAcquire();
    }
}

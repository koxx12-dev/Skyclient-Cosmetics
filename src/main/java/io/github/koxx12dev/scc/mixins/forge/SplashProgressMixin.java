package io.github.koxx12dev.scc.mixins.forge;

import io.github.koxx12dev.scc.gui.CustomSplashProgress;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.lwjgl.LWJGLException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SplashProgress.class, remap = false)
public class SplashProgressMixin {
    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private static void overwriteStart(CallbackInfo ci) {
        ci.cancel();
        CustomSplashProgress.start();
    }

    @Inject(method = "getMaxTextureSize", at = @At("HEAD"), cancellable = true)
    private static void overwriteMaxTextureSize(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(CustomSplashProgress.getMaxTextureSize());
    }

    @Inject(method = "pause", at = @At("HEAD"), cancellable = true)
    private static void overwritePause(CallbackInfo ci) {
        ci.cancel();
        CustomSplashProgress.pause();
    }

    @Inject(method = "resume", at = @At("HEAD"), cancellable = true)
    private static void overwriteResume(CallbackInfo ci) {
        ci.cancel();
        CustomSplashProgress.resume();
    }

    @Inject(method = "finish", at = @At("HEAD"), cancellable = true)
    private static void overwriteFinish(CallbackInfo ci) {
        ci.cancel();
        CustomSplashProgress.finish();
    }

    @Inject(method = "drawVanillaScreen", at = @At("HEAD"), cancellable = true)
    private static void overwriteDrawVanillaScreen(TextureManager renderEngine, CallbackInfo ci) throws LWJGLException {
        ci.cancel();
        CustomSplashProgress.drawVanillaScreen(renderEngine);
    }

    @Inject(method = "clearVanillaResources", at = @At("HEAD"), cancellable = true)
    private static void overwriteClearVanillaResources(TextureManager renderEngine, ResourceLocation mojangLogo, CallbackInfo ci) {
        ci.cancel();
        CustomSplashProgress.clearVanillaResources(renderEngine, mojangLogo);
    }

    @Inject(method = "checkGLError", at = @At("HEAD"), cancellable = true)
    private static void overwriteCheckGLError(String where, CallbackInfo ci) {
        ci.cancel();
        CustomSplashProgress.checkGLError(where);
    }
}

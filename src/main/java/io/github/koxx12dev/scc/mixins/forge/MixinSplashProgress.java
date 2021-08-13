package io.github.koxx12dev.scc.mixins.forge;

import io.github.koxx12dev.scc.utils.Files;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = {SplashProgress.class},priority = Integer.MAX_VALUE)
public abstract class MixinSplashProgress {
    @ModifyVariable(method = "start", at = @At(value = "STORE"), ordinal = 2, remap = false)
    private static ResourceLocation setForgeGif(ResourceLocation resourceLocation) {
        if (Files.hidePetZord()) {
            return resourceLocation;
        } else {
            return new ResourceLocation("scc", "petzord.gif");
        }
    }
}

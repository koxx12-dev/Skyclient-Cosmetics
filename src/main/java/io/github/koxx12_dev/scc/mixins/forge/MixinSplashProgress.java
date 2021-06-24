package io.github.koxx12_dev.scc.mixins.forge;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SplashProgress.class)
public abstract class MixinSplashProgress {
    @ModifyVariable(method = "start", at = @At(value = "STORE"), ordinal = 2, remap = false)
    private static ResourceLocation setForgeGif(ResourceLocation resourceLocation) {
        return new ResourceLocation("scc", "petzord.gif");
    }
}

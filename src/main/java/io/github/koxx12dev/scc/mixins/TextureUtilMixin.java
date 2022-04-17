package io.github.koxx12dev.scc.mixins;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.client.SplashProgress;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.IntBuffer;

@Mixin(TextureUtil.class)
public abstract class TextureUtilMixin {
    @Shadow
    public static void deleteTexture(int textureId) {
    }

    @Shadow
    static void bindTexture(int p_94277_0_) {
    }

    @Inject(method = "allocateTextureImpl", at = @At("HEAD"), cancellable = true)
    private static void overwriteAllocate(int p_180600_0_, int p_180600_1_, int p_180600_2_, int p_180600_3_, CallbackInfo ci) {
        ci.cancel();
        synchronized (SplashProgress.class) {
            deleteTexture(p_180600_0_);
            bindTexture(p_180600_0_);
        }
        if (p_180600_1_ >= 0) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, p_180600_1_);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 0.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, (float) p_180600_1_);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.0F);
        }

        for (int i = 0; i <= p_180600_1_; ++i) {
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, i, GL11.GL_RGBA, p_180600_2_ >> i, p_180600_3_ >> i, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (IntBuffer) null);
        }
    }
}

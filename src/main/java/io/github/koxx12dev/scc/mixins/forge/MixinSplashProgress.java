/*
 * SkyclientCosmetics - Cool cosmetics for a mod installer Skyclient!
 * Copyright (C) koxx12-dev [2021 - 2021]
 *
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found under this url
 * https://github.com/koxx12-dev/Skyclient-Cosmetics
 *
 * If you have a private concern, please contact me on
 * Discord: Koxx12#8061
 */

package io.github.koxx12dev.scc.mixins.forge;

import io.github.koxx12dev.scc.utils.Files;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(value = SplashProgress.class, priority = Integer.MAX_VALUE, remap = false)
public class MixinSplashProgress {
    @ModifyVariable(method = "start", at = @At(value = "STORE"), ordinal = 2)
    private static ResourceLocation setForgeGif(ResourceLocation resourceLocation) {
        if (Files.hidePetLis()) {
            return resourceLocation;
        } else {
            return new ResourceLocation("scc", "petlis.gif");
        }
    }

    /*/
    @ModifyVariable(method = "start", at = @At(value = "STORE"), ordinal = 1)
    private static ResourceLocation setMojangGif(ResourceLocation resourceLocation) {
        if (Files.hidePetLis()) {
            return resourceLocation;
        } else {
            return new ResourceLocation("scc", "skyclient_logo.png");
        }
    }

     */
}

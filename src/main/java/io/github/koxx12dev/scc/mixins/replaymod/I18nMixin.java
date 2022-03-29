package io.github.koxx12dev.scc.mixins.replaymod;

import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(I18n.class)
public class I18nMixin {
    @Inject(method = "format", at = @At("HEAD"), cancellable = true)
    private static void onFormat(String translateKey, Object[] parameters, CallbackInfoReturnable<String> cir) {
        if (translateKey.equals("replaymod.gui.replayviewer")) {
            cir.setReturnValue("");
        }
    }
}

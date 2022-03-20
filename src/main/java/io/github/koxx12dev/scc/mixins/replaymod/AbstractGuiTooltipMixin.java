package io.github.koxx12dev.scc.mixins.replaymod;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Pseudo
@Mixin(targets = "com.replaymod.lib.de.johni0702.minecraft.gui.element.AbstractGuiTooltip")
public class AbstractGuiTooltipMixin {
    @Shadow
    private String[] text = new String[0];

    @Dynamic("From ReplayMod")
    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private void onDraw(@Coerce Object a, @Coerce Object b, @Coerce Object c, CallbackInfo ci) {
        if (text.length == 0 || Arrays.stream(text).anyMatch(String::isEmpty)) ci.cancel();
    }
}

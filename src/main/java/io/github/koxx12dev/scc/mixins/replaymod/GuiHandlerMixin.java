package io.github.koxx12dev.scc.mixins.replaymod;

import com.replaymod.lib.de.johni0702.minecraft.gui.utils.lwjgl.Point;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Pseudo
@Mixin(targets = "com.replaymod.replay.handler.GuiHandler")
public class GuiHandlerMixin {

    @Dynamic("From ReplayMod")
    @Inject(method = "determineButtonPos", at = @At("HEAD"), cancellable = true)
    private void redirectButtonPosition(@Coerce Object a, GuiScreen screen, @Coerce Object c, CallbackInfoReturnable<Point> cir) {
        cir.setReturnValue(new Point(screen.width - 44, screen.height - 35));
    }

    @Dynamic("From ReplayMod")
    @Inject(method = "moveAllButtonsInRect", at = @At("HEAD"), cancellable = true)
    private void redirectMoveButtons(Collection<GuiButton> buttons, int xStart, int xEnd, int yStart, int yEnd, int moveBy, CallbackInfo ci) {
        ci.cancel();
    }
}

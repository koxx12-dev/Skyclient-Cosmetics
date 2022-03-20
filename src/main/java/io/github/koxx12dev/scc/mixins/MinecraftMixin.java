package io.github.koxx12dev.scc.mixins;

import gg.essential.api.EssentialAPI;
import io.github.koxx12dev.scc.gui.SkyClientMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = Integer.MIN_VALUE)
public abstract class MinecraftMixin {
    @Shadow
    public abstract void displayGuiScreen(GuiScreen guiScreenIn);

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    private void onDisplayScreen(GuiScreen i, CallbackInfo ci) {
        if (i instanceof GuiMainMenu && !(i instanceof SkyClientMainMenu) && (EssentialAPI.getOnboardingData().hasAcceptedEssentialTOS() || EssentialAPI.getOnboardingData().hasAcceptedEssentialTOS())) {
            ci.cancel();
            displayGuiScreen(new SkyClientMainMenu());
        }
    }
}

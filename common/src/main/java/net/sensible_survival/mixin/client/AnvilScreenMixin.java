package net.sensible_survival.mixin.client;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.sensible_survival.SensibleSurvivalMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    /// Without this the label still reads "Too Expensive!" over a result the server happily
    /// hands out. Only the local client is fixed — a vanilla client on a modded server sees the
    /// old label but can still take the item.
    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40))
    private int sensible_survival$levelLimit(int constant) {
        return SensibleSurvivalMod.getConfig().anvil_no_level_limit ? Integer.MAX_VALUE : constant;
    }
}

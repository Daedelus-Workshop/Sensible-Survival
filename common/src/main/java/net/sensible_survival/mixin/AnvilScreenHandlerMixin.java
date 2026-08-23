package net.sensible_survival.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.screen.AnvilScreenHandler;
import net.sensible_survival.SensibleSurvivalMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    /// `updateResult` accumulates the prior work penalty of both inputs into its only `long`
    /// local. Zeroing every store to it keeps the penalty out of the price, which also frees
    /// items that were already penalized before this mod was installed.
    @ModifyVariable(method = "updateResult", at = @At("STORE"), ordinal = 0)
    private long sensible_survival$priorWorkPenalty(long value) {
        return SensibleSurvivalMod.getConfig().anvil_no_prior_work_penalty ? 0L : value;
    }

    /// The repair cost stamped onto the result. Kept at 0, so the next use of the same item
    /// costs the same as this one.
    @ModifyExpressionValue(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/screen/AnvilScreenHandler;getNextCost(I)I"))
    private int sensible_survival$nextRepairCost(int original) {
        return SensibleSurvivalMod.getConfig().anvil_no_prior_work_penalty ? 0 : original;
    }

    /// The second `levelCost.get()` of `updateResult` is the `>= 40` gate that empties the
    /// output slot ("Too Expensive!"). Reporting 0 there leaves the result in place; taking it
    /// still goes through `canTakeOutput`, which charges the real, unmodified level cost.
    @ModifyExpressionValue(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/screen/Property;get()I", ordinal = 1))
    private int sensible_survival$levelLimitCheck(int original) {
        return SensibleSurvivalMod.getConfig().anvil_no_level_limit ? 0 : original;
    }
}

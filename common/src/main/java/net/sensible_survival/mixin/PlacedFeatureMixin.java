package net.sensible_survival.mixin;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.sensible_survival.SensibleSurvivalMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlacedFeature.class)
public class PlacedFeatureMixin {
    /// Skipping the placed feature (rather than the `lake_lava` configured feature it shares
    /// with `lake_lava_underground`) is what keeps cave lava untouched.
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void sensible_survival$skipSurfaceLavaLakes(StructureWorldAccess world, ChunkGenerator generator,
                                                        Random random, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!SensibleSurvivalMod.getConfig().no_surface_lava_lakes) {
            return;
        }
        var self = (PlacedFeature)(Object)this;
        var key = world.getRegistryManager().get(RegistryKeys.PLACED_FEATURE).getKey(self).orElse(null);
        if (MiscPlacedFeatures.LAKE_LAVA_SURFACE.equals(key)) {
            cir.setReturnValue(false);
        }
    }
}

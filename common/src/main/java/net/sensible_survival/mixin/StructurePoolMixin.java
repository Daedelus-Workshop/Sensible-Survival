package net.sensible_survival.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.structure.pool.EmptyPoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.math.random.Random;
import net.sensible_survival.SensibleSurvivalMod;
import net.sensible_survival.feature.Villages;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(StructurePool.class)
public class StructurePoolMixin {
    @Shadow @Final private ObjectArrayList<StructurePoolElement> elements;

    /// The piece a structure starts from — for villages, the town center that decides whether
    /// the whole village comes out abandoned. Draws exactly one random number either way, so
    /// pools without zombie pieces generate bit for bit as they did before.
    @Inject(method = "getRandomElement", at = @At("HEAD"), cancellable = true)
    private void sensible_survival$noZombieStart(Random random, CallbackInfoReturnable<StructurePoolElement> cir) {
        if (!SensibleSurvivalMod.getConfig().no_zombie_villages) {
            return;
        }
        var allowed = this.elements.stream().filter(element -> !Villages.isZombieVariant(element)).toList();
        if (allowed.size() == this.elements.size()) {
            return;
        }
        cir.setReturnValue(allowed.isEmpty()
                ? EmptyPoolElement.INSTANCE
                : allowed.get(random.nextInt(allowed.size())));
    }

    /// The pieces attached to every jigsaw block afterwards. Vanilla only reaches zombie pieces
    /// from a zombie town center, but filtering here covers pools other mods may have mixed.
    @ModifyReturnValue(method = "getElementIndicesInRandomOrder", at = @At("RETURN"))
    private List<StructurePoolElement> sensible_survival$noZombiePieces(List<StructurePoolElement> original) {
        if (!SensibleSurvivalMod.getConfig().no_zombie_villages) {
            return original;
        }
        return original.stream().filter(element -> !Villages.isZombieVariant(element)).toList();
    }
}

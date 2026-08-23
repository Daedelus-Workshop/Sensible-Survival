package net.sensible_survival.feature;

import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolElement;
import net.sensible_survival.mixin.SinglePoolElementAccessor;

public class Villages {
    /// Vanilla builds abandoned villages out of a parallel set of pieces living under
    /// `village/<type>/zombie/...`. The variant is decided by the town center that starts the
    /// jigsaw, so keeping those pieces out of the draw is enough to never get one.
    public static boolean isZombieVariant(StructurePoolElement element) {
        if (!(element instanceof SinglePoolElement single)) {
            return false;
        }
        var location = ((SinglePoolElementAccessor)single).sensible_survival$getLocation().left();
        if (location.isEmpty()) {
            return false;
        }
        var path = location.get().getPath();
        return path.startsWith("village/") && path.contains("/zombie/");
    }
}

package net.sensible_survival.neoforge;

import net.neoforged.fml.common.Mod;

import net.sensible_survival.SensibleSurvivalMod;

@Mod(SensibleSurvivalMod.ID)
public final class NeoForgeMod {
    public NeoForgeMod() {
        // Run our common setup.
        SensibleSurvivalMod.init();
    }
}

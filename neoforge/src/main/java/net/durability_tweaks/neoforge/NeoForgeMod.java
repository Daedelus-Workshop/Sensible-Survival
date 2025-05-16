package net.durability_tweaks.neoforge;

import net.neoforged.fml.common.Mod;

import net.durability_tweaks.DurabilityTweaksMod;

@Mod(DurabilityTweaksMod.ID)
public final class NeoForgeMod {
    public NeoForgeMod() {
        // Run our common setup.
        DurabilityTweaksMod.init();
    }
}

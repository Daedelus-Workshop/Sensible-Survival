package net.durability_tweaks.fabric;

import net.fabricmc.api.ModInitializer;

import net.durability_tweaks.DurabilityTweaksMod;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        DurabilityTweaksMod.init();
    }
}

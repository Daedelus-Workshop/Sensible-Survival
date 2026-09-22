package net.sensible_survival.compat;

import net.sensible_survival.Platform;

/// Registers every team system the mod knows how to read. Order carries no meaning — `Teams`
/// unions the answers — but vanilla is listed first because it is the only one that is never
/// optional: if this method stops running, vanilla team support disappears with it.
public class CompatFeatures {
    public static void initialize() {
        VanillaTeamsCompat.init();
        // The mod check belongs here rather than inside the integration: reaching the call at
        // all would load and verify `FTBTeamsCompat`, and verifying it can drag in the FTB
        // types its methods mention. Guarded from the outside, the class is never touched on a
        // server without FTB Teams.
        if (Platform.util().isModLoaded("ftbteams")) {
            FTBTeamsCompat.init();
        }
    }
}

package net.durability_tweaks;

import net.durability_tweaks.config.Config;
import net.tiny_config.ConfigManager;

public final class DurabilityTweaksMod {
    public static final String ID = "durability_tweaks";

    private static ConfigManager<Config> config = new ConfigManager<>
            ("durability_tweaks", new Config())
            .builder()
            // .setDirectory(ID)
            .sanitize(true)
            .build();

    private static boolean initialized = false;
    public static Config getConfig() {
        if (!initialized) {
            config.refresh();
            initialized = true;
        }
        return config.value;
    }

    public static void init() {
        getConfig();
    }
}

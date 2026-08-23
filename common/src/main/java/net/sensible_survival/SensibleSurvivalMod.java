package net.sensible_survival;

import net.sensible_survival.compat.CompatFeatures;
import net.sensible_survival.config.Config;
import net.sensible_survival.config.ConfigMigration;
import net.tiny_config.ConfigManager;

public final class SensibleSurvivalMod {
    public static final String ID = "sensible_survival";

    private static ConfigManager<Config> config = new ConfigManager<>
            (ID, new Config())
            .builder()
            // .setDirectory(ID)
            .sanitize(true)
            .build();

    private static boolean initialized = false;
    public static Config getConfig() {
        if (!initialized) {
            ConfigMigration.migrateLegacyConfig(ID);
            config.refresh();
            initialized = true;
        }
        return config.value;
    }

    public static void init() {
        getConfig();
        CompatFeatures.initialize();
    }
}

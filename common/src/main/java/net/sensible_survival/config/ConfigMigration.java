package net.sensible_survival.config;

import net.sensible_survival.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;

/// This mod was called Durability Tweaks until 1.1.0. Carry the old config file over once,
/// so an update doesn't silently reset everyone's durability settings.
public class ConfigMigration {
    private static final Logger LOGGER = LoggerFactory.getLogger("sensible_survival");
    private static final String LEGACY_CONFIG_NAME = "durability_tweaks.json";

    public static void migrateLegacyConfig(String configName) {
        try {
            var configDir = Platform.util().getConfigDir();
            var current = configDir.resolve(configName + ".json");
            var legacy = configDir.resolve(LEGACY_CONFIG_NAME);
            if (Files.exists(current) || !Files.exists(legacy)) {
                return;
            }
            Files.copy(legacy, current);
            LOGGER.info("Migrated {} to {}", LEGACY_CONFIG_NAME, current.getFileName());
        } catch (Exception e) {
            LOGGER.error("Failed migrating {}: {}", LEGACY_CONFIG_NAME, e.getMessage());
        }
    }
}

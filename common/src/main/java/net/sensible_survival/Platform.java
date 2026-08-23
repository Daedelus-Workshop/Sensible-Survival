package net.sensible_survival;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

/// Loader-neutral hooks. Only what the common code actually needs: mod presence checks for
/// optional integrations (see `net.sensible_survival.compat`) and the config directory.
public class Platform {
    public interface Util {
        boolean isModLoaded(String modid);
        Path getConfigDir();
    }

    @ExpectPlatform
    public static Util util() {
        throw new AssertionError();
    }
}

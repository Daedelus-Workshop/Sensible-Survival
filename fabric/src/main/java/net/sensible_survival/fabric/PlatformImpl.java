package net.sensible_survival.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.sensible_survival.Platform;

import java.nio.file.Path;

public class PlatformImpl {
    public static class FabricUtil implements Platform.Util {
        @Override
        public boolean isModLoaded(String modid) {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        @Override
        public Path getConfigDir() {
            return FabricLoader.getInstance().getConfigDir();
        }
    }

    private static final Platform.Util UTIL = new FabricUtil();
    public static Platform.Util util() {
        return UTIL;
    }
}

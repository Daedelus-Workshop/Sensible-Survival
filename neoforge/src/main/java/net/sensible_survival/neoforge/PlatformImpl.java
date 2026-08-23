package net.sensible_survival.neoforge;

import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.FMLPaths;
import net.sensible_survival.Platform;

import java.nio.file.Path;

public class PlatformImpl {
    public static class NeoForgeUtil implements Platform.Util {
        @Override
        public boolean isModLoaded(String modid) {
            // LoadingModList (not ModList): populated during mod discovery, before any constructor
            // runs, so compat gates resolve at the same time they do on Fabric.
            return LoadingModList.get().getModFileById(modid) != null;
        }

        @Override
        public Path getConfigDir() {
            return FMLPaths.CONFIGDIR.get();
        }
    }

    private static final Platform.Util UTIL = new NeoForgeUtil();
    public static Platform.Util util() {
        return UTIL;
    }
}

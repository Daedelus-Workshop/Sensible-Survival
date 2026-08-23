package net.sensible_survival.teams;

import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/// Team membership across the various team mods. Vanilla scoreboard teams are the built-in
/// fallback; optional integrations (see `net.sensible_survival.compat`) register their own
/// matcher on top.
public class Teams {
    public interface Matcher {
        /// `null` means "no opinion" — the next matcher, and finally vanilla teams, decides.
        @Nullable Boolean areTeammates(PlayerEntity a, PlayerEntity b);
    }

    private static final Map<String, Matcher> matchers = new LinkedHashMap<>();

    public static void registerMatcher(String id, Matcher matcher) {
        matchers.put(id, matcher);
    }

    public static boolean areTeammates(PlayerEntity a, PlayerEntity b) {
        if (a == b) {
            return true;
        }
        for (var matcher : matchers.values()) {
            var result = matcher.areTeammates(a, b);
            if (result != null) {
                return result;
            }
        }
        // Vanilla scoreboard teams. Returns false when either player has no team.
        return a.isTeammate(b);
    }
}

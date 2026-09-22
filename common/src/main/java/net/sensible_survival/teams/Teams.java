package net.sensible_survival.teams;

import net.minecraft.entity.player.PlayerEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/// Team membership across the various team systems a server might run. Every system, vanilla
/// scoreboard teams included, plugs in as a matcher from `net.sensible_survival.compat`.
public class Teams {
    public interface Matcher {
        /// `true` only when this system positively recognises the two as teammates. Anything
        /// else — no opinion, not teammates, system not loaded — is `false`, which leaves the
        /// answer to the remaining systems.
        boolean areTeammates(PlayerEntity a, PlayerEntity b);
    }

    private static final Map<String, Matcher> matchers = new LinkedHashMap<>();

    public static void registerMatcher(String id, Matcher matcher) {
        matchers.put(id, matcher);
    }

    /// Teammates when *any* registered system says so. Servers commonly run more than one, and
    /// players pair up in whichever they happen to use, so the systems are unioned rather than
    /// consulted in order: no matcher can veto another, and the answer never depends on which
    /// integration happened to register first.
    public static boolean areTeammates(PlayerEntity a, PlayerEntity b) {
        if (a == b) {
            return true;
        }
        for (var matcher : matchers.values()) {
            if (matcher.areTeammates(a, b)) {
                return true;
            }
        }
        return false;
    }
}

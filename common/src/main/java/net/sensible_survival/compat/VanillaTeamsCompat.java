package net.sensible_survival.compat;

import net.minecraft.entity.player.PlayerEntity;
import net.sensible_survival.teams.Teams;

/// Vanilla scoreboard teams — `/team add`, `/team join`. Unlike the other integrations there is
/// nothing to detect here: the scoreboard is part of the game, so this one always registers and
/// is the baseline every server can count on.
public class VanillaTeamsCompat {
    public static void init() {
        Teams.registerMatcher("vanilla", VanillaTeamsCompat::areTeammates);
    }

    private static boolean areTeammates(PlayerEntity a, PlayerEntity b) {
        // Looks each player up in the scoreboard by name and compares the two team objects by
        // identity. False whenever either player is on no team at all.
        return a.isTeammate(b);
    }
}

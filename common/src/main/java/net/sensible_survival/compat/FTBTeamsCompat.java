package net.sensible_survival.compat;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import net.minecraft.entity.player.PlayerEntity;
import net.sensible_survival.Platform;
import net.sensible_survival.teams.Teams;

/// Optional FTB Teams integration. The mod is a compile-only dependency: nothing here is
/// touched unless `ftbteams` is actually present, so the class never loads without it.
public class FTBTeamsCompat {
    public static void init() {
        if (!Platform.util().isModLoaded("ftbteams")) {
            return;
        }
        Teams.registerMatcher("ftb", FTBTeamsCompat::areTeammates);
    }

    private static Boolean areTeammates(PlayerEntity a, PlayerEntity b) {
        // Server side only — everything this mod shares (experience) is decided on the server.
        if (a.getWorld().isClient() || !FTBTeamsAPI.api().isManagerLoaded()) {
            return null;
        }
        var manager = FTBTeamsAPI.api().getManager();
        var teamA = manager.getTeamForPlayerID(a.getUuid());
        var teamB = manager.getTeamForPlayerID(b.getUuid());
        if (teamA.isEmpty() || teamB.isEmpty()) {
            return null;
        }
        if (teamA.get().getTeamId().equals(teamB.get().getTeamId())) {
            return true;
        }
        return mutualAllies(teamA.get(), a, teamB.get(), b) ? true : null;
    }

    /// Alliance has to be mutual, otherwise a one-sided "ally" declaration would be enough
    /// to siphon another team's experience.
    private static boolean mutualAllies(Team teamA, PlayerEntity a, Team teamB, PlayerEntity b) {
        return teamA.getRankForPlayer(b.getUuid()).isAllyOrBetter()
                && teamB.getRankForPlayer(a.getUuid()).isAllyOrBetter();
    }
}

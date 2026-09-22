package net.sensible_survival.compat;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamRank;
import net.minecraft.entity.player.PlayerEntity;
import net.sensible_survival.teams.Teams;

/// Optional FTB Teams integration. FTB is a compile-only dependency, so every member of this
/// class may mention types that do not exist at runtime.
///
/// Nothing here — including this class being loaded at all — is safe unless `ftbteams` is
/// actually installed. `CompatFeatures` is what checks; do not call into this class from
/// anywhere that is not behind that check.
public class FTBTeamsCompat {
    public static void init() {
        Teams.registerMatcher("ftb", FTBTeamsCompat::areTeammates);
    }

    private static boolean areTeammates(PlayerEntity a, PlayerEntity b) {
        // Server side only — everything this mod shares (experience) is decided on the server.
        if (a.getWorld().isClient() || !FTBTeamsAPI.api().isManagerLoaded()) {
            return false;
        }
        var manager = FTBTeamsAPI.api().getManager();
        // `getTeamForPlayerID` resolves to the party a player belongs to, falling back to their
        // personal team, and `getTeamId` follows that same redirect — so two players in one
        // party always compare equal here, which `getId` would not guarantee.
        var teamA = manager.getTeamForPlayerID(a.getUuid());
        var teamB = manager.getTeamForPlayerID(b.getUuid());
        if (teamA.isEmpty() || teamB.isEmpty()) {
            return false;
        }
        if (teamA.get().getTeamId().equals(teamB.get().getTeamId())) {
            return true;
        }
        return mutualAllies(teamA.get(), a, teamB.get(), b);
    }

    /// Alliance has to be mutual, otherwise a one-sided "ally" declaration would be enough
    /// to siphon another team's experience.
    private static boolean mutualAllies(Team teamA, PlayerEntity a, Team teamB, PlayerEntity b) {
        return isAlly(teamA.getRankForPlayer(b.getUuid()))
                && isAlly(teamB.getRankForPlayer(a.getUuid()));
    }

    /// Not `TeamRank.isAllyOrBetter()`: ranks run ENEMY < NONE < ALLY < INVITED < MEMBER, and
    /// FTB reports INVITED for *everyone* against a team flagged `free_to_join`. That would
    /// make any two strangers on such a server mutual allies, so INVITED is excluded by name.
    private static boolean isAlly(TeamRank rank) {
        return rank == TeamRank.ALLY || rank.isMemberOrBetter();
    }
}

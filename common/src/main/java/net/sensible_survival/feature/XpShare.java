package net.sensible_survival.feature;

import net.minecraft.entity.player.PlayerEntity;
import net.sensible_survival.SensibleSurvivalMod;
import net.sensible_survival.teams.Teams;

import java.util.ArrayList;
import java.util.List;

/// Splits picked up experience between nearby players.
public class XpShare {
    /// Players the given amount should be split between, always including `picker` itself.
    /// An empty result means "no sharing" — the caller keeps vanilla behaviour.
    public static List<PlayerEntity> recipients(PlayerEntity picker) {
        var config = SensibleSurvivalMod.getConfig();
        if (!config.xp_share_enabled || config.xp_share_radius <= 0) {
            return List.of();
        }
        var radiusSquared = config.xp_share_radius * config.xp_share_radius;
        var recipients = new ArrayList<PlayerEntity>();
        for (var other : picker.getWorld().getPlayers()) {
            if (other == picker) {
                continue;
            }
            if (other.isSpectator() || !other.isAlive()) {
                continue;
            }
            if (other.squaredDistanceTo(picker) > radiusSquared) {
                continue;
            }
            if (config.xp_share_teammates_only && !Teams.areTeammates(picker, other)) {
                continue;
            }
            recipients.add(other);
        }
        if (recipients.isEmpty()) {
            return List.of();
        }
        recipients.add(picker);
        return recipients;
    }

    /// Who gets the experience left over once the amount is split evenly. Spreading it around
    /// keeps the player who happens to walk into the orbs from slowly pulling ahead.
    public static PlayerEntity remainderRecipient(List<PlayerEntity> recipients, PlayerEntity picker) {
        if (!SensibleSurvivalMod.getConfig().xp_share_random_remainder) {
            return picker;
        }
        return recipients.get(picker.getRandom().nextInt(recipients.size()));
    }
}

package net.sensible_survival.config;

public class Config {
    // MARK: Equipment durability

    public float equipment_takes_damage_chance = 1f;
    public int equipment_damage_cap = 2;
    public float equipment_damage_multiplier = 0.5f;
    public float shield_takes_damage_chance = 1f;
    public int shield_damage_cap = 4;
    public float shield_damage_multiplier = 0.5f;

    // MARK: Anvil

    /// Repairing/enchanting an item no longer doubles its future anvil cost,
    /// and existing "prior work" penalties stop being charged.
    public boolean anvil_no_prior_work_penalty = true;
    /// Removes the "Too Expensive!" wall, so any anvil result can be taken
    /// as long as the player has the levels to pay for it.
    public boolean anvil_no_level_limit = true;

    // MARK: Fixes

    /// Losing max health (for example unequipping a shield that grants it) clamps current
    /// health, which vanilla renders as a hurt flash. Cosmetic only, client side.
    public boolean no_hurt_effect_on_max_health_loss = true;

    // MARK: Experience sharing

    /// Experience picked up by a player is split evenly with nearby players.
    public boolean xp_share_enabled = true;
    /// Maximum distance (blocks) between the player picking up the orb and a player sharing it.
    public double xp_share_radius = 64;
    /// Only share with players on the same team. Supports vanilla scoreboard teams and,
    /// when installed, FTB Teams. Set to `false` to share with every nearby player.
    public boolean xp_share_teammates_only = true;
    /// Experience rarely divides evenly. The leftover goes to a random player of the group.
    /// Set to `false` to always leave it with the player who picked the orb up.
    public boolean xp_share_random_remainder = true;

    // MARK: World generation
    // These only affect chunks generated after the change.

    /// Stops lava lakes from generating on the surface. Caves keep their lava.
    public boolean no_surface_lava_lakes = false;
    /// Stops villages from generating in their abandoned "zombie village" variant,
    /// the one with cobwebs instead of intact houses.
    public boolean no_zombie_villages = false;
}

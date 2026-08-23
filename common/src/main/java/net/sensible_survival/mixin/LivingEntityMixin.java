package net.sensible_survival.mixin;

import net.sensible_survival.SensibleSurvivalMod;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private static final Random DURABILITY_RNG = new Random();
    @ModifyVariable(method = "damageEquipment", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyExhaustion(float amount) {
        var config = SensibleSurvivalMod.getConfig();
        if (config.equipment_takes_damage_chance < 1F
                && DURABILITY_RNG.nextFloat() > config.equipment_takes_damage_chance) {
            return 0F;
        }
        amount *= config.equipment_damage_multiplier;
        if (config.equipment_damage_cap >= 0) {
            // Multiply by 4 because the amount is divided by 4 in the followup code
            // (to split the damage between all pieces of equipment)
            amount = Math.min(amount, config.equipment_damage_cap * 4);
        }
        return amount;
    }
}

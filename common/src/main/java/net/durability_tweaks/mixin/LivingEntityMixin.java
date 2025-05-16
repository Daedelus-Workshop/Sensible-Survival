package net.durability_tweaks.mixin;

import net.durability_tweaks.DurabilityTweaksMod;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private static final Random RANDOM = new Random();
    @ModifyVariable(method = "damageEquipment", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyExhaustion(float amount) {
        var config = DurabilityTweaksMod.getConfig();
        if (config.equipment_takes_damage_chance < 1f) {
            if (RANDOM.nextFloat() > config.equipment_takes_damage_chance) {
                return 0F;
            }
        }
        amount *= config.equipment_damage_multiplier;
        if (config.equipment_damage_cap >= 0) {
            amount = Math.min(amount, config.equipment_damage_cap * 4);
        }
        return amount;
    }
}

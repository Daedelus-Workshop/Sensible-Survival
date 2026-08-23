package net.sensible_survival.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.sensible_survival.SensibleSurvivalMod;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Unique
    private static final Random DURABILITY_RNG = new Random();
    @WrapOperation(
            method = "damageShield",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"),
            require = 0 // Funnily NeoForge rewrites the whole function, so the target funciton wont be found, ggwp
    )
    private void damageShield_damageStack(ItemStack instance, int amount, LivingEntity entity, EquipmentSlot slot, Operation<Void> original) {
        var config = SensibleSurvivalMod.getConfig();
        if (config.shield_takes_damage_chance < 1F
                && DURABILITY_RNG.nextFloat() > config.shield_takes_damage_chance) {
            amount = 0;
        }
        amount = Math.round(amount * config.shield_damage_multiplier);
        if (config.shield_damage_cap >= 0) {
            amount = Math.min(amount, config.shield_damage_cap);
        }
        original.call(instance, amount, entity, slot);
    }
}

package net.sensible_survival.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.sensible_survival.feature.XpShare;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    /// Split the orb between the player who picked it up and the nearby players it is shared
    /// with. Mending still repairs the gear of whoever actually walked into the orb.
    @WrapOperation(method = "onPlayerCollision", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"))
    private void sensible_survival$shareExperience(PlayerEntity player, int amount, Operation<Void> original) {
        var recipients = XpShare.recipients(player);
        if (amount <= 0 || recipients.size() < 2) {
            original.call(player, amount);
            return;
        }
        var share = amount / recipients.size();
        // Whatever doesn't divide evenly is handed to one of the group in full, so no
        // experience is ever lost to rounding.
        var remainder = amount - share * recipients.size();
        var remainderRecipient = XpShare.remainderRecipient(recipients, player);
        for (var recipient : recipients) {
            var give = recipient == remainderRecipient ? share + remainder : share;
            if (give <= 0) {
                continue;
            }
            if (recipient == player) {
                original.call(player, give);
            } else {
                recipient.addExperience(give);
            }
        }
    }
}

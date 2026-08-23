package net.sensible_survival.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.sensible_survival.SensibleSurvivalMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow private boolean healthInitialized;

    /// Vanilla flashes the screen for any health drop reported by the server, including drops
    /// that aren't damage at all — most visibly the clamp that happens when a max health bonus
    /// goes away, such as unequipping a shield that grants it.
    ///
    /// Actual damage doesn't need this flash: the server sends the damaged player their own
    /// `EntityDamageS2CPacket`, and `LivingEntity.onDamaged` sets the very same hurt timer
    /// (plus the hurt sound). So health loss is applied here without the visuals, and real hits
    /// still look exactly like they did.
    ///
    /// The one hit vanilla doesn't send that packet for is a stronger blow landing during
    /// invulnerability frames — which vanilla deliberately doesn't restart the hurt animation
    /// for either, so it goes unflashed here as well.
    @Inject(method = "updateHealth", at = @At("HEAD"), cancellable = true)
    private void sensible_survival$silentHealthLoss(float health, CallbackInfo ci) {
        if (!SensibleSurvivalMod.getConfig().no_hurt_effect_on_max_health_loss || !this.healthInitialized) {
            return;
        }
        var self = (ClientPlayerEntity)(Object)this;
        if (self.getHealth() <= health) {
            return; // Healing — vanilla path, nothing to suppress.
        }
        // `lastDamageTaken` / `timeUntilRegen` are deliberately left alone: LivingEntity.damage
        // returns early on the client, so nothing reads them here.
        self.setHealth(health);
        ci.cancel();
    }
}

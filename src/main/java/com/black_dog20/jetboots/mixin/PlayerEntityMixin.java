package com.black_dog20.jetboots.mixin;

import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Shadow
    public void awardStat(ResourceLocation p_36223_, int p_36224_) {}

    @Inject(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void onLivingFallDamage(float distance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> ci) {
        if (((Object)this) instanceof Player player) {
            ItemStack boots = ModUtils.getJetBoots(player);

            if(!boots.isEmpty() && !JetBootsProperties.hasShockUpgrade(boots)) {
                if (distance >= 2.0F) {
                    this.awardStat(Stats.FALL_ONE_CM, (int) Math.round((double) distance * 100.0D));
                }

                ci.setReturnValue(super.causeFallDamage(distance, damageMultiplier, damageSource));
                ci.cancel();
            } else if(!boots.isEmpty() && JetBootsProperties.hasShockUpgrade(boots)) {
                ci.setReturnValue(false);
                ci.cancel();
            }
        }
    }
}
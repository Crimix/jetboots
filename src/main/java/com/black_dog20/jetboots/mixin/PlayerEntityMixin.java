package com.black_dog20.jetboots.mixin;

import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Shadow
    public void addStat(ResourceLocation p_195067_1_, int p_195067_2_) {}

    @Inject(method = "onLivingFall(FF)Z", at = @At("HEAD"), cancellable = true)
    private void onLivingFallDamage(float distance, float damageMultiplier, CallbackInfoReturnable<Boolean> ci) {
        if (((Object)this) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            ItemStack boots = ModUtils.getJetBoots(player);

            if(!boots.isEmpty() && !JetBootsProperties.hasShockUpgrade(boots)) {
                if (distance >= 2.0F) {
                    this.addStat(Stats.FALL_ONE_CM, (int) Math.round((double) distance * 100.0D));
                }

                ci.setReturnValue(super.onLivingFall(distance, damageMultiplier));
                ci.cancel();
            } else if(!boots.isEmpty() && JetBootsProperties.hasShockUpgrade(boots)) {
                ci.setReturnValue(false);
                ci.cancel();
            }
        }
    }
}

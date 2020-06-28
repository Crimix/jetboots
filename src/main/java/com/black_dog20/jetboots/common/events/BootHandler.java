package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.PlayerMoveEvent;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class BootHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerAttack(LivingAttackEvent event) {
        if (event.getSource().equals(DamageSource.OUT_OF_WORLD))
            return;
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (ModUtils.hasJetBoots(player)) {
                ItemStack jetboots = ModUtils.getJetBoots(player);

                jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                        .ifPresent(e -> EnergyUtil.extractOrReceive(e, EnergyUtil.getEnergyOnHit(jetboots)));
                IArmorUpgrade armorUpgrade = JetBootsProperties.getArmorUpgrade(jetboots)
                        .filter(a -> a.getArmorUpgradeType() == IArmorUpgrade.ArmorType.PERCENTAGE_REDUCTION)
                        .orElse(null);

                if (armorUpgrade != null && armorUpgrade.providesProtection(jetboots) && armorUpgrade.protectAgainst(event.getSource())) {
                    double reduction = MathUtil.clamp(armorUpgrade.getPercentageDamageReduction(), 0.0, 1.0);
                    float amount = (float) (Math.round(event.getAmount() * reduction));
                    if (amount >= 0.5) {
                        return;
                    } else {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getSource().equals(DamageSource.OUT_OF_WORLD))
            return;
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (ModUtils.hasJetBoots(player)) {
                ItemStack jetboots = ModUtils.getJetBoots(player);

                IArmorUpgrade armorUpgrade = JetBootsProperties.getArmorUpgrade(jetboots)
                        .filter(a -> a.getArmorUpgradeType() == IArmorUpgrade.ArmorType.PERCENTAGE_REDUCTION)
                        .orElse(null);

                if (armorUpgrade != null && armorUpgrade.providesProtection(jetboots) && armorUpgrade.protectAgainst(event.getSource())) {
                    double reduction = MathUtil.clamp(armorUpgrade.getPercentageDamageReduction(), 0.0, 1.0);
                    float amount = (float) (Math.round(event.getAmount() * reduction));
                    if (amount >= 0.5) {
                        event.setAmount(amount);
                        event.getSource().setDamageBypassesArmor();
                    } else {
                        event.setCanceled(true);
                    }
                }
                jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                        .ifPresent(e -> EnergyUtil.extractOrReceive(e, EnergyUtil.getEnergyOnHurt(jetboots)));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerKnockback(LivingKnockBackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (ModUtils.hasJetBoots(player)) {
                ItemStack jetboots = ModUtils.getJetBoots(player);
                IArmorUpgrade armorUpgrade = JetBootsProperties.getArmorUpgrade(jetboots).orElse(null);

                if (armorUpgrade != null) {
                    double reduction = MathUtil.clamp(armorUpgrade.getKnockBackReduction(), 0.0, 1.0);
                    float strength = (float) (event.getStrength() * reduction);
                    if (strength >= 0.1) {
                        event.setStrength(strength);
                    } else {
                        event.setCanceled(true);
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void onPlayerMove(PlayerMoveEvent event) {
        if (event.getDistance() < 0.18)
            return;
        if (!ModUtils.hasJetBoots(event.getPlayer()))
            return;
        PlayerEntity playerEntity = event.getPlayer();
        ItemStack jetboots = ModUtils.getJetBoots(playerEntity);
        jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                .ifPresent(e -> EnergyUtil.extractOrReceive(e, EnergyUtil.getEnergyWhileWalking(jetboots)));
    }

}

package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IEnergyCostModifier;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.JetbootsUtil;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class EventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (JetbootsUtil.hasJetBoots(player)) {
                ItemStack jetboots = JetbootsUtil.getJetBoots(player);
                IArmorUpgrade armorUpgrade = JetBootsProperties.getArmorUpgrade(jetboots)
                        .filter(a -> a.getArmorUpgradeType() == IArmorUpgrade.ArmorType.PERCENTAGE_REDUCTION)
                        .orElse(null);

                if (armorUpgrade != null && armorUpgrade.providesProtection(jetboots) && armorUpgrade.protectAgainst(event.getSource())) {
                    double reduction = MathUtil.clamp(armorUpgrade.getPercentageDamageReduction(), 0.0, 1.0);
                    float amount = (float) (Math.round(event.getAmount() * reduction));
                    if (amount > 0) {
                        event.setAmount(amount);
                        event.getSource().setDamageBypassesArmor();
                    } else {
                        event.setCanceled(true);
                    }
                }
                if (Config.USE_POWER.get()) {
                    IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                            .orElse(null);
                    if (energy != null) {
                        int cost = (int) Math.round(JetBootsProperties.getEnergyModifiers(jetboots).stream()
                                .filter(e -> e.getType() == IEnergyCostModifier.ModifierType.ON_HURT)
                                .map(IEnergyCostModifier::getEnergyCostModifier)
                                .reduce(0.0, Double::sum));
                        energy.extractEnergy(cost, false);
                    }
                }

            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerKnockback(LivingKnockBackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (JetbootsUtil.hasJetBoots(player)) {
                ItemStack jetboots = JetbootsUtil.getJetBoots(player);
                IArmorUpgrade armorUpgrade = JetBootsProperties.getArmorUpgrade(jetboots).orElse(null);

                if (armorUpgrade != null){
                    double reduction = MathUtil.clamp(armorUpgrade.getKnockBackReduction(), 0.0, 1.0);
                    float strength = (float) (event.getStrength() * reduction);
                    event.setStrength(strength);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onPlayerHit(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && Config.USE_POWER.get()) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (JetbootsUtil.hasJetBoots(player)) {
                ItemStack jetboots = JetbootsUtil.getJetBoots(player);

                IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                        .orElse(null);
                if (energy != null) {
                    int cost = (int) Math.round(JetBootsProperties.getEnergyModifiers(jetboots).stream()
                            .filter(e -> e.getType() == IEnergyCostModifier.ModifierType.ON_HIT)
                            .map(IEnergyCostModifier::getEnergyCostModifier)
                            .reduce(0.0, Double::sum));
                    energy.extractEnergy(cost, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTick(ArmorEvent.Tick event) {
        if (Config.USE_POWER.get() && event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            ItemStack jetboots = event.armor;
            IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                    .orElse(null);
            if (energy != null) {
                int cost = (int) Math.round(JetBootsProperties.getEnergyModifiers(jetboots).stream()
                        .filter(e -> e.getType() == IEnergyCostModifier.ModifierType.ON_TICK)
                        .map(IEnergyCostModifier::getEnergyCostModifier)
                        .reduce(0.0, Double::sum));
                energy.extractEnergy(cost, false);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e) {
        if (e.getItemStack().isEmpty())
            return;
        if (e.getItemStack().getItem() instanceof IEnergyCostModifier) {
            List<ITextComponent> tooltips = e.getToolTip();
            IEnergyCostModifier modifier = (IEnergyCostModifier) e.getItemStack().getItem();
            if (modifier.getType() == null)
                return;
            switch (modifier.getType()) {
                case ON_USE:
                    tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.ENERGY_COST_USE, modifier.getEnergyCostModifier()));
                    break;
                case ON_HURT:
                    tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.ENERGY_COST_HURT, modifier.getEnergyCostModifier()));
                    break;
                case ON_HIT:
                    tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.ENERGY_COST_HIT, modifier.getEnergyCostModifier()));
                    break;
                case ON_TICK:
                    tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.ENERGY_COST_TIC, modifier.getEnergyCostModifier()));
                    break;
            }
        }
    }
}

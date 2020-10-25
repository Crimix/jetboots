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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class BootHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerKnockback(LivingKnockBackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (ModUtils.hasJetBoots(player)) {
                ItemStack jetboots = ModUtils.getJetBoots(player);
                LazyOptional<IArmorUpgrade> armorUpgrade = JetBootsProperties.getArmorUpgrade(jetboots);

                armorUpgrade.ifPresent(upgrade -> {
                    double reduction = MathUtil.clamp(upgrade.getKnockBackReduction(), 0.0, 1.0);
                    float strength = (float) (event.getStrength() * reduction);
                    if (strength >= 0.1) {
                        event.setStrength(strength);
                    } else {
                        event.setCanceled(true);
                    }
                });
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
        if(!playerEntity.isCreative()) {
            ItemStack jetboots = ModUtils.getJetBoots(playerEntity);
            jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                    .ifPresent(e -> EnergyUtil.extractOrReceive(e, EnergyUtil.getEnergyWhileWalking(jetboots)));
        }
    }

}

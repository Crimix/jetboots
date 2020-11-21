package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.PlayerMoveEvent;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class BootHandler {

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

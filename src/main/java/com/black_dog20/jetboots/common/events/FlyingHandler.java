package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.bml.utils.player.MovementUtil;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketSyncPartical;
import com.black_dog20.jetboots.common.network.packets.PacketSyncSound;
import com.black_dog20.jetboots.common.network.packets.PacketSyncStopSound;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import com.black_dog20.jetboots.common.util.properties.RocketBootsProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class FlyingHandler {

    @SubscribeEvent
    public static void onEquipJetboots(ArmorEvent.Equip event) {
        Player player = event.player;
        if (event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            if (ModUtils.canFlyWithJetboots(player)) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onJetbootsTick(ArmorEvent.Tick event) {
        Player player = event.player;
        ItemStack jetboots = event.armor;
        if (event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            // To render elytra flight correct
            if (event.phase == TickEvent.Phase.END) {
                if (ModUtils.canUseElytraFlight(player)) {
                    player.startFallFlying(); // Start elytra flight pose
                }
                return;
            }

            if (ModUtils.canFlyWithJetboots(player)) {
                // Enable allow flying if not using elytra flight
                if (!player.getAbilities().mayfly && !ModUtils.useElytraFlight(player, jetboots) && !player.isFallFlying()) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }

                if (ModUtils.useElytraFlight(player, jetboots)) {
                    if (event.side == LogicalSide.SERVER)
                        drawpower(player, jetboots);
                    doElytraFlight(player, jetboots);
                } else if (player.isFallFlying()) {
                    stopElytraFlight(player);
                } else if (player.getAbilities().flying && event.side == LogicalSide.SERVER) {
                    drawpower(player,jetboots);
                }
                sendSoundAndPartical(event, player, jetboots);
            } else {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
                if (player.isFallFlying())
                    stopElytraFlight(player);
                if (event.side == LogicalSide.SERVER)
                    PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
            }
        }
    }

    @SubscribeEvent
    public static void onUnequipJetboots(ArmorEvent.Unequip event) {
        Player player = event.player;
        if (event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            stopElytraFlight(player);
            PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
            if (!player.isCreative()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();

        }
    }

    private static void sendSoundAndPartical(ArmorEvent.Tick event, Player player, ItemStack jetboots) {
        if (event.side == LogicalSide.SERVER) {
            if (ModUtils.isJetbootsFlying(player) && ModUtils.canFlyWithJetboots(player)) {
                PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncPartical(player.getUUID(), player.getAbilities().flying));
                if (!JetBootsProperties.hasActiveMuffledUpgrade(jetboots)) {
                    if (!player.isInWater()) {
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncSound(player.getUUID()));
                    } else {
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
                    }
                }
            } else {
                PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
            }
        }
    }

    private static void stopElytraFlight(Player player) {
        player.stopFallFlying(); // Stop elytra flight pose
        player.getAbilities().mayfly = true;
        if (ModUtils.isBlocksOverGround(player, 2.9, 4)) {
            player.getAbilities().flying = true;
        }
        player.onUpdateAbilities();
    }

    private static void doElytraFlight(Player player, ItemStack jetboots) {
        // Disable flying
        if (player.getAbilities().mayfly || player.getAbilities().flying) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
        player.startFallFlying(); // Start elytra flight pose
        double speed = getSpeed(player, jetboots);
        MovementUtil.speedUpElytraFlight(player, speed,  0.1D, 0.5D);
    }

    private static void drawpower(Player player, ItemStack jetboots) {
        if (!player.isCreative()) {
            jetboots.getCapability(ForgeCapabilities.ENERGY, null)
                    .ifPresent(e -> EnergyUtil.extractOrReceive(e, EnergyUtil.getEnergyWhileFlying(jetboots)));
        }
    }

    private static double getSpeed(Player player, ItemStack jetboots) {
        boolean inWater = player.isInWater();

        if (JetBootsProperties.hasThrusterUpgrade(jetboots) && JetBootsProperties.getSpeed(jetboots))
            return inWater ? 1.5D : 3D;
        else
            return inWater ? 0.5D : 1.5D;
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModUtils.hasJetBoots(player) && JetBootsProperties.hasShockUpgrade(ModUtils.getJetBoots(player))) {
                event.setCanceled(true);
            }

            if (ModUtils.hasRocketBoots(player) && RocketBootsProperties.hasShockUpgrade(ModUtils.getRocketBoots(player))) {
                event.setCanceled(true);
            }
        }
    }
}

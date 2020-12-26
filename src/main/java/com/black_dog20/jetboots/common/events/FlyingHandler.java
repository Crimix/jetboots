package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketSyncPartical;
import com.black_dog20.jetboots.common.network.packets.PacketSyncSound;
import com.black_dog20.jetboots.common.network.packets.PacketSyncStopSound;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.black_dog20.jetboots.common.util.NBTTags.*;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class FlyingHandler {

    @SubscribeEvent
    public static void onEquipJetboots(ArmorEvent.Equip event) {
        PlayerEntity player = event.player;
        if (event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            if (ModUtils.canFlyWithBoots(player)) {
                player.abilities.allowFlying = true;
                player.sendPlayerAbilities();
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onJetbootsTick(ArmorEvent.Tick event) {
        PlayerEntity player = event.player;
        ItemStack jetboots = event.armor;
        if (event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            // To render elytra flight correct
            if (event.phase == TickEvent.Phase.END) {
                if (ModUtils.canUseElytraFlight(player)) {
                    player.startFallFlying(); // Start elytra flight pose
                }
                return;
            }

            if (ModUtils.canFlyWithBoots(player)) {
                // Enable allow flying if not using elytra flight
                if (!player.abilities.allowFlying && !ModUtils.useElytraFlight(player, jetboots) && !player.isElytraFlying()) {
                    player.abilities.allowFlying = true;
                    player.sendPlayerAbilities();
                }

                if (ModUtils.useElytraFlight(player, jetboots)) {
                    if (event.side == LogicalSide.SERVER)
                        drawpower(player, jetboots);
                    doElytraFlight(player, jetboots);
                } else if (player.isElytraFlying()) {
                    stopElytraFlight(player);
                } else if (player.abilities.isFlying && event.side == LogicalSide.SERVER) {
                    drawpower(player,jetboots);
                }
                sendSoundAndPartical(event, player, jetboots);
            } else {
                player.abilities.allowFlying = false;
                player.abilities.isFlying = false;
                player.sendPlayerAbilities();
                if (player.isElytraFlying())
                    stopElytraFlight(player);
                if (event.side == LogicalSide.SERVER)
                    PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUniqueID()));
            }
        }
    }

    @SubscribeEvent
    public static void onUnequipJetboots(ArmorEvent.Unequip event) {
        PlayerEntity player = event.player;
        if (event.isArmorEqualTo(ModItems.JET_BOOTS.get())) {
            stopElytraFlight(player);
            PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUniqueID()));
            player.abilities.allowFlying = false;
            player.abilities.isFlying = false;
            player.sendPlayerAbilities();

        }
    }

    private static void sendSoundAndPartical(ArmorEvent.Tick event, PlayerEntity player, ItemStack jetboots) {
        if (event.side == LogicalSide.SERVER) {
            if (ModUtils.isFlying(player)) {
                PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncPartical(player.getUniqueID(), player.abilities.isFlying));
                if (!JetBootsProperties.hasActiveMuffledUpgrade(jetboots)) {
                    if (!player.isInWater()) {
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncSound(player.getUniqueID()));
                    } else {
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUniqueID()));
                    }
                }
            } else {
                PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUniqueID()));
            }
        }
    }

    private static void stopElytraFlight(PlayerEntity player) {
        player.stopFallFlying(); // Stop elytra flight pose
        player.abilities.allowFlying = true;
        if (player.getPersistentData().getBoolean(WAS_FLYING_BEFORE)) {
            player.abilities.isFlying = true;
            player.getPersistentData().remove(WAS_FLYING_BEFORE);
        }
        player.sendPlayerAbilities();
    }

    private static void doElytraFlight(PlayerEntity player, ItemStack jetboots) {
        // Disable flying
        if (player.abilities.allowFlying || player.abilities.isFlying) {
            player.getPersistentData().putBoolean(WAS_FLYING_BEFORE, player.abilities.isFlying);
            player.abilities.allowFlying = false;
            player.abilities.isFlying = false;
            player.sendPlayerAbilities();
        }
        player.startFallFlying(); // Start elytra flight pose
        Vector3d vec3d = player.getLookVec();
        double d0 = 1.5D;
        double d1 = 0.1D;
        double d2 = 0.5D;
        double d3 = 3D;
        Vector3d vec3d1 = player.getMotion();
        double speed = getSpeed(player, jetboots);
        player.setMotion(vec3d1.add(vec3d.x * d1 + (vec3d.x * speed - vec3d1.x) * d2, vec3d.y * d1 + (vec3d.y * speed - vec3d1.y) * d2, vec3d.z * d1 + (vec3d.z * speed - vec3d1.z) * d2));
    }

    private static void drawpower(PlayerEntity player, ItemStack jetboots) {
        if(!player.isCreative()) {
            jetboots.getCapability(CapabilityEnergy.ENERGY, null)
                    .ifPresent(e -> EnergyUtil.extractOrReceive(e, EnergyUtil.getEnergyWhileFlying(jetboots)));
        }
    }

    private static double getSpeed(PlayerEntity player, ItemStack jetboots) {
        boolean inWater = player.isInWater();

        if (JetBootsProperties.hasThrusterUpgrade(jetboots) && JetBootsProperties.getSpeed(jetboots))
            return inWater ? 1.5D : 3D;
        else
            return inWater ? 0.5D : 1.5D;
    }
}

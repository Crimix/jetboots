package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.player.MovementUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketAwardRocketBootXp;
import com.black_dog20.jetboots.common.network.packets.PacketSyncRocketFlight;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.RocketBootsProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class RocketFlightHandler {

    public static Map<UUID, RocketBootsPower> playerGlidePowerMap = new ConcurrentHashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGlidebootsTick(ArmorEvent.Tick event) {
        Player player = event.player;
        if (event.isArmorEqualTo(ModItems.ROCKET_BOOTS.get())) {
            if (event.phase == TickEvent.Phase.END) {
                RocketBootsPower rocketBootsPower = playerGlidePowerMap.computeIfAbsent(player.getUUID(), RocketBootsPower::new);
                rocketBootsPower.cooldown--;
                if (rocketBootsPower.cooldown < 0) {
                    rocketBootsPower.resetCoolDown();
                } else {
                    rocketBootsPower.tick(player);
                }
            }
        }
    }


    public static class RocketBootsPower {
        private final UUID uuid;
        public int cooldown = 20;
        private int tickFlight = 0;
        private int xpCounter = 0;

        public RocketBootsPower(UUID uuid) {
            this.uuid = uuid;
        }

        public UUID getUuid() {
            return uuid;
        }

        public int getMaxFlightTime(ItemStack stack) {
            return (int) (ItemLevelProperties.calculateValue(Config.ROCKETBOOTS_BASE_FLIGHT_TIME.get(), Config.ROCKETBOOTS_MAX_FLIGHT_TIME.get(), stack) * 2);
        }

        public int getTickFlight() {
            return tickFlight;
        }

        public void resetCoolDown() {
            cooldown = 20;
        }

        public void tick(Player player) {
            if (player == null) return;
            if (!ModUtils.hasRocketBoots(player)) return;
            ItemStack rocketboots = ModUtils.getRocketBoots(player);
            if (!RocketBootsProperties.getEngineState(rocketboots)) return;

            boolean onGround = player.onGround();
            boolean isJumping = Minecraft.getInstance().options.keyJump.isDown();

            if (!onGround && isJumping && !player.isInWater()) {
                if (tickFlight < getMaxFlightTime(rocketboots)) {
                    tickFlight++;
                    xpCounter++;
                    player.fallDistance = 0.0F;
                    double motion_y = player.getDeltaMovement().y;
                    MovementUtil.jetpackFly(player, motion_y * 0.9 + 0.1);
                    MovementUtil.speedUp(player, 0.03D, 1);
                    PacketHandler.sendToServer(new PacketSyncRocketFlight(true));
                } else {
                    PacketHandler.sendToServer(new PacketSyncRocketFlight(false));
                    awardXp();
                }
            }

            if (!isJumping || player.isInWater()) {
                if (tickFlight > 0) {
                    tickFlight--;
                    PacketHandler.sendToServer(new PacketSyncRocketFlight(false));
                    awardXp();
                }
            }
        }

        private void awardXp() {
            if (xpCounter != 0) {
                double xp = MathUtil.clamp(xpCounter * Config.ROCKET_XP_MODIFIER.get(), Config.ROCKET_XP_MIN.get(), Config.ROCKET_XP_MAX.get());
                PacketHandler.sendToServer(new PacketAwardRocketBootXp((int) xp));
                xpCounter = 0;
            }
        }
    }

}

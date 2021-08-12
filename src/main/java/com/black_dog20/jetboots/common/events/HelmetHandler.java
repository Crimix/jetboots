package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.GuardinanHelmetProperties;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static com.black_dog20.jetboots.common.util.NBTTags.USING_NIGHT_VISION;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class HelmetHandler {

    private static final Set<DamageSource> HELMET_SOURCES = ImmutableSet.of(DamageSource.DROWN, DamageSource.WITHER);
    private static final Set<MobEffect> HELMET_EFFECTS_REMOVED = ImmutableSet.of(MobEffects.WITHER);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerAttack(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();

            if (ModUtils.hasGuardianHelmet(player)) {
                ItemStack helmet = ModUtils.getGuardianHelmet(player);
                if (GuardinanHelmetProperties.getMode(helmet) && HELMET_SOURCES.contains(event.getSource())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();

            if (ModUtils.hasGuardianHelmet(player)) {
                ItemStack helmet = ModUtils.getGuardianHelmet(player);
                if (GuardinanHelmetProperties.getMode(helmet) && HELMET_SOURCES.contains(event.getSource())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdatePlayer(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof Player && !event.getEntity().level.isClientSide) {
            Player player = (Player) event.getEntity();

            if (ModUtils.hasGuardianHelmet(player) && GuardinanHelmetProperties.getMode(ModUtils.getGuardianHelmet(player))) {
                if (player.isInWater()) {
                    player.setAirSupply(player.getMaxAirSupply());
                }

                HELMET_EFFECTS_REMOVED.stream()
                        .filter(player::hasEffect)
                        .forEach(player::removeEffect);
            }
        }
    }

    @SubscribeEvent
    public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack tool = player.getMainHandItem();
            float toolSpeed = tool.getItem().getDestroySpeed(tool, event.getState());
            if (ModUtils.hasGuardianHelmet(player) && player.isInWater()) {
                if (toolSpeed > event.getOriginalSpeed())
                    if (player.isOnGround())
                        event.setNewSpeed(event.getOriginalSpeed() * 5);
                    else
                        event.setNewSpeed(event.getOriginalSpeed() * 25);
            } else if (ModUtils.isJetbootsFlying(player) && !player.isOnGround()) {
                if (toolSpeed > event.getOriginalSpeed())
                    event.setNewSpeed(event.getOriginalSpeed() * 5);
            }
        }
    }

    @SubscribeEvent
    public static void onUnequipHelmet(ArmorEvent.Unequip event) {
        Player player = event.player;
        ItemStack helmet = event.armor;
        if (event.isArmorEqualTo(ModItems.GUARDIAN_HELMET.get())) {
            if (GuardinanHelmetProperties.getNightVision(helmet))
                player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    @SubscribeEvent
    public static void onHelmetTick(ArmorEvent.Tick event) {
        Player player = event.player;
        ItemStack helmet = event.armor;
        if(event.side.isClient())
            return;
        if (event.isArmorEqualTo(ModItems.GUARDIAN_HELMET.get())) {
            if (GuardinanHelmetProperties.getNightVision(helmet) && GuardinanHelmetProperties.getMode(helmet)) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 32767, 0, false, false, false));
                player.getPersistentData().putBoolean(USING_NIGHT_VISION, true);
            } else {
                if (player.getPersistentData().getBoolean(USING_NIGHT_VISION)) {
                    player.getPersistentData().remove(USING_NIGHT_VISION);
                    player.removeEffect(MobEffects.NIGHT_VISION);
                }
            }
        }
    }
}

package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class HelmetHandler {

    private final static String USING_NIGHT_VISION = "guardian_helmet_night_vision";
    private static final Set<DamageSource> HELMET_SOURCES = ImmutableSet.of(DamageSource.DROWN, DamageSource.WITHER);
    private static final Set<Effect> HELMET_EFFECTS_REMOVED = ImmutableSet.of(Effects.WITHER);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerAttack(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

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
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

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
        if (event.getEntity() instanceof PlayerEntity && !event.getEntity().world.isRemote) {
            PlayerEntity player = (PlayerEntity) event.getEntity();

            if (ModUtils.hasGuardianHelmet(player) && GuardinanHelmetProperties.getMode(ModUtils.getGuardianHelmet(player))) {
                if (player.isInWater()) {
                    player.setAir(player.getMaxAir());
                }

                HELMET_EFFECTS_REMOVED.stream()
                        .filter(player::isPotionActive)
                        .forEach(player::removePotionEffect);
            }
        }
    }

    @SubscribeEvent
    public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            ItemStack tool = player.getHeldItemMainhand();
            float toolSpeed = tool.getItem().getDestroySpeed(tool, event.getState());
            if (ModUtils.hasGuardianHelmet(player) && player.isInWater()) {
                if (toolSpeed > event.getOriginalSpeed())
                    if (player.isOnGround())
                        event.setNewSpeed(event.getOriginalSpeed() * 5);
                    else
                        event.setNewSpeed(event.getOriginalSpeed() * 25);
            } else if (ModUtils.isFlying(player) && !player.isOnGround()) {
                if (toolSpeed > event.getOriginalSpeed())
                    event.setNewSpeed(event.getOriginalSpeed() * 5);
            }
        }
    }

    @SubscribeEvent
    public static void onUnequipHelmet(ArmorEvent.Unequip event) {
        PlayerEntity player = event.player;
        ItemStack helmet = event.armor;
        if (event.isArmorEqualTo(ModItems.GUARDIAN_HELMET.get())) {
            if (GuardinanHelmetProperties.getNightVision(helmet))
                player.removePotionEffect(Effects.NIGHT_VISION);
        }
    }

    @SubscribeEvent
    public static void onHelmetTick(ArmorEvent.Tick event) {
        PlayerEntity player = event.player;
        ItemStack helmet = event.armor;
        if(event.side.isClient())
            return;
        if (event.isArmorEqualTo(ModItems.GUARDIAN_HELMET.get())) {
            if (GuardinanHelmetProperties.getNightVision(helmet) && GuardinanHelmetProperties.getMode(helmet)) {
                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 32767, 0, false, false, false));
                player.getPersistentData().putBoolean(USING_NIGHT_VISION, true);
            } else {
                if (player.getPersistentData().getBoolean(USING_NIGHT_VISION)) {
                    player.getPersistentData().remove(USING_NIGHT_VISION);
                    player.removePotionEffect(Effects.NIGHT_VISION);
                }
            }
        }
    }
}

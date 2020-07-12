package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class HelmetHandler {

    private final static String USING_NIGHT_VISION = "guardian_helmet_night_vision";
    private final static String SATURATION_TICKS = "guardian_helmet_saturation_ticks";
    private static final Set<DamageSource> HELMET_SOURCES = ImmutableSet.of(DamageSource.DROWN, DamageSource.WITHER);
    private static final Set<UseAction> HELMET_USE_ACTIONS = ImmutableSet.of(UseAction.DRINK, UseAction.EAT);

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
                if (player.isPotionActive(Effects.WITHER)) {
                    player.removePotionEffect(Effects.WITHER);
                }
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
                    if (player.onGround)
                        event.setNewSpeed(event.getOriginalSpeed() * 5);
                    else
                        event.setNewSpeed(event.getOriginalSpeed() * 25);
            } else if (ModUtils.isFlying(player) && !player.onGround) {
                if (toolSpeed > event.getOriginalSpeed())
                    event.setNewSpeed(event.getOriginalSpeed() * 5);
            }
        }
    }

    @SubscribeEvent
    public static void onEat(PlayerInteractEvent.RightClickItem event) {
        if (Config.EAT_WITH_HELMET.get())
            return;
        if (!HELMET_USE_ACTIONS.contains(event.getItemStack().getUseAction()))
            return;
        PlayerEntity player = event.getPlayer();
        if (ModUtils.hasGuardianHelmet(player) && GuardinanHelmetProperties.getMode(ModUtils.getGuardianHelmet(player))) {
            player.sendStatusMessage(TranslationHelper.translate(Translations.CANNOT_EAT_WHILE_MATERIALIZED), true);
            event.setCanceled(true);
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onHelmetTick(ArmorEvent.Tick event) {
        PlayerEntity player = event.player;
        ItemStack helmet = event.armor;
        if (event.isArmorEqualTo(ModItems.GUARDIAN_HELMET.get())) {
            if (GuardinanHelmetProperties.getNightVision(helmet) && GuardinanHelmetProperties.getMode(helmet)) {
                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 32767, 0, false, false, Config.SHOW_NIGHT_VISION_ICON.get()));
                player.getPersistentData().putBoolean(USING_NIGHT_VISION, true);
            } else {
                if (player.getPersistentData().getBoolean(USING_NIGHT_VISION)) {
                    player.getPersistentData().remove(USING_NIGHT_VISION);
                    player.removePotionEffect(Effects.NIGHT_VISION);
                }
            }

            if (Config.HELMET_PROVIDES_FOOD.get() && GuardinanHelmetProperties.getMode(helmet)) {
                if (player.getPersistentData().getInt(SATURATION_TICKS) % 300 == 0) {
                    player.getFoodStats().addStats(1, 0.5F);
                    player.getPersistentData().putInt(SATURATION_TICKS, 1);
                }

                player.getPersistentData().putInt(SATURATION_TICKS, player.getPersistentData().getInt(SATURATION_TICKS) + 1);
            }
        }
    }
}

package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.api.ILevelableItem;
import com.black_dog20.bml.event.PlayerMoveEvent;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.BaseGuardianArmorItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianSwordItem;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.stream.Collectors;

import static com.black_dog20.jetboots.common.util.NBTTags.TAG_FLIGHT_XP_COOLDOWN;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class XpHandler {

    @SubscribeEvent
    public static void onPlayerMoveFlying(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.level.isClientSide)
            return;
        if (!ModUtils.hasJetBoots(player) && ModUtils.isJetbootsFlying(player))
            return;
        if (event.getDistance() < 0.9)
            return;
        if (getRemainingCooldown(player) > 0)
            return;
        ItemStack jetboots = ModUtils.getJetBoots(player);
        ItemLevelProperties.addXp(player, jetboots, Config.FLIGHT_XP.get());
        setCooldown(player);

    }

    private static void setCooldown(Player player) {
        CompoundTag compound = player.getPersistentData();
        compound.putLong(TAG_FLIGHT_XP_COOLDOWN, System.currentTimeMillis() / 1000);
    }

    public static long getCooldown(Player player) {
        CompoundTag compound = player.getPersistentData();
        return compound.getLong(TAG_FLIGHT_XP_COOLDOWN);
    }

    public static long getRemainingCooldown(Player player) {
        long currentTime = System.currentTimeMillis() / 1000;
        long cooldownStart = getCooldown(player);
        long remaining = (Math.max(Config.TICKS_BETWEEN_FLIGHT_XP_GAIN.get(), 20) / 20) - (currentTime - cooldownStart);
        return remaining < 0 ? 0 : remaining;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getAmount() > 0 && !event.getSource().isBypassArmor() && event.getEntityLiving() instanceof Player player) {
            if (player.level.isClientSide)
                return;

            Set<ItemStack> levelableItems = player.getInventory().armor.stream()
                    .filter(stack -> stack.getItem() instanceof BaseGuardianArmorItem)
                    .filter(stack -> stack.getItem() instanceof ILevelableItem)
                    .collect(Collectors.toSet());

            int xp = (int) Math.ceil(MathUtil.clamp(event.getAmount() * Config.HURT_XP_MODIFIER.get(), Config.HURT_XP_MIN.get(), Config.HURT_XP_MAX.get()));

            for (ItemStack stack : levelableItems) {
                ItemLevelProperties.addXp(player, stack, xp);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerAttackEntity(LivingAttackEvent event) {
        if (event.getAmount() > 0 && !event.getSource().isBypassArmor() && event.getSource().msgId.equals("player") && event.getSource() instanceof EntityDamageSource damageSource) {
            if (damageSource.getEntity() == null)
                return;
            if (!(damageSource.getEntity() instanceof Player player))
                return;
            if (player.level.isClientSide)
                return;
            ItemStack weapon = player.getMainHandItem();

            if (weapon.getItem() instanceof ILevelableItem && weapon.getItem() instanceof GuardianSwordItem) {
                int xp = (int) Math.ceil(MathUtil.clamp(event.getAmount() * Config.ATTACK_XP_MODIFIER.get(), Config.ATTACK_XP_MIN.get(), Config.ATTACK_XP_MAX.get()));
                ItemLevelProperties.addXp(player, weapon, xp);
            }
        }
    }

}

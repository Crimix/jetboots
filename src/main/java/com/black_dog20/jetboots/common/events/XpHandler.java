package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.event.PlayerMoveEvent;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.api.ILevelableItem;
import com.black_dog20.jetboots.common.util.LevelProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.stream.Collectors;

import static com.black_dog20.jetboots.common.util.NBTTags.*;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class XpHandler {

    @SubscribeEvent
    public static void onPlayerMoveFlying(PlayerMoveEvent event) {
        PlayerEntity player = event.getPlayer();
        if(player.world.isRemote)
            return;
        if (!ModUtils.hasJetBoots(player) && ModUtils.isFlying(player))
            return;
        if (event.getDistance() < 0.9)
            return;
        if(getRemainingCooldown(player) > 0)
            return;
        ItemStack jetboots = ModUtils.getJetBoots(player);
        LevelProperties.addXp(player, jetboots, 8);
        setCooldown(player);

    }

    private static void setCooldown(PlayerEntity player) {
        CompoundNBT compound = player.getPersistentData();
        compound.putLong(TAG_FLIGHT_XP_COOLDOWN, System.currentTimeMillis() / 1000);
    }

    public static long getCooldown(PlayerEntity player) {
        CompoundNBT compound = player.getPersistentData();
        return compound.getLong(TAG_FLIGHT_XP_COOLDOWN);
    }

    public static long getRemainingCooldown(PlayerEntity player) {
        long currentTime = System.currentTimeMillis() / 1000;
        long cooldownStart = getCooldown(player);
        long remaining = (Math.max(Config.TICKS_BETWEEN_FLIGHT_XP_GAIN.get(), 20) / 20) - (currentTime - cooldownStart);
        return remaining < 0 ? 0 : remaining;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getAmount() > 0 && !event.getSource().isUnblockable() && event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if(player.world.isRemote)
                return;

            Set<ItemStack> levelableItems = player.inventory.armorInventory.stream()
                    .filter(stack -> stack.getItem() instanceof ILevelableItem)
                    .collect(Collectors.toSet());

            int xp = (int) Math.ceil(MathUtil.clamp(event.getAmount(), 1.0f, 10.0f));

            for (ItemStack stack : levelableItems) {
                LevelProperties.addXp(player, stack, xp);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerAttackEntity(LivingAttackEvent event) {
        if (event.getAmount() > 0 && !event.getSource().isUnblockable() && event.getSource().damageType.equals("player") && event.getSource() instanceof EntityDamageSource) {
            EntityDamageSource damageSource = (EntityDamageSource) event.getSource();
            if (damageSource.getTrueSource() == null)
                return;
            if (!(damageSource.getTrueSource() instanceof PlayerEntity))
                return;
            PlayerEntity player = (PlayerEntity) damageSource.getTrueSource();
            if(player.world.isRemote)
                return;
            ItemStack weapon = player.getHeldItemMainhand();

            if(weapon.getItem() instanceof ILevelableItem) {
                int xp = (int) Math.ceil(MathUtil.clamp(event.getAmount(), 1.0f, 8.0f));
                LevelProperties.addXp(player, weapon, xp);
            }
        }
    }

}

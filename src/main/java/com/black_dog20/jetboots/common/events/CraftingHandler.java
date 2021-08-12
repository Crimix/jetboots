package com.black_dog20.jetboots.common.events;

import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import com.black_dog20.jetboots.common.items.equipment.RocketBootsItem;
import com.black_dog20.jetboots.common.util.properties.RocketBootsProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.black_dog20.jetboots.common.util.NBTTags.TAG_HAS_SHOCK_ABSORBER_UPGRADE;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class CraftingHandler {

    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getPlayer();
        if (!player.level.isClientSide() && event.getCrafting().getItem() instanceof JetBootsItem) {
            ItemStack jetboots = event.getCrafting();
            ItemStack rocketboots = ItemStack.EMPTY;
            Container container = event.getInventory();
            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack input = container.getItem(i);
                if (input.getItem() instanceof RocketBootsItem) {
                    rocketboots = input;
                    break;
                }
            }

            if (!rocketboots.isEmpty()) {
                CompoundTag compoundNBT = jetboots.getOrCreateTag();
                if (RocketBootsProperties.hasShockUpgrade(rocketboots)) {
                    compoundNBT.putBoolean(TAG_HAS_SHOCK_ABSORBER_UPGRADE, true);
                }

                int currentLevel = ItemLevelProperties.getCurrentLevel(rocketboots);
                int currentXp = ItemLevelProperties.getCurrentXp(rocketboots);


                if (currentLevel >= ItemLevelProperties.getMaxLevel(jetboots)) {
                    compoundNBT.putInt(ItemLevelProperties.TAG_LEVEL, ItemLevelProperties.getMaxLevel(jetboots));
                } else {
                    compoundNBT.putInt(ItemLevelProperties.TAG_LEVEL, currentLevel);
                    compoundNBT.putInt(ItemLevelProperties.TAG_XP, currentXp);
                }
            }
        }
    }
}

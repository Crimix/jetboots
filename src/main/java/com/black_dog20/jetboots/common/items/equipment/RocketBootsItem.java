package com.black_dog20.jetboots.common.items.equipment;

import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.capabilities.GuardianCapabilities;
import com.black_dog20.jetboots.common.items.BaseGuardianArmorItem;
import com.black_dog20.jetboots.common.items.materials.GuardianMaterial;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.RocketBootsProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class RocketBootsItem extends BaseGuardianArmorItem {

    public RocketBootsItem(Properties builder) {
        super(GuardianMaterial.getInstance(), Type.BOOTS, builder.durability(-1).setNoRepair());
    }

    @Override
    protected double getCustomDamageReduceAmount(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.ROCKETBOOTS_BASE_DAMAGE_REDUCE_AMOUNT.get(), Config.ROCKETBOOTS_MAX_DAMAGE_REDUCE_AMOUNT.get(), stack);
    }

    @Override
    protected double getCustomToughness(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.ROCKETBOOTS_BASE_TOUGHNESS_AMOUNT.get(), Config.ROCKETBOOTS_MAX_TOUGHNESS_AMOUNT.get(), stack);
    }

    @Override
    protected double getCustomKnockbackResistance(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.ROCKETBOOTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT.get(), Config.ROCKETBOOTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT.get(), stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "jetboots:textures/armor/rocketboots.png";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        KeyMapping sneak = Minecraft.getInstance().options.keyShift;

        tooltip.add(TURN_OFF.get(ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keyRocketBoots)));
        tooltip.add(ModUtils.getEngineStateText(stack, ChatFormatting.GRAY));

        ExtraTooltipEvent normalExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NORMAL);
        if (!MinecraftForge.EVENT_BUS.post(normalExtraTooltips)) {
            tooltip.addAll(normalExtraTooltips.getExtraTooltips());
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)) {
            tooltip.addAll(getLevelTooltips(stack));
            if (RocketBootsProperties.hasShockUpgrade(stack)) {
                tooltip.add(SHOCK_ABSORBER_UPGRADE.get());
            }

            ExtraTooltipEvent sneakExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.SNEAKING);
            if (!MinecraftForge.EVENT_BUS.post(sneakExtraTooltips)) {
                tooltip.addAll(sneakExtraTooltips.getExtraTooltips());
            }
        } else {
            ExtraTooltipEvent notSneakExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NOT_SNEAKING);
            if (!MinecraftForge.EVENT_BUS.post(notSneakExtraTooltips)) {
                tooltip.addAll(notSneakExtraTooltips.getExtraTooltips());
            }

            tooltip.add(TranslationUtil.translate(OPEN_CONTAINER, ChatFormatting.GRAY));
            tooltip.add(TranslationUtil.translate(SHOW_MORE_INFO, ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak)));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new GuardianCapabilities(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return Config.ROCKETBOOTS_MAX_LEVEL.get();
    }
}

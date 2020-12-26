package com.black_dog20.jetboots.common.items.equipment;

import com.black_dog20.bml.api.ISoulbindable;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.api.ILevelableItem;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.client.containers.EnchantableItemContainer;
import com.black_dog20.jetboots.common.capabilities.GuardianCapabilities;
import com.black_dog20.jetboots.common.items.materials.GuardianTier;
import com.black_dog20.jetboots.common.util.LevelProperties;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class GuardianSwordItem extends SwordItem implements ISoulbindable, ILevelableItem {

    public GuardianSwordItem(Properties builder) {
        super(GuardianTier.getInstance(), 0, 0, builder.maxDamage(-1).setNoRepair());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlotType.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "jetboots.damage", getCustomAttackDamage(stack), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "jetboots.speed", getCustomAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return builder.build();
    }

    private double getCustomAttackDamage(ItemStack stack) {
        return LevelProperties.calculateValue(5, 11, stack);
    }

    private double getCustomAttackSpeed(ItemStack stack) {
        return LevelProperties.calculateValue(-2.4f, -1.6f, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        KeyBinding sneak = Minecraft.getInstance().gameSettings.keyBindSneak;

        ExtraTooltipEvent normalExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NORMAL);
        if (!MinecraftForge.EVENT_BUS.post(normalExtraTooltips)) {
            tooltip.addAll(normalExtraTooltips.getExtraTooltips());
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)) {
            tooltip.add(TranslationHelper.getLevelProgress(stack));
            if (isSoulbound(stack)) {
                tooltip.add(TranslationUtil.translate(SOULBOUND, TextFormatting.LIGHT_PURPLE));
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

            tooltip.add(TranslationUtil.translate(OPEN_CONTAINER, TextFormatting.GRAY));
            tooltip.add(TranslationUtil.translate(SHOW_MORE_INFO, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak)));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new GuardianCapabilities(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.isCrouching()) {
            if (!world.isRemote) {
                player.openContainer(new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return player.getHeldItem(hand).getDisplayName();
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
                        return new EnchantableItemContainer(windowId, playerInventory, player);
                    }
                });
            }
            return ActionResult.resultPass(player.getHeldItem(hand));
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean isSoulbound(ItemStack stack) {
        return isSoulboundByLevel(stack);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}

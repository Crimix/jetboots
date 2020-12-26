package com.black_dog20.jetboots.common.compat.refinedstorage.events;

import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Compat.*;

public class RSCompatTooltipsHandler {

    @SubscribeEvent
    public void onExtraTooltips(ExtraTooltipEvent event) {
        ItemStack stack = event.getStack();
        if (stack.getItem() instanceof GuardianHelmetItem) {
            if (event.getType() == ExtraTooltipEvent.Type.SNEAKING) {
                if (NBTUtil.getBoolean(stack, TAG_HAS_WIRELESS_CRAFTING_UPGRADE)) {
                   event.add(WIRELESS_CRAFTING_UPGRADE.get());
                }
                int upgradeCount = NBTUtil.getInt(stack, TAG_WIRELESS_RANGE_UPGRADE);
                if (upgradeCount != 0) {
                   event.add(WIRELESS_RANGE_UPGRADE.get(TextFormatting.GRAY, getRomanNumber(upgradeCount)));
                }
            } else if (event.getType() == ExtraTooltipEvent.Type.NOT_SNEAKING) {
                if (NBTUtil.getBoolean(stack, TAG_HAS_WIRELESS_CRAFTING_UPGRADE)) {
                    event.add(WIRELESS_CRAFTING_USE.get(TextFormatting.GRAY, KeybindsUtil.getKeyBindText(RSCompatKeybindHandler.keyOpenCraftingGrid)));
                }
            }
        }
    }

    private static String getRomanNumber(int number) {
        return String.join("", Collections.nCopies(number, "I"))
                .replace("IIIII", "V")
                .replace("IIII", "IV");
    }
}

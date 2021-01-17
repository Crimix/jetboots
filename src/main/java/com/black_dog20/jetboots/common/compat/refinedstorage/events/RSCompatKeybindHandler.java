package com.black_dog20.jetboots.common.compat.refinedstorage.events;

import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.jetboots.common.compat.refinedstorage.network.RSCompatPacketHandler;
import com.black_dog20.jetboots.common.compat.refinedstorage.network.packets.PacketOpenCraftingGrid;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Compat.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class RSCompatKeybindHandler {

    public static final KeyBinding keyOpenCraftingGrid = new KeyBinding("key.jetboots.open_crafting_grid", KeyConflictContext.IN_GAME, KeyModifier.ALT, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY.getDescription());

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Minecraft.getInstance().player != null) {
            PlayerEntity player = Minecraft.getInstance().player;

            ItemStack helmet = ModUtils.getGuardianHelmet(player);
            if (keyOpenCraftingGrid.isPressed()) {
                if (NBTUtil.getBoolean(helmet, TAG_HAS_WIRELESS_CRAFTING_UPGRADE))
                    RSCompatPacketHandler.sendToServer(new PacketOpenCraftingGrid());
                else
                    player.sendMessage(WIRELESS_CRAFTING_UPGRADE_NOT_INSTALLED.get(), player.getUniqueID());
            }
        }
    }
}

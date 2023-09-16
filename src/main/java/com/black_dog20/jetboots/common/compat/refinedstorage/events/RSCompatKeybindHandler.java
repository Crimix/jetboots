//package com.black_dog20.jetboots.common.compat.refinedstorage.events;
//
//import com.black_dog20.bml.utils.item.NBTUtil;
//import com.black_dog20.jetboots.common.compat.refinedstorage.network.RSCompatPacketHandler;
//import com.black_dog20.jetboots.common.compat.refinedstorage.network.packets.PacketOpenCraftingGrid;
//import com.black_dog20.jetboots.common.util.ModUtils;
//import com.mojang.blaze3d.platform.InputConstants;
//import net.minecraft.client.KeyMapping;
//import net.minecraft.client.Minecraft;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.client.settings.KeyConflictContext;
//import net.minecraftforge.client.settings.KeyModifier;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import org.lwjgl.glfw.GLFW;
//
//import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.TAG_HAS_WIRELESS_CRAFTING_UPGRADE;
//import static com.black_dog20.jetboots.common.util.TranslationHelper.Compat.WIRELESS_CRAFTING_UPGRADE_NOT_INSTALLED;
//import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.KEY_CATEGORY;
//
//public class RSCompatKeybindHandler {
//
//    public static final KeyMapping keyOpenCraftingGrid = new KeyMapping("key.jetboots.open_crafting_grid", KeyConflictContext.IN_GAME, KeyModifier.ALT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY.getDescription());
//
//    @SubscribeEvent
//    public void onKeyInput(TickEvent.ClientTickEvent event) {
//        Level level = Minecraft.getInstance().level;
//        if (event.phase != TickEvent.Phase.START || level == null || Minecraft.getInstance().screen != null) { return; }
//        if (Minecraft.getInstance().player != null) {
//            Player player = Minecraft.getInstance().player;
//
//            ItemStack helmet = ModUtils.getGuardianHelmet(player);
//            if (keyOpenCraftingGrid.consumeClick()) {
//                if (NBTUtil.getBoolean(helmet, TAG_HAS_WIRELESS_CRAFTING_UPGRADE))
//                    RSCompatPacketHandler.sendToServer(new PacketOpenCraftingGrid());
//                else
//                    player.sendSystemMessage(WIRELESS_CRAFTING_UPGRADE_NOT_INSTALLED.get());
//            }
//        }
//    }
//}

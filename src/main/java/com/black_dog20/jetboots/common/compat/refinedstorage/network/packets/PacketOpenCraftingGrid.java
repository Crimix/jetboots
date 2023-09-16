//package com.black_dog20.jetboots.common.compat.refinedstorage.network.packets;
//
//import com.black_dog20.bml.utils.item.NBTUtil;
//import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
//import com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageCompatUtil;
//import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
//import com.black_dog20.jetboots.common.util.ModUtils;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.fml.ModList;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.TAG_HAS_WIRELESS_CRAFTING_UPGRADE;
//
//public class PacketOpenCraftingGrid {
//    public PacketOpenCraftingGrid() {
//    }
//
//    public static void encode(PacketOpenCraftingGrid msg, FriendlyByteBuf buffer) {
//
//    }
//
//    public static PacketOpenCraftingGrid decode(FriendlyByteBuf buffer) {
//        return new PacketOpenCraftingGrid();
//    }
//
//    public static class Handler {
//        public static void handle(PacketOpenCraftingGrid msg, Supplier<NetworkEvent.Context> ctx) {
//            ctx.get().enqueueWork(() -> {
//                ServerPlayer player = ctx.get().getSender();
//                if (player == null)
//                    return;
//
//                ItemStack stack = ModUtils.getGuardianHelmet(player);
//
//                if (!stack.isEmpty() && stack.getItem() instanceof GuardianHelmetItem && NBTUtil.getBoolean(stack, TAG_HAS_WIRELESS_CRAFTING_UPGRADE)) {
//                    if(ModList.get().isLoaded(RefinedStorageCompat.MOD_ID)) {
//                        RefinedStorageCompatUtil.openNetwork(player.server, stack, player);
//                    }
//                }
//            });
//
//            ctx.get().setPacketHandled(true);
//        }
//    }
//}

package com.black_dog20.jetboots.common.compat.refinedstorage.network.packets;

import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageCompatUtil;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;

public class PacketOpenCraftingGrid {
    public PacketOpenCraftingGrid() {
    }

    public static void encode(PacketOpenCraftingGrid msg, PacketBuffer buffer) {

    }

    public static PacketOpenCraftingGrid decode(PacketBuffer buffer) {
        return new PacketOpenCraftingGrid();
    }

    public static class Handler {
        public static void handle(PacketOpenCraftingGrid msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getGuardianHelmet(player);

                if (!stack.isEmpty() && stack.getItem() instanceof GuardianHelmetItem && NBTUtil.getBoolean(stack, TAG_HAS_WIRELESS_CRAFTING_UPGRADE)) {
                    if(ModList.get().isLoaded(RefinedStorageCompat.MOD_ID)) {
                        RefinedStorageCompatUtil.openNetwork(player.server, stack, player);
                    }
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}

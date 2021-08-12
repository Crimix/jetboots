package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.jetboots.common.items.equipment.RocketBootsItem;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketAwardRocketBootXp {

    private final int xp;

    public PacketAwardRocketBootXp(int xp) {
        this.xp = xp;
    }

    public static void encode(PacketAwardRocketBootXp msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.xp);
    }

    public static PacketAwardRocketBootXp decode(FriendlyByteBuf buffer) {
        return new PacketAwardRocketBootXp(buffer.readInt());
    }

    public static class Handler {
        public static void handle(PacketAwardRocketBootXp msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null) return;
                if (!ModUtils.hasRocketBoots(player)) return;

                ItemStack stack = ModUtils.getRocketBoots(player);

                if (!stack.isEmpty() && stack.getItem() instanceof RocketBootsItem) {
                    ItemLevelProperties.addXp(player, stack, msg.xp);
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}

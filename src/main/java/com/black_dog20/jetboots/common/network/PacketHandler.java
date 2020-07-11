package com.black_dog20.jetboots.common.network;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.network.packets.PacketSyncPartical;
import com.black_dog20.jetboots.common.network.packets.PacketSyncSound;
import com.black_dog20.jetboots.common.network.packets.PacketSyncStopSound;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateFlightMode;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateFlightSpeed;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateHelmetMode;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateHelmetVision;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static short index = 0;

    public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Jetboots.MOD_ID, "network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        registerMessage(PacketUpdateFlightMode.class, PacketUpdateFlightMode::encode, PacketUpdateFlightMode::decode, PacketUpdateFlightMode.Handler::handle);
        registerMessage(PacketUpdateFlightSpeed.class, PacketUpdateFlightSpeed::encode, PacketUpdateFlightSpeed::decode, PacketUpdateFlightSpeed.Handler::handle);
        registerMessage(PacketSyncPartical.class, PacketSyncPartical::encode, PacketSyncPartical::decode, PacketSyncPartical.Handler::handle);
        registerMessage(PacketSyncSound.class, PacketSyncSound::encode, PacketSyncSound::decode, PacketSyncSound.Handler::handle);
        registerMessage(PacketSyncStopSound.class, PacketSyncStopSound::encode, PacketSyncStopSound::decode, PacketSyncStopSound.Handler::handle);
        registerMessage(PacketUpdateHelmetMode.class, PacketUpdateHelmetMode::encode, PacketUpdateHelmetMode::decode, PacketUpdateHelmetMode.Handler::handle);
        registerMessage(PacketUpdateHelmetVision.class, PacketUpdateHelmetVision::encode, PacketUpdateHelmetVision::decode, PacketUpdateHelmetVision.Handler::handle);
    }

    public static void sendTo(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer))
            NETWORK.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAll(Object msg, World world) {
        for (PlayerEntity player : world.getPlayers()) {
            if (!(player instanceof FakePlayer))
                NETWORK.sendTo(msg, ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToServer(Object msg) {
        NETWORK.sendToServer(msg);
    }

    private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
        NETWORK.registerMessage(index, messageType, encoder, decoder, messageConsumer);
        index++;
        if (index > 0xFF)
            throw new RuntimeException("Too many messages!");
    }
}

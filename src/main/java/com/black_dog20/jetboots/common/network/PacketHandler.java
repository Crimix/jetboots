package com.black_dog20.jetboots.common.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.black_dog20.jetboots.Jetboots;

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
	        //registerMessage(PacketSyncBootCapability.class, PacketSyncBootCapability::encode, PacketSyncBootCapability::decode, PacketSyncBootCapability.Handler::handle);
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

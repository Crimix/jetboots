package com.black_dog20.jetboots.client.containers;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Jetboots.MOD_ID);

    public static final RegistryObject<ContainerType<JetBootsContainer>> JETBOOTS_CONTAINER = CONTAINERS.register("jetboots", () -> IForgeContainerType.create((windowId, inv, data) -> new JetBootsContainer(windowId, inv, Minecraft.getInstance().player)));
}

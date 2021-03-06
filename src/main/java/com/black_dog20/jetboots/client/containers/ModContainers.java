package com.black_dog20.jetboots.client.containers;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Jetboots.MOD_ID);

    public static final RegistryObject<ContainerType<EnchantableItemContainer>> ENCHANTABLE_ITEM_CONTAINER = CONTAINERS.register("enchantable_item_container", () -> IForgeContainerType.create((windowId, inv, data) -> new EnchantableItemContainer(windowId, inv, inv.player)));
}

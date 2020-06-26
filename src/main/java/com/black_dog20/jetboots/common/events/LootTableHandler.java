package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class LootTableHandler {

    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event) {
        if (event.getName().equals(LootTables.CHESTS_NETHER_BRIDGE)) {
            LootTable table = event.getTable();
            table.addPool(LootPool.builder().name("jetboots")
                    .addEntry(ItemLootEntry.builder(ModItems.FORCEFIELD_GENERATOR.get()).weight(10).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                    .build());
            Jetboots.getLogger().debug("[Loot Table] Loot added for nether bridge");
        } else if (event.getName().equals(LootTables.CHESTS_END_CITY_TREASURE)) {
            LootTable table = event.getTable();
            table.addPool(LootPool.builder().name("jetboots")
                    .addEntry(ItemLootEntry.builder(ModItems.FORCEFIELD_PROJECTOR.get()).weight(10).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                    .addEntry(ItemLootEntry.builder(ModItems.GUARDIAN_HELMET.get()).weight(10).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                    .build());
            Jetboots.getLogger().debug("[Loot Table] Loot added for end city");
        }
    }
}

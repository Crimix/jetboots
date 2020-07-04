package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
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
            Jetboots.getLogger().debug("[Loot Table] Nether Bridge part added? {}", Config.FORCEFIELD_PARTS_LOOT.get());
            table.addPool(LootPool.builder().name("jetboots-nether-forcefield").rolls(RandomValueRange.of(-2F, 1F))
                    .addEntry(ItemLootEntry.builder(ModItems.FORCEFIELD_GENERATOR.get()).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                    .build());
            Jetboots.getLogger().debug("[Loot Table] Loot added for Nether Bridge");
        } else if (event.getName().equals(LootTables.CHESTS_END_CITY_TREASURE)) {
            LootTable table = event.getTable();
            Jetboots.getLogger().debug("[Loot Table] End City part added? {}", Config.FORCEFIELD_PARTS_LOOT.get());
            Jetboots.getLogger().debug("[Loot Table] End City helmet added? {}", Config.HELMET_LOOT.get());
            table.addPool(LootPool.builder().name("jetboots-end-forcefield").rolls(RandomValueRange.of(-2F, 1F))
                    .addEntry(ItemLootEntry.builder(ModItems.FORCEFIELD_PROJECTOR.get()).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                    .build());
            table.addPool(LootPool.builder().name("jetboots-end-helmet").rolls(RandomValueRange.of(-2F, 1F))
                    .addEntry(ItemLootEntry.builder(ModItems.GUARDIAN_HELMET.get()).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                    .build());
            Jetboots.getLogger().debug("[Loot Table] Loot added for End City ");
        }
    }
}

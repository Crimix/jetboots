package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class LootTableHandler {

    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event) {
        if (event.getName().equals(LootTables.CHESTS_NETHER_BRIDGE)) {
            LootTable table = event.getTable();
            Jetboots.getLogger().debug("[Loot Table] Nether Bridge helmet added? {}", Config.HELMET_LOOT.get());
            if(Config.HELMET_LOOT.get()) {
                table.addPool(LootPool.builder().name("jetboots-end-helmet").rolls(RandomValueRange.of(-2F, 1F))
                        .addEntry(ItemLootEntry.builder(ModItems.GUARDIAN_HELMET.get()).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                        .build());
            }
            Jetboots.getLogger().debug("[Loot Table] Loot added for Nether Bridge");
        } else if (event.getName().equals(LootTables.CHESTS_END_CITY_TREASURE)) {
            LootTable table = event.getTable();
            Jetboots.getLogger().debug("[Loot Table] End City helmet added? {}", Config.HELMET_LOOT.get());
            if(Config.HELMET_LOOT.get()) {
                table.addPool(LootPool.builder().name("jetboots-end-helmet").rolls(RandomValueRange.of(-2F, 1F))
                        .addEntry(ItemLootEntry.builder(ModItems.GUARDIAN_HELMET.get()).acceptFunction(SetCount.builder(ConstantRange.of(1))))
                        .build());
            }
            Jetboots.getLogger().debug("[Loot Table] Loot added for End City ");
        }
    }
}

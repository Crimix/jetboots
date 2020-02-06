package com.black_dog20.jetboots.common.crafting;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class BatteryOnCondition implements ICondition
{
    private static final ResourceLocation NAME = new ResourceLocation(Jetboots.MOD_ID, "battery_on");

    public BatteryOnCondition() {}

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test()
    {
        return Config.USE_POWER.get();
    }

    @Override
    public String toString()
    {
        return "is_battery_on";
    }

    public static class Serializer implements IConditionSerializer<BatteryOnCondition>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, BatteryOnCondition value)
        {
        }

        @Override
        public BatteryOnCondition read(JsonObject json)
        {
            return new BatteryOnCondition();
        }

        @Override
        public ResourceLocation getID()
        {
            return BatteryOnCondition.NAME;
        }
    }

}

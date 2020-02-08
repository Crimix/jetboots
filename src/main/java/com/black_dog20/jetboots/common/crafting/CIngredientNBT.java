package com.black_dog20.jetboots.common.crafting;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.IngredientNBT;

public class CIngredientNBT extends IngredientNBT{

	protected ItemStack stack;
	
	protected CIngredientNBT(ItemStack stack) {
		super(stack);
		this.stack = stack;
	}

	public static CIngredientNBT fromNBTStack(ItemStack stack) {
		return new CIngredientNBT(stack);
	}
	
    @Override
    public boolean test(@Nullable ItemStack input)
    {
        if (input == null)
            return false;
        //Can't use areItemStacksEqualUsingNBTShareTag because it compares stack size as well
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && areInputTagsInStackTage(this.stack.getShareTag(), input.getShareTag());
        
    }
    
    private static boolean areInputTagsInStackTage(CompoundNBT stackTag, CompoundNBT inputTag) {
    	boolean res = true;
    	Set<String> stackKeySet = stackTag.keySet();
    	for(String key : stackKeySet) {
    		if(!stackTag.get(key).equals(inputTag.get(key))) {
    		  return false;
    		}
    	}
    	
    	return res;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement serialize()
    {
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        json.addProperty("item", stack.getItem().getRegistryName().toString());
        json.addProperty("count", stack.getCount());
        if (stack.hasTag())
            json.addProperty("nbt", stack.getTag().toString());
        return json;
    }

    public static class Serializer implements IIngredientSerializer<CIngredientNBT>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public CIngredientNBT parse(PacketBuffer buffer) {
            return new CIngredientNBT(buffer.readItemStack());
        }

        @Override
        public CIngredientNBT parse(JsonObject json) {
            return new CIngredientNBT(CraftingHelper.getItemStack(json, true));
        }

        @Override
        public void write(PacketBuffer buffer, CIngredientNBT ingredient) {
            buffer.writeItemStack(ingredient.stack);
        }
    }
    
    

}

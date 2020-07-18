package com.black_dog20.jetboots.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

@OnlyIn(Dist.CLIENT)
public class Keybinds {
    public static final KeyBinding keyMode = new KeyBinding("key.jetboots.engine", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY.getDescription());
    public static final KeyBinding keySpeed = new KeyBinding("key.jetboots.speed", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY.getDescription());
    public static final KeyBinding keyHelmetMode = new KeyBinding("key.jetboots.helmet", KeyConflictContext.IN_GAME, KeyModifier.ALT, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY.getDescription());
    public static final KeyBinding keyHelmetVision = new KeyBinding("key.jetboots.helmet_vision", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY.getDescription());
}

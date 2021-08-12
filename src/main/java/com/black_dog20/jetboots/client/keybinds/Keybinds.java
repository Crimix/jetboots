package com.black_dog20.jetboots.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.KEY_CATEGORY;

@OnlyIn(Dist.CLIENT)
public class Keybinds {
    public static final KeyMapping keyMode = new KeyMapping("key.jetboots.engine", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY.getDescription());
    public static final KeyMapping keySpeed = new KeyMapping("key.jetboots.speed", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY.getDescription());
    public static final KeyMapping keyHelmetMode = new KeyMapping("key.jetboots.helmet", KeyConflictContext.IN_GAME, KeyModifier.ALT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY.getDescription());
    public static final KeyMapping keyHelmetVision = new KeyMapping("key.jetboots.helmet_vision", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY.getDescription());
    public static final KeyMapping keyRocketBoots = new KeyMapping("key.rocketboots.engine", KeyConflictContext.IN_GAME, KeyModifier.ALT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY.getDescription());
}

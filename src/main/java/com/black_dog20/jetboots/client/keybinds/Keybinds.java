package com.black_dog20.jetboots.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class Keybinds {
    private static String CATEGORY = "Jet Boots";

    public static final KeyBinding keyMode = new KeyBinding("key.jetboots.engine", GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyBinding keySpeed = new KeyBinding("key.jetboots.speed", GLFW.GLFW_KEY_G, CATEGORY);
    public static final KeyBinding keyHelmet = new KeyBinding("key.jetboots.helmet", GLFW.GLFW_KEY_H, CATEGORY);
}

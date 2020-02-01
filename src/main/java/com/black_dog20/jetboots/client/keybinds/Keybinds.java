package com.black_dog20.jetboots.client.keybinds;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;

public class Keybinds {
	private static String CATEGORY = "Jet Boots";
	
	public static final KeyBinding keyEngine = new KeyBinding("key.jetboots.engine", GLFW.GLFW_KEY_V, CATEGORY);
	public static final KeyBinding keySpeed = new KeyBinding("key.jetboots.speed", GLFW.GLFW_KEY_G, CATEGORY);
}

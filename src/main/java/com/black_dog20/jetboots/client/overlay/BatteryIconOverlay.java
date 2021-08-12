package com.black_dog20.jetboots.client.overlay;

import com.black_dog20.bml.client.DrawUtil;
import com.black_dog20.bml.client.overlay.configure.ConfigurablePercentageScaledOverlay;
import com.black_dog20.bml.utils.color.Color4i;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Optional;

public class BatteryIconOverlay extends ConfigurablePercentageScaledOverlay.Post {

    private static final ResourceLocation BATTERY_FULL = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_full.png");
    private static final ResourceLocation BATTERY_ABOVE_HALF = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_above_half.png");
    private static final ResourceLocation BATTERY_HALF = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_half.png");
    private static final ResourceLocation BATTERY_BELOW_HALF = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_below_half.png");
    private static final ResourceLocation BATTERY_NEARLY_EMPTY = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_nearly_empty.png");
    private static final ResourceLocation BATTERY_EMPTY = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_empty.png");
    private int ticks = 1;
    private boolean flickState = true;

    @Override
    public void setPercentagePosition(int percentageX, int percentageY) {
        Config.ICON_X_PERCENTAGE.set(percentageX);
        Config.ICON_Y_PERCENTAGE.set(percentageY);
        Config.ICON_X_PERCENTAGE.save();
        Config.ICON_Y_PERCENTAGE.save();
    }

    @Override
    public int getPercentagePosX() {
        return Config.ICON_X_PERCENTAGE.get();
    }

    @Override
    public int getPercentagePosY() {
        return Config.ICON_Y_PERCENTAGE.get();
    }

    @Override
    public void onRender(PoseStack matrixStack, int scaledwidth, int scaledheight) {
        int xPos = (int) (scaledwidth * (getPercentagePosX() / 100.0));
        int yPos = (int) (scaledheight * (getPercentagePosY() / 100.0));
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        matrixStack.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        ItemStack jetboots = ModUtils.getJetBoots(player);
        if (!jetboots.isEmpty()) {
            int percentage = EnergyUtil.getBatteryPercentage(jetboots);
            if (isBetween(percentage,80, 100)) {
                RenderSystem.setShaderTexture(0, BATTERY_FULL);
                ticks = 1;
            } else if ((isBetween(percentage,60, 80))) {
                RenderSystem.setShaderTexture(0, BATTERY_ABOVE_HALF);
                ticks = 1;
            } else if ((isBetween(percentage,40, 60))) {
                RenderSystem.setShaderTexture(0, BATTERY_HALF);
                ticks = 1;
            } else if ((isBetween(percentage,20, 40))) {
                RenderSystem.setShaderTexture(0, BATTERY_BELOW_HALF);
                ticks = 1;
            } else if ((isBetween(percentage,10, 20))) {
                RenderSystem.setShaderTexture(0, BATTERY_NEARLY_EMPTY);
                ticks = 1;
            } else {
                if (ticks % 50 == 0) {
                    flickState = !flickState;
                    ticks = 1;
                }
                if (flickState) {
                    RenderSystem.setShaderTexture(0, BATTERY_NEARLY_EMPTY);
                } else {
                    RenderSystem.setShaderTexture(0, BATTERY_EMPTY);
                }
                ticks++;
            }
            DrawUtil.drawNonStandardTexturedRect(xPos, yPos, 0, 0, 18,  18, 18, 18);
        }

        matrixStack.popPose();
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower < x && x <= upper;
    }

    @Override
    public boolean doRender(RenderGameOverlayEvent.ElementType elementType) {
        if(!Config.BATTERY_ICON_STATE.get()) {
            return false;
        }
        Minecraft minecraft = Minecraft.getInstance();
        boolean playerListShown = minecraft.options.keyPlayerList.isDown();
        boolean showDebugInfo = minecraft.options.renderDebug;
        boolean chatOpen = minecraft.screen instanceof ChatScreen;
        return !playerListShown && !showDebugInfo && !chatOpen && (RenderGameOverlayEvent.ElementType.ALL.equals(elementType) || RenderGameOverlayEvent.ElementType.TEXT.equals(elementType));
    }

    @Override
    public boolean doesCancelEvent() {
        return false;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth() {
        return 18;
    }

    @Override
    public Color4i getActiveColor() {
        return Color4i.of(0, 255, 0, 50);
    }

    @Override
    public boolean getSate() {
        return Config.BATTERY_ICON_STATE.get();
    }

    @Override
    public boolean isStateChangeable() {
        return true;
    }

    @Override
    public void setState(boolean state) {
        Config.BATTERY_ICON_STATE.set(state);
        Config.BATTERY_ICON_STATE.save();
    }

    @Override
    public Optional<Component> getMessage() {
        return Optional.empty();
    }
}

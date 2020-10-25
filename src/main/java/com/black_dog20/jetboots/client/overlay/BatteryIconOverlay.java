package com.black_dog20.jetboots.client.overlay;

import com.black_dog20.bml.client.overlay.configure.ConfigurablePercentageScaledOverlay;
import com.black_dog20.bml.utils.color.Color4i;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Optional;

public class BatteryIconOverlay extends ConfigurablePercentageScaledOverlay.Post {

    private static ResourceLocation BATTERY_FULL = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_full.png");
    private static ResourceLocation BATTERY_ABOVE_HALF = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_above_half.png");
    private static ResourceLocation BATTERY_HALF = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_half.png");
    private static ResourceLocation BATTERY_BELOW_HALF = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_below_half.png");
    private static ResourceLocation BATTERY_NEARLY_EMPTY = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_nearly_empty.png");
    private static ResourceLocation BATTERY_EMPTY = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/battery_icon_empty.png");
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
    public void onRender(MatrixStack matrixStack, int scaledwidth, int scaledheight) {
        int xPos = (int) (scaledwidth * (getPercentagePosX() / 100.0));
        int yPos = (int) (scaledheight * (getPercentagePosY() / 100.0));
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        RenderSystem.pushMatrix();
        RenderSystem.color3f(1F, 1F, 1F);
        RenderSystem.disableLighting();
        RenderSystem.enableAlphaTest();
        ItemStack jetboots = ModUtils.getJetBoots(player);
        if (!jetboots.isEmpty()) {
            int percentage = EnergyUtil.getBatteryPercentage(jetboots);
            if (isBetween(percentage,80, 100)) {
                Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_FULL);
                ticks = 1;
            } else if ((isBetween(percentage,60, 80))) {
                Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_ABOVE_HALF);
                ticks = 1;
            } else if ((isBetween(percentage,40, 60))) {
                Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_HALF);
                ticks = 1;
            } else if ((isBetween(percentage,20, 40))) {
                Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_BELOW_HALF);
                ticks = 1;
            } else if ((isBetween(percentage,10, 20))) {
                Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_NEARLY_EMPTY);
                ticks = 1;
            } else {
                if (ticks % 50 == 0) {
                    flickState = !flickState;
                    ticks = 1;
                }
                if (flickState)
                    Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_NEARLY_EMPTY);
                else {
                    Minecraft.getInstance().getTextureManager().bindTexture(BATTERY_EMPTY);
                }
                ticks++;
            }
            drawNonStandardTexturedRect(xPos,yPos,0,0,18,18,18,18);
        }

        RenderSystem.popMatrix();
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
        boolean playerListShown = minecraft.gameSettings.keyBindPlayerList.isKeyDown();
        boolean showDebugInfo = minecraft.gameSettings.showDebugInfo;
        boolean chatOpen = minecraft.currentScreen instanceof ChatScreen;
        return !playerListShown && !showDebugInfo && !chatOpen && RenderGameOverlayEvent.ElementType.ALL.equals(elementType);
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
    public Optional<ITextComponent> getMessage() {
        return Optional.empty();
    }

    protected void drawNonStandardTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        float f = 1F / (float) textureWidth;
        float f1 = 1F / (float) textureHeight;
        BufferBuilder wr = Tessellator.getInstance().getBuffer();
        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
        wr.pos(x,y + height, 0).tex( u * f, (v + height) * f1).endVertex();
        wr.pos(x + width, y + height, 0).tex((u + width) * f, (v + height) * f1).endVertex();
        wr.pos(x + width, y, 0).tex((u + width) * f, v * f1).endVertex();
        wr.pos(x, y, 0).tex(u * f, v * f1).endVertex();
        Tessellator.getInstance().draw();
    }
}

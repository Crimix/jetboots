package com.black_dog20.jetboots.client.overlay;

import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.common.events.RocketFlightHandler;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FlightBarOverlay implements IGuiOverlay {

    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        Player player = Minecraft.getInstance().player;
        if (player == null || player.isPassenger()) return;
        if (!ModUtils.hasRocketBoots(player)) return;
        RocketFlightHandler.RocketBootsPower rocketBootsPower = RocketFlightHandler.playerGlidePowerMap.get(player.getUUID());
        if (rocketBootsPower == null || rocketBootsPower.getTickFlight() == 0) return;

        guiGraphics.pose().pushPose();
        RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();

        float charge = MathUtil.clamp(1 - (((float) rocketBootsPower.getTickFlight()) / rocketBootsPower.getMaxFlightTime(ModUtils.getRocketBoots(player))), 0, 1);

        final int barWidth = 182;

        int topModifyer = player.getArmorValue() > 0 ? 9 : 0;

        int x = (width / 2) - (barWidth / 2);
        int filled = (int) (charge * (float) (barWidth + 1));
        int top = height - 32 + 3 - 18 - topModifyer;

        Gui gui = Minecraft.getInstance().gui;

        guiGraphics.blit(GUI_ICONS_LOCATION, x, top, 0, 84, barWidth, 5);

        if (filled > 0) {
            guiGraphics.blit(GUI_ICONS_LOCATION, x, top, 0, 89, filled, 5);
        }

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.pose().popPose();
    }
}

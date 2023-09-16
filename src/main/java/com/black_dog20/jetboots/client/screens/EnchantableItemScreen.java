package com.black_dog20.jetboots.client.screens;

import com.black_dog20.bml.api.ILevelableItem;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.containers.EnchantableItemContainer;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateMuffledMode;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;
import java.util.function.Supplier;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class EnchantableItemScreen extends AbstractContainerScreen<EnchantableItemContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/container_gui.png");
    private final Inventory inventory;
    private static final Button.CreateNarration DEFAULT_NARRATION = Supplier::get;

    public EnchantableItemScreen(EnchantableItemContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.inventory = playerInventory;
    }

    @Override
    protected void init() {
        super.init();
        ItemStack container = inventory.getSelected();
        if (container.getItem() == ModItems.JET_BOOTS.get()) {
            Button icon = new ExtendedButton(width / 2 + 70, height / 2 - 80, 10, 10, Component.literal("I"), this::onIconPress, DEFAULT_NARRATION);
            addRenderableWidget(icon);

            if (JetBootsProperties.hasMuffledUpgrade(container)) {
                Button muffled = new ExtendedButton(width / 2 + 40, height / 2 - 40, 40, 10, getMuffledText(!JetBootsProperties.hasActiveMuffledUpgrade(container)), this::onMuffledPress, DEFAULT_NARRATION);
                addRenderableWidget(muffled);
            }
        }
    }

    private void onIconPress(Button p_onPress_1) {
        Minecraft.getInstance().player.connection.sendCommand("bml overlayConfig");
        this.removed();
    }

    private void onMuffledPress(Button p_onPress_1) {
        boolean current = !JetBootsProperties.hasActiveMuffledUpgrade(inventory.getSelected());
        PacketHandler.sendToServer(new PacketUpdateMuffledMode());
        p_onPress_1.setMessage(getMuffledText(!current));
    }

    private Component getMuffledText(boolean on) {
        return TextComponentBuilder.of("\u266A")
                .with(":")
                .space()
                .with(ON, OFF, () -> on)
                .build();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(pGuiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
        int fontHeight = this.font.lineHeight;
        pGuiGraphics.drawString(this.font, this.inventory.getDisplayName(), 8, 58, 4210752, false);
        pGuiGraphics.drawString(this.font, this.getTitle(), 8, 6, 4210752, false);
        ItemStack container = inventory.getSelected();
        Optional.ofNullable(container)
                .filter(stack -> stack.getItem() instanceof ILevelableItem)
                .filter(stack -> ItemLevelProperties.getMaxLevel(stack) > 0)
                .ifPresent(i -> {
                    pGuiGraphics.drawString(this.font, ItemLevelProperties.getLevelProgress(container, ChatFormatting.DARK_GRAY), 8, 6 + fontHeight + 2, 4210752, false);
                });

        Optional.ofNullable(container)
                .ifPresent(i -> i.getCapability(ForgeCapabilities.ENERGY, null)
                        .ifPresent(energy -> {
                            Component energyText = TranslationUtil.translate(STORED_ENERGY, ChatFormatting.GREEN,
                                    MathUtil.formatThousands(energy.getEnergyStored()),
                                    MathUtil.formatThousands(energy.getMaxEnergyStored()));
                            pGuiGraphics.drawString(this.font, energyText, 8, 6 + 2 * (fontHeight + 2), 4210752, false);
                        }));
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics,float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}

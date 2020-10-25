package com.black_dog20.jetboots.client.screens;

import com.black_dog20.bml.client.widgets.SmallButton;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.containers.JetBootsContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.Optional;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class JetBootsScreen extends ContainerScreen<JetBootsContainer> {

    private ResourceLocation GUI = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/jetboots_gui.png");

    public JetBootsScreen(JetBootsContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        Button button = new SmallButton(width / 2 + 70, height / 2 - 80, 10, 10, "I", this::onIconPress);
        addButton(button);
    }

    private void onIconPress(Button p_onPress_1) {
        Minecraft.getInstance().player.sendChatMessage("/bml overlayConfig");
        this.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderComponentHoverEffect(matrixStack, null, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        int fontHeight = this.font.FONT_HEIGHT;
        this.font.func_243246_a(matrixStack, this.playerInventory.getDisplayName(), 8, 58, 0xffffff);
        this.font.func_243246_a(matrixStack, this.getTitle(), 8, 6, 0xffffff);
        ItemStack jetboots = playerInventory.getCurrentItem();
        Optional.ofNullable(jetboots)
                .ifPresent(i -> i.getCapability(CapabilityEnergy.ENERGY, null)
                        .ifPresent(energy -> {
                            ITextComponent energyText = TranslationUtil.translate(STORED_ENERGY, TextFormatting.GREEN,
                                    MathUtil.formatThousands(energy.getEnergyStored()),
                                    MathUtil.formatThousands(energy.getMaxEnergyStored()));
                            this.font.func_243246_a(matrixStack, energyText, 8, 6 + fontHeight + 2, 0xffffff);
                        }));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack,float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);
    }
}

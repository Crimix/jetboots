package com.black_dog20.jetboots.client.screens;

import com.black_dog20.bml.client.widgets.SmallButton;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.api.ILevelableItem;
import com.black_dog20.jetboots.client.containers.EnchantableItemContainer;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateMuffledMode;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.Optional;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class EnchantableItemScreen extends ContainerScreen<EnchantableItemContainer> {

    private ResourceLocation GUI = new ResourceLocation(Jetboots.MOD_ID, "textures/gui/container_gui.png");

    public EnchantableItemScreen(EnchantableItemContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        ItemStack container = playerInventory.getCurrentItem();
        if (container.getItem() == ModItems.JET_BOOTS.get()) {
            Button icon = new SmallButton(width / 2 + 70, height / 2 - 80, 10, 10, new StringTextComponent("I"), this::onIconPress);
            addButton(icon);

            if (JetBootsProperties.hasMuffledUpgrade(container)) {
                Button muffled = new SmallButton(width / 2 + 40, height / 2 - 40, 40, 10, getMuffledText(!JetBootsProperties.hasActiveMuffledUpgrade(container)), this::onMuffledPress);
                addButton(muffled);
            }
        }
    }

    private void onIconPress(Button p_onPress_1) {
        Minecraft.getInstance().player.sendChatMessage("/bml overlayConfig");
        this.onClose();
    }

    private void onMuffledPress(Button p_onPress_1) {
        boolean current = !JetBootsProperties.hasActiveMuffledUpgrade(playerInventory.getCurrentItem());
        PacketHandler.sendToServer(new PacketUpdateMuffledMode());
        p_onPress_1.setMessage(getMuffledText(!current));
    }

    private ITextComponent getMuffledText(boolean on) {
        return TextComponentBuilder.of("\u266A")
                .with(":")
                .space()
                .with(ON, OFF, () -> on)
                .build();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        int fontHeight = this.font.FONT_HEIGHT;
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), 8, 58, 0xffffff);
        this.font.func_243248_b(matrixStack, this.getTitle(), 8, 6, 0xffffff);
        ItemStack container = playerInventory.getCurrentItem();
        Optional.ofNullable(container)
                .filter(stack -> stack.getItem() instanceof ILevelableItem)
                .ifPresent(i -> {
                    this.font.func_243248_b(matrixStack, TranslationHelper.getLevelProgress(container, TextFormatting.WHITE), 8, 6 + fontHeight + 2, 0xffffff);
                });

        Optional.ofNullable(container)
                .ifPresent(i -> i.getCapability(CapabilityEnergy.ENERGY, null)
                        .ifPresent(energy -> {
                            ITextComponent energyText = TranslationUtil.translate(STORED_ENERGY, TextFormatting.GREEN,
                                    MathUtil.formatThousands(energy.getEnergyStored()),
                                    MathUtil.formatThousands(energy.getMaxEnergyStored()));
                            this.font.func_243248_b(matrixStack, energyText, 8, 6 + 2 * (fontHeight + 2), 0xffffff);
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

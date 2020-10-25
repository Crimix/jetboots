package com.black_dog20.jetboots.client.renders;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class GuardianHelmetLayerRender<T extends PlayerEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Jetboots.MOD_ID, "textures/layers/guardian_helmet_layer.png");
    private final GuardianHelmetModel<T> model = new GuardianHelmetModel(0.1F);


    public GuardianHelmetLayerRender(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = entitylivingbase.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (itemstack.getItem() == ModItems.GUARDIAN_HELMET.get()) {
            if (GuardinanHelmetProperties.getMode(itemstack)) {
                this.model.isSneak = entitylivingbase.isCrouching();
                matrixStack.push();
                this.getEntityModel().setModelAttributes(this.model);
                this.model.render(entitylivingbase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, this.model.getRenderType(TEXTURE), false, itemstack.hasEffect());
                this.model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.pop();
            }
        }
    }

    private static boolean showBefore = true;
    private static boolean update = true;

    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
        ItemStack itemstack = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (itemstack.getItem() == ModItems.GUARDIAN_HELMET.get()) {
            if (GuardinanHelmetProperties.getMode(itemstack)) {
                showBefore = event.getRenderer().getEntityModel().bipedHeadwear.showModel;
                update = true;
                event.getRenderer().getEntityModel().bipedHeadwear.showModel = false;
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerPost(RenderPlayerEvent.Post event) {
        if (update) {
            update = false;
            event.getRenderer().getEntityModel().bipedHeadwear.showModel = showBefore;
        }
    }
}

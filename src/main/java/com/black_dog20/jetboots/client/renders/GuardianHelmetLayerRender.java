package com.black_dog20.jetboots.client.renders;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
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
    private static final ResourceLocation TEXTURE = new ResourceLocation(Jetboots.MOD_ID, "textures/layers/guardian_layer.png");
    private final PlayerModel<T> model;


    public GuardianHelmetLayerRender(IEntityRenderer<T, M> renderer, boolean smallArms) {
        super(renderer);
        model = new PlayerModel(0.1F, smallArms);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (shouldRender(entitylivingbase)) {
            prepareModel(entitylivingbase);
            this.model.isSneak = entitylivingbase.isCrouching();
            matrixStack.push();
            this.getEntityModel().setModelAttributes(this.model);
            this.model.setRotationAngles(entitylivingbase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, this.model.getRenderType(TEXTURE), false, false);
            this.model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }

        /*ItemStack itemstack = entitylivingbase.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (itemstack.getItem() == ModItems.GUARDIAN_HELMET.get()) {
            if (GuardinanHelmetProperties.getMode(itemstack)) {
                this.model.isSneak = entitylivingbase.isCrouching();
                matrixStack.push();
                this.getEntityModel().setModelAttributes(this.model);
                this.model.setRotationAngles(entitylivingbase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, this.model.getRenderType(TEXTURE), false, false);
                this.model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.pop();
            }
        }*/
    }

    private boolean shouldRender(T entitylivingbase) {
        return hasHelmet(entitylivingbase)
                || hasJacket(entitylivingbase)
                || hasPants(entitylivingbase);
    }


    private void prepareModel(T entitylivingbase){
        boolean hasHelmet = hasHelmet(entitylivingbase);
        boolean hasJacket = hasJacket(entitylivingbase);
        boolean hasPants = hasPants(entitylivingbase);
        model.bipedHead.showModel = hasHelmet;
        model.bipedHeadwear.showModel = hasHelmet;
        model.bipedBody.showModel = hasJacket;
        model.bipedRightArm.showModel = hasJacket;
        model.bipedLeftArm.showModel = hasJacket;
        model.bipedRightLeg.showModel = hasPants;
        model.bipedLeftLeg.showModel = hasPants;
    }

    private static boolean hasHelmet(PlayerEntity entitylivingbase){
        ItemStack helmet = entitylivingbase.getItemStackFromSlot(EquipmentSlotType.HEAD);
        return helmet.getItem() == ModItems.GUARDIAN_HELMET.get() && GuardinanHelmetProperties.getMode(helmet);
    }

    private static boolean hasJacket(PlayerEntity entitylivingbase){
        return entitylivingbase.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ModItems.GUARDIAN_JACKET.get();
    }

    private static boolean hasPants(PlayerEntity entitylivingbase){
        return entitylivingbase.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == ModItems.GUARDIAN_PANTS.get();
    }

    private static ModelStateHolder headWear = new ModelStateHolder();
    private static ModelStateHolder bodyWear = new ModelStateHolder();
    private static ModelStateHolder rightArmWear = new ModelStateHolder();
    private static ModelStateHolder leftArmWear = new ModelStateHolder();
    private static ModelStateHolder rightLegWear = new ModelStateHolder();
    private static ModelStateHolder leftLegWear = new ModelStateHolder();

    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
        PlayerEntity playerEntity = event.getPlayer();
        PlayerModel<AbstractClientPlayerEntity> playerModel = event.getRenderer().getEntityModel();
        if (hasHelmet(playerEntity)) {
            headWear.update(playerModel.bipedHeadwear);
        }
        if (hasJacket(playerEntity)) {
            bodyWear.update(playerModel.bipedBodyWear);
            rightArmWear.update(playerModel.bipedRightArmwear);
            leftArmWear.update(playerModel.bipedLeftArmwear);
        }
        if (hasPants(playerEntity)) {
            rightLegWear.update(playerModel.bipedRightLegwear);
            leftLegWear.update(playerModel.bipedLeftLegwear);
        }
    }

    @SubscribeEvent
    public static void renderPlayerPost(RenderPlayerEvent.Post event) {
        PlayerModel<AbstractClientPlayerEntity> playerModel = event.getRenderer().getEntityModel();
        headWear.restore(playerModel.bipedHeadwear);
        bodyWear.restore(playerModel.bipedBodyWear);
        rightArmWear.restore(playerModel.bipedRightArmwear);
        leftArmWear.restore(playerModel.bipedLeftArmwear);
        rightLegWear.restore(playerModel.bipedRightLegwear);
        leftLegWear.restore(playerModel.bipedLeftLegwear);
    }

    private static class ModelStateHolder {

        boolean update;
        boolean showBefore;

        private void update(ModelRenderer modelRenderer) {
            showBefore = modelRenderer.showModel;
            update = true;
            modelRenderer.showModel = false;
        }

        private void restore(ModelRenderer modelRenderer) {
            if(update) {
                update = false;
                modelRenderer.showModel = showBefore;
            }
        }
    }
}

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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuardianLayerRender<T extends PlayerEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Jetboots.MOD_ID, "textures/layers/guardian_layer.png");
    private final PlayerModel<T> model;

    public GuardianLayerRender(IEntityRenderer<T, M> renderer, boolean smallArms) {
        super(renderer);
        model = new PlayerModel(0.1F, smallArms);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, @Nonnull T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        prepareModel(entitylivingbase, model);
        if (shouldRender(entitylivingbase)) {
            this.model.isSneak = entitylivingbase.isCrouching();
            matrixStack.push();
            this.getEntityModel().setModelAttributes(this.model);
            this.model.setRotationAngles(entitylivingbase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.getEntityModel().setModelAttributes(this.model); //Do it again because mods like Quark reset the rotateZ angle at every render tick see https://github.com/Vazkii/Quark/issues/2765
            IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(buffer, this.model.getRenderType(TEXTURE), false, false);
            this.model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }

    private boolean shouldRender(PlayerEntity entitylivingbase) {
        return hasHelmet(entitylivingbase)
                || hasJacket(entitylivingbase)
                || hasPants(entitylivingbase);
    }


    private void prepareModel(PlayerEntity entitylivingbase, PlayerModel<T> model){
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

    private ModelStateHolder headWear = new ModelStateHolder();
    private ModelStateHolder bodyWear = new ModelStateHolder();
    private ModelStateHolder rightArmWear = new ModelStateHolder();
    private ModelStateHolder leftArmWear = new ModelStateHolder();
    private ModelStateHolder rightLegWear = new ModelStateHolder();
    private ModelStateHolder leftLegWear = new ModelStateHolder();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderPlayerPre(RenderPlayerEvent.Pre event) {
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
    public void renderPlayerPost(RenderPlayerEvent.Post event) {
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

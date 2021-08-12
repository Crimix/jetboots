package com.black_dog20.jetboots.client.renders;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.properties.GuardinanHelmetProperties;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuardianLayerRender<T extends Player, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Jetboots.MOD_ID, "textures/layers/guardian_layer.png");
    private final PlayerModel<T> model;

    public GuardianLayerRender(RenderLayerParent<T, M> renderer, EntityModelSet entityModelSet, boolean smallArms) {
        super(renderer);
        model = new PlayerModel<>(entityModelSet.bakeLayer(smallArms ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), smallArms);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        prepareModel(entitylivingbase, model);
        if (shouldRender(entitylivingbase)) {
            this.model.crouching = entitylivingbase.isCrouching();
            matrixStack.pushPose();
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entitylivingbase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.getParentModel().copyPropertiesTo(this.model); //Do it again because mods like Quark reset the rotateZ angle at every render tick see https://github.com/Vazkii/Quark/issues/2765
            VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(buffer, this.model.renderType(TEXTURE), false, false);
            this.model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.popPose();
        }
    }

    private boolean shouldRender(Player entitylivingbase) {
        return hasHelmet(entitylivingbase)
                || hasJacket(entitylivingbase)
                || hasPants(entitylivingbase);
    }


    private void prepareModel(Player entitylivingbase, PlayerModel<T> model){
        boolean hasHelmet = hasHelmet(entitylivingbase);
        boolean hasJacket = hasJacket(entitylivingbase);
        boolean hasPants = hasPants(entitylivingbase);
        model.head.visible = hasHelmet;
        model.hat.visible = hasHelmet;
        model.body.visible = hasJacket;
        model.rightArm.visible = hasJacket;
        model.leftArm.visible = hasJacket;
        model.rightLeg.visible = hasPants;
        model.leftLeg.visible = hasPants;
    }

    private static boolean hasHelmet(Player entitylivingbase){
        ItemStack helmet = entitylivingbase.getItemBySlot(EquipmentSlot.HEAD);
        return helmet.getItem() == ModItems.GUARDIAN_HELMET.get() && GuardinanHelmetProperties.getMode(helmet);
    }

    private static boolean hasJacket(Player entitylivingbase){
        return entitylivingbase.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.GUARDIAN_JACKET.get();
    }

    private static boolean hasPants(Player entitylivingbase){
        return entitylivingbase.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.GUARDIAN_PANTS.get();
    }

    private final ModelStateHolder headWear = new ModelStateHolder();
    private final ModelStateHolder bodyWear = new ModelStateHolder();
    private final ModelStateHolder rightArmWear = new ModelStateHolder();
    private final ModelStateHolder leftArmWear = new ModelStateHolder();
    private final ModelStateHolder rightLegWear = new ModelStateHolder();
    private final ModelStateHolder leftLegWear = new ModelStateHolder();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderPlayerPre(RenderPlayerEvent.Pre event) {
        Player playerEntity = event.getPlayer();
        PlayerModel<AbstractClientPlayer> playerModel = event.getRenderer().getModel();
        if (hasHelmet(playerEntity)) {
            headWear.update(playerModel.hat);
        }
        if (hasJacket(playerEntity)) {
            bodyWear.update(playerModel.jacket);
            rightArmWear.update(playerModel.rightSleeve);
            leftArmWear.update(playerModel.leftSleeve);
        }
        if (hasPants(playerEntity)) {
            rightLegWear.update(playerModel.rightPants);
            leftLegWear.update(playerModel.leftPants);
        }
    }

    @SubscribeEvent
    public void renderPlayerPost(RenderPlayerEvent.Post event) {
        PlayerModel<AbstractClientPlayer> playerModel = event.getRenderer().getModel();
        headWear.restore(playerModel.hat);
        bodyWear.restore(playerModel.jacket);
        rightArmWear.restore(playerModel.rightSleeve);
        leftArmWear.restore(playerModel.leftSleeve);
        rightLegWear.restore(playerModel.rightPants);
        leftLegWear.restore(playerModel.leftPants);
    }

    private static class ModelStateHolder {

        boolean update;
        boolean showBefore;

        private void update(ModelPart modelRenderer) {
            showBefore = modelRenderer.visible;
            update = true;
            modelRenderer.visible = false;
        }

        private void restore(ModelPart modelRenderer) {
            if(update) {
                update = false;
                modelRenderer.visible = showBefore;
            }
        }
    }
}

package com.black_dog20.jetboots.client.renders;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class GuardianModel<T extends LivingEntity> extends AgeableModel<T> implements IHasHead {

    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public boolean isSneak;
    public float swimAnimation;
    private float remainingItemUseTime;

    public GuardianModel(float modelSize) {
        this(RenderType::entityTranslucent, modelSize, 0.0F, 64, 64);
    }

    public GuardianModel(Function<ResourceLocation, RenderType> renderTypeIn, float modelSizeIn, float yOffsetIn, int textureWidthIn, int textureHeightIn) {
        super(renderTypeIn, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSizeIn);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + yOffsetIn, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSizeIn + 0.25F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + yOffsetIn, 0.0F);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(bipedHead);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(bipedHeadwear);
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.bipedHead;
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.swimAnimation = entityIn.getSwimAnimation(partialTick);
        this.remainingItemUseTime = (float) entityIn.getItemInUseMaxCount();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entityIn.getTicksElytraFlying() > 4;
        boolean flag1 = entityIn.isActualySwimming();
        this.bipedHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        if (flag) {
            this.bipedHead.rotateAngleX = (-(float) Math.PI / 4F);
        } else if (this.swimAnimation > 0.0F) {
            if (flag1) {
                this.bipedHead.rotateAngleX = this.rotLerpRad(this.bipedHead.rotateAngleX, (-(float) Math.PI / 4F), this.swimAnimation);
            } else {
                this.bipedHead.rotateAngleX = this.rotLerpRad(this.bipedHead.rotateAngleX, headPitch * ((float) Math.PI / 180F), this.swimAnimation);
            }
        } else {
            this.bipedHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        }

        if (this.isSneak) {
            this.bipedHead.rotationPointY = 4.2F;
        } else {
            this.bipedHead.rotationPointY = 0.0F;
        }

        this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }

    protected float rotLerpRad(float angleIn, float maxAngleIn, float mulIn) {
        float f = (maxAngleIn - angleIn) % ((float) Math.PI * 2F);
        if (f < -(float) Math.PI) {
            f += ((float) Math.PI * 2F);
        }

        if (f >= (float) Math.PI) {
            f -= ((float) Math.PI * 2F);
        }

        return angleIn + mulIn * f;
    }

    public void setVisible(boolean visible) {
        this.bipedHead.showModel = visible;
        this.bipedHeadwear.showModel = visible;
    }


}

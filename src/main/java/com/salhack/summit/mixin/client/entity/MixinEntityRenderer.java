// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client.entity;

import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraft.world.World;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import com.salhack.summit.main.SummitStatic;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import com.salhack.summit.util.render.RenderUtil;
import com.salhack.summit.events.render.EventRenderHurtCameraEffect;
import com.salhack.summit.events.render.EventRenderHand;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.ArrayList;
import com.salhack.summit.events.render.EventRenderGetEntitiesINAABBexcluding;
import java.util.List;
import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.salhack.summit.SummitMod;
import com.salhack.summit.events.render.EventRenderSetupFog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    @Final
    public Minecraft field_78531_r;
    @Shadow
    public float field_78491_C;
    @Shadow
    public boolean field_78500_U;
    
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFog(final int startCoords, final float partialTicks, final CallbackInfo p_Info) {
        final EventRenderSetupFog l_Event = new EventRenderSetupFog(startCoords, partialTicks);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        final EventRenderGetEntitiesINAABBexcluding l_Event = new EventRenderGetEntitiesINAABBexcluding(worldClient, entityIn, boundingBox, predicate);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.func_175674_a(entityIn, boundingBox, predicate);
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") }, cancellable = true)
    private void renderHand(final float partialTicks, final int pass, final CallbackInfo p_Info) {
        final EventRenderHand l_Event = new EventRenderHand(partialTicks, pass);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo info) {
        final EventRenderHurtCameraEffect l_Event = new EventRenderHurtCameraEffect(ticks);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.AFTER) })
    private void renderWorldPassPost(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo callbackInfo) {
        RenderUtil.updateModelViewProjectionMatrix();
    }
    
    @Overwrite
    public void func_78467_g(final float partialTicks) {
        final Entity entity = this.field_78531_r.func_175606_aa();
        float f = entity.func_70047_e();
        double d0 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * partialTicks;
        double d2 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * partialTicks + f;
        double d3 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * partialTicks;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70608_bn()) {
            ++f;
            GlStateManager.func_179109_b(0.0f, 0.3f, 0.0f);
            if (!this.field_78531_r.field_71474_y.field_74325_U) {
                final BlockPos blockpos = new BlockPos(entity);
                final IBlockState iblockstate = this.field_78531_r.field_71441_e.func_180495_p(blockpos);
                ForgeHooksClient.orientBedCamera((IBlockAccess)this.field_78531_r.field_71441_e, blockpos, iblockstate, entity);
                GlStateManager.func_179114_b(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.func_179114_b(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.field_78531_r.field_71474_y.field_74320_O > 0) {
            double d4 = (double)((SummitStatic.VIEWCLIP != null && SummitStatic.VIEWCLIP.isEnabled()) ? SummitStatic.VIEWCLIP.Distance.getValue() : ((double)(this.field_78491_C + (4.0f - this.field_78491_C) * partialTicks)));
            if (this.field_78531_r.field_71474_y.field_74325_U) {
                GlStateManager.func_179109_b(0.0f, 0.0f, (float)(-d4));
            }
            else {
                final float f2 = entity.field_70177_z;
                float f3 = entity.field_70125_A;
                if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
                    f3 += 180.0f;
                }
                final double d5 = -MathHelper.func_76126_a(f2 * 0.017453292f) * MathHelper.func_76134_b(f3 * 0.017453292f) * d4;
                final double d6 = MathHelper.func_76134_b(f2 * 0.017453292f) * MathHelper.func_76134_b(f3 * 0.017453292f) * d4;
                final double d7 = -MathHelper.func_76126_a(f3 * 0.017453292f) * d4;
                for (int i = 0; i < 8; ++i) {
                    float f4 = (float)((i & 0x1) * 2 - 1);
                    float f5 = (float)((i >> 1 & 0x1) * 2 - 1);
                    float f6 = (float)((i >> 2 & 0x1) * 2 - 1);
                    f4 *= 0.1f;
                    f5 *= 0.1f;
                    f6 *= 0.1f;
                    if (SummitStatic.VIEWCLIP == null || !SummitStatic.VIEWCLIP.isEnabled()) {
                        final RayTraceResult raytraceresult = this.field_78531_r.field_71441_e.func_72933_a(new Vec3d(d0 + f4, d2 + f5, d3 + f6), new Vec3d(d0 - d5 + f4 + f6, d2 - d7 + f5, d3 - d6 + f6));
                        if (raytraceresult != null) {
                            final double d8 = raytraceresult.field_72307_f.func_72438_d(new Vec3d(d0, d2, d3));
                            if (d8 < d4) {
                                d4 = d8;
                            }
                        }
                    }
                }
                if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
                    GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.func_179114_b(entity.field_70125_A - f3, 1.0f, 0.0f, 0.0f);
                GlStateManager.func_179114_b(entity.field_70177_z - f2, 0.0f, 1.0f, 0.0f);
                GlStateManager.func_179109_b(0.0f, 0.0f, (float)(-d4));
                GlStateManager.func_179114_b(f2 - entity.field_70177_z, 0.0f, 1.0f, 0.0f);
                GlStateManager.func_179114_b(f3 - entity.field_70125_A, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.func_179109_b(0.0f, 0.0f, 0.05f);
        }
        if (!this.field_78531_r.field_71474_y.field_74325_U) {
            float yaw = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks + 180.0f;
            final float pitch = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
            final float roll = 0.0f;
            if (entity instanceof EntityAnimal) {
                final EntityAnimal entityanimal = (EntityAnimal)entity;
                yaw = entityanimal.field_70758_at + (entityanimal.field_70759_as - entityanimal.field_70758_at) * partialTicks + 180.0f;
            }
            final IBlockState state = ActiveRenderInfo.func_186703_a((World)this.field_78531_r.field_71441_e, entity, partialTicks);
            final EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup((EntityRenderer)this, entity, state, (double)partialTicks, yaw, pitch, roll);
            MinecraftForge.EVENT_BUS.post((Event)event);
            GlStateManager.func_179114_b(event.getRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.func_179114_b(event.getPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.func_179114_b(event.getYaw(), 0.0f, 1.0f, 0.0f);
        }
        GlStateManager.func_179109_b(0.0f, -f, 0.0f);
        d0 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * partialTicks;
        d2 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * partialTicks + f;
        d3 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * partialTicks;
        this.field_78500_U = this.field_78531_r.field_71438_f.func_72721_a(d0, d2, d3, partialTicks);
    }
}

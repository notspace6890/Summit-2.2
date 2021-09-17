// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client.entity;

import com.salhack.summit.SummitMod;
import com.salhack.summit.events.entity.EventEntityCollisionBorderSize;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.salhack.summit.util.CameraUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public double field_70165_t;
    @Shadow
    public double field_70163_u;
    @Shadow
    public double field_70161_v;
    @Shadow
    public double field_70169_q;
    @Shadow
    public double field_70167_r;
    @Shadow
    public double field_70166_s;
    @Shadow
    public double field_70142_S;
    @Shadow
    public double field_70137_T;
    @Shadow
    public double field_70136_U;
    @Shadow
    public float field_70126_B;
    @Shadow
    public float field_70127_C;
    @Shadow
    public float field_70125_A;
    @Shadow
    public float field_70177_z;
    @Shadow
    public boolean field_70122_E;
    @Shadow
    public double field_70159_w;
    @Shadow
    public double field_70181_x;
    @Shadow
    public double field_70179_y;
    @Shadow
    public World field_70170_p;
    
    @Shadow
    @Override
    public abstract boolean equals(final Object p0);
    
    @Shadow
    public abstract boolean func_70051_ag();
    
    @Shadow
    public abstract boolean func_184218_aH();
    
    @Shadow
    public void func_70091_d(final MoverType type, final double x, final double y, final double z) {
    }
    
    @Shadow
    public abstract AxisAlignedBB func_174813_aQ();
    
    @Shadow
    public abstract boolean func_70083_f(final int p0);
    
    @Shadow
    public abstract Entity func_184208_bv();
    
    @Inject(method = { "turn" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F", ordinal = 0) })
    private void overrideYaw(final float yawChange, final float pitchChange, final CallbackInfo ci) {
        if (this instanceof EntityPlayerSP && CameraUtils.shouldPreventPlayerMovement()) {
            this.field_70177_z = this.field_70126_B;
            this.field_70125_A = this.field_70127_C;
            CameraUtils.updateCameraRotations(yawChange, pitchChange);
        }
    }
    
    @Inject(method = { "getCollisionBorderSize" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBorderSize(final CallbackInfoReturnable<Float> callback) {
        final EventEntityCollisionBorderSize event = new EventEntityCollisionBorderSize();
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue((Object)event.getSize());
        }
    }
}

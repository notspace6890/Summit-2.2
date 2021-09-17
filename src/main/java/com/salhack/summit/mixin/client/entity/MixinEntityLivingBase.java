// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client.entity;

import com.salhack.summit.events.entity.EventItemUseFinish;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.salhack.summit.SummitMod;
import com.salhack.summit.events.player.EventPlayerIsPotionActive;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends MixinEntity
{
    @Shadow
    protected ItemStack field_184627_bm;
    @Shadow
    public float field_70702_br;
    @Shadow
    public float field_70701_bs;
    @Shadow
    public float field_191988_bg;
    
    @Shadow
    public void func_70664_aZ() {
    }
    
    @Inject(method = { "isPotionActive" }, at = { @At("HEAD") }, cancellable = true)
    public void isPotionActive(final Potion potionIn, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final EventPlayerIsPotionActive l_Event = new EventPlayerIsPotionActive(potionIn);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "onItemUseFinish" }, at = { @At("HEAD") }, cancellable = true)
    protected void onItemUseFinish(final CallbackInfo info) {
        final EventItemUseFinish event = new EventItemUseFinish((EntityLivingBase)this, this.field_184627_bm);
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Shadow
    public abstract boolean func_184613_cA();
}

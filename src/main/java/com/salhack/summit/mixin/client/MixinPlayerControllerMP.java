// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client;

import com.salhack.summit.events.player.EventPlayerOnStoppedUsingItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.item.ItemSword;
import com.salhack.summit.events.player.EventPlayerDestroyBlock;
import com.salhack.summit.events.player.EventPlayerClickBlock;
import com.salhack.summit.events.player.EventPlayerResetBlockRemoving;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.salhack.summit.SummitMod;
import com.salhack.summit.events.player.EventPlayerDamageBlock;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.player.EntityPlayer;
import com.salhack.summit.main.SummitStatic;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Shadow
    @Final
    private Minecraft field_78776_a;
    @Shadow
    private GameType field_78779_k;
    @Shadow
    private BlockPos field_178895_c;
    
    @Inject(method = { "getBlockReachDistance" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemoving(final CallbackInfoReturnable<Float> callbackInfo) {
        if (SummitStatic.REACH != null && SummitStatic.REACH.isEnabled()) {
            final float attrib = (float)this.field_78776_a.field_71439_g.func_110148_a(EntityPlayer.REACH_DISTANCE).func_111126_e() + ((SummitStatic.REACH != null && SummitStatic.REACH.isEnabled()) ? SummitStatic.REACH.ReachAdd.getValue() : 0.0f);
            callbackInfo.setReturnValue((Object)(this.field_78779_k.func_77145_d() ? attrib : (attrib - 0.5f)));
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> p_Info) {
        final EventPlayerDamageBlock l_Event = new EventPlayerDamageBlock(posBlock, directionFacing);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.setReturnValue((Object)false);
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemoving(final CallbackInfo callbackInfo) {
        final EventPlayerResetBlockRemoving event = new EventPlayerResetBlockRemoving();
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void clickBlock(final BlockPos loc, final EnumFacing face, final CallbackInfoReturnable<Boolean> callbackInfo) {
        final EventPlayerClickBlock event = new EventPlayerClickBlock(loc, face);
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.setReturnValue((Object)false);
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfo) {
        final EventPlayerDestroyBlock event = new EventPlayerDestroyBlock(pos);
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
            callbackInfo.setReturnValue((Object)false);
        }
        if (SummitStatic.NOGLITCHBLOCKS != null && SummitStatic.NOGLITCHBLOCKS.isEnabled()) {
            callbackInfo.cancel();
            if (this.field_78779_k.func_82752_c()) {
                if (this.field_78779_k == GameType.SPECTATOR) {
                    callbackInfo.setReturnValue((Object)false);
                }
                if (!this.field_78776_a.field_71439_g.func_175142_cm()) {
                    final ItemStack itemstack = this.field_78776_a.field_71439_g.func_184614_ca();
                    if (itemstack.func_190926_b()) {
                        callbackInfo.setReturnValue((Object)false);
                    }
                    if (!itemstack.func_179544_c(this.field_78776_a.field_71441_e.func_180495_p(pos).func_177230_c())) {
                        callbackInfo.setReturnValue((Object)false);
                    }
                }
            }
            if (this.field_78779_k.func_77145_d() && !this.field_78776_a.field_71439_g.func_184614_ca().func_190926_b() && this.field_78776_a.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
                callbackInfo.setReturnValue((Object)false);
            }
            else {
                final World world = (World)this.field_78776_a.field_71441_e;
                final IBlockState iblockstate = world.func_180495_p(pos);
                final Block block = iblockstate.func_177230_c();
                if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !this.field_78776_a.field_71439_g.func_189808_dh()) {
                    callbackInfo.setReturnValue((Object)false);
                }
                else if (iblockstate.func_185904_a() == Material.field_151579_a) {
                    callbackInfo.setReturnValue((Object)false);
                }
                else {
                    world.func_175718_b(2001, pos, Block.func_176210_f(iblockstate));
                    block.func_176208_a(world, pos, iblockstate, (EntityPlayer)this.field_78776_a.field_71439_g);
                    boolean flag = false;
                    final boolean skipClientDestroy = SummitStatic.NOGLITCHBLOCKS != null && SummitStatic.NOGLITCHBLOCKS.isEnabled() && SummitStatic.NOGLITCHBLOCKS.Destroy.getValue();
                    if (!skipClientDestroy) {
                        flag = world.func_180501_a(pos, Blocks.field_150350_a.func_176223_P(), 11);
                        if (flag) {
                            block.func_176206_d(world, pos, iblockstate);
                        }
                    }
                    this.field_178895_c = new BlockPos(this.field_178895_c.func_177958_n(), -1, this.field_178895_c.func_177952_p());
                    if (!this.field_78779_k.func_77145_d()) {
                        final ItemStack itemstack2 = this.field_78776_a.field_71439_g.func_184614_ca();
                        if (!itemstack2.func_190926_b()) {
                            itemstack2.func_179548_a(world, iblockstate, pos, (EntityPlayer)this.field_78776_a.field_71439_g);
                            if (itemstack2.func_190926_b()) {
                                this.field_78776_a.field_71439_g.func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
                            }
                        }
                    }
                    callbackInfo.setReturnValue((Object)flag);
                }
            }
        }
    }
    
    @Inject(method = { "onStoppedUsingItem" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final EntityPlayer playerIn, final CallbackInfo info) {
        final EventPlayerOnStoppedUsingItem event = new EventPlayerOnStoppedUsingItem();
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}

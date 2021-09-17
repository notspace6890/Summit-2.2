// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import com.salhack.summit.SummitMod;
import com.salhack.summit.events.blocks.EventPlaceBlockAt;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemBlock.class })
public abstract class MixinItemBlock
{
    @Shadow
    @Final
    protected Block field_150939_a;
    
    @Overwrite
    public EnumActionResult func_180614_a(final EntityPlayer player, final World worldIn, BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final IBlockState iblockstate = worldIn.func_180495_p(pos);
        final Block block = iblockstate.func_177230_c();
        if (!block.func_176200_f((IBlockAccess)worldIn, pos)) {
            pos = pos.func_177972_a(facing);
        }
        final ItemStack itemstack = player.func_184586_b(hand);
        if (!itemstack.func_190926_b() && player.func_175151_a(pos, facing, itemstack) && worldIn.func_190527_a(this.field_150939_a, pos, false, facing, (Entity)null)) {
            final int i = ((ItemBlock)this).func_77647_b(itemstack.func_77960_j());
            IBlockState iblockstate2 = this.field_150939_a.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, (EntityLivingBase)player, hand);
            if (this.placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate2)) {
                iblockstate2 = worldIn.func_180495_p(pos);
                final SoundType soundtype = iblockstate2.func_177230_c().getSoundType(iblockstate2, worldIn, pos, (Entity)player);
                worldIn.func_184133_a(player, pos, soundtype.func_185841_e(), SoundCategory.BLOCKS, (soundtype.func_185843_a() + 1.0f) / 2.0f, soundtype.func_185847_b() * 0.8f);
                itemstack.func_190918_g(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
    
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final IBlockState newState) {
        final EventPlaceBlockAt event = new EventPlaceBlockAt();
        SummitMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            final IBlockState iblockstate1 = worldIn.func_180495_p(pos);
            final SoundType soundtype = iblockstate1.func_177230_c().getSoundType(iblockstate1, worldIn, pos, (Entity)player);
            worldIn.func_184133_a(player, pos, soundtype.func_185841_e(), SoundCategory.BLOCKS, (soundtype.func_185843_a() + 1.0f) / 2.0f, soundtype.func_185847_b() * 0.8f);
            return false;
        }
        if (!worldIn.func_180501_a(pos, newState, 11)) {
            return false;
        }
        final IBlockState state = worldIn.func_180495_p(pos);
        if (state.func_177230_c() == this.field_150939_a) {
            ItemBlock.func_179224_a(worldIn, player, pos, stack);
            this.field_150939_a.func_180633_a(worldIn, pos, state, (EntityLivingBase)player, stack);
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)player, pos, stack);
            }
        }
        return true;
    }
}

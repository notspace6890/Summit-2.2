// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.managers;

import com.salhack.summit.main.Wrapper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class BlockManager
{
    private static Minecraft mc;
    private static BlockPos _currBlock;
    private static boolean _started;
    
    public static void SetCurrentBlock(final BlockPos block) {
        BlockManager._currBlock = block;
        BlockManager._started = false;
    }
    
    public static BlockPos GetCurrBlock() {
        return BlockManager._currBlock;
    }
    
    public static boolean GetState() {
        return BlockManager._currBlock != null && IsDoneBreaking(BlockManager.mc.field_71441_e.func_180495_p(BlockManager._currBlock));
    }
    
    private static boolean IsDoneBreaking(final IBlockState blockState) {
        return blockState.func_177230_c() == Blocks.field_150357_h || blockState.func_177230_c() == Blocks.field_150350_a || blockState.func_177230_c() instanceof BlockLiquid;
    }
    
    public static boolean Update(final float range, final boolean rayTrace) {
        if (BlockManager._currBlock == null) {
            return false;
        }
        final IBlockState state = BlockManager.mc.field_71441_e.func_180495_p(BlockManager._currBlock);
        if (IsDoneBreaking(state) || BlockManager.mc.field_71439_g.func_174818_b(BlockManager._currBlock) > Math.pow(range, range)) {
            BlockManager._currBlock = null;
            return false;
        }
        BlockManager.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        EnumFacing facing = EnumFacing.UP;
        if (rayTrace) {
            final RayTraceResult result = BlockManager.mc.field_71441_e.func_72933_a(new Vec3d(BlockManager.mc.field_71439_g.field_70165_t, BlockManager.mc.field_71439_g.field_70163_u + BlockManager.mc.field_71439_g.func_70047_e(), BlockManager.mc.field_71439_g.field_70161_v), new Vec3d(BlockManager._currBlock.func_177958_n() + 0.5, BlockManager._currBlock.func_177956_o() - 0.5, BlockManager._currBlock.func_177952_p() + 0.5));
            if (result != null && result.field_178784_b != null) {
                facing = result.field_178784_b;
            }
        }
        if (!BlockManager._started) {
            BlockManager._started = true;
            BlockManager.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockManager._currBlock, facing));
        }
        else {
            BlockManager.mc.field_71442_b.func_180512_c(BlockManager._currBlock, facing);
        }
        return true;
    }
    
    static {
        BlockManager.mc = Wrapper.GetMC();
        BlockManager._currBlock = null;
        BlockManager._started = false;
    }
}

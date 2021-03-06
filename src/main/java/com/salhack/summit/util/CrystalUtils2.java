// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import java.util.concurrent.CopyOnWriteArrayList;
import com.salhack.summit.main.Wrapper;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Iterator;
import com.salhack.summit.util.entity.PlayerUtil;
import net.minecraft.util.math.MathHelper;
import com.salhack.summit.module.combat.AutoCrystal;
import com.salhack.summit.main.SummitStatic;
import com.salhack.summit.main.Summit;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import net.minecraft.client.Minecraft;

public class CrystalUtils2
{
    private static Minecraft mc;
    public static List<BlockPos> obbyRock;
    public static List<BlockPos> crystalBlocks;
    
    public static boolean canPlaceCrystalAt(final BlockPos blockpos, final IBlockState state) {
        final World worldIn = (World)CrystalUtils2.mc.field_71441_e;
        final BlockPos blockpos2 = blockpos.func_177984_a();
        final BlockPos blockpos3 = blockpos.func_177984_a().func_177984_a();
        boolean flag = !worldIn.func_175623_d(blockpos2) && !worldIn.func_180495_p(blockpos).func_177230_c().func_176200_f((IBlockAccess)worldIn, blockpos2);
        flag |= (!worldIn.func_175623_d(blockpos3) && !worldIn.func_180495_p(blockpos3).func_177230_c().func_176200_f((IBlockAccess)worldIn, blockpos3));
        if (flag) {
            return false;
        }
        final double d0 = blockpos.func_177958_n();
        final double d2 = blockpos.func_177956_o();
        final double d3 = blockpos.func_177952_p();
        return worldIn.func_72839_b((Entity)null, new AxisAlignedBB(d0, d2, d3, d0 + 1.0, d2 + 2.0, d3 + 1.0)).isEmpty();
    }
    
    public static void onSetBlockState(final BlockPos pos, final IBlockState newState, final int flags) {
        if (newState.func_177230_c() == Blocks.field_150343_Z || newState.func_177230_c() == Blocks.field_150357_h) {
            if (!CrystalUtils2.obbyRock.contains(pos)) {
                CrystalUtils2.obbyRock.add(pos);
            }
        }
        else {
            CrystalUtils2.obbyRock.remove(pos);
            if (CrystalUtils2.crystalBlocks.contains(pos)) {
                CrystalUtils2.crystalBlocks.remove(pos);
            }
        }
    }
    
    public static void updateList(final BlockPos pos, final IBlockState newState) {
        if (newState.func_177230_c() == Blocks.field_150343_Z || newState.func_177230_c() == Blocks.field_150357_h) {
            if (canPlaceCrystalAt(pos, newState)) {
                if (!CrystalUtils2.crystalBlocks.contains(pos)) {
                    CrystalUtils2.crystalBlocks.add(pos);
                    Summit.SendMessage("Added a block at " + pos.toString());
                }
            }
            else {
                Summit.SendMessage("removed a block " + pos.toString());
                CrystalUtils2.crystalBlocks.remove(pos);
            }
        }
        else {
            CrystalUtils2.crystalBlocks.remove(pos);
        }
    }
    
    public static void loadWorld() {
        CrystalUtils2.crystalBlocks.clear();
    }
    
    public static void markBlockRangeForRenderUpdate(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        final long ms = System.currentTimeMillis();
        final int minX = (x1 < x2) ? x1 : x2;
        final int minY = (y1 < y2) ? y1 : y2;
        final int minZ = (z1 < z2) ? z1 : z2;
        final int maxX = (x2 > x1) ? x2 : x1;
        final int maxY = (y2 > y1) ? y2 : y1;
        final int maxZ = (z2 > z1) ? z2 : z1;
        for (int x3 = minX; x3 < maxX; ++x3) {
            int y3 = minY;
            while (y1 < maxY) {
                int z3 = minZ;
                while (z1 < maxZ) {
                    final BlockPos pos = new BlockPos(x3, y3, z3);
                    final IBlockState state = CrystalUtils2.mc.field_71441_e.func_180495_p(pos);
                    if (state.func_177230_c() != Blocks.field_150343_Z && state.func_177230_c() != Blocks.field_150357_h) {
                        CrystalUtils2.obbyRock.remove(pos);
                    }
                    else if (!CrystalUtils2.obbyRock.contains(pos)) {
                        CrystalUtils2.obbyRock.add(pos);
                    }
                    ++z3;
                }
                ++y3;
            }
        }
        System.out.println("[2] Took " + (System.currentTimeMillis() - ms) + " ms to execute.");
    }
    
    public static void onUpdate() {
        if (CrystalUtils2.mc.field_71439_g == null) {
            return;
        }
        if (SummitStatic.AUTOCRYSTAL == null || !SummitStatic.AUTOCRYSTAL.isEnabled()) {
            return;
        }
        final long ms = System.currentTimeMillis();
        for (final BlockPos pos : CrystalUtils2.obbyRock) {
            final IBlockState state = CrystalUtils2.mc.field_71441_e.func_180495_p(pos);
            final boolean alreadyContains = CrystalUtils2.crystalBlocks.contains(pos);
            if (state.func_177230_c() != Blocks.field_150343_Z && state.func_177230_c() != Blocks.field_150357_h) {
                CrystalUtils2.crystalBlocks.remove(pos);
                CrystalUtils2.obbyRock.remove(pos);
            }
            else {
                final float dist = (float)CrystalUtils2.mc.field_71439_g.func_70011_f(pos.func_177958_n() + 0.5, (double)pos.func_177956_o(), pos.func_177952_p() + 0.5);
                if (dist > AutoCrystal.PlaceRadius.getValue()) {
                    if (alreadyContains) {
                        CrystalUtils2.crystalBlocks.remove(pos);
                    }
                    CrystalUtils2.obbyRock.remove(pos);
                }
                else if (!alreadyContains && canPlaceCrystalAt(pos, CrystalUtils2.mc.field_71441_e.func_180495_p(pos))) {
                    if (!AutoCrystal.VerifyCrystalBlocks(CrystalUtils2.mc, pos)) {
                        continue;
                    }
                    CrystalUtils2.crystalBlocks.add(pos);
                }
                else {
                    if (!alreadyContains || (canPlaceCrystalAt(pos, CrystalUtils2.mc.field_71441_e.func_180495_p(pos)) && AutoCrystal.VerifyCrystalBlocks(CrystalUtils2.mc, pos))) {
                        continue;
                    }
                    CrystalUtils2.crystalBlocks.remove(pos);
                }
            }
        }
        final int flooredRadius = MathHelper.func_76141_d((float)AutoCrystal.PlaceRadius.getValue()) + 1;
        final BlockPos playerPosFloored = PlayerUtil.GetLocalPlayerPosFloored();
        for (int x = playerPosFloored.func_177958_n() - flooredRadius; x <= playerPosFloored.func_177958_n() + flooredRadius; ++x) {
            for (int y = playerPosFloored.func_177956_o() - flooredRadius; y <= playerPosFloored.func_177956_o() + flooredRadius; ++y) {
                for (int z = playerPosFloored.func_177952_p() - flooredRadius; z <= playerPosFloored.func_177952_p() + flooredRadius; ++z) {
                    final BlockPos pos2 = new BlockPos(x, y, z);
                    if (!CrystalUtils2.obbyRock.contains(pos2)) {
                        final float dist2 = (float)CrystalUtils2.mc.field_71439_g.func_70011_f(pos2.func_177958_n() + 0.5, (double)pos2.func_177956_o(), pos2.func_177952_p() + 0.5);
                        if (dist2 <= AutoCrystal.PlaceRadius.getValue()) {
                            final IBlockState state2 = CrystalUtils2.mc.field_71441_e.func_180495_p(pos2);
                            if (state2.func_177230_c() == Blocks.field_150343_Z || state2.func_177230_c() == Blocks.field_150357_h) {
                                CrystalUtils2.obbyRock.add(pos2);
                            }
                        }
                    }
                }
            }
        }
        final long diff = System.currentTimeMillis() - ms;
    }
    
    public static void onEntityRemoved(final Entity entity) {
        if (entity instanceof EntityEnderCrystal && CrystalUtils2.mc.field_71439_g.func_70032_d(entity) <= AutoCrystal.PlaceRadius.getValue()) {
            final BlockPos pos = entity.func_180425_c().func_177977_b();
            final IBlockState state = CrystalUtils2.mc.field_71441_e.func_180495_p(pos);
            if (state.func_177230_c() == Blocks.field_150343_Z || state.func_177230_c() == Blocks.field_150357_h) {
                CrystalUtils2.crystalBlocks.add(pos);
            }
        }
    }
    
    static {
        CrystalUtils2.mc = Wrapper.GetMC();
        CrystalUtils2.obbyRock = new CopyOnWriteArrayList<BlockPos>();
        CrystalUtils2.crystalBlocks = new CopyOnWriteArrayList<BlockPos>();
    }
}

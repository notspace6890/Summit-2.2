// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import java.util.Arrays;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.BlockSlab;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import java.util.List;

public class BlockInteractionHelper
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    private static final Minecraft mc;
    
    public static void placeBlockScaffold(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Minecraft.func_71410_x().field_71439_g.field_70165_t, Minecraft.func_71410_x().field_71439_g.field_70163_u + Minecraft.func_71410_x().field_71439_g.func_70047_e(), Minecraft.func_71410_x().field_71439_g.field_70161_v);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72436_e(hitVec) <= 18.0625) {
                    faceVectorPacketInstant(hitVec);
                    processRightClickBlock(neighbor, side2, hitVec);
                    Minecraft.func_71410_x().field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                    BlockInteractionHelper.mc.field_71467_ac = 4;
                    return;
                }
            }
        }
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        final double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        final double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Minecraft.func_71410_x().field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - Minecraft.func_71410_x().field_71439_g.field_70177_z), Minecraft.func_71410_x().field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - Minecraft.func_71410_x().field_71439_g.field_70125_A) };
    }
    
    private static Vec3d getEyesPos() {
        return new Vec3d(Minecraft.func_71410_x().field_71439_g.field_70165_t, Minecraft.func_71410_x().field_71439_g.field_70163_u + Minecraft.func_71410_x().field_71439_g.func_70047_e(), Minecraft.func_71410_x().field_71439_g.field_70161_v);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getLegitRotations(vec);
        Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Minecraft.func_71410_x().field_71439_g.field_70122_E));
    }
    
    private static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().func_187099_a(Minecraft.func_71410_x().field_71439_g, BlockInteractionHelper.mc.field_71441_e, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).func_176209_a(getState(pos), false);
    }
    
    private static Block getBlock(final BlockPos pos) {
        return getState(pos).func_177230_c();
    }
    
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.func_71410_x().field_71442_b;
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return Minecraft.func_71410_x().field_71441_e.func_180495_p(pos);
    }
    
    public static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.func_177972_a(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.func_177972_a(side);
            if (!Minecraft.func_71410_x().field_71441_e.func_180495_p(neighbour).func_185904_a().func_76222_j()) {
                return true;
            }
        }
        return false;
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.func_177958_n();
        final int cy = loc.func_177956_o();
        final int cz = loc.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        circleblocks.add(new BlockPos(x, y + plus_y, z));
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static ValidResult valid(final BlockPos pos) {
        return valid(pos, false);
    }
    
    public static PlaceResult place(final BlockPos pos, final float p_Distance, final boolean p_Rotate, final boolean p_UseSlabRule) {
        return place(pos, p_Distance, p_Rotate, p_UseSlabRule, false);
    }
    
    public static PlaceResult place(final BlockPos pos, final float p_Distance, final boolean p_Rotate, final boolean p_UseSlabRule, final boolean packetSwing) {
        final IBlockState l_State = BlockInteractionHelper.mc.field_71441_e.func_180495_p(pos);
        final boolean l_Replaceable = l_State.func_185904_a().func_76222_j();
        final boolean l_IsSlabAtBlock = l_State.func_177230_c() instanceof BlockSlab;
        if (!l_Replaceable && !l_IsSlabAtBlock) {
            return PlaceResult.NotReplaceable;
        }
        if (!checkForNeighbours(pos)) {
            return PlaceResult.Neighbors;
        }
        if (!l_IsSlabAtBlock) {
            final ValidResult l_Result = valid(pos);
            if (l_Result != ValidResult.Ok && !l_Replaceable) {
                return PlaceResult.CantPlace;
            }
        }
        if (p_UseSlabRule && l_IsSlabAtBlock && !l_State.func_185917_h()) {
            return PlaceResult.CantPlace;
        }
        final Vec3d eyesPos = new Vec3d(BlockInteractionHelper.mc.field_71439_g.field_70165_t, BlockInteractionHelper.mc.field_71439_g.field_70163_u + BlockInteractionHelper.mc.field_71439_g.func_70047_e(), BlockInteractionHelper.mc.field_71439_g.field_70161_v);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (BlockInteractionHelper.mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(BlockInteractionHelper.mc.field_71441_e.func_180495_p(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72438_d(hitVec) <= p_Distance) {
                    final Block neighborPos = BlockInteractionHelper.mc.field_71441_e.func_180495_p(neighbor).func_177230_c();
                    final boolean activated = neighborPos.func_180639_a((World)BlockInteractionHelper.mc.field_71441_e, pos, BlockInteractionHelper.mc.field_71441_e.func_180495_p(pos), (EntityPlayer)BlockInteractionHelper.mc.field_71439_g, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
                    if (BlockInteractionHelper.blackList.contains(neighborPos) || BlockInteractionHelper.shulkerList.contains(neighborPos) || activated) {
                        BlockInteractionHelper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BlockInteractionHelper.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    if (p_Rotate) {
                        faceVectorPacketInstant(hitVec);
                    }
                    final EnumActionResult l_Result2 = BlockInteractionHelper.mc.field_71442_b.func_187099_a(BlockInteractionHelper.mc.field_71439_g, BlockInteractionHelper.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    if (l_Result2 != EnumActionResult.FAIL) {
                        if (packetSwing) {
                            BlockInteractionHelper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                        else {
                            BlockInteractionHelper.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                        }
                        if (activated) {
                            BlockInteractionHelper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BlockInteractionHelper.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        return PlaceResult.Placed;
                    }
                }
            }
        }
        return PlaceResult.CantPlace;
    }
    
    public static boolean IsLiquidOrAir(final BlockPos p_Pos) {
        final IBlockState l_State = BlockInteractionHelper.mc.field_71441_e.func_180495_p(p_Pos);
        return l_State.func_177230_c() == Blocks.field_150355_j || l_State.func_177230_c() == Blocks.field_150353_l || l_State.func_177230_c() == Blocks.field_150350_a;
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing) {
        return getFacingRotations(x, y, z, facing, 1.0);
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing, final double width) {
        return getRotationsForPosition(x + 0.5 + facing.func_176730_m().func_177958_n() * width / 2.0, y + 0.5 + facing.func_176730_m().func_177956_o() * width / 2.0, z + 0.5 + facing.func_176730_m().func_177952_p() * width / 2.0);
    }
    
    public static float[] getRotationsForPosition(final double x, final double y, final double z) {
        return getRotationsForPosition(x, y, z, BlockInteractionHelper.mc.field_71439_g.field_70165_t, BlockInteractionHelper.mc.field_71439_g.field_70163_u + BlockInteractionHelper.mc.field_71439_g.func_70047_e(), BlockInteractionHelper.mc.field_71439_g.field_70161_v);
    }
    
    public static float[] getRotationsForPosition(final double x, final double y, final double z, final double sourceX, final double sourceY, final double sourceZ) {
        final double deltaX = x - sourceX;
        final double deltaY = y - sourceY;
        final double deltaZ = z - sourceZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        final double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        yawToEntity = wrapAngleTo180((float)yawToEntity);
        pitchToEntity = wrapAngleTo180((float)pitchToEntity);
        yawToEntity = (Double.isNaN(yawToEntity) ? 0.0 : yawToEntity);
        pitchToEntity = (Double.isNaN(pitchToEntity) ? 0.0 : pitchToEntity);
        return new float[] { (float)yawToEntity, (float)pitchToEntity };
    }
    
    public static float wrapAngleTo180(float angle) {
        for (angle %= 360.0f; angle >= 180.0f; angle -= 360.0f) {}
        while (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    
    public static ValidResult valid(final BlockPos pos, final boolean ignoreEntityCollision) {
        if (BlockInteractionHelper.mc.field_71441_e == null) {
            return ValidResult.NoEntityCollision;
        }
        if (!BlockInteractionHelper.mc.field_71441_e.func_72855_b(new AxisAlignedBB(pos)) && !ignoreEntityCollision) {
            return ValidResult.NoEntityCollision;
        }
        if (!checkForNeighbours(pos)) {
            return ValidResult.NoNeighbors;
        }
        final IBlockState l_State = BlockInteractionHelper.mc.field_71441_e.func_180495_p(pos);
        if (l_State.func_177230_c() == Blocks.field_150350_a) {
            final BlockPos[] array;
            final BlockPos[] l_Blocks = array = new BlockPos[] { pos.func_177978_c(), pos.func_177968_d(), pos.func_177974_f(), pos.func_177976_e(), pos.func_177984_a(), pos.func_177977_b() };
            for (final BlockPos l_Pos : array) {
                final IBlockState l_State2 = BlockInteractionHelper.mc.field_71441_e.func_180495_p(l_Pos);
                if (l_State2.func_177230_c() != Blocks.field_150350_a) {
                    for (final EnumFacing side : EnumFacing.values()) {
                        final BlockPos neighbor = pos.func_177972_a(side);
                        if (BlockInteractionHelper.mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(BlockInteractionHelper.mc.field_71441_e.func_180495_p(neighbor), false)) {
                            return ValidResult.Ok;
                        }
                    }
                }
            }
            return ValidResult.NoNeighbors;
        }
        return ValidResult.AlreadyBlockThere;
    }
    
    static {
        blackList = Arrays.asList(Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn);
        shulkerList = Arrays.asList(Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA);
        mc = Minecraft.func_71410_x();
    }
    
    public enum ValidResult
    {
        NoEntityCollision, 
        AlreadyBlockThere, 
        NoNeighbors, 
        Ok;
    }
    
    public enum PlaceResult
    {
        NotReplaceable, 
        Neighbors, 
        CantPlace, 
        Placed;
    }
}

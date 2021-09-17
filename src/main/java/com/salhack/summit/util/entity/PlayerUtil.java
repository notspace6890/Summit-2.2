// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import com.salhack.summit.events.player.EventPlayerMotionUpdate;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemElytra;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.Minecraft;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import com.salhack.summit.main.Wrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.text.DecimalFormat;
import net.minecraft.entity.Entity;
import com.salhack.summit.util.MinecraftInstance;

public class PlayerUtil extends MinecraftInstance
{
    private static Entity en;
    static final DecimalFormat Formatter;
    
    public static int GetItemSlot(final Item input) {
        if (PlayerUtil.mc.field_71439_g == null) {
            return 0;
        }
        for (int i = 0; i < PlayerUtil.mc.field_71439_g.field_71069_bz.func_75138_a().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.field_71439_g.field_71069_bz.func_75138_a().get(i);
                    if (!s.func_190926_b()) {
                        if (s.func_77973_b() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetRecursiveItemSlot(final Item input) {
        if (PlayerUtil.mc.field_71439_g == null) {
            return 0;
        }
        for (int i = PlayerUtil.mc.field_71439_g.field_71069_bz.func_75138_a().size() - 1; i > 0; --i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.field_71439_g.field_71069_bz.func_75138_a().get(i);
                    if (!s.func_190926_b()) {
                        if (s.func_77973_b() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetItemSlotNotHotbar(final Item input) {
        if (PlayerUtil.mc.field_71439_g == null) {
            return 0;
        }
        for (int i = 9; i < 36; ++i) {
            final Item item = PlayerUtil.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == input) {
                return i;
            }
        }
        return -1;
    }
    
    public static int GetItemCount(final Item input) {
        if (PlayerUtil.mc.field_71439_g == null) {
            return 0;
        }
        int items = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = PlayerUtil.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() == input) {
                items += stack.func_190916_E();
            }
        }
        return items;
    }
    
    public static boolean CanSeeBlock(final BlockPos p_Pos) {
        if (PlayerUtil.mc.field_71439_g == null) {
            return false;
        }
        if (PlayerUtil.en == null && PlayerUtil.mc.field_71441_e != null) {
            PlayerUtil.en = (Entity)new EntityChicken(PlayerUtil.mc.field_71439_g.field_70170_p);
        }
        PlayerUtil.en.func_70107_b(p_Pos.func_177958_n() + 0.5, p_Pos.func_177956_o() + 0.5, p_Pos.func_177952_p() + 0.5);
        return PlayerUtil.mc.field_71441_e.func_147447_a(new Vec3d(PlayerUtil.mc.field_71439_g.field_70165_t, PlayerUtil.mc.field_71439_g.field_70163_u + PlayerUtil.mc.field_71439_g.func_70047_e(), PlayerUtil.mc.field_71439_g.field_70161_v), new Vec3d(PlayerUtil.en.field_70165_t, PlayerUtil.en.field_70163_u, PlayerUtil.en.field_70161_v), false, true, false) == null;
    }
    
    public static boolean isCurrentViewEntity() {
        return PlayerUtil.mc.func_175606_aa() == PlayerUtil.mc.field_71439_g;
    }
    
    public static boolean IsEating() {
        return PlayerUtil.mc.field_71439_g != null && PlayerUtil.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFood && PlayerUtil.mc.field_71439_g.func_184587_cr();
    }
    
    public static int GetItemInHotbar(final Item p_Item) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = PlayerUtil.mc.field_71439_g.field_71071_by.func_70301_a(l_I);
            if (l_Stack != ItemStack.field_190927_a && l_Stack.func_77973_b() == p_Item) {
                return l_I;
            }
        }
        return -1;
    }
    
    public static BlockPos GetLocalPlayerPosFloored() {
        if (PlayerUtil.mc.field_71439_g == null) {
            return BlockPos.field_177992_a;
        }
        return new BlockPos(Math.floor(PlayerUtil.mc.field_71439_g.field_70165_t), Math.floor(PlayerUtil.mc.field_71439_g.field_70163_u), Math.floor(PlayerUtil.mc.field_71439_g.field_70161_v));
    }
    
    public static BlockPos EntityPosToFloorBlockPos(final Entity e) {
        return new BlockPos(Math.floor(e.field_70165_t), Math.floor(e.field_70163_u), Math.floor(e.field_70161_v));
    }
    
    public static float GetHealthWithAbsorption() {
        return PlayerUtil.mc.field_71439_g.func_110143_aJ() + PlayerUtil.mc.field_71439_g.func_110139_bj();
    }
    
    public static boolean IsPlayerInHole() {
        final BlockPos blockPos = GetLocalPlayerPosFloored();
        final IBlockState blockState = PlayerUtil.mc.field_71441_e.func_180495_p(blockPos);
        if (blockState.func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (PlayerUtil.mc.field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (PlayerUtil.mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
            return false;
        }
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
                ++validHorizontalBlocks;
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean IsPlayerInHole(final EntityPlayer who) {
        final BlockPos blockPos = new BlockPos(Math.floor(who.field_70165_t), Math.floor(who.field_70163_u), Math.floor(who.field_70161_v));
        final IBlockState blockState = PlayerUtil.mc.field_71441_e.func_180495_p(blockPos);
        if (blockState.func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (PlayerUtil.mc.field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (PlayerUtil.mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
            return false;
        }
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
                ++validHorizontalBlocks;
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean isPlayerInHole(final Block block) {
        final BlockPos blockPos = GetLocalPlayerPosFloored();
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
                if (block.equals(Blocks.field_150343_Z)) {
                    if (touchingState.func_177230_c().equals(Blocks.field_150343_Z) || touchingState.func_177230_c().equals(Blocks.field_150357_h)) {
                        ++validHorizontalBlocks;
                    }
                }
                else if (touchingState.func_177230_c().equals(block)) {
                    ++validHorizontalBlocks;
                }
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean isEntityInHole(final EntityPlayer who, final Block block) {
        final BlockPos blockPos = new BlockPos(Math.floor(who.field_70165_t), Math.floor(who.field_70163_u), Math.floor(who.field_70161_v));
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
                if (block.equals(Blocks.field_150343_Z)) {
                    if (touchingState.func_177230_c().equals(Blocks.field_150343_Z) || touchingState.func_177230_c().equals(Blocks.field_150357_h)) {
                        ++validHorizontalBlocks;
                    }
                }
                else if (touchingState.func_177230_c().equals(block)) {
                    ++validHorizontalBlocks;
                }
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean IsPlayerTrapped() {
        final BlockPos l_PlayerPos = GetLocalPlayerPosFloored();
        final BlockPos[] array;
        final BlockPos[] l_TrapPositions = array = new BlockPos[] { l_PlayerPos.func_177977_b(), l_PlayerPos.func_177984_a().func_177984_a(), l_PlayerPos.func_177978_c(), l_PlayerPos.func_177968_d(), l_PlayerPos.func_177974_f(), l_PlayerPos.func_177976_e(), l_PlayerPos.func_177978_c().func_177984_a(), l_PlayerPos.func_177968_d().func_177984_a(), l_PlayerPos.func_177974_f().func_177984_a(), l_PlayerPos.func_177976_e().func_177984_a() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = PlayerUtil.mc.field_71441_e.func_180495_p(l_Pos);
            if (l_State.func_177230_c() != Blocks.field_150343_Z && PlayerUtil.mc.field_71441_e.func_180495_p(l_Pos).func_177230_c() != Blocks.field_150357_h) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean IsEntityTrapped(final Entity e) {
        final BlockPos l_PlayerPos = EntityPosToFloorBlockPos(e);
        final BlockPos[] array;
        final BlockPos[] l_TrapPositions = array = new BlockPos[] { l_PlayerPos.func_177984_a().func_177984_a(), l_PlayerPos.func_177978_c(), l_PlayerPos.func_177968_d(), l_PlayerPos.func_177974_f(), l_PlayerPos.func_177976_e(), l_PlayerPos.func_177978_c().func_177984_a(), l_PlayerPos.func_177968_d().func_177984_a(), l_PlayerPos.func_177974_f().func_177984_a(), l_PlayerPos.func_177976_e().func_177984_a() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = PlayerUtil.mc.field_71441_e.func_180495_p(l_Pos);
            if (l_State.func_177230_c() != Blocks.field_150343_Z && PlayerUtil.mc.field_71441_e.func_180495_p(l_Pos).func_177230_c() != Blocks.field_150357_h) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBlockAbovePlayerHead(final EntityPlayer who, final boolean tall) {
        final BlockPos l_PlayerPos = new BlockPos(Math.floor(who.field_70165_t), Math.floor(who.field_70163_u), Math.floor(who.field_70161_v));
        final BlockPos posOne = l_PlayerPos.func_177984_a().func_177984_a();
        final BlockPos posTwo = l_PlayerPos.func_177984_a().func_177984_a().func_177984_a();
        final IBlockState stateOne = PlayerUtil.mc.field_71441_e.func_180495_p(posOne);
        final IBlockState stateTwo = PlayerUtil.mc.field_71441_e.func_180495_p(posTwo);
        final Block blockOne = stateOne.func_177230_c();
        final Block blockTwo = stateTwo.func_177230_c();
        return (blockOne != Blocks.field_150350_a && stateOne.func_185913_b()) || (tall && blockTwo != Blocks.field_150350_a && stateTwo.func_185913_b());
    }
    
    public static void switchToItem(final Item item) {
        if (PlayerUtil.mc.field_71439_g.func_184614_ca().func_77973_b() != item) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = PlayerUtil.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (!stack.func_190926_b() && stack.func_77973_b() == item) {
                    PlayerUtil.mc.field_71439_g.field_71071_by.field_70461_c = i;
                    PlayerUtil.mc.field_71442_b.func_78765_e();
                    break;
                }
            }
        }
    }
    
    public static FacingDirection GetFacing() {
        switch (MathHelper.func_76128_c(PlayerUtil.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0:
            case 1: {
                return FacingDirection.South;
            }
            case 2:
            case 3: {
                return FacingDirection.West;
            }
            case 4:
            case 5: {
                return FacingDirection.North;
            }
            case 6:
            case 7: {
                return FacingDirection.East;
            }
            default: {
                return FacingDirection.North;
            }
        }
    }
    
    public static CardinalFacingDirection getCardinalFacingDirection() {
        switch (MathHelper.func_76128_c(PlayerUtil.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return CardinalFacingDirection.South;
            }
            case 1: {
                return CardinalFacingDirection.SouthWest;
            }
            case 2: {
                return CardinalFacingDirection.West;
            }
            case 3: {
                return CardinalFacingDirection.NorthWest;
            }
            case 4: {
                return CardinalFacingDirection.North;
            }
            case 5: {
                return CardinalFacingDirection.NorthEast;
            }
            case 6: {
                return CardinalFacingDirection.East;
            }
            case 7: {
                return CardinalFacingDirection.SouthEast;
            }
            default: {
                return CardinalFacingDirection.North;
            }
        }
    }
    
    public static float getSpeedInKM() {
        final double deltaX = PlayerUtil.mc.field_71439_g.field_70165_t - PlayerUtil.mc.field_71439_g.field_70169_q;
        final double deltaZ = PlayerUtil.mc.field_71439_g.field_70161_v - PlayerUtil.mc.field_71439_g.field_70166_s;
        final float l_Distance = MathHelper.func_76133_a(deltaX * deltaX + deltaZ * deltaZ);
        final double l_KMH = Math.floor(l_Distance / 1000.0f / 1.3888889E-5f);
        String l_Formatter = PlayerUtil.Formatter.format(l_KMH);
        if (!l_Formatter.contains(".")) {
            l_Formatter += ".0";
        }
        return Float.valueOf(l_Formatter);
    }
    
    public static boolean isInLiquid() {
        if (Wrapper.GetPlayer() == null) {
            return false;
        }
        boolean inLiquid = false;
        final int y = (int)(Wrapper.GetPlayer().func_174813_aQ().field_72338_b + 0.02);
        for (int x = MathHelper.func_76128_c(Wrapper.GetPlayer().func_174813_aQ().field_72340_a); x < MathHelper.func_76128_c(Wrapper.GetPlayer().func_174813_aQ().field_72336_d) + 1; ++x) {
            for (int z = MathHelper.func_76128_c(Wrapper.GetPlayer().func_174813_aQ().field_72339_c); z < MathHelper.func_76128_c(Wrapper.GetPlayer().func_174813_aQ().field_72334_f) + 1; ++z) {
                final Block block = Wrapper.GetMC().field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean isOnLiquid() {
        final float offset = 0.05f;
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g == null) {
            return false;
        }
        if (mc.field_71439_g.field_70143_R >= 3.0f) {
            return false;
        }
        if (mc.field_71439_g != null) {
            final AxisAlignedBB bb = (mc.field_71439_g.func_184187_bx() != null) ? mc.field_71439_g.func_184187_bx().func_174813_aQ().func_191195_a(0.0, 0.0, 0.0).func_72317_d(0.0, -0.05000000074505806, 0.0) : mc.field_71439_g.func_174813_aQ().func_191195_a(0.0, 0.0, 0.0).func_72317_d(0.0, -0.05000000074505806, 0.0);
            boolean onLiquid = false;
            final int y = (int)bb.field_72338_b;
            for (int x = MathHelper.func_76128_c(bb.field_72340_a); x < MathHelper.func_76128_c(bb.field_72336_d + 1.0); ++x) {
                for (int z = MathHelper.func_76128_c(bb.field_72339_c); z < MathHelper.func_76128_c(bb.field_72334_f + 1.0); ++z) {
                    final Block block = mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                    if (block != Blocks.field_150350_a) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        onLiquid = true;
                    }
                }
            }
            return onLiquid;
        }
        return false;
    }
    
    public static boolean isWearingUseableElytra() {
        final ItemStack itemstack = PlayerUtil.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST);
        return itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack);
    }
    
    public static int GetItemSlotInHotbar(final Block web) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = PlayerUtil.mc.field_71439_g.field_71071_by.func_70301_a(l_I);
            if (l_Stack != ItemStack.field_190927_a && l_Stack.func_77973_b() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)l_Stack.func_77973_b();
                if (block.func_179223_d().equals(web)) {
                    return l_I;
                }
            }
        }
        return -1;
    }
    
    public static void sendMovementPackets(final EventPlayerMotionUpdate event) {
        final Minecraft mc = Wrapper.GetMC();
        final boolean flag = mc.field_71439_g.func_70051_ag();
        if (flag != mc.field_71439_g.field_175171_bO) {
            if (flag) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            mc.field_71439_g.field_175171_bO = flag;
        }
        final boolean flag2 = mc.field_71439_g.func_70093_af();
        if (flag2 != mc.field_71439_g.field_175170_bN) {
            if (flag2) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            mc.field_71439_g.field_175170_bN = flag2;
        }
        if (mc.func_175606_aa() == mc.field_71439_g) {
            final double d0 = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_175172_bI;
            final double d2 = event.getY() - mc.field_71439_g.field_175166_bJ;
            final double d3 = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_175167_bK;
            final double d4 = event.getYaw() - mc.field_71439_g.field_175164_bL;
            final double d5 = event.getPitch() - mc.field_71439_g.field_175165_bM;
            final EntityPlayerSP field_71439_g = mc.field_71439_g;
            ++field_71439_g.field_175168_bP;
            boolean flag3 = d0 * d0 + d2 * d2 + d3 * d3 > 9.0E-4 || mc.field_71439_g.field_175168_bP >= 20;
            final boolean flag4 = d4 != 0.0 || d5 != 0.0;
            if (mc.field_71439_g.func_184218_aH()) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70159_w, -999.0, mc.field_71439_g.field_70179_y, event.getYaw(), event.getPitch(), mc.field_71439_g.field_70122_E));
                flag3 = false;
            }
            else if (flag3 && flag4) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70165_t, event.getY(), mc.field_71439_g.field_70161_v, event.getYaw(), event.getPitch(), mc.field_71439_g.field_70122_E));
            }
            else if (flag3) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, event.getY(), mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
            }
            else if (flag4) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(event.getYaw(), event.getPitch(), mc.field_71439_g.field_70122_E));
            }
            else if (mc.field_71439_g.field_184841_cd != mc.field_71439_g.field_70122_E) {
                mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer(mc.field_71439_g.field_70122_E));
            }
            if (flag3) {
                mc.field_71439_g.field_175172_bI = mc.field_71439_g.field_70165_t;
                mc.field_71439_g.field_175166_bJ = event.getY();
                mc.field_71439_g.field_175167_bK = mc.field_71439_g.field_70161_v;
                mc.field_71439_g.field_175168_bP = 0;
            }
            if (flag4) {
                mc.field_71439_g.field_175164_bL = event.getYaw();
                mc.field_71439_g.field_175165_bM = event.getPitch();
            }
            mc.field_71439_g.field_184841_cd = mc.field_71439_g.field_70122_E;
            mc.field_71439_g.field_189811_cr = mc.field_71439_g.field_71159_c.field_71474_y.field_189989_R;
        }
    }
    
    static {
        PlayerUtil.en = null;
        Formatter = new DecimalFormat("#.#");
    }
    
    public enum FacingDirection
    {
        North, 
        South, 
        East, 
        West;
    }
    
    public enum CardinalFacingDirection
    {
        North, 
        NorthWest, 
        NorthEast, 
        South, 
        SouthWest, 
        SouthEast, 
        East, 
        West;
    }
}

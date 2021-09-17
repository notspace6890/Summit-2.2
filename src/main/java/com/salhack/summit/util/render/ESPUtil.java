// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util.render;

import com.salhack.summit.events.render.RenderEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import com.salhack.summit.util.Hole;
import net.minecraft.init.Blocks;
import com.salhack.summit.main.Wrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

public class ESPUtil
{
    public static void ColorToGL(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void RenderBoundingBox(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static boolean IsVoidHole(final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos.func_177956_o() > 4 || blockPos.func_177956_o() <= 0) {
            return false;
        }
        BlockPos l_Pos = blockPos;
        for (int l_I = blockPos.func_177956_o(); l_I >= 0; --l_I) {
            if (Wrapper.GetMC().field_71441_e.func_180495_p(l_Pos).func_177230_c() != Blocks.field_150350_a) {
                return false;
            }
            l_Pos = l_Pos.func_177977_b();
        }
        return true;
    }
    
    public static Hole.HoleTypes isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.func_177230_c() != Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        if (Wrapper.GetMC().field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        if (Wrapper.GetMC().field_71441_e.func_180495_p(blockPos.func_177981_b(2)).func_177230_c() != Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        if (Wrapper.GetMC().field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
            return Hole.HoleTypes.None;
        }
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        boolean l_Bedrock = true;
        boolean l_Obsidian = true;
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = Wrapper.GetMC().field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
                ++validHorizontalBlocks;
                if (touchingState.func_177230_c() != Blocks.field_150357_h && l_Bedrock) {
                    l_Bedrock = false;
                }
                if (!l_Bedrock && touchingState.func_177230_c() != Blocks.field_150343_Z && touchingState.func_177230_c() != Blocks.field_150357_h) {
                    l_Obsidian = false;
                }
            }
        }
        if (validHorizontalBlocks < 4) {
            return Hole.HoleTypes.None;
        }
        if (l_Bedrock) {
            return Hole.HoleTypes.Bedrock;
        }
        if (l_Obsidian) {
            return Hole.HoleTypes.Obsidian;
        }
        return Hole.HoleTypes.Normal;
    }
    
    public static void Render(final String p_Mode, final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        switch (p_Mode) {
            case "Flat": {
                RenderGlobal.func_189695_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72338_b, bb.field_72334_f, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "FlatOutline": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72338_b, bb.field_72334_f, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "Full": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, p_Red, p_Green, p_Blue, p_Alpha);
                RenderGlobal.func_189695_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "Outline": {
                RenderGlobal.func_189694_a(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c, bb.field_72336_d, bb.field_72337_e, bb.field_72334_f, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
        }
    }
    
    public static void RenderOutline(final RenderEvent p_Event, final BlockPos p_Pos, final float red, final float green, final float blue, final float alpha) {
    }
}

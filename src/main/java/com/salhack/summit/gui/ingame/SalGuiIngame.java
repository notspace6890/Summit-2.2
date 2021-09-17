// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.ingame;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.EnumHandSide;
import net.minecraft.client.renderer.GlStateManager;
import com.salhack.summit.util.CameraUtils;
import com.salhack.summit.events.render.EventRenderGameOverlay;
import net.minecraft.client.gui.ScaledResolution;
import com.salhack.summit.SummitMod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.GuiIngameForge;

public class SalGuiIngame extends GuiIngameForge
{
    public SalGuiIngame(final Minecraft mc) {
        super(mc);
        ObfuscationReflectionHelper.setPrivateValue((Class)GuiIngame.class, (Object)this, (Object)new SalGuiPlayerTabOverlay(mc, (GuiIngame)this), new String[] { "field_175196_v" });
    }
    
    public void func_175180_a(final float partialTicks) {
        super.func_175180_a(partialTicks);
        if (!this.field_73839_d.field_71474_y.field_74330_P) {
            SummitMod.EVENT_BUS.post(new EventRenderGameOverlay(partialTicks, new ScaledResolution(this.field_73839_d)));
        }
    }
    
    public void func_180479_a(final ScaledResolution sr, final float partialTicks) {
        if (!CameraUtils.freecamEnabled()) {
            super.func_180479_a(sr, partialTicks);
            return;
        }
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_73839_d.func_110434_K().func_110577_a(SalGuiIngame.field_110330_c);
        final EntityPlayer entityplayer = (EntityPlayer)this.field_73839_d.field_71439_g;
        final ItemStack itemstack = entityplayer.func_184592_cb();
        final EnumHandSide enumhandside = entityplayer.func_184591_cq().func_188468_a();
        final int i = sr.func_78326_a() / 2;
        final float f = this.field_73735_i;
        this.field_73735_i = -90.0f;
        this.func_73729_b(i - 91, sr.func_78328_b() - 22, 0, 0, 182, 22);
        this.func_73729_b(i - 91 - 1 + entityplayer.field_71071_by.field_70461_c * 20, sr.func_78328_b() - 22 - 1, 0, 22, 24, 22);
        if (!itemstack.func_190926_b()) {
            if (enumhandside == EnumHandSide.LEFT) {
                this.func_73729_b(i - 91 - 29, sr.func_78328_b() - 23, 24, 22, 29, 24);
            }
            else {
                this.func_73729_b(i + 91, sr.func_78328_b() - 23, 53, 22, 29, 24);
            }
        }
        this.field_73735_i = f;
        GlStateManager.func_179091_B();
        GlStateManager.func_179147_l();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.func_74520_c();
        for (int l = 0; l < 9; ++l) {
            final int i2 = i - 90 + l * 20 + 2;
            final int j1 = sr.func_78328_b() - 16 - 3;
            this.func_184044_a(i2, j1, partialTicks, entityplayer, (ItemStack)entityplayer.field_71071_by.field_70462_a.get(l));
        }
        if (!itemstack.func_190926_b()) {
            final int l2 = sr.func_78328_b() - 16 - 3;
            if (enumhandside == EnumHandSide.LEFT) {
                this.func_184044_a(i - 91 - 26, l2, partialTicks, entityplayer, itemstack);
            }
            else {
                this.func_184044_a(i + 91 + 10, l2, partialTicks, entityplayer, itemstack);
            }
        }
        if (this.field_73839_d.field_71474_y.field_186716_M == 2) {
            final float f2 = this.field_73839_d.field_71439_g.func_184825_o(0.0f);
            if (f2 < 1.0f) {
                final int i3 = sr.func_78328_b() - 20;
                int j2 = i + 91 + 6;
                if (enumhandside == EnumHandSide.RIGHT) {
                    j2 = i - 91 - 22;
                }
                this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
                final int k1 = (int)(f2 * 19.0f);
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                this.func_73729_b(j2, i3, 0, 94, 18, 18);
                this.func_73729_b(j2, i3 + 18 - k1, 18, 112 - k1, 18, k1);
            }
        }
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179084_k();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.chat;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import com.salhack.summit.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class SalGuiTextField extends GuiTextField
{
    public boolean HandleCommandsInternal;
    
    public SalGuiTextField(final int componentId, final FontRenderer RenderUtilObj, final int x, final int y, final int par5Width, final int par6Height) {
        super(componentId, RenderUtilObj, x, y, par5Width, par6Height);
        this.HandleCommandsInternal = true;
    }
    
    public SalGuiTextField(final int componentId, final FontRenderer RenderUtilObj, final int x, final int y, final int par5Width, final int par6Height, final boolean b) {
        this(componentId, RenderUtilObj, x, y, par5Width, par6Height);
        this.HandleCommandsInternal = b;
    }
    
    public void func_146199_i(int position) {
        final int i = this.field_146216_j.length();
        if (position > i) {
            position = i;
        }
        if (position < 0) {
            position = 0;
        }
        this.field_146223_s = position;
        if (this.field_146225_q > i) {
            this.field_146225_q = i;
        }
        final int j = this.func_146200_o();
        final String s = RenderUtil.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), j);
        final int k = s.length() + this.field_146225_q;
        if (position == this.field_146225_q) {
            this.field_146225_q -= RenderUtil.trimStringToWidth(this.field_146216_j, j, true).length();
        }
        if (position > k) {
            this.field_146225_q += position - k;
        }
        else if (position <= this.field_146225_q) {
            this.field_146225_q -= this.field_146225_q - position;
        }
        this.field_146225_q = MathHelper.func_76125_a(this.field_146225_q, 0, i);
    }
    
    public boolean func_146192_a(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean flag = mouseX >= this.field_146209_f && mouseX < this.field_146209_f + this.field_146218_h && mouseY >= this.field_146210_g && mouseY < this.field_146210_g + this.field_146219_i;
        if (this.field_146212_n) {
            this.func_146195_b(flag);
        }
        if (this.field_146213_o && flag && mouseButton == 0) {
            int i = mouseX - this.field_146209_f;
            if (this.field_146215_m) {
                i -= 4;
            }
            final String s = RenderUtil.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), this.func_146200_o());
            this.func_146190_e(RenderUtil.trimStringToWidth(s, i).length() + this.field_146225_q);
            return true;
        }
        return false;
    }
    
    public void func_146194_f() {
        if (this.func_146176_q()) {
            if (this.func_146181_i()) {
                func_73734_a(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, -6250336);
                func_73734_a(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216);
            }
            final int i = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
            final int j = this.field_146224_r - this.field_146225_q;
            int k = this.field_146223_s - this.field_146225_q;
            final String s = RenderUtil.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), this.func_146200_o());
            final boolean flag = j >= 0 && j <= s.length();
            final boolean flag2 = this.field_146213_o && this.field_146214_l / 6 % 2 == 0 && flag;
            final int l = this.field_146215_m ? (this.field_146209_f + 4) : this.field_146209_f;
            final int i2 = this.field_146215_m ? (this.field_146210_g + (this.field_146219_i - 8) / 2) : this.field_146210_g;
            int j2 = l;
            if (k > s.length()) {
                k = s.length();
            }
            if (!s.isEmpty()) {
                final String s2 = flag ? s.substring(0, j) : s;
                j2 = (int)RenderUtil.drawStringWithShadow(s2, (float)l, (float)i2, 16777215) + 6;
            }
            final boolean flag3 = this.field_146224_r < this.field_146216_j.length() || this.field_146216_j.length() >= this.func_146208_g();
            int k2 = j2;
            if (!flag) {
                k2 = ((j > 0) ? (l + this.field_146218_h) : l);
            }
            else if (flag3) {
                k2 = j2 - 1;
                --j2;
            }
            if (!s.isEmpty() && flag && j < s.length()) {
                j2 = (int)RenderUtil.drawStringWithShadow(s.substring(j), (float)j2, (float)i2, i);
            }
            if (flag2) {
                if (flag3) {
                    Gui.func_73734_a(k2, i2 - 1, k2 + 1, (int)(i2 + 1 + RenderUtil.getStringHeight("_")), -3092272);
                }
                else {
                    RenderUtil.drawStringWithShadow("_", (float)k2, (float)i2, i);
                }
            }
            if (k != j) {
                final int l2 = (int)(l + RenderUtil.getStringWidth(s.substring(0, k)));
                this.drawSelectionBox(k2, i2 - 1, l2 - 1, i2 + 1 + (int)RenderUtil.getStringHeight("_"));
            }
        }
    }
    
    private void drawSelectionBox(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            final int i = startX;
            startX = endX;
            endX = i;
        }
        if (startY < endY) {
            final int j = startY;
            startY = endY;
            endY = j;
        }
        if (endX > this.field_146209_f + this.field_146218_h) {
            endX = this.field_146209_f + this.field_146218_h;
        }
        if (startX > this.field_146209_f + this.field_146218_h) {
            startX = this.field_146209_f + this.field_146218_h;
        }
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179131_c(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.func_179090_x();
        GlStateManager.func_179115_u();
        GlStateManager.func_187422_a(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b((double)startX, (double)endY, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)endX, (double)endY, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)endX, (double)startY, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)startX, (double)startY, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179134_v();
        GlStateManager.func_179098_w();
    }
}

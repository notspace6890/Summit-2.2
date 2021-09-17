// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractGui
{
    public static final ResourceLocation BACKGROUND_LOCATION;
    public static final ResourceLocation STATS_ICON_LOCATION;
    public static final ResourceLocation GUI_ICONS_LOCATION;
    protected int blitOffset;
    
    public static void fill(int x0, int y1, int x1, int y0, final int color) {
        if (x0 < x1) {
            final int i = x0;
            x0 = x1;
            x1 = i;
        }
        if (y1 < y0) {
            final int j = y1;
            y1 = y0;
            y0 = j;
        }
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c(r, g, b, a);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b((double)x0, (double)y0, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)x1, (double)y0, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)x1, (double)y1, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)x0, (double)y1, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void blit(final int x, final int y, final int z, final int width, final int height, final TextureAtlasSprite sprite) {
        innerBlit(x, x + width, y, y + height, z, sprite.func_94209_e(), sprite.func_94212_f(), sprite.func_94206_g(), sprite.func_94210_h());
    }
    
    public static void blit(final int x, final int y, final int z, final float u, final float v, final int width, final int height, final int vScale, final int uScale) {
        innerBlit(x, x + width, y, y + height, z, width, height, u, v, uScale, vScale);
    }
    
    public static void blit(final int x, final int y, final int width, final int height, final float minU, final float minV, final int maxU, final int maxV, final int uScale, final int vScale) {
        innerBlit(x, x + width, y, y + height, 0, maxU, maxV, minU, minV, uScale, vScale);
    }
    
    public static void blit(final int x, final int y, final float minU, final float minV, final int width, final int height, final int uScale, final int vScale) {
        blit(x, y, width, height, minU, minV, width, height, uScale, vScale);
    }
    
    private static void innerBlit(final int x0, final int x1, final int y0, final int y1, final int z, final int maxU, final int maxV, final float minU, final float minV, final int uScale, final int vScale) {
        innerBlit(x0, x1, y0, y1, z, (minU + 0.0f) / uScale, (minU + maxU) / uScale, (minV + 0.0f) / vScale, (minV + maxV) / vScale);
    }
    
    protected static void innerBlit(final int x0, final int x1, final int y1, final int y0, final int z, final float minU, final float maxU, final float minV, final float maxV) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        bufferbuilder.func_181662_b((double)x0, (double)y0, (double)z).func_187315_a((double)minU, (double)maxV).func_181675_d();
        bufferbuilder.func_181662_b((double)x1, (double)y0, (double)z).func_187315_a((double)maxU, (double)maxV).func_181675_d();
        bufferbuilder.func_181662_b((double)x1, (double)y1, (double)z).func_187315_a((double)maxU, (double)minV).func_181675_d();
        bufferbuilder.func_181662_b((double)x0, (double)y1, (double)z).func_187315_a((double)minU, (double)minV).func_181675_d();
        tessellator.func_78381_a();
    }
    
    protected void hLine(int x, int width, final int y, final int color) {
        if (width < x) {
            final int i = x;
            x = width;
            width = i;
        }
        fill(x, y, width + 1, y + 1, color);
    }
    
    protected void vLine(final int x, int y, int height, final int color) {
        if (height < y) {
            final int i = y;
            y = height;
            height = i;
        }
        fill(x, y + 1, x + 1, height, color);
    }
    
    protected void fillGradient(final int x1, final int y0, final int x0, final int y1, final int color0, final int color1) {
        final float a0 = (color0 >> 24 & 0xFF) / 255.0f;
        final float r0 = (color0 >> 16 & 0xFF) / 255.0f;
        final float g0 = (color0 >> 8 & 0xFF) / 255.0f;
        final float b0 = (color0 & 0xFF) / 255.0f;
        final float a2 = (color1 >> 24 & 0xFF) / 255.0f;
        final float r2 = (color1 >> 16 & 0xFF) / 255.0f;
        final float g2 = (color1 >> 8 & 0xFF) / 255.0f;
        final float b2 = (color1 & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179103_j(7425);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b((double)x0, (double)y0, (double)this.blitOffset).func_181666_a(r0, g0, b0, a0).func_181675_d();
        bufferbuilder.func_181662_b((double)x1, (double)y0, (double)this.blitOffset).func_181666_a(r0, g0, b0, a0).func_181675_d();
        bufferbuilder.func_181662_b((double)x1, (double)y1, (double)this.blitOffset).func_181666_a(r2, g2, b2, a2).func_181675_d();
        bufferbuilder.func_181662_b((double)x0, (double)y1, (double)this.blitOffset).func_181666_a(r2, g2, b2, a2).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }
    
    public void blit(final int x, final int y, final int u, final int v, final int width, final int height) {
        blit(x, y, this.blitOffset, (float)u, (float)v, width, height, 256, 256);
    }
    
    static {
        BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
        STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
        GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
    }
}

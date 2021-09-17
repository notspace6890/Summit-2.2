// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.text.DecimalFormat;
import com.salhack.summit.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import com.salhack.summit.gui.hud.components.DraggableHudComponent;

public class PlayerFrameComponent extends DraggableHudComponent
{
    public PlayerFrameComponent() {
        super("PlayerFrame", 200.0f, 2.0f, 100.0f, 100.0f);
    }
    
    @Override
    public void onRender(final ScaledResolution res, final float mouseX, final float mouseY, final float partialTicks) {
        if (this.mc.field_71441_e == null) {
            return;
        }
        super.onRender(res, mouseX, mouseY, partialTicks);
        RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -1727263732);
        final float l_HealthPct = (this.mc.field_71439_g.func_110143_aJ() + this.mc.field_71439_g.func_110139_bj()) / this.mc.field_71439_g.func_110138_aP() * 100.0f;
        final float l_HealthBarPct = Math.min(l_HealthPct, 100.0f);
        final float l_HungerPct = (this.mc.field_71439_g.func_71024_bL().func_75116_a() + this.mc.field_71439_g.func_71024_bL().func_75115_e()) / 20.0f * 100.0f;
        final float l_HungerBarPct = Math.min(l_HungerPct, 100.0f);
        final DecimalFormat l_Format = new DecimalFormat("#.#");
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GuiInventory.func_147046_a((int)this.getX() + 10, (int)this.getY() + 30, 15, mouseX, mouseY, (EntityLivingBase)this.mc.field_71439_g);
        GlStateManager.func_179091_B();
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtil.drawStringWithShadow(this.mc.func_110432_I().func_111285_a(), this.getX() + 20.0f, this.getY() + 4.0f, 16777215);
        RenderUtil.drawGradientRect(this.getX() + 20.0f, this.getY() + 11.0f, this.getX() + 20.0f + l_HealthBarPct, this.getY() + 22.0f, -1717570715, -1726742784);
        RenderUtil.drawGradientRect(this.getX() + 20.0f, this.getY() + 22.0f, this.getX() + 20.0f + l_HungerBarPct, this.getY() + 33.0f, -1711690747, -1711690747);
        RenderUtil.drawStringWithShadow(String.format("(%s) %s / %s", l_Format.format(l_HealthPct) + "%", l_Format.format(this.mc.field_71439_g.func_110143_aJ() + this.mc.field_71439_g.func_110139_bj()), l_Format.format(this.mc.field_71439_g.func_110138_aP())), this.getX() + 20.0f, this.getY() + 13.0f, 16777215);
        RenderUtil.drawStringWithShadow(String.format("(%s) %s / %s", l_Format.format(l_HungerPct) + "%", l_Format.format(this.mc.field_71439_g.func_71024_bL().func_75116_a() + this.mc.field_71439_g.func_71024_bL().func_75115_e()), "20"), this.getX() + 20.0f, this.getY() + 24.0f, 16777215);
        this.setWidth(120.0f);
        this.setHeight(33.0f);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud.items;

import net.minecraft.item.ItemStack;
import com.salhack.summit.util.render.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import com.salhack.summit.module.Value;
import com.salhack.summit.gui.hud.components.DraggableHudComponent;

public class InventoryComponent extends DraggableHudComponent
{
    public final Value<Boolean> ShowHotbar;
    public final Value<Boolean> ShowXCarry;
    public final Value<Boolean> Background;
    public final Value<Float> Scale;
    
    public InventoryComponent() {
        super("Inventory", 2.0f, 15.0f, 100.0f, 100.0f);
        this.ShowHotbar = new Value<Boolean>("ShowHotbar", new String[] { "" }, "Displays the hotbar", false);
        this.ShowXCarry = new Value<Boolean>("ShowXCarry", new String[] { "" }, "Displays the crafting inventory", true);
        this.Background = new Value<Boolean>("Background", new String[] { "" }, "Displays the Background", true);
        this.Scale = new Value<Float>("Scale", new String[] { "" }, "Allows you to modify the scale", 1.0f, 0.0f, 10.0f, 1.0f);
        this.setEnabled(true);
    }
    
    @Override
    public void onRender(final ScaledResolution res, final float mouseX, final float mouseY, final float partialTicks) {
        super.onRender(res, mouseX, mouseY, partialTicks);
        GlStateManager.func_179094_E();
        RenderHelper.func_74520_c();
        GlStateManager.func_179152_a((float)this.Scale.getValue(), (float)this.Scale.getValue(), (float)this.Scale.getValue());
        if (this.Background.getValue()) {
            RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1963986960);
        }
        for (int i = 0; i < 27; ++i) {
            final ItemStack itemStack = (ItemStack)this.mc.field_71439_g.field_71071_by.field_70462_a.get(i + 9);
            final int offsetX = (int)this.getX() + i % 9 * 16;
            final int offsetY = (int)this.getY() + i / 9 * 16;
            this.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
            this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
        }
        if (this.ShowHotbar.getValue()) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack itemStack = (ItemStack)this.mc.field_71439_g.field_71071_by.field_70462_a.get(i);
                final int offsetX = (int)this.getX() + i % 9 * 16;
                final int offsetY = (int)this.getY() + 48;
                this.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
                this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
            }
        }
        if (this.ShowXCarry.getValue()) {
            if (this.Background.getValue()) {
                RenderUtil.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 32.0f, this.getY() + 32.0f, 1963986960);
            }
            for (int i = 1; i < 5; ++i) {
                final ItemStack itemStack = (ItemStack)this.mc.field_71439_g.field_71069_bz.func_75138_a().get(i);
                int offsetX = (int)this.getX();
                int offsetY = (int)this.getY();
                switch (i) {
                    case 1:
                    case 2: {
                        offsetX += 128 + i * 16;
                        break;
                    }
                    case 3:
                    case 4: {
                        offsetX += 128 + (i - 2) * 16;
                        offsetY += 16;
                        break;
                    }
                }
                this.mc.func_175599_af().func_180450_b(itemStack, offsetX, offsetY);
                this.mc.func_175599_af().func_180453_a(this.mc.field_71466_p, itemStack, offsetX, offsetY, (String)null);
            }
        }
        this.setWidth(144.0f);
        this.setHeight((float)(16 * (this.ShowHotbar.getValue() ? 4 : 3)));
        RenderHelper.func_74518_a();
        this.mc.func_175599_af().field_77023_b = 0.0f;
        GlStateManager.func_179121_F();
    }
}

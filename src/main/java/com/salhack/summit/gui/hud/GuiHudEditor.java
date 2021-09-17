// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud;

import java.io.IOException;
import java.util.Iterator;
import com.salhack.summit.gui.hud.components.HudComponentItem;
import com.salhack.summit.managers.HudManager;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import com.salhack.summit.main.SummitStatic;
import com.salhack.summit.module.ui.HudEditor;
import com.salhack.summit.gui.SalGuiScreen;

public class GuiHudEditor extends SalGuiScreen
{
    private HudEditor HudEditor;
    private boolean Clicked;
    private boolean Dragging;
    private int ClickMouseX;
    private int ClickMouseY;
    
    public GuiHudEditor(final HudEditor p_HudEditor) {
        this.Clicked = false;
        this.Dragging = false;
        this.ClickMouseX = 0;
        this.ClickMouseY = 0;
        SummitStatic.SELECTORMENU.UpdateMenu();
        this.HudEditor = p_HudEditor;
    }
    
    @Override
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_146276_q_();
        GL11.glPushMatrix();
        final HudComponentItem l_LastHovered = null;
        final ScaledResolution res = new ScaledResolution(this.field_146297_k);
        for (final HudComponentItem l_Item : HudManager.Get().Items) {
            if (l_Item.isEnabled()) {
                l_Item.onRender(res, (float)mouseX, (float)mouseY, partialTicks);
            }
        }
        if (l_LastHovered != null) {
            HudManager.Get().Items.remove(l_LastHovered);
            HudManager.Get().Items.add(l_LastHovered);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    public void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        for (final HudComponentItem l_Item : HudManager.Get().Items) {
            if (l_Item.isEnabled() && l_Item.onMouseClick((float)mouseX, (float)mouseY, mouseButton)) {
                return;
            }
        }
        this.Clicked = true;
        this.ClickMouseX = mouseX;
        this.ClickMouseY = mouseY;
    }
    
    @Override
    public void func_146286_b(final int mouseX, final int mouseY, final int state) {
        super.func_146286_b(mouseX, mouseY, state);
        HudManager.Get().Items.forEach(p_Item -> {
            if (p_Item.isEnabled()) {
                p_Item.onMouseRelease();
            }
            return;
        });
        this.Clicked = false;
    }
    
    @Override
    public void func_146273_a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.func_146273_a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        if (this.HudEditor.isEnabled()) {
            this.HudEditor.toggle();
        }
        this.Clicked = false;
        this.Dragging = false;
        this.ClickMouseX = 0;
        this.ClickMouseY = 0;
        HudManager.Get().onHudEditorClosed();
    }
}

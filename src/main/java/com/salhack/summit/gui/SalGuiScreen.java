// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import com.salhack.summit.main.SummitStatic;
import net.minecraft.client.gui.GuiScreen;

public class SalGuiScreen extends GuiScreen
{
    private boolean InventoryMoveEnabled() {
        return SummitStatic.NOSLOW.InventoryMove.getValue();
    }
    
    public static void UpdateRotationPitch(final float p_Amount) {
        final Minecraft mc = Minecraft.func_71410_x();
        float l_NewRotation = mc.field_71439_g.field_70125_A + p_Amount;
        l_NewRotation = Math.max(l_NewRotation, -90.0f);
        l_NewRotation = Math.min(l_NewRotation, 90.0f);
        mc.field_71439_g.field_70125_A = l_NewRotation;
    }
    
    public static void UpdateRotationYaw(final float p_Amount) {
        final Minecraft mc = Minecraft.func_71410_x();
        final float l_NewRotation = mc.field_71439_g.field_70177_z + p_Amount;
        mc.field_71439_g.field_70177_z = l_NewRotation;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (!this.InventoryMoveEnabled()) {
            return;
        }
        if (Keyboard.isKeyDown(200)) {
            UpdateRotationPitch(-2.5f);
        }
        if (Keyboard.isKeyDown(208)) {
            UpdateRotationPitch(2.5f);
        }
        if (Keyboard.isKeyDown(205)) {
            UpdateRotationYaw(2.5f);
        }
        if (Keyboard.isKeyDown(203)) {
            UpdateRotationYaw(-2.5f);
        }
    }
    
    public void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
    
    public void func_146286_b(final int mouseX, final int mouseY, final int state) {
        super.func_146286_b(mouseX, mouseY, state);
    }
    
    public void func_146273_a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.func_146273_a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public boolean func_73868_f() {
        return false;
    }
}

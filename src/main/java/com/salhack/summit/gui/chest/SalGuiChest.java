// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.chest;

import java.io.IOException;
import com.salhack.summit.main.SummitStatic;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.gui.inventory.GuiChest;

public class SalGuiChest extends GuiChest
{
    private boolean WasEnabledByGUI;
    
    public SalGuiChest(final IInventory upperInv, final IInventory lowerInv) {
        super(upperInv, lowerInv);
        this.WasEnabledByGUI = false;
        this.field_146297_k = Minecraft.func_71410_x();
        final ScaledResolution l_Res = new ScaledResolution(this.field_146297_k);
        this.func_146280_a(this.field_146297_k, l_Res.func_78326_a(), l_Res.func_78328_b());
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(new GuiButton(1337, this.field_146294_l / 2 + 100, this.field_146295_m / 2 - this.field_147000_g + 110, 50, 20, "Steal"));
        this.field_146292_n.add(new GuiButton(1338, this.field_146294_l / 2 + 100, this.field_146295_m / 2 - this.field_147000_g + 130, 50, 20, "Store"));
        this.field_146292_n.add(new GuiButton(1339, this.field_146294_l / 2 + 100, this.field_146295_m / 2 - this.field_147000_g + 150, 50, 20, "Drop"));
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        super.func_146284_a(button);
        switch (button.field_146127_k) {
            case 1337: {
                SummitStatic.CHESTSTEALER.Mode.setValue("Steal");
                if (!SummitStatic.CHESTSTEALER.isEnabled()) {
                    this.WasEnabledByGUI = true;
                    SummitStatic.CHESTSTEALER.toggle();
                    break;
                }
                break;
            }
            case 1338: {
                SummitStatic.CHESTSTEALER.Mode.setValue("Store");
                if (!SummitStatic.CHESTSTEALER.isEnabled()) {
                    this.WasEnabledByGUI = true;
                    SummitStatic.CHESTSTEALER.toggle();
                    break;
                }
                break;
            }
            case 1339: {
                SummitStatic.CHESTSTEALER.Mode.setValue("Drop");
                if (!SummitStatic.CHESTSTEALER.isEnabled()) {
                    this.WasEnabledByGUI = true;
                    SummitStatic.CHESTSTEALER.toggle();
                    break;
                }
                break;
            }
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        if (this.WasEnabledByGUI && SummitStatic.CHESTSTEALER.isEnabled()) {
            SummitStatic.CHESTSTEALER.toggle();
        }
    }
}

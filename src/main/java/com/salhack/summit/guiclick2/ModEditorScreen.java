// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.guiclick2;

import org.lwjgl.input.Mouse;
import java.io.IOException;
import com.salhack.summit.util.render.RenderUtil;
import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import com.salhack.summit.guiclick2.components.MenuComponentHidden;
import com.salhack.summit.guiclick2.components.MenuComponentKeybind;
import com.salhack.summit.guiclick2.components.MenuComponentValue;
import java.util.ArrayList;
import com.salhack.summit.module.Module;
import com.salhack.summit.module.Value;
import com.salhack.summit.guiclick2.components.MenuComponent;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import com.salhack.summit.gui.SalGuiScreen;

public class ModEditorScreen extends SalGuiScreen
{
    private final GuiScreen prev;
    private String displayName;
    private List<MenuComponent> items;
    private float totalY;
    private float guiWidth;
    private boolean isDragging;
    private float deltaX;
    private float deltaY;
    private float x;
    private float y;
    
    public ModEditorScreen(final String displayName, final List<Value<?>> valList, final ClickGuiS click, final Module mod) {
        this.items = new ArrayList<MenuComponent>();
        this.guiWidth = 320.0f;
        this.isDragging = false;
        this.prev = click;
        this.displayName = displayName;
        float currY = 20.0f;
        int counter = 0;
        float currX = 0.0f;
        this.guiWidth = 135.0f;
        for (final Value<?> val : valList) {
            final MenuComponentValue component = new MenuComponentValue(val, currX + 15.0f, currY, 105.0f, 15.0f);
            this.items.add(component);
            currY += 20.0f;
            if (++counter == 10) {
                counter = 0;
                this.totalY = currY;
                currY = 20.0f;
                currX += 110.0f;
                this.guiWidth += 110.0f;
            }
        }
        this.items.add(new MenuComponentKeybind(mod, currX + 15.0f, currY, 105.0f, 15.0f));
        currY += 20.0f;
        this.items.add(new MenuComponentHidden(mod, currX + 15.0f, currY, 105.0f, 15.0f));
        currY += 20.0f;
        if (this.totalY < currY) {
            this.totalY = currY;
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
        this.x = res.func_78326_a() / 2 - this.guiWidth / 2.0f;
        this.y = res.func_78328_b() / 2 - this.totalY / 2.0f;
        if (this.y <= 10.0f) {
            this.y = 10.0f;
        }
    }
    
    public ModEditorScreen(final String displayName, final List<Value<?>> valList, final GuiScreen click) {
        this.items = new ArrayList<MenuComponent>();
        this.guiWidth = 320.0f;
        this.isDragging = false;
        this.prev = click;
        this.displayName = displayName;
        float currY = 20.0f;
        int counter = 0;
        float currX = 0.0f;
        this.guiWidth = 135.0f;
        for (final Value<?> val : valList) {
            final MenuComponentValue component = new MenuComponentValue(val, currX + 15.0f, currY, 105.0f, 15.0f);
            this.items.add(component);
            currY += 20.0f;
            if (++counter == 10) {
                counter = 0;
                this.totalY = currY;
                currY = 20.0f;
                currX += 110.0f;
                this.guiWidth += 110.0f;
            }
        }
        if (this.totalY < currY) {
            this.totalY = currY;
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
        this.x = res.func_78326_a() / 2 - this.guiWidth / 2.0f;
        this.y = res.func_78328_b() / 2 - this.totalY / 2.0f;
        if (this.y <= 10.0f) {
            this.y = 10.0f;
        }
    }
    
    @Override
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (this.isDragging) {
            this.x = mouseX - this.deltaX;
            this.y = mouseY - this.deltaY;
        }
        this.prev.func_73863_a(-1, -1, partialTicks);
        RenderUtil.drawRect(this.x, this.y, this.x + this.guiWidth, this.y + 15.0f, -3980989);
        RenderUtil.drawRect(this.x, this.y + 15.0f, this.x + this.guiWidth, this.y + this.totalY, -16777216);
        this.items.forEach(item -> item.renderWith(this.x, this.y, (float)mouseX, (float)mouseY, partialTicks));
    }
    
    @Override
    public void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseX > this.x && mouseX < this.x + this.guiWidth && mouseY > this.y && mouseY < this.y + this.totalY) {
            if (mouseY < this.y + 15.0f) {
                this.deltaX = mouseX - this.x;
                this.deltaY = mouseY - this.y;
                this.isDragging = true;
            }
            this.items.forEach(item -> item.clicked(mouseButton));
        }
        else if (!this.isDragging) {
            this.field_146297_k.func_147108_a(this.prev);
        }
    }
    
    @Override
    public void func_146286_b(final int mouseX, final int mouseY, final int state) {
        super.func_146286_b(mouseX, mouseY, state);
        this.isDragging = false;
        this.items.forEach(item -> item.onReleased(mouseX, mouseY, state));
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a(this.prev);
        }
        else {
            super.func_73869_a(typedChar, keyCode);
        }
        this.items.forEach(item -> item.keyTyped(typedChar, keyCode));
    }
    
    public void func_146274_d() throws IOException {
        super.func_146274_d();
        if (!Mouse.getEventButtonState()) {
            return;
        }
        this.items.forEach(menu -> menu.onMouseInput());
    }
}

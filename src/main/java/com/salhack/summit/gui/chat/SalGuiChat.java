// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.chat;

import net.minecraft.util.TabCompleter;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;

public class SalGuiChat extends GuiChat
{
    public SalGuiChat(final GuiChat oldChat) {
        this.field_146409_v = oldChat.field_146415_a.func_146179_b();
        this.func_146280_a(oldChat.field_146297_k, oldChat.field_146294_l, oldChat.field_146295_m);
    }
    
    public SalGuiChat(final String defaultText) {
        this.field_146409_v = defaultText;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.field_146416_h = this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().size();
        (this.field_146415_a = new SalGuiTextField(0, this.field_146289_q, 4, this.field_146295_m - 12, this.field_146294_l - 4, 12)).func_146203_f(256);
        this.field_146415_a.func_146185_a(false);
        this.field_146415_a.func_146195_b(true);
        this.field_146415_a.func_146180_a(this.field_146409_v);
        this.field_146415_a.func_146205_d(false);
        this.field_184096_i = (TabCompleter)new GuiChat.ChatTabCompleter(this.field_146415_a);
    }
}

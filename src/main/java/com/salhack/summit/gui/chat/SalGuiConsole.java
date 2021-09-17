// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.chat;

import net.minecraft.util.TabCompleter;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import com.salhack.summit.managers.CommandManager;
import com.salhack.summit.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;
import com.salhack.summit.main.Summit;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import com.salhack.summit.module.ui.Console;
import com.salhack.summit.command.Command;
import net.minecraft.client.gui.GuiChat;

public final class SalGuiConsole extends GuiChat
{
    private Command CurrentCommand;
    private Console Console;
    
    public SalGuiConsole(final Console p_Console) {
        this.CurrentCommand = null;
        this.Console = p_Console;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.field_146416_h = this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().size();
        (this.field_146415_a = new SalGuiTextField(2, this.field_146289_q, this.field_146294_l - 295, 5, 595, 12, true)).func_146203_f(256);
        this.field_146415_a.func_146185_a(false);
        this.field_146415_a.func_146195_b(true);
        this.field_146415_a.func_146180_a("");
        this.field_146415_a.func_146205_d(false);
        this.field_184096_i = (TabCompleter)new GuiChat.ChatTabCompleter(this.field_146415_a);
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        this.field_184096_i.func_186843_d();
        if (keyCode == 15) {
            this.field_184096_i.func_186841_a();
        }
        else {
            this.field_184096_i.func_186842_c();
        }
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
        }
        else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.func_146402_a(-1);
            }
            else if (keyCode == 208) {
                this.func_146402_a(1);
            }
            else if (keyCode == 201) {
                this.field_146297_k.field_71456_v.func_146158_b().func_146229_b(this.field_146297_k.field_71456_v.func_146158_b().func_146232_i() - 1);
            }
            else if (keyCode == 209) {
                this.field_146297_k.field_71456_v.func_146158_b().func_146229_b(-this.field_146297_k.field_71456_v.func_146158_b().func_146232_i() + 1);
            }
            else {
                this.field_146415_a.func_146201_a(typedChar, keyCode);
            }
        }
        else {
            final String s = this.field_146415_a.func_146179_b().trim();
            this.func_175275_f(s);
        }
    }
    
    public void func_175275_f(final String msg) {
        try {
            if (this.CurrentCommand != null) {
                this.CurrentCommand.processCommand(msg, msg.split(" "));
            }
        }
        catch (Exception e) {
            Summit.SendMessage("[Console]: bad input, Exception caught");
        }
    }
    
    public void func_146280_a(final Minecraft mc, final int width, final int height) {
        this.field_146297_k = mc;
        this.field_146296_j = mc.func_175599_af();
        this.field_146289_q = mc.field_71466_p;
        this.field_146294_l = width / 2;
        this.field_146295_m = height / 2;
        if (!MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.InitGuiEvent.Pre((GuiScreen)this, this.field_146292_n))) {
            this.field_146292_n.clear();
            this.func_73866_w_();
        }
        MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.InitGuiEvent.Post((GuiScreen)this, this.field_146292_n));
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        func_73734_a(this.field_146294_l - 300, 0, this.field_146294_l + 300, 17, Integer.MIN_VALUE);
        this.field_146415_a.func_146194_f();
        final ITextComponent itextcomponent = this.field_146297_k.field_71456_v.func_146158_b().func_146236_a(Mouse.getX(), Mouse.getY());
        if (itextcomponent != null && itextcomponent.func_150256_b().func_150210_i() != null) {
            this.func_175272_a(itextcomponent, mouseX, mouseY);
        }
        final String s = this.field_146415_a.func_146179_b();
        if (s.isEmpty()) {
            func_73734_a(this.field_146294_l - 300, 23, this.field_146294_l + 300, 40, Integer.MIN_VALUE);
            RenderUtil.drawStringWithShadow(String.format("Type a %scommand%s to get help.", ChatFormatting.GREEN, ChatFormatting.RESET), (float)(this.field_146294_l - 295), 26.0f, 16777215);
            return;
        }
        final String[] l_Split = s.split(" ");
        if (l_Split.length == 0) {
            return;
        }
        final List<Command> l_CommandsLike = CommandManager.Get().GetCommandsLike(l_Split[0]);
        if (l_CommandsLike == null || l_CommandsLike.isEmpty()) {
            func_73734_a(this.field_146294_l - 300, 23, this.field_146294_l + 300, 40, Integer.MIN_VALUE);
            RenderUtil.drawStringWithShadow("No commands found...", (float)(this.field_146294_l - 295), 26.0f, 16711680);
            return;
        }
        final float l_Divider = 17.0f;
        this.CurrentCommand = l_CommandsLike.get(0);
        int l_RealItr = 0;
        for (int l_I = 0; l_I < l_CommandsLike.size(); ++l_I) {
            final Command l_Command = l_CommandsLike.get(l_I);
            int l_Color = 16777215;
            if (l_Command.getName().equalsIgnoreCase(l_Split[0])) {
                for (final String l_Addon : l_Command.getChunks()) {
                    final String[] l_AddonSplit = l_Addon.split(" ");
                    l_Color = 16777215;
                    String l_ToWrite = ChatFormatting.GREEN + l_Command.getName() + ChatFormatting.RESET + " " + l_Addon;
                    if (l_AddonSplit.length > 0 && l_Split.length > 1) {
                        if (!l_AddonSplit[0].toLowerCase().startsWith(l_Split[1].toLowerCase())) {
                            continue;
                        }
                        l_ToWrite = ChatFormatting.GREEN + l_Command.getName() + " ";
                        for (int l_Y = 0; l_Y < l_AddonSplit.length; ++l_Y) {
                            if (l_Y == 0) {
                                l_ToWrite = l_ToWrite + ChatFormatting.GREEN + l_AddonSplit[l_Y] + ChatFormatting.RESET;
                            }
                            else {
                                l_ToWrite = l_ToWrite + " " + l_AddonSplit[l_Y];
                            }
                        }
                        func_73734_a(this.field_146294_l - 300, 23 + (int)(l_RealItr * 17.0f), this.field_146294_l + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                        RenderUtil.drawStringWithShadow(l_ToWrite, (float)(this.field_146294_l - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
                    }
                    else {
                        func_73734_a(this.field_146294_l - 300, 23 + (int)(l_RealItr * 17.0f), this.field_146294_l + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                        RenderUtil.drawStringWithShadow(l_ToWrite, (float)(this.field_146294_l - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
                    }
                }
                if (l_Split.length == 1) {
                    l_Color = 16772096;
                    func_73734_a(this.field_146294_l - 300, 23 + (int)(l_RealItr * 17.0f), this.field_146294_l + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                    RenderUtil.drawStringWithShadow(ChatFormatting.GREEN + l_Command.getName() + ChatFormatting.RESET + " " + l_Command.getDescription(), (float)(this.field_146294_l - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
                }
            }
            else {
                func_73734_a(this.field_146294_l - 300, 23 + (int)(l_RealItr * 17.0f), this.field_146294_l + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                RenderUtil.drawStringWithShadow(l_Command.getName(), (float)(this.field_146294_l - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
            }
        }
        func_73734_a(2, this.field_146295_m, this.field_146294_l, this.field_146295_m, Integer.MIN_VALUE);
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        if (this.Console.isEnabled()) {
            this.Console.toggle();
        }
    }
}

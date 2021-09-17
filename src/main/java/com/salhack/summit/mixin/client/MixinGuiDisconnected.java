// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import com.salhack.summit.main.AlwaysEnabledModule;
import com.salhack.summit.main.Wrapper;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.salhack.summit.gui.minecraft.SalGuiReconnectButton;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiDisconnected;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { GuiDisconnected.class }, priority = Integer.MAX_VALUE)
public class MixinGuiDisconnected extends MixinGuiScreen
{
    @Shadow
    public int field_175353_i;
    private SalGuiReconnectButton ReconnectingButton;
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void initGui(final CallbackInfo info) {
        this.field_146292_n.clear();
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b, this.field_146295_m - 30), I18n.func_135052_a("gui.toMenu", new Object[0])));
        this.field_146292_n.add(new GuiButton(421, this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 20, this.field_146295_m - 10), "Reconnect"));
        this.field_146292_n.add(this.ReconnectingButton = new SalGuiReconnectButton(420, this.field_146294_l / 2 - 100, Math.min(this.field_146295_m / 2 + this.field_175353_i / 2 + this.field_146289_q.field_78288_b + 40, this.field_146295_m + 10), "AutoReconnect"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("RETURN") })
    protected void actionPerformed(final GuiButton button, final CallbackInfo info) {
        if (button.field_146127_k == 420) {
            this.ReconnectingButton.Clicked();
        }
        else if (button.field_146127_k == 421) {
            Wrapper.GetMC().func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)null, Wrapper.GetMC(), AlwaysEnabledModule.LastIP, AlwaysEnabledModule.LastPort));
        }
    }
}

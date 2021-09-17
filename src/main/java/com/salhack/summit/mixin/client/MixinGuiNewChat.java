// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import com.salhack.summit.main.Wrapper;
import net.minecraft.util.math.MathHelper;
import com.salhack.summit.main.SummitStatic;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.text.ITextComponent;
import com.google.common.collect.Lists;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.ChatLine;
import java.util.List;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat
{
    @Shadow
    @Final
    public final List<ChatLine> field_146252_h;
    @Shadow
    @Final
    public final List<ChatLine> field_146253_i;
    @Shadow
    public int field_146250_j;
    @Shadow
    public boolean field_146251_k;
    
    public MixinGuiNewChat() {
        this.field_146252_h = (List<ChatLine>)Lists.newArrayList();
        this.field_146253_i = (List<ChatLine>)Lists.newArrayList();
    }
    
    @Inject(method = { "setChatLine" }, at = { @At("HEAD") }, cancellable = true)
    private void setChatLine(final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly, final CallbackInfo info) {
        if (SummitStatic.CHATMODIFICATIONS != null) {
            info.cancel();
            final int maxLines = (SummitStatic.CHATMODIFICATIONS.ChatLength.getValue() == -1) ? 16777215 : SummitStatic.CHATMODIFICATIONS.ChatLength.getValue();
            final GuiNewChat guiNewChat = (GuiNewChat)this;
            if (chatLineId != 0) {
                guiNewChat.func_146242_c(chatLineId);
            }
            final int i = MathHelper.func_76141_d(guiNewChat.func_146228_f() / guiNewChat.func_146244_h());
            final List<ITextComponent> list = (List<ITextComponent>)GuiUtilRenderComponents.func_178908_a(chatComponent, i, Wrapper.GetMC().field_71466_p, false, false);
            final boolean flag = guiNewChat.func_146241_e();
            for (final ITextComponent itextcomponent : list) {
                if (flag && this.field_146250_j > 0) {
                    this.field_146251_k = true;
                    guiNewChat.func_146229_b(1);
                }
                this.field_146253_i.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId));
            }
            while (this.field_146253_i.size() > maxLines) {
                this.field_146253_i.remove(this.field_146253_i.size() - 1);
            }
            if (!displayOnly) {
                this.field_146252_h.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
                while (this.field_146252_h.size() > maxLines) {
                    this.field_146252_h.remove(this.field_146252_h.size() - 1);
                }
            }
        }
    }
}

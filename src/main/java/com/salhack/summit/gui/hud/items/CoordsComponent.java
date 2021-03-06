// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.salhack.summit.managers.HudManager;
import java.text.DecimalFormat;
import com.salhack.summit.module.Value;
import com.salhack.summit.gui.hud.components.OptionalListHudComponent;

public class CoordsComponent extends OptionalListHudComponent
{
    public final Value<Boolean> NetherCoords;
    public final Value<Modes> Mode;
    final DecimalFormat Formatter;
    
    public CoordsComponent() {
        super("Coords", 2.0f, 245.0f, 100.0f, 100.0f);
        this.NetherCoords = new Value<Boolean>("NetherCoords", new String[] { "NC" }, "Displays nether coords.", true);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "Mode of displaying coordinates", Modes.Inline);
        this.Formatter = new DecimalFormat("#.#");
        this.setCurrentCornerList(HudManager.Get().GetModList("BottomLeft"));
        this.setEnabled(true);
    }
    
    public String format(final double p_Input) {
        String l_Result = this.Formatter.format(p_Input);
        if (!l_Result.contains(".")) {
            l_Result += ".0";
        }
        return l_Result;
    }
    
    @Override
    public void onUpdate() {
        StringBuilder builder = new StringBuilder();
        builder = new StringBuilder("XYZ ").append("(").append(ChatFormatting.WHITE).append(this.format(this.mc.field_71439_g.field_70165_t)).append(", ").append(ChatFormatting.WHITE).append(this.format(this.mc.field_71439_g.field_70163_u)).append(", ").append(ChatFormatting.WHITE).append(this.format(this.mc.field_71439_g.field_70161_v)).append(ChatFormatting.RESET).append(") ");
        final boolean shouldAppend = this.mc.field_71439_g.field_71093_bK != 1 && this.NetherCoords.getValue();
        final double x = (this.mc.field_71439_g.field_71093_bK == 0) ? (this.mc.field_71439_g.field_70165_t / 8.0) : (this.mc.field_71439_g.field_70165_t * 8.0);
        final double y = this.mc.field_71439_g.field_70163_u;
        final double z = (this.mc.field_71439_g.field_71093_bK == 0) ? (this.mc.field_71439_g.field_70161_v / 8.0) : (this.mc.field_71439_g.field_70161_v * 8.0);
        if (shouldAppend) {
            builder.append("[").append(ChatFormatting.WHITE).append(this.format(x)).append(", ").append(this.format(y)).append(", ").append(this.format(z)).append(ChatFormatting.RESET).append("]");
        }
        this.cornerItem.setName(builder.toString());
    }
    
    public enum Modes
    {
        Inline, 
        NextLine;
    }
}

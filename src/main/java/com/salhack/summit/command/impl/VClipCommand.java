// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.command.impl;

import net.minecraft.entity.Entity;
import com.salhack.summit.command.Command;

public class VClipCommand extends Command
{
    public VClipCommand() {
        super("VClip", "Allows you to vclip x blocks");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final double distance = Double.parseDouble(args[1]);
        final Entity entity = (Entity)(this.mc.field_71439_g.func_184218_aH() ? this.mc.field_71439_g.func_184187_bx() : this.mc.field_71439_g);
        if (entity != null) {
            entity.func_70107_b(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + distance, this.mc.field_71439_g.field_70161_v);
        }
        this.SendToChat(String.format("Teleported you %s blocks up", distance));
    }
    
    @Override
    public String getHelp() {
        return "Allows you teleport up x amount of blocks.";
    }
}

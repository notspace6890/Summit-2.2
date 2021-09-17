// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.command.impl;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import com.salhack.summit.util.MathUtil;
import com.salhack.summit.command.Command;

public class HClipCommand extends Command
{
    public HClipCommand() {
        super("HClip", "Allows you to horizontally clip x blocks");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final double blocksForward = Double.parseDouble(args[1]);
        final Vec3d direction = MathUtil.direction(this.mc.field_71439_g.field_70177_z);
        final Entity entity = (Entity)(this.mc.field_71439_g.func_184218_aH() ? this.mc.field_71439_g.func_184187_bx() : this.mc.field_71439_g);
        if (entity != null) {
            entity.func_70107_b(this.mc.field_71439_g.field_70165_t + direction.field_72450_a * blocksForward, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + direction.field_72449_c * blocksForward);
        }
        this.SendToChat(String.format("Teleported you %s blocks forward", blocksForward));
    }
    
    @Override
    public String getHelp() {
        return "Allows you teleport forward x amount of blocks.";
    }
}

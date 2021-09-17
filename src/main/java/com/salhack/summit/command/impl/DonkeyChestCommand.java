// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.command.impl;

import java.util.Iterator;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.Entity;
import com.salhack.summit.main.Summit;
import com.salhack.summit.main.Wrapper;
import com.salhack.summit.command.Command;

public class DonkeyChestCommand extends Command
{
    public DonkeyChestCommand() {
        super("DonkeyChest", "Opens an entity chest");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (Wrapper.GetPlayer() != null) {
            Summit.SendMessage("Sent the packet to open horse inventory");
            for (final Entity e : this.mc.field_71441_e.field_72996_f) {
                if (e instanceof AbstractChestHorse && e.func_70032_d((Entity)this.mc.field_71439_g) < 10.0f) {
                    this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)this.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(e, EnumHand.MAIN_HAND, new Vec3d(Math.random(), Math.random(), Math.random())));
                    this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(e, EnumHand.MAIN_HAND));
                    this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)this.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    break;
                }
            }
        }
    }
    
    @Override
    public String getHelp() {
        return "Opens an entity's chest";
    }
}

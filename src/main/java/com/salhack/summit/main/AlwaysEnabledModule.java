// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.main;

import com.salhack.summit.SummitMod;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import com.salhack.summit.events.MinecraftEvent;
import com.salhack.summit.events.network.EventServerPacket;
import com.salhack.summit.events.bus.EventHandler;
import com.salhack.summit.events.network.EventClientPacket;
import com.salhack.summit.events.bus.Listener;
import com.salhack.summit.events.bus.EventListener;

public class AlwaysEnabledModule implements EventListener
{
    public static String LastIP;
    public static int LastPort;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    
    public AlwaysEnabledModule() {
        C00Handshake packet;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof C00Handshake) {
                    packet = (C00Handshake)event.getPacket();
                    if (packet.func_149594_c() == EnumConnectionState.LOGIN) {
                        AlwaysEnabledModule.LastIP = packet.field_149598_b;
                        AlwaysEnabledModule.LastPort = packet.field_149599_c;
                    }
                }
                return;
            }
        });
        SPacketChat packet2;
        TextComponentString component;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                try {
                    if (event.getPacket() instanceof SPacketChat) {
                        if (Wrapper.GetMC().field_71439_g != null) {
                            packet2 = (SPacketChat)event.getPacket();
                            if (packet2.func_148915_c() instanceof TextComponentString) {
                                component = (TextComponentString)packet2.func_148915_c();
                                if (component.func_150254_d().toLowerCase().contains("polymer") || component.func_150254_d().toLowerCase().contains("veteranhack")) {
                                    event.cancel();
                                }
                            }
                        }
                    }
                    else if (event.getPacket() instanceof SPacketTitle) {
                        event.cancel();
                    }
                    else if (event.getPacket() instanceof SPacketPlayerListItem) {}
                }
                catch (Exception ex) {}
            }
        });
    }
    
    public void init() {
        SummitMod.EVENT_BUS.subscribe(this);
    }
    
    static {
        AlwaysEnabledModule.LastIP = null;
        AlwaysEnabledModule.LastPort = -1;
    }
}

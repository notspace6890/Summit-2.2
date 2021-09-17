// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.salhack.summit.events.world.EventChunkLoad;
import com.salhack.summit.SummitMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.play.server.SPacketChunkData;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.network.NetworkManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNetHandlerPlayClient
{
    @Shadow
    private WorldClient field_147300_g;
    @Shadow
    private Minecraft field_147299_f;
    @Shadow
    private boolean field_147309_h;
    @Shadow
    @Final
    private NetworkManager field_147302_e;
    @Shadow
    public int field_147304_c;
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") })
    public void handleChunkData(final SPacketChunkData packet, final CallbackInfo info) {
        SummitMod.EVENT_BUS.post(new EventChunkLoad(EventChunkLoad.Type.LOAD, this.field_147300_g.func_72964_e(packet.func_149273_e(), packet.func_149271_f())));
    }
}

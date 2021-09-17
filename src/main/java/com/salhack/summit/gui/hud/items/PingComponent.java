// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud.items;

import net.minecraft.client.network.NetworkPlayerInfo;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.salhack.summit.gui.hud.components.OptionalListHudComponent;

public class PingComponent extends OptionalListHudComponent
{
    public PingComponent() {
        super("Ping", 2.0f, 230.0f, 100.0f, 100.0f);
        this.setEnabled(true);
    }
    
    @Override
    public void onUpdate() {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null || this.mc.field_71439_g.func_110124_au() == null) {
            return;
        }
        final NetworkPlayerInfo playerInfo = this.mc.func_147114_u().func_175102_a(this.mc.field_71439_g.func_110124_au());
        if (playerInfo == null) {
            return;
        }
        this.cornerItem.setName("Ping " + ChatFormatting.WHITE + playerInfo.func_178853_c() + "ms");
    }
}

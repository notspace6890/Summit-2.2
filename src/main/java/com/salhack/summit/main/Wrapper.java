// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.main;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    static final Minecraft mc;
    
    public static Minecraft GetMC() {
        return Wrapper.mc;
    }
    
    public static EntityPlayerSP GetPlayer() {
        return Wrapper.mc.field_71439_g;
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}

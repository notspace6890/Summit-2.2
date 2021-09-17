// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import com.salhack.summit.util.entity.EntityUtil;
import net.minecraft.entity.Entity;
import com.salhack.summit.managers.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SalUtil
{
    public static EntityPlayer findClosestTarget() {
        if (Minecraft.func_71410_x().field_71441_e.field_73010_i.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : Minecraft.func_71410_x().field_71441_e.field_73010_i) {
            if (target == Minecraft.func_71410_x().field_71439_g) {
                continue;
            }
            if (FriendManager.Get().IsFriend((Entity)target)) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (closestTarget != null && Minecraft.func_71410_x().field_71439_g.func_70032_d((Entity)target) > Minecraft.func_71410_x().field_71439_g.func_70032_d((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    public Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}

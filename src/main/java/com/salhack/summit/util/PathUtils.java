// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import net.minecraft.util.math.MathHelper;
import java.util.ArrayList;
import javax.vecmath.Vector3d;
import java.util.List;

public final class PathUtils extends MinecraftInstance
{
    public static List<Vector3d> findBlinkPath(final double tpX, final double tpY, final double tpZ) {
        final List<Vector3d> positions = new ArrayList<Vector3d>();
        double curX = PathUtils.mc.field_71439_g.field_70165_t;
        double curY = PathUtils.mc.field_71439_g.field_70163_u;
        double curZ = PathUtils.mc.field_71439_g.field_70161_v;
        double distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);
        int count = 0;
        while (distance > 0.0) {
            distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);
            final double diffX = curX - tpX;
            final double diffY = curY - tpY;
            final double diffZ = curZ - tpZ;
            final double offset = ((count & 0x1) == 0x0) ? 0.4 : 0.1;
            final double minX = Math.min(Math.abs(diffX), offset);
            if (diffX < 0.0) {
                curX += minX;
            }
            if (diffX > 0.0) {
                curX -= minX;
            }
            final double minY = Math.min(Math.abs(diffY), 0.25);
            if (diffY < 0.0) {
                curY += minY;
            }
            if (diffY > 0.0) {
                curY -= minY;
            }
            final double minZ = Math.min(Math.abs(diffZ), offset);
            if (diffZ < 0.0) {
                curZ += minZ;
            }
            if (diffZ > 0.0) {
                curZ -= minZ;
            }
            positions.add(new Vector3d(curX, curY, curZ));
            ++count;
        }
        return positions;
    }
    
    public static List<Vector3d> findPath(final double tpX, final double tpY, final double tpZ, final double offset) {
        final List<Vector3d> positions = new ArrayList<Vector3d>();
        final double steps = Math.ceil(getDistance(PathUtils.mc.field_71439_g.field_70165_t, PathUtils.mc.field_71439_g.field_70163_u, PathUtils.mc.field_71439_g.field_70161_v, tpX, tpY, tpZ) / offset);
        final double dX = tpX - PathUtils.mc.field_71439_g.field_70165_t;
        final double dY = tpY - PathUtils.mc.field_71439_g.field_70163_u;
        final double dZ = tpZ - PathUtils.mc.field_71439_g.field_70161_v;
        for (double d = 1.0; d <= steps; ++d) {
            positions.add(new Vector3d(PathUtils.mc.field_71439_g.field_70165_t + dX * d / steps, PathUtils.mc.field_71439_g.field_70163_u + dY * d / steps, PathUtils.mc.field_71439_g.field_70161_v + dZ * d / steps));
        }
        return positions;
    }
    
    private static double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double xDiff = x1 - x2;
        final double yDiff = y1 - y2;
        final double zDiff = z1 - z2;
        return MathHelper.func_76133_a(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
}

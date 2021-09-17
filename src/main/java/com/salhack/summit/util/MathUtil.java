// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

public final class MathUtil
{
    private static Minecraft mc;
    
    public static double calculateDistanceWithPartialTicks(final double n, final double n2, final float renderPartialTicks) {
        return n2 + (n - n2) * MathUtil.mc.func_184121_ak();
    }
    
    public static Vec3d interpolateEntityClose(final Entity entity, final float renderPartialTicks) {
        return new Vec3d(calculateDistanceWithPartialTicks(entity.field_70165_t, entity.field_70142_S, renderPartialTicks) - MathUtil.mc.func_175598_ae().field_78725_b, calculateDistanceWithPartialTicks(entity.field_70163_u, entity.field_70137_T, renderPartialTicks) - MathUtil.mc.func_175598_ae().field_78726_c, calculateDistanceWithPartialTicks(entity.field_70161_v, entity.field_70136_U, renderPartialTicks) - MathUtil.mc.func_175598_ae().field_78723_d);
    }
    
    public static Vec3d interpolateVec3dPos(final Vec3d pos, final float renderPartialTicks) {
        return new Vec3d(calculateDistanceWithPartialTicks(pos.field_72450_a, pos.field_72450_a, renderPartialTicks) - MathUtil.mc.func_175598_ae().field_78725_b, calculateDistanceWithPartialTicks(pos.field_72448_b, pos.field_72448_b - 0.021, renderPartialTicks) - MathUtil.mc.func_175598_ae().field_78726_c, calculateDistanceWithPartialTicks(pos.field_72449_c, pos.field_72449_c, renderPartialTicks) - MathUtil.mc.func_175598_ae().field_78723_d);
    }
    
    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * time);
    }
    
    public static double radToDeg(final double rad) {
        return rad * 57.295780181884766;
    }
    
    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.field_72450_a - from.field_72450_a;
        final double difY = (to.field_72448_b - from.field_72448_b) * -1.0;
        final double difZ = to.field_72449_c - from.field_72449_c;
        final double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static double[] directionSpeed(final double speed) {
        final Minecraft mc = Minecraft.func_71410_x();
        float forward = mc.field_71439_g.field_71158_b.field_192832_b;
        float side = mc.field_71439_g.field_71158_b.field_78902_a;
        float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static double[] directionSpeedNoForward(final double speed) {
        final Minecraft mc = Minecraft.func_71410_x();
        float forward = 1.0f;
        float side = 0.0f;
        float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static Vec3d mult(final Vec3d factor, final Vec3d multiplier) {
        return new Vec3d(factor.field_72450_a * multiplier.field_72450_a, factor.field_72448_b * multiplier.field_72448_b, factor.field_72449_c * multiplier.field_72449_c);
    }
    
    public static Vec3d mult(final Vec3d factor, final float multiplier) {
        return new Vec3d(factor.field_72450_a * multiplier, factor.field_72448_b * multiplier, factor.field_72449_c * multiplier);
    }
    
    public static Vec3d div(final Vec3d factor, final Vec3d divisor) {
        return new Vec3d(factor.field_72450_a / divisor.field_72450_a, factor.field_72448_b / divisor.field_72448_b, factor.field_72449_c / divisor.field_72449_c);
    }
    
    public static Vec3d div(final Vec3d factor, final float divisor) {
        return new Vec3d(factor.field_72450_a / divisor, factor.field_72448_b / divisor, factor.field_72449_c / divisor);
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            return value;
        }
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float clamp(float val, final float min, final float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }
    
    public static float wrap(float val) {
        val %= 360.0f;
        if (val >= 180.0f) {
            val -= 360.0f;
        }
        if (val < -180.0f) {
            val += 360.0f;
        }
        return val;
    }
    
    public static double map(double value, final double a, final double b, final double c, final double d) {
        value = (value - a) / (b - a);
        return c + value * (d - c);
    }
    
    public static double linear(final double from, final double to, final double incline) {
        return (from < to - incline) ? (from + incline) : ((from > to + incline) ? (from - incline) : to);
    }
    
    public static double parabolic(final double from, final double to, final double incline) {
        return from + (to - from) / incline;
    }
    
    public static double getDistance(final Vec3d pos, final double x, final double y, final double z) {
        final double deltaX = pos.field_72450_a - x;
        final double deltaY = pos.field_72448_b - y;
        final double deltaZ = pos.field_72449_c - z;
        return MathHelper.func_76133_a(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    
    public static double[] calcIntersection(final double[] line, final double[] line2) {
        final double a1 = line[3] - line[1];
        final double b1 = line[0] - line[2];
        final double c1 = a1 * line[0] + b1 * line[1];
        final double a2 = line2[3] - line2[1];
        final double b2 = line2[0] - line2[2];
        final double c2 = a2 * line2[0] + b2 * line2[1];
        final double delta = a1 * b2 - a2 * b1;
        return new double[] { (b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta };
    }
    
    public static double calculateAngle(final double x1, final double y1, final double x2, final double y2) {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        angle += Math.ceil(-angle / 360.0) * 360.0;
        return angle;
    }
    
    static {
        MathUtil.mc = Minecraft.func_71410_x();
    }
}

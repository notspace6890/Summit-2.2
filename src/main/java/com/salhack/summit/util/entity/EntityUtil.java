// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util.entity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.apache.commons.io.IOUtils;
import java.net.URL;
import java.util.UUID;
import net.minecraft.util.math.Vec3i;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import com.salhack.summit.util.MathUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.Entity;

public class EntityUtil
{
    public static boolean isPassive(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).func_70919_bu()) && (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid || (e instanceof EntityIronGolem && ((EntityIronGolem)e).func_70643_av() == null));
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    public static boolean isFakeLocalPlayer(final Entity entity) {
        return entity != null && entity.func_145782_y() == -100 && Minecraft.func_71410_x().field_71439_g != entity;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, 0.0 * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final Vec3d vec) {
        return getInterpolatedAmount(entity, vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static boolean isMobAggressive(final Entity entity) {
        if (entity instanceof EntityPigZombie) {
            if (((EntityPigZombie)entity).func_184734_db() || ((EntityPigZombie)entity).func_175457_ck()) {
                return true;
            }
        }
        else {
            if (entity instanceof EntityWolf) {
                return ((EntityWolf)entity).func_70919_bu() && !Minecraft.func_71410_x().field_71439_g.equals((Object)((EntityWolf)entity).func_70902_q());
            }
            if (entity instanceof EntityEnderman) {
                return ((EntityEnderman)entity).func_70823_r();
            }
        }
        return isHostileMob(entity);
    }
    
    public static boolean isNeutralMob(final Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }
    
    public static boolean isFriendlyMob(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(entity)) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity instanceof EntityVillager || entity instanceof EntityIronGolem || (isNeutralMob(entity) && !isMobAggressive(entity));
    }
    
    public static boolean isHostileMob(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity)) || entity instanceof EntitySpider;
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return getInterpolatedPos(entity, ticks).func_178786_a(Minecraft.func_71410_x().func_175598_ae().field_78725_b, Minecraft.func_71410_x().func_175598_ae().field_78726_c, Minecraft.func_71410_x().func_175598_ae().field_78723_d);
    }
    
    public static boolean isInWater(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.field_70163_u + 0.01;
        for (int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); ++x) {
            for (int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Minecraft.func_71410_x().field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isDrivenByPlayer(final Entity entityIn) {
        return Minecraft.func_71410_x().field_71439_g != null && entityIn != null && entityIn.equals((Object)Minecraft.func_71410_x().field_71439_g.func_184187_bx());
    }
    
    public static boolean isAboveWater(final Entity entity) {
        return isAboveWater(entity, false);
    }
    
    public static boolean isAboveWater(final Entity entity, final boolean packet) {
        if (entity == null) {
            return false;
        }
        final double y = entity.field_70163_u - (packet ? 0.03 : (isPlayer(entity) ? 0.2 : 0.5));
        for (int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); ++x) {
            for (int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.func_76128_c(y), z);
                if (Minecraft.func_71410_x().field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.field_70165_t - px;
        double diry = me.field_70163_u - py;
        double dirz = me.field_70161_v - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static boolean isPlayer(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static double getRelativeX(final float yaw) {
        return MathHelper.func_76126_a(-yaw * 0.017453292f);
    }
    
    public static double getRelativeZ(final float yaw) {
        return MathHelper.func_76134_b(yaw * 0.017453292f);
    }
    
    public static int GetPlayerMS(final EntityPlayer p_Player) {
        if (p_Player.func_110124_au() == null) {
            return 0;
        }
        final NetworkPlayerInfo playerInfo = Minecraft.func_71410_x().func_147114_u().func_175102_a(p_Player.func_110124_au());
        if (playerInfo == null) {
            return 0;
        }
        return playerInfo.func_178853_c();
    }
    
    public static Vec3d CalculateExpectedPosition(final EntityPlayer p_Player) {
        final Vec3d l_Position = new Vec3d(p_Player.field_70165_t, p_Player.field_70163_u, p_Player.field_70161_v);
        if (p_Player.field_70142_S != p_Player.field_70165_t && p_Player.field_70137_T != p_Player.field_70163_u && p_Player.field_70136_U != p_Player.field_70161_v) {
            return l_Position;
        }
        final int l_PlayerMS = GetPlayerMS(p_Player);
        final double deltaX = p_Player.field_70165_t - p_Player.field_70169_q;
        final double deltaZ = p_Player.field_70161_v - p_Player.field_70166_s;
        final float tickRate = Minecraft.func_71410_x().field_71428_T.field_194149_e / 1000.0f;
        final float l_Distance = MathHelper.func_76133_a(deltaX * deltaX + deltaZ * deltaZ);
        final double l_Facing = MathUtil.calculateAngle(p_Player.field_70165_t, p_Player.field_70161_v, p_Player.field_70142_S, p_Player.field_70136_U) / 45.0;
        return new Vec3d(p_Player.field_70165_t + Math.cos(l_Facing) * l_Distance, p_Player.field_70163_u, p_Player.field_70161_v + Math.sin(l_Facing) * l_Distance);
    }
    
    public static double GetDistance(final double p_X, final double p_Y, final double p_Z, final double x, final double y, final double z) {
        final double d0 = p_X - x;
        final double d2 = p_Y - y;
        final double d3 = p_Z - z;
        return MathHelper.func_76133_a(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public static double GetDistanceOfEntityToBlock(final Entity p_Entity, final BlockPos p_Pos) {
        return GetDistance(p_Entity.field_70165_t, p_Entity.field_70163_u, p_Entity.field_70161_v, p_Pos.func_177958_n(), p_Pos.func_177956_o(), p_Pos.func_177952_p());
    }
    
    public static boolean IsVehicle(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }
    
    public static BlockPos GetPositionVectorBlockPos(final Entity entity, @Nullable final BlockPos toAdd) {
        final Vec3d v = entity.func_174791_d();
        if (toAdd == null) {
            return new BlockPos(v.field_72450_a, v.field_72448_b, v.field_72449_c);
        }
        return new BlockPos(v.field_72450_a, v.field_72448_b, v.field_72449_c).func_177971_a((Vec3i)toAdd);
    }
    
    public static String getNameFromUUID(final UUID id) {
        final String url = "https://api.mojang.com/user/profiles/" + id.toString().replace("-", "") + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "unk";
    }
}

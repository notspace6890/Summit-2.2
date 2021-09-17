// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import com.salhack.summit.util.entity.EntityUtil;
import net.minecraft.world.World;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import com.salhack.summit.main.Wrapper;
import net.minecraft.util.math.BlockPos;

public class CrystalUtils
{
    public static boolean CanPlaceCrystalIfObbyWasAtPos(final BlockPos pos) {
        final Minecraft mc = Wrapper.GetMC();
        final Block floor = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c();
        final Block ceil = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c();
        return floor == Blocks.field_150350_a && ceil == Blocks.field_150350_a && mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos.func_177982_a(0, 1, 0))).isEmpty();
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos) {
        final Minecraft mc = Wrapper.GetMC();
        final Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (block == Blocks.field_150343_Z || block == Blocks.field_150357_h) {
            final Block floor = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c();
            final Block ceil = mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c();
            if (floor == Blocks.field_150350_a && ceil == Blocks.field_150350_a && mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos.func_177982_a(0, 1, 0))).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static BlockPos GetPlayerPosFloored(final EntityPlayer p_Player) {
        return new BlockPos(Math.floor(p_Player.field_70165_t), Math.floor(p_Player.field_70163_u), Math.floor(p_Player.field_70161_v));
    }
    
    public static List<BlockPos> findCrystalBlocks(final EntityPlayer p_Player, final float p_Range) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)BlockInteractionHelper.getSphere(GetPlayerPosFloored(p_Player), p_Range, (int)p_Range, false, true, 0).stream().filter((Predicate<? super Object>)CrystalUtils::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public static float calculateDamage(final World p_World, final double posX, final double posY, final double posZ, final Entity entity, final int p_InterlopedAmount) {
        if (entity == Wrapper.GetPlayer() && Wrapper.GetPlayer().field_71075_bZ.field_75098_d) {
            return 0.0f;
        }
        final float doubleExplosionSize = 12.0f;
        double l_Distance = entity.func_70011_f(posX, posY, posZ);
        if (l_Distance > doubleExplosionSize) {
            return 0.0f;
        }
        if (p_InterlopedAmount > 0) {
            final Vec3d l_Interloped = EntityUtil.getInterpolatedAmount(entity, p_InterlopedAmount);
            l_Distance = EntityUtil.GetDistance(l_Interloped.field_72450_a, l_Interloped.field_72448_b, l_Interloped.field_72449_c, posX, posY, posZ);
        }
        final double distancedsize = l_Distance / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(p_World, damage), new Explosion(p_World, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.func_94539_a(explosion);
            damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            final int k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
            final float f = MathHelper.func_76131_a((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.func_70644_a(Potion.func_188412_a(11))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
        return damage;
    }
    
    private static float getDamageMultiplied(final World p_World, final float damage) {
        final int diff = p_World.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final World p_World, final EntityEnderCrystal crystal, final Entity entity) {
        return calculateDamage(p_World, crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity, 0);
    }
}

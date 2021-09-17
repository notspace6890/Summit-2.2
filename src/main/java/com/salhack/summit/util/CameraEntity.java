// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import net.minecraft.entity.MoverType;
import com.salhack.summit.main.SummitStatic;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityPlayerSP;

public class CameraEntity extends EntityPlayerSP
{
    @Nullable
    private static Entity originalRenderViewEntity;
    @Nullable
    private static CameraEntity camera;
    private static boolean cullChunksOriginal;
    private static float forwardRamped;
    private static float strafeRamped;
    private static float verticalRamped;
    private static boolean sprinting;
    
    public CameraEntity(final Minecraft mc, final World world, final NetHandlerPlayClient nethandler, final StatisticsManager stats, final RecipeBook recipeBook) {
        super(mc, world, nethandler, stats, recipeBook);
    }
    
    public boolean func_175149_v() {
        return true;
    }
    
    public static void movementTick(final boolean sneak, final boolean jump) {
        final CameraEntity camera = getCamera();
        if (camera != null) {
            final Minecraft mc = Minecraft.func_71410_x();
            camera.updateLastTickPosition();
            float forward = 0.0f;
            float vertical = 0.0f;
            float strafe = 0.0f;
            final GameSettings options = mc.field_71474_y;
            if (options.field_74351_w.func_151470_d()) {
                ++forward;
            }
            if (options.field_74368_y.func_151470_d()) {
                --forward;
            }
            if (options.field_74370_x.func_151470_d()) {
                ++strafe;
            }
            if (options.field_74366_z.func_151470_d()) {
                --strafe;
            }
            if (options.field_74314_A.func_151470_d()) {
                ++vertical;
            }
            if (options.field_74311_E.func_151470_d()) {
                --vertical;
            }
            if (options.field_151444_V.func_151470_d()) {
                CameraEntity.sprinting = true;
            }
            else if (forward == 0.0f) {
                CameraEntity.sprinting = false;
            }
            final float rampAmount = 0.15f;
            float speed = strafe * strafe + forward * forward;
            if (forward != 0.0f && strafe != 0.0f) {
                speed = (float)Math.sqrt(speed * 0.6);
            }
            else {
                speed = 1.0f;
            }
            CameraEntity.forwardRamped = getRampedMotion(CameraEntity.forwardRamped, forward, rampAmount) / speed;
            CameraEntity.verticalRamped = getRampedMotion(CameraEntity.verticalRamped, vertical, rampAmount);
            CameraEntity.strafeRamped = getRampedMotion(CameraEntity.strafeRamped, strafe, rampAmount) / speed;
            forward = (CameraEntity.sprinting ? (CameraEntity.forwardRamped * 3.0f) : CameraEntity.forwardRamped);
            camera.handleMotion(forward, CameraEntity.verticalRamped, CameraEntity.strafeRamped);
        }
    }
    
    private static float getRampedMotion(float current, final float input, float rampAmount) {
        if (input != 0.0f) {
            if (input < 0.0f) {
                rampAmount *= -1.0f;
            }
            if (input < 0.0f != current < 0.0f) {
                current = 0.0f;
            }
            current = MathHelper.func_76131_a(current + rampAmount, -1.0f, 1.0f);
        }
        else {
            current *= 0.5f;
        }
        return current;
    }
    
    private static double getMoveSpeed() {
        double base = 0.07;
        if (SummitStatic.FREECAM != null) {
            base = SummitStatic.FREECAM.speed.getValue() / 10.0f;
        }
        return base * 10.0;
    }
    
    private void handleMotion(final float forward, final float up, final float strafe) {
        final double xFactor = Math.sin(this.field_70177_z * 3.141592653589793 / 180.0);
        final double zFactor = Math.cos(this.field_70177_z * 3.141592653589793 / 180.0);
        final double scale = getMoveSpeed();
        this.field_70159_w = (strafe * zFactor - forward * xFactor) * scale;
        this.field_70181_x = up * scale;
        this.field_70179_y = (forward * zFactor + strafe * xFactor) * scale;
        this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70176_ah = (int)Math.floor(this.field_70165_t) >> 4;
        this.field_70162_ai = (int)Math.floor(this.field_70163_u) >> 4;
        this.field_70164_aj = (int)Math.floor(this.field_70161_v) >> 4;
    }
    
    private void updateLastTickPosition() {
        final double field_70165_t = this.field_70165_t;
        this.field_70142_S = field_70165_t;
        this.field_70169_q = field_70165_t;
        final double field_70163_u = this.field_70163_u;
        this.field_70137_T = field_70163_u;
        this.field_70167_r = field_70163_u;
        final double field_70161_v = this.field_70161_v;
        this.field_70136_U = field_70161_v;
        this.field_70166_s = field_70161_v;
    }
    
    public void setCameraRotations(final float yaw, final float pitch) {
        this.field_70177_z = yaw;
        this.field_70125_A = pitch;
        this.field_70126_B = this.field_70177_z;
        this.field_70127_C = this.field_70125_A;
        this.func_70034_d(this.field_70177_z);
        this.func_181013_g(this.field_70177_z);
    }
    
    public void updateCameraRotations(final float yawChange, final float pitchChange) {
        this.field_70177_z += yawChange * 0.15f;
        this.field_70125_A = MathHelper.func_76131_a(this.field_70125_A - pitchChange * 0.15f, -90.0f, 90.0f);
        this.setCameraRotations(this.field_70177_z, this.field_70125_A);
    }
    
    private static CameraEntity createCameraEntity(final Minecraft mc) {
        final CameraEntity camera = new CameraEntity(mc, (World)mc.field_71441_e, mc.field_71439_g.field_71174_a, mc.field_71439_g.func_146107_m(), mc.field_71439_g.func_192035_E());
        camera.field_70145_X = true;
        final Entity player = (Entity)mc.field_71439_g;
        if (player != null) {
            camera.func_70012_b(player.field_70165_t, player.field_70163_u, player.field_70161_v, player.field_70177_z, player.field_70125_A);
            camera.field_70126_B = camera.field_70177_z;
            camera.field_70127_C = camera.field_70125_A;
            camera.func_70034_d(camera.field_70177_z);
            camera.func_181013_g(camera.field_70177_z);
        }
        return camera;
    }
    
    @Nullable
    public static CameraEntity getCamera() {
        return CameraEntity.camera;
    }
    
    public static void setCameraState(final boolean enabled) {
        final Minecraft mc = Minecraft.func_71410_x();
        if (enabled) {
            createAndSetCamera(mc);
        }
        else {
            removeCamera(mc);
        }
    }
    
    private static void createAndSetCamera(final Minecraft mc) {
        CameraEntity.camera = createCameraEntity(mc);
        CameraEntity.originalRenderViewEntity = mc.func_175606_aa();
        CameraEntity.cullChunksOriginal = mc.field_175612_E;
        mc.func_175607_a((Entity)CameraEntity.camera);
        mc.field_175612_E = false;
    }
    
    private static void removeCamera(final Minecraft mc) {
        mc.func_175607_a(CameraEntity.originalRenderViewEntity);
        mc.field_175612_E = CameraEntity.cullChunksOriginal;
        if (mc.field_71441_e != null && CameraEntity.camera != null) {
            CameraUtils.markChunksForRebuildOnDeactivation(CameraEntity.camera.field_70176_ah, CameraEntity.camera.field_70164_aj);
        }
        CameraEntity.originalRenderViewEntity = null;
        CameraEntity.camera = null;
    }
}

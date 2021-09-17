// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util;

import net.minecraft.entity.Entity;
import com.salhack.summit.main.Wrapper;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.util.math.MathHelper;
import com.salhack.summit.main.SummitStatic;
import net.minecraft.client.Minecraft;

public class CameraUtils
{
    private static final Minecraft MC;
    private static float cameraYaw;
    private static float cameraPitch;
    private static boolean freeCameraSpectator;
    
    public static void setFreeCameraSpectator(final boolean isSpectator) {
        CameraUtils.freeCameraSpectator = isSpectator;
    }
    
    public static boolean getFreeCameraSpectator() {
        return CameraUtils.freeCameraSpectator;
    }
    
    public static boolean freecamEnabled() {
        return SummitStatic.FREECAM != null && SummitStatic.FREECAM.isEnabled() && SummitStatic.FREECAM.Mode.getValue().equals("Camera");
    }
    
    public static boolean shouldPreventPlayerInputs() {
        return freecamEnabled();
    }
    
    public static boolean shouldPreventPlayerMovement() {
        return freecamEnabled();
    }
    
    public static float getCameraYaw() {
        return MathHelper.func_76142_g(CameraUtils.cameraYaw);
    }
    
    public static float getCameraPitch() {
        return MathHelper.func_76142_g(CameraUtils.cameraPitch);
    }
    
    public static void setCameraYaw(final float yaw) {
        CameraUtils.cameraYaw = yaw;
    }
    
    public static void setCameraPitch(final float pitch) {
        CameraUtils.cameraPitch = pitch;
    }
    
    public static void setCameraRotations(final float yaw, final float pitch) {
        final CameraEntity camera = CameraEntity.getCamera();
        if (camera != null) {
            camera.setCameraRotations(yaw, pitch);
        }
    }
    
    public static void updateCameraRotations(final float yawChange, final float pitchChange) {
        final CameraEntity camera = CameraEntity.getCamera();
        if (camera != null) {
            camera.updateCameraRotations(yawChange, pitchChange);
        }
    }
    
    public static void markChunksForRebuild(final ViewFrustum storage, final int chunkX, final int chunkZ, final int lastChunkX, final int lastChunkZ) {
        if (chunkX == lastChunkX && chunkZ == lastChunkZ) {
            return;
        }
        final int viewDistance = CameraUtils.MC.field_71474_y.field_151451_c;
        final ChunkProviderClient provider = CameraUtils.MC.field_71441_e.func_72863_F();
        if (chunkX != lastChunkX) {
            final int minCX = (chunkX > lastChunkX) ? (lastChunkX + viewDistance) : (chunkX - viewDistance);
            for (int maxCX = (chunkX > lastChunkX) ? (chunkX + viewDistance) : (lastChunkX - viewDistance), cx = minCX; cx <= maxCX; ++cx) {
                for (int cz = chunkZ - viewDistance; cz <= chunkZ + viewDistance; ++cz) {
                    if (provider.func_191062_e(cx, cz)) {
                        final int x = cx << 4;
                        final int z = cz << 4;
                        storage.func_187474_a(x, 0, z, x, 255, z, false);
                    }
                }
            }
        }
        if (chunkZ != lastChunkZ) {
            final int minCZ = (chunkZ > lastChunkZ) ? (lastChunkZ + viewDistance) : (chunkZ - viewDistance);
            for (int maxCZ = (chunkZ > lastChunkZ) ? (chunkZ + viewDistance) : (lastChunkZ - viewDistance), cz2 = minCZ; cz2 <= maxCZ; ++cz2) {
                for (int cx2 = chunkX - viewDistance; cx2 <= chunkX + viewDistance; ++cx2) {
                    if (provider.func_191062_e(cx2, cz2)) {
                        final int x = cx2 << 4;
                        final int z = cz2 << 4;
                        storage.func_187474_a(x, 0, z, x, 255, z, false);
                    }
                }
            }
        }
    }
    
    public static void markChunksForRebuildOnDeactivation(final int lastChunkX, final int lastChunkZ) {
        final ChunkProviderClient provider = CameraUtils.MC.field_71441_e.func_72863_F();
        final Entity entity = Wrapper.GetMC().func_175606_aa();
        final int viewDistance = CameraUtils.MC.field_71474_y.field_151451_c;
        final int chunkX = entity.field_70176_ah;
        final int chunkZ = entity.field_70164_aj;
        final int minCameraCX = lastChunkX - viewDistance;
        final int maxCameraCX = lastChunkX + viewDistance;
        final int minCameraCZ = lastChunkZ - viewDistance;
        final int maxCameraCZ = lastChunkZ + viewDistance;
        final int minCX = chunkX - viewDistance;
        final int maxCX = chunkX + viewDistance;
        final int minCZ = chunkZ - viewDistance;
        for (int maxCZ = chunkZ + viewDistance, cz = minCZ; cz <= maxCZ; ++cz) {
            for (int cx = minCX; cx <= maxCX; ++cx) {
                if ((cx < minCameraCX || cx > maxCameraCX || cz < minCameraCZ || cz > maxCameraCZ) && provider.func_191062_e(cx, cz)) {
                    final int x = cx << 4;
                    final int z = cz << 4;
                    CameraUtils.MC.field_71441_e.func_147458_c(x, 0, z, x, 255, z);
                }
            }
        }
    }
    
    static {
        MC = Minecraft.func_71410_x();
    }
}

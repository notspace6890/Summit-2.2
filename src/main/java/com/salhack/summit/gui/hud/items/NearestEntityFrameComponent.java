// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud.items;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityPigZombie;
import com.salhack.summit.util.entity.EntityUtil;
import com.salhack.summit.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.text.DecimalFormat;
import java.util.Comparator;
import net.minecraft.entity.EntityLivingBase;
import com.salhack.summit.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import com.salhack.summit.module.Value;
import com.salhack.summit.gui.hud.components.DraggableHudComponent;

public class NearestEntityFrameComponent extends DraggableHudComponent
{
    public final Value<Boolean> Players;
    public final Value<Boolean> Friends;
    public final Value<Boolean> Mobs;
    public final Value<Boolean> Animals;
    
    public NearestEntityFrameComponent() {
        super("NearestEntityFrame", 400.0f, 2.0f, 0.0f, 0.0f);
        this.Players = new Value<Boolean>("Players", new String[] { "P" }, "Displays players", true);
        this.Friends = new Value<Boolean>("Friends", new String[] { "F" }, "Displays Friends", false);
        this.Mobs = new Value<Boolean>("Mobs", new String[] { "M" }, "Displays Mobs", true);
        this.Animals = new Value<Boolean>("Animals", new String[] { "A" }, "Displays Animals", true);
    }
    
    @Override
    public void onRender(final ScaledResolution res, final float p_MouseX, final float p_MouseY, final float p_PartialTicks) {
        if (this.mc.field_71441_e == null) {
            return;
        }
        super.onRender(res, p_MouseX, p_MouseY, p_PartialTicks);
        RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -1727263732);
        final EntityLivingBase l_Entity = (EntityLivingBase)this.mc.field_71441_e.field_72996_f.stream().filter(entity -> this.IsValidEntity(entity)).map(entity -> entity).min(Comparator.comparing(c -> this.mc.field_71439_g.func_70032_d(c))).orElse(null);
        if (l_Entity == null) {
            return;
        }
        final float l_HealthPct = (l_Entity.func_110143_aJ() + l_Entity.func_110139_bj()) / l_Entity.func_110138_aP() * 100.0f;
        final float l_HealthBarPct = Math.min(l_HealthPct, 100.0f);
        final DecimalFormat l_Format = new DecimalFormat("#.#");
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GuiInventory.func_147046_a((int)this.getX() + 10, (int)this.getY() + 30, 15, p_MouseX, p_MouseY, l_Entity);
        GlStateManager.func_179091_B();
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtil.drawStringWithShadow(l_Entity.func_70005_c_(), this.getX() + 20.0f, this.getY() + 1.0f, 16777215);
        RenderUtil.drawGradientRect(this.getX() + 20.0f, this.getY() + 11.0f, this.getX() + 20.0f + l_HealthBarPct, this.getY() + 22.0f, -1717570715, -1726742784);
        RenderUtil.drawStringWithShadow(String.format("(%s) %s / %s", l_Format.format(l_HealthPct) + "%", l_Format.format(l_Entity.func_110143_aJ() + l_Entity.func_110139_bj()), l_Format.format(l_Entity.func_110138_aP())), this.getX() + 20.0f, this.getY() + 11.0f, 16777215);
        this.setWidth(120.0f);
        this.setHeight(33.0f);
    }
    
    private boolean IsValidEntity(final Entity p_Entity) {
        if (!(p_Entity instanceof EntityLivingBase)) {
            return false;
        }
        if (p_Entity instanceof EntityPlayer) {
            if (p_Entity == this.mc.field_71439_g) {
                return false;
            }
            if (!this.Players.getValue()) {
                return false;
            }
            if (FriendManager.Get().IsFriend(p_Entity) && !this.Friends.getValue()) {
                return false;
            }
        }
        return (!EntityUtil.isHostileMob(p_Entity) || this.Mobs.getValue()) && (!(p_Entity instanceof EntityPigZombie) || this.Mobs.getValue()) && (!(p_Entity instanceof EntityAnimal) || this.Animals.getValue());
    }
}

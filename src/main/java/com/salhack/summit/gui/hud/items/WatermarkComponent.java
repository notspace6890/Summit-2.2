// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.gui.hud.items;

import com.salhack.summit.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import com.salhack.summit.gui.hud.components.DraggableHudComponent;

public class WatermarkComponent extends DraggableHudComponent
{
    public WatermarkComponent() {
        super("Watermark", 2.0f, 6.0f, 100.0f, 100.0f);
        this.setEnabled(true);
    }
    
    @Override
    public void onRender(final ScaledResolution res, final float mouseX, final float mouseY, final float partialTicks) {
        super.onRender(res, mouseX, mouseY, partialTicks);
        RenderUtil.drawStringWithShadow("Summit", this.getX(), this.getY(), 16711680);
        this.setWidth(83.0f);
        this.setHeight(16.0f);
    }
}

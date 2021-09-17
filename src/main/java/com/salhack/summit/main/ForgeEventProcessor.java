// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.main;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import com.salhack.summit.events.render.EventRenderGetFOVModifier;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import com.salhack.summit.events.minecraft.EventKeyInput;
import com.salhack.summit.managers.MacroManager;
import com.salhack.summit.managers.ModuleManager;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import com.salhack.summit.events.client.EventClientTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.salhack.summit.events.render.RenderEvent;
import com.salhack.summit.SummitMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ForgeEventProcessor
{
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179103_j(7425);
        GlStateManager.func_179097_i();
        GlStateManager.func_187441_d(1.0f);
        SummitMod.EVENT_BUS.post(new RenderEvent(event.getPartialTicks()));
        GlStateManager.func_187441_d(1.0f);
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179089_o();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.GetMC().field_71439_g == null) {
            return;
        }
        SummitMod.EVENT_BUS.post(new EventClientTick());
    }
    
    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        if (event.isCanceled()) {
            return;
        }
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            final String key = Keyboard.getKeyName(Keyboard.getEventKey());
            ModuleManager.Get().OnKeyPress(key);
            MacroManager.Get().OnKeyPress(key);
            if (!key.equals("NONE") && !key.isEmpty()) {
                SummitMod.EVENT_BUS.post(new EventKeyInput(key));
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState()) {
            final String button = Mouse.getButtonName(Mouse.getEventButton());
            ModuleManager.Get().OnKeyPress(button);
            MacroManager.Get().OnKeyPress(button);
            SummitMod.EVENT_BUS.post(event);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDrawn(final RenderPlayerEvent.Pre event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDrawn(final RenderPlayerEvent.Post event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onChunkLoaded(final ChunkEvent.Load event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onChunkUnLoaded(final ChunkEvent.Unload event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemEventTick(final LivingEntityUseItemEvent.Start entityUseItemEvent) {
        SummitMod.EVENT_BUS.post(entityUseItemEvent);
    }
    
    @SubscribeEvent
    public void onLivingDamageEvent(final LivingDamageEvent event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onEntityJoinWorldEvent(final EntityJoinWorldEvent entityJoinWorldEvent) {
        SummitMod.EVENT_BUS.post(entityJoinWorldEvent);
    }
    
    @SubscribeEvent
    public void onPlayerPush(final PlayerSPPushOutOfBlocksEvent event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent entityEvent) {
        SummitMod.EVENT_BUS.post(entityEvent);
    }
    
    @SubscribeEvent
    public void onRenderBlockOverlay(final RenderBlockOverlayEvent event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onClientChat(final ClientChatReceivedEvent event) {
        SummitMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void getFOVModifier(final EntityViewRenderEvent.FOVModifier p_Event) {
        final EventRenderGetFOVModifier l_Event = new EventRenderGetFOVModifier((float)p_Event.getRenderPartialTicks(), true);
        SummitMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Event.setFOV(l_Event.GetFOV());
        }
    }
    
    @SubscribeEvent
    public void LivingAttackEvent(final LivingAttackEvent p_Event) {
        SummitMod.EVENT_BUS.post(p_Event);
    }
    
    @SubscribeEvent
    public void OnWorldChange(final WorldEvent p_Event) {
        SummitMod.EVENT_BUS.post(p_Event);
    }
}

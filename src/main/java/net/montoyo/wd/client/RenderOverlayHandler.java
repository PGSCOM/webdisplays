/*
 * Copyright (C) 2024 PGSCOM
 * NeoForge 1.21.1 replacement for OverlayMixin
 */

package net.montoyo.wd.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

/**
 * NeoForge event-based replacement for OverlayMixin
 * Handles custom crosshair rendering
 */
@EventBusSubscriber(modid = "webdisplays", value = Dist.CLIENT)
public class RenderOverlayHandler {

    @SubscribeEvent
    public static void onRenderCrosshair(RenderGuiEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        
        // Delegate to ClientProxy for crosshair rendering logic
        // This allows ClientProxy to cancel default crosshair if needed
        if (ClientProxy.shouldRenderCustomCrosshair(minecraft)) {
            ClientProxy.renderCrosshair(
                minecraft.options,
                event.getGuiGraphics().guiWidth(),
                event.getGuiGraphics().guiHeight(),
                0,
                event.getGuiGraphics()
            );
            
            // Note: The actual cancellation logic is handled in ClientProxy
            // to maintain compatibility with existing code structure
        }
    }
}

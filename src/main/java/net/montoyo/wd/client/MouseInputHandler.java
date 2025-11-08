/*
 * Copyright (C) 2024 PGSCOM
 * NeoForge 1.21.1 replacement for MouseHandlerMixin
 */

package net.montoyo.wd.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.HitResult;
import net.montoyo.wd.item.ItemLaserPointer;
import net.montoyo.wd.registry.ItemRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

/**
 * NeoForge event-based replacement for MouseHandlerMixin
 * Handles laser pointer mouse input
 */
@EventBusSubscriber(modid = "webdisplays", value = Dist.CLIENT)
public class MouseInputHandler {

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        
        // Only process if no GUI is open
        if (minecraft.screen != null) {
            return;
        }
        
        // Check if player is holding laser pointer
        if (minecraft.player != null && 
            minecraft.level != null &&
            minecraft.player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(ItemRegistry.LASER_POINTER.get()) &&
            (minecraft.hitResult == null || 
             minecraft.hitResult.getType() == HitResult.Type.BLOCK || 
             minecraft.hitResult.getType() == HitResult.Type.MISS)) {
            
            // Handle mouse button press/release
            boolean isPress = event.getAction() == 1; // 1 = PRESS, 0 = RELEASE
            ItemLaserPointer.press(isPress, event.getButton());
            
            // Cancel the event to prevent default behavior
            event.setCanceled(true);
        }
    }
}

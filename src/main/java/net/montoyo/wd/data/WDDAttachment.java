/*
 * Copyright (C) 2024 PGSCOM
 * NeoForge 1.21.1 replacement for WDDCapability using Attachment system
 */

package net.montoyo.wd.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * NeoForge Attachment-based replacement for WDDCapability
 * Tracks per-player data like first run status
 */
public class WDDAttachment {
    
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = 
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "webdisplays");
    
    /**
     * Codec for serializing/deserializing the attachment data
     */
    public static final Codec<WDDAttachment> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("firstRun").forGetter(WDDAttachment::isFirstRun)
        ).apply(instance, WDDAttachment::new)
    );
    
    /**
     * The attachment type registration
     */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<WDDAttachment>> WDD_DATA = 
        ATTACHMENT_TYPES.register("wdd_data", () -> 
            AttachmentType.builder(() -> new WDDAttachment(true))
                .serialize(CODEC)
                .build()
        );
    
    private boolean firstRun;
    
    /**
     * Default constructor - first run is true
     */
    public WDDAttachment() {
        this(true);
    }
    
    /**
     * Constructor with initial first run value
     */
    public WDDAttachment(boolean firstRun) {
        this.firstRun = firstRun;
    }
    
    /**
     * Check if this is the player's first run
     */
    public boolean isFirstRun() {
        return firstRun;
    }
    
    /**
     * Mark that the player has completed first run
     */
    public void clearFirstRun() {
        firstRun = false;
    }
    
    /**
     * Clone data to another attachment
     */
    public void cloneTo(WDDAttachment dst) {
        if (!isFirstRun()) {
            dst.clearFirstRun();
        }
    }
    
    /**
     * Utility method to get attachment from player
     */
    public static WDDAttachment get(Player player) {
        return player.getData(WDD_DATA);
    }
    
    /**
     * Utility method to check if player is on first run
     */
    public static boolean isPlayerFirstRun(Player player) {
        return get(player).isFirstRun();
    }
    
    /**
     * Utility method to clear first run for player
     */
    public static void clearPlayerFirstRun(Player player) {
        get(player).clearFirstRun();
    }
}

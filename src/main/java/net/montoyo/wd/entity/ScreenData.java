/*
 * Copyright (C) 2019 BARBOTIN Nicolas
 * 
 * STUB VERSION for build stability
 * Minimal stub to allow compilation of dependent classes
 * TODO: Replace with full implementation when browser/client packages are available
 */

package net.montoyo.wd.entity;

import net.minecraft.core.BlockPos;
import net.montoyo.wd.utilities.data.BlockSide;
import net.montoyo.wd.utilities.data.Rotation;
import net.montoyo.wd.utilities.math.Vector2i;
import net.montoyo.wd.utilities.math.Vector3i;

/**
 * STUB: Minimal implementation of ScreenData
 * Represents screen state and properties
 */
public class ScreenData {
    
    public final Vector2i size;
    public final Vector3i pos;
    public final BlockSide side;
    public final Rotation rotation;
    
    public ScreenData(BlockPos blockPos, BlockSide side, Vector2i size) {
        this.pos = new Vector3i(blockPos);
        this.side = side;
        this.size = size;
        this.rotation = Rotation.ROT_0;
    }
    
    // Stub: URL management
    public String getURL() {
        return "about:blank";
    }
    
    public void setURL(String url) {
        // TODO: Implement when browser is available
    }
    
    // Stub: Resolution
    public int getResolution() {
        return 1024;
    }
    
    public void setResolution(int resolution) {
        // TODO: Implement
    }
    
    // Stub: Upgrades
    public boolean hasUpgrade(Object upgrade) {
        return false;
    }
    
    public void addUpgrade(Object upgrade) {
        // TODO: Implement when IUpgrade is fully available
    }
    
    public void removeUpgrade(Object upgrade) {
        // TODO: Implement
    }
    
    // Stub: Browser management
    public Object getBrowser() {
        // TODO: Return WDBrowser when available
        return null;
    }
    
    // Stub: Interaction
    public void click(int x, int y, int button) {
        // TODO: Implement when browser is available
    }
    
    public void type(String text) {
        // TODO: Implement when browser is available
    }
    
    // Stub: Serialization
    public void save(Object tag) {
        // TODO: Implement NBT serialization
    }
    
    public void load(Object tag) {
        // TODO: Implement NBT deserialization
    }
    
    // Stub: Cleanup
    public void clear() {
        // TODO: Implement browser cleanup
    }
}

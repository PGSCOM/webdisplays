package net.montoyo.wd.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.montoyo.wd.utilities.data.BlockSide;

/**
 * TODO: Implement full screen data storage
 * Stub implementation for NeoForge 1.21.1 migration
 */
public class ScreenData {
    
    private String url = "";
    
    public ScreenData() {
    }
    
    /**
     * TODO: Implement URL getter
     */
    public String getURL() {
        return url;
    }
    
    /**
     * TODO: Implement URL setter
     */
    public void setURL(String url) {
        this.url = url;
    }
    
    /**
     * TODO: Implement position getter
     */
    public BlockPos getBlockPos() {
        return BlockPos.ZERO;
    }
    
    /**
     * TODO: Implement side getter
     */
    public BlockSide getSide() {
        return BlockSide.BOTTOM;
    }
}

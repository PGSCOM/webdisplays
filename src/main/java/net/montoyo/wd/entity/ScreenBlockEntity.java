package net.montoyo.wd.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.montoyo.wd.utilities.data.BlockSide;
import net.montoyo.wd.utilities.math.Vector2i;

/**
 * STUB: Minimal implementation of ScreenBlockEntity
 * This allows other classes to compile against this interface
 * without requiring the full dependency tree.
 */
public class ScreenBlockEntity extends BlockEntity {
    
    // Constructor for manual instantiation
    public ScreenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // Constructor for BlockEntityType.Builder.of() - takes (BlockPos, BlockState)
    public ScreenBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);  // Type will be set by registry
    }

    // Stub methods - minimal API surface
    
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        // TODO: Implement when ScreenData is available
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        // TODO: Implement when ScreenData is available
    }

    // Stub: Screen management methods
    public ScreenData getScreen(BlockSide side) {
        // TODO: Implement proper screen management
        return new ScreenData();
    }

    public boolean hasUpgrade(Object upgrade) {
        // TODO: Implement when IUpgrade is available
        return false;
    }
    
    public void clearUpgrades(BlockSide side) {
        // TODO: Implement
    }
    
    public void addUpgrade(Object upgrade, BlockSide side) {
        // TODO: Implement
    }

    public Vector2i getSize(BlockSide side) {
        // TODO: Implement when ScreenData is available
        return new Vector2i(1, 1);
    }

    public BlockSide getSide() {
        // TODO: Implement proper side detection
        return BlockSide.BOTTOM;
    }

    // Stub: Interaction methods
    public void click(BlockSide side, Vector2i vec, Object hit) {
        // TODO: Implement when click handling is available
    }
    
    public void click(Object player, BlockSide side, Object hit) {
        // TODO: Implement when click handling is available
    }

    public void type(BlockSide side, String text, BlockPos soundPos) {
        // TODO: Implement when keyboard handling is available (client-side)
    }
    
    public void type(BlockSide side, String text, BlockPos soundPos, Object player) {
        // TODO: Implement when keyboard handling is available (server-side)
    }
    
    public void type(Object player, BlockSide side, String text) {
        // TODO: Implement when keyboard handling is available
    }

    // Stub: Utility methods
    public boolean isLoaded() {
        return true;
    }

    public void setLoaded(boolean loaded) {
        // TODO: Implement
    }
}

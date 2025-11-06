/*
 * STUB VERSION for build stability
 * Minimal implementation to allow compilation
 */

package net.montoyo.wd.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * STUB: Keyboard block entity
 */
public class KeyboardBlockEntity extends BlockEntity {
    
    public KeyboardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    
    public KeyboardBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
    }
    
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
    }
}

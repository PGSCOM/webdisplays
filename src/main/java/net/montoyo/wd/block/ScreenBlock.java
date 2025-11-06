/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * STUB: Minimal ScreenBlock implementation for compilation
 * TODO: Implement full functionality from ScreenBlock.java.old
 */
public class ScreenBlock extends BaseEntityBlock {
    
    public ScreenBlock(Properties properties) {
        super(properties.strength(1.5f, 10.f));
    }

    @Override
    protected com.mojang.serialization.MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ScreenBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // TODO: Return proper ScreenBlockEntity when implemented
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}

package net.montoyo.wd.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.montoyo.wd.core.IUpgrade;

public class PeripheralBlock extends Block {
    
    private final IUpgrade peripheralType;
    
    public PeripheralBlock(IUpgrade peripheralType) {
        super(BlockBehaviour.Properties.of());
        this.peripheralType = peripheralType;
    }
    
    public IUpgrade getPeripheralType() {
        return peripheralType;
    }
}
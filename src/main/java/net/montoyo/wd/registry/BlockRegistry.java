package net.montoyo.wd.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
// TODO: Re-enable when KeyboardBlock and PeripheralBlock are available
// import net.montoyo.wd.block.KeyboardBlockLeft;
// import net.montoyo.wd.block.KeyboardBlockRight;
// import net.montoyo.wd.block.PeripheralBlock;
// TODO: Re-enable when ScreenBlock is available
// import net.montoyo.wd.block.ScreenBlock;
// TODO: Re-enable when DefaultPeripheral is available
// import net.montoyo.wd.core.DefaultPeripheral;

public class BlockRegistry {
    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, "webdisplays");

    // TODO: Re-enable when ScreenBlock is available
    // public static final DeferredHolder<Block, ScreenBlock> SCREEN_BLOCK = BLOCKS.register("screen", () -> new ScreenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    
    // TODO: Re-enable when KeyboardBlock and PeripheralBlock are available
    /*
    public static final DeferredHolder<Block, KeyboardBlockLeft> KEYBOARD_BLOCK = BLOCKS.register("kb_left", KeyboardBlockLeft::new);
    public static final DeferredHolder<Block, KeyboardBlockRight> blockKbRight = BLOCKS.register("kb_right", KeyboardBlockRight::new);
    
    public static final DeferredHolder<Block, PeripheralBlock> REDSTONE_CONTROL_BLOCK = BLOCKS.register("redctrl", () -> new PeripheralBlock(DefaultPeripheral.REDSTONE_CONTROLLER));
    public static final DeferredHolder<Block, PeripheralBlock> REMOTE_CONTROLLER_BLOCK = BLOCKS.register("rctrl", () -> new PeripheralBlock(DefaultPeripheral.REMOTE_CONTROLLER));
    public static final DeferredHolder<Block, PeripheralBlock> SERVER_BLOCK = BLOCKS.register("server", () -> new PeripheralBlock(DefaultPeripheral.SERVER));
    */
}

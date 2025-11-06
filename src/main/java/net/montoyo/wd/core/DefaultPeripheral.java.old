/*
 * Copyright (C) 2019 BARBOTIN Nicolas
 */

package net.montoyo.wd.core;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.montoyo.wd.entity.KeyboardBlockEntity;
import net.montoyo.wd.entity.RemoteControlBlockEntity;
import net.montoyo.wd.entity.RedstoneControlBlockEntity;
import net.montoyo.wd.entity.ServerBlockEntity;
import net.montoyo.wd.registry.BlockRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum DefaultPeripheral implements StringRepresentable, IUpgrade {
    KEYBOARD("keyboard", "Keyboard", KeyboardBlockEntity::new, BlockRegistry.KEYBOARD_BLOCK),                          //WITH FACING (< 3)
//    CC_INTERFACE("ccinterface", "ComputerCraft_Interface", TileEntityCCInterface.class),
//    OC_INTERFACE("cointerface", "OpenComputers_Interface", TileEntityOCInterface.class),
    REMOTE_CONTROLLER("remotectrl", "Remote_Controller", RemoteControlBlockEntity::new , BlockRegistry.REMOTE_CONTROLLER_BLOCK),         //WITHOUT FACING (>= 3)
    REDSTONE_CONTROLLER("redstonectrl", "Redstone_Controller", RedstoneControlBlockEntity::new , BlockRegistry.REDSTONE_CONTROL_BLOCK),
    SERVER("server", "Server", ServerBlockEntity::new, BlockRegistry.SERVER_BLOCK);

    private final String name;
    private final String wikiName;
    private final BlockEntityType.BlockEntitySupplier<? extends BlockEntity> teClass;
    private final Supplier<? extends Block> bClass;

    DefaultPeripheral(String name, String wname, BlockEntityType.BlockEntitySupplier<? extends BlockEntity> factory, Supplier<? extends Block> supplier) {
        this.name = name;
        wikiName = wname;
        teClass = factory;
        bClass = supplier;
    }

    public Supplier<? extends Block> getBlockClass() {
        return bClass;
    }

    public static DefaultPeripheral fromMetadata(int meta) {
        if((meta & 3) == 3)
            return values()[(((meta >> 2) & 3) | 4) - 1]; //Without facing
        else
            return values()[meta & 3]; //With facing
    }

    public BlockEntityType.BlockEntitySupplier<? extends BlockEntity> getTEClass() {
        return teClass;
    }

    public boolean hasFacing() {
        return ordinal() < 3;
    }

    public int toMetadata(int facing) {
        int ret = ordinal();
        if(ret < 3) //With facing
            ret |= facing << 2;
        else //Without facing
            ret = (((ret + 1) & 3) << 2) | 3;

        return ret;
    }

    @Override
    public @NotNull String getSerializedName() {
        return "default_peripheral_" + name;
    }
    
    // IUpgrade implementation stubs
    @Override
    public void onInstall(@javax.annotation.Nonnull net.montoyo.wd.entity.ScreenBlockEntity tes,
                         @javax.annotation.Nonnull net.montoyo.wd.utilities.data.BlockSide screenSide,
                         @javax.annotation.Nullable net.minecraft.world.entity.player.Player player,
                         @javax.annotation.Nonnull net.minecraft.world.item.ItemStack is) {
        // TODO: Implement installation logic
    }
    
    @Override
    public boolean onRemove(@javax.annotation.Nonnull net.montoyo.wd.entity.ScreenBlockEntity tes,
                           @javax.annotation.Nonnull net.montoyo.wd.utilities.data.BlockSide screenSide,
                           @javax.annotation.Nullable net.minecraft.world.entity.player.Player player,
                           @javax.annotation.Nonnull net.minecraft.world.item.ItemStack is) {
        // TODO: Implement removal logic
        return false;
    }
    
    @Override
    public boolean isSameUpgrade(@javax.annotation.Nonnull net.minecraft.world.item.ItemStack myStack,
                                @javax.annotation.Nonnull net.minecraft.world.item.ItemStack otherStack) {
        // TODO: Implement comparison logic
        return false;
    }
    
    @Override
    public String getJSName(@javax.annotation.Nonnull net.minecraft.world.item.ItemStack is) {
        return "webdisplays:" + name;
    }
}

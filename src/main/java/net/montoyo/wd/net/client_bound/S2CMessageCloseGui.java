/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.net.client_bound;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.WebDisplays;
import net.montoyo.wd.net.WDPayload;
import net.montoyo.wd.utilities.data.BlockSide;

import java.util.Arrays;

public class S2CMessageCloseGui extends WDPayload {
	
	public static final CustomPacketPayload.Type<S2CMessageCloseGui> TYPE = 
		new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("webdisplays", "close_gui"));
	
	private BlockPos blockPos;
	private BlockSide blockSide;
	
	public S2CMessageCloseGui(BlockPos bp) {
		blockPos = bp;
		blockSide = null;
	}
	
	public S2CMessageCloseGui(BlockPos bp, BlockSide side) {
		blockPos = bp;
		blockSide = side;
	}
	
	public S2CMessageCloseGui(FriendlyByteBuf buf) {
		blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		byte b = buf.readByte();
		if (b <= 0) blockSide = null;
		else blockSide = BlockSide.values()[b - 1];
	}
	
	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(blockPos.getX());
		buf.writeInt(blockPos.getY());
		buf.writeInt(blockPos.getZ());
		
		if (blockSide == null) buf.writeByte(0);
		else buf.writeByte(blockSide.ordinal() + 1);
	}
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	@Override
	public void handle(IPayloadContext context) {
		if (isClient(context)) {
			context.enqueueWork(() -> {
				// TODO: Re-enable when PROXY is available
				/*
				if (blockSide == null)
					Arrays.stream(BlockSide.values()).forEach(s -> WebDisplays.PROXY.closeGui(blockPos, s));
				else
					WebDisplays.PROXY.closeGui(blockPos, blockSide);
				*/
			});
		}
	}
}

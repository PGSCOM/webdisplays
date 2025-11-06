/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.net.client_bound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.WebDisplays;
import net.montoyo.wd.miniserv.client.Client;
import net.montoyo.wd.net.WDPayload;
import net.montoyo.wd.net.server_bound.C2SMessageMiniservConnect;

public class S2CMessageServerInfo extends WDPayload {
	
	public static final CustomPacketPayload.Type<S2CMessageServerInfo> TYPE = 
		new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "server_info"));
	
	private int miniservPort;
	
	public S2CMessageServerInfo(int msPort) {
		miniservPort = msPort;
	}
	
	public S2CMessageServerInfo(FriendlyByteBuf buf) {
		miniservPort = buf.readShort();
	}
	
	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeShort(miniservPort);
	}
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	@Override
	public void handle(IPayloadContext context) {
		if (isClient(context)) {
			context.enqueueWork(() -> {
				try {
					WebDisplays.PROXY.setMiniservClientPort(miniservPort);
					C2SMessageMiniservConnect message = Client.getInstance().beginConnection();
					context.reply(message);
				} catch (Throwable err) {
					err.printStackTrace();
					throw new RuntimeException(err);
				}
			});
		}
	}
}

/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.net.server_bound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.miniserv.server.ClientManager;
import net.montoyo.wd.miniserv.server.Server;
import net.montoyo.wd.net.BufferUtils;
import net.montoyo.wd.net.WDPayload;
import net.montoyo.wd.net.client_bound.S2CMessageMiniservKey;

import java.util.Objects;

public class C2SMessageMiniservConnect extends WDPayload {

	public static final CustomPacketPayload.Type<C2SMessageMiniservConnect> TYPE = 
		new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("webdisplays", "miniserv_connect"));
	
	private byte[] modulus;
	private byte[] exponent;
	
	public C2SMessageMiniservConnect(byte[] mod, byte[] exp) {
		modulus = mod;
		exponent = exp;
	}
	
	public C2SMessageMiniservConnect(FriendlyByteBuf buf) {
		
		modulus = BufferUtils.readBytes(buf);
		exponent = BufferUtils.readBytes(buf);
	}
	
	@Override
	public void write(FriendlyByteBuf buf) {
		BufferUtils.writeBytes(buf, modulus);
		BufferUtils.writeBytes(buf, exponent);
	}
	
	@Override
	public void handle(IPayloadContext context) {
		if (!isServer(context)) return;

		try {
			ClientManager cliMgr = Server.getInstance().getClientManager();
			byte[] encKey = cliMgr.encryptClientKey(Objects.requireNonNull(context.player()).getGameProfile().getId(), modulus, exponent);
			
			if (encKey != null) {
				context.reply(new S2CMessageMiniservKey(encKey));
			}
		} catch (Throwable err) {
			err.printStackTrace();
			throw new RuntimeException(err);
		}
	}
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}

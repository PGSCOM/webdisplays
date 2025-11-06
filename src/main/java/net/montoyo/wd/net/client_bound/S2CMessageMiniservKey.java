/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.net.client_bound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.WebDisplays;
import net.montoyo.wd.client.ClientProxy;
import net.montoyo.wd.miniserv.client.Client;
import net.montoyo.wd.net.BufferUtils;
import net.montoyo.wd.net.WDPayload;
import net.montoyo.wd.utilities.Log;

public class S2CMessageMiniservKey extends WDPayload {
	
	public static final CustomPacketPayload.Type<S2CMessageMiniservKey> TYPE = 
		new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "miniserv_key"));
	
	private byte[] encryptedKey;
	
	public S2CMessageMiniservKey(byte[] key) {
		encryptedKey = key;
	}
	
	public S2CMessageMiniservKey(FriendlyByteBuf buf) {
		encryptedKey = BufferUtils.readBytes(buf);
	}
	
	@Override
	public void write(FriendlyByteBuf buf) {
		BufferUtils.writeBytes(buf, encryptedKey);
	}
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	@Override
	public void handle(IPayloadContext context) {
		if (isClient(context)) {
			context.enqueueWork(() -> {
				if (Client.getInstance().decryptKey(encryptedKey)) {
					Log.info("Successfully received and decrypted key, starting miniserv client...");
					if (WebDisplays.PROXY instanceof ClientProxy proxy) {
						proxy.startMiniservClient();
					}
				}
			});
		}
	}
}

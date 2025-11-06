/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.net.server_bound;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.WebDisplays;
import net.montoyo.wd.net.WDPayload;
import net.montoyo.wd.net.client_bound.S2CMessageACResult;
import net.montoyo.wd.utilities.serialization.NameUUIDPair;

import java.util.Arrays;

public class C2SMessageACQuery extends WDPayload {

	public static final CustomPacketPayload.Type<C2SMessageACQuery> TYPE = 
		new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "ac_query"));
	
	private String beginning;
	private boolean matchExact;
	
	public C2SMessageACQuery(String beg, boolean exact) {
		beginning = beg;
		matchExact = exact;
	}
	
	public C2SMessageACQuery(FriendlyByteBuf buf) {
		beginning = buf.readUtf();
		matchExact = buf.readBoolean();
	}
	
	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeUtf(beginning);
		buf.writeBoolean(matchExact);
	}
	
	@Override
	public void handle(IPayloadContext context) {
		if (!isServer(context)) return;

		context.enqueueWork(() -> {
			GameProfile[] profiles = WebDisplays.PROXY.getOnlineGameProfiles();
			NameUUIDPair[] result;
			
			if (matchExact)
				result = Arrays.stream(profiles).filter(gp -> gp.getName().equalsIgnoreCase(beginning)).map(NameUUIDPair::new).toArray(NameUUIDPair[]::new);
			else {
				final String lBeg = beginning.toLowerCase();
				result = Arrays.stream(profiles).filter(gp -> gp.getName().toLowerCase().startsWith(lBeg)).map(NameUUIDPair::new).toArray(NameUUIDPair[]::new);
			}
			
			// Use context.reply() to send response back to the requesting player
			context.reply(new S2CMessageACResult(result));
		});
	}
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}

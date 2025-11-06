/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.net.client_bound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.WebDisplays;
import net.montoyo.wd.net.WDPayload;
import net.montoyo.wd.utilities.serialization.NameUUIDPair;

public class S2CMessageACResult extends WDPayload {
	
	public static final CustomPacketPayload.Type<S2CMessageACResult> TYPE = 
		new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "ac_result"));
	
    private static NameUUIDPair[] result;

    public S2CMessageACResult(NameUUIDPair[] pairs) {
        result = pairs;
    }
    
    public S2CMessageACResult(FriendlyByteBuf buf) {
        int cnt = buf.readByte();
        result = new NameUUIDPair[cnt];

        for(int i = 0; i < cnt; i++)
            result[i] = new NameUUIDPair(buf);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeByte(result.length);

        for(NameUUIDPair pair : result)
            pair.writeTo(buf);
    }
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

    @Override
    public void handle(IPayloadContext context) {
        if (isClient(context)) {
            context.enqueueWork(() -> {
                WebDisplays.PROXY.onAutocompleteResult(result);
            });
        }
    }
}

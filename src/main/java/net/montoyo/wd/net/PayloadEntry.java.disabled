package net.montoyo.wd.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Function;

/**
 * Helper class to register WebDisplays payloads with NeoForge's new networking system
 */
public class PayloadEntry<T extends WDPayload> {
    private final CustomPacketPayload.Type<T> type;
    private final StreamCodec<FriendlyByteBuf, T> codec;
    private final IPayloadHandler<T> handler;
    
    public PayloadEntry(
            ResourceLocation id,
            Function<FriendlyByteBuf, T> reader,
            IPayloadHandler<T> handler
    ) {
        this.type = new CustomPacketPayload.Type<>(id);
        this.codec = StreamCodec.of(
            (buf, payload) -> payload.write(buf),
            reader
        );
        this.handler = handler;
    }
    
    /**
     * Register this payload with the given registrar for bidirectional communication
     */
    public void registerPlayBidirectional(PayloadRegistrar registrar) {
        registrar.playBidirectional(
            type,
            codec,
            handler
        );
    }
    
    /**
     * Register this payload for client->server communication only
     */
    public void registerPlayToServer(PayloadRegistrar registrar) {
        registrar.playToServer(
            type,
            codec,
            handler
        );
    }
    
    /**
     * Register this payload for server->client communication only
     */
    public void registerPlayToClient(PayloadRegistrar registrar) {
        registrar.playToClient(
            type,
            codec,
            handler
        );
    }
    
    public CustomPacketPayload.Type<T> getType() {
        return type;
    }
}

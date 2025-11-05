package net.montoyo.wd.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Base class for all WebDisplays custom packet payloads.
 * Replaces the old Packet class for NeoForge 21.x networking.
 */
public abstract class WDPayload implements CustomPacketPayload {
    
    /**
     * Write payload data to buffer
     */
    public abstract void write(FriendlyByteBuf buf);
    
    /**
     * Handle the payload on the receiving side
     * @param context The payload context with information about the connection
     */
    public abstract void handle(IPayloadContext context);
    
    /**
     * Check if we're on the client side
     */
    protected boolean isClient(IPayloadContext context) {
        return context.flow().isClientbound();
    }
    
    /**
     * Check if we're on the server side
     */
    protected boolean isServer(IPayloadContext context) {
        return context.flow().isServerbound();
    }
    
    /**
     * Helper to create a StreamCodec for a payload type
     */
    public static <T extends WDPayload> StreamCodec<FriendlyByteBuf, T> codec(
            StreamCodec<FriendlyByteBuf, T> reader,
            WriteFunction<T> writer
    ) {
        return StreamCodec.of(
            (buf, payload) -> writer.write(payload, buf),
            reader
        );
    }
    
    @FunctionalInterface
    public interface WriteFunction<T extends WDPayload> {
        void write(T payload, FriendlyByteBuf buf);
    }
}

package net.montoyo.wd.net.server_bound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.montoyo.wd.item.ItemMinePad2;
import net.montoyo.wd.net.WDPayload;

import java.util.UUID;

public class C2SMessageMinepadUrl extends WDPayload {

	public static final CustomPacketPayload.Type<C2SMessageMinepadUrl> TYPE = 
		new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "minepad_url"));
	
	UUID id;
	String url;
	
	public C2SMessageMinepadUrl(UUID id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public C2SMessageMinepadUrl(FriendlyByteBuf buf) {
		this.id = buf.readUUID();
		this.url = buf.readUtf();
	}
	
	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeUUID(id);
		buf.writeUtf(url);
	}
	
	protected void merge(ItemStack stack) {
		if (url.equals("")) {
			stack.getOrCreateTag().remove("PadID");
		} else {
			stack.getOrCreateTag().putUUID("PadID", id);
			stack.getOrCreateTag().putString("PadURL", url);
		}
	}
	
	@Override
	public void handle(IPayloadContext context) {
		if (!isServer(context)) return;

		// check if the player is holding a minePad with the requested id
		// if the player is, then update that pad
		for (InteractionHand value : InteractionHand.values()) {
			ItemStack stack = context.player().getItemInHand(value);
			if (stack.getItem() instanceof ItemMinePad2 && stack.getOrCreateTag().contains("PadID")) {
				UUID padId = stack.getTag().getUUID("PadID");
				if (padId.equals(id)) {
					merge(stack);
					return;
				}
			}
		}
		
		// if the player is not holding the requested minePad, update the first one that does not already have an ID
		for (InteractionHand value : InteractionHand.values()) {
			ItemStack stack = context.player().getItemInHand(value);
			if (stack.getItem() instanceof ItemMinePad2 && !stack.getOrCreateTag().contains("PadID")) {
				merge(stack);
				return;
			}
		}
	}
	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}

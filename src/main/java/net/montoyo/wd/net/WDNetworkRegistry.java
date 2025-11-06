package net.montoyo.wd.net;

import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
// TODO: Temporarily disabled - complex messages
// import net.montoyo.wd.net.client_bound.*;
// import net.montoyo.wd.net.server_bound.*;
import net.montoyo.wd.net.client_bound.S2CMessageServerInfo;
import net.montoyo.wd.net.client_bound.S2CMessageMiniservKey;
import net.montoyo.wd.net.client_bound.S2CMessageCloseGui;
import net.montoyo.wd.net.server_bound.C2SMessageMiniservConnect;
import net.montoyo.wd.net.server_bound.C2SMessageMinepadUrl;

public class WDNetworkRegistry {
	public static final String PROTOCOL_VERSION = "2";
	
	@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
		
		// Bidirectional - login handshake
		registrar.playBidirectional(
			S2CMessageServerInfo.TYPE,
			StreamCodec.of(
				(buf, msg) -> msg.write(buf),
				S2CMessageServerInfo::new
			),
			(payload, context) -> payload.handle(context)
		);
		
		// Server -> Client messages
		registrar.playToClient(
			S2CMessageMiniservKey.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageMiniservKey::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToClient(
			S2CMessageCloseGui.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageCloseGui::new),
			(payload, context) -> payload.handle(context)
		);
		
		// TODO: Temporarily disabled - complex messages
		/* 
		registrar.playToClient(
			S2CMessageOpenGui.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageOpenGui::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToClient(
			S2CMessageAddScreen.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageAddScreen::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToClient(
			S2CMessageScreenUpdate.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageScreenUpdate::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToClient(
			S2CMessageACResult.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageACResult::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToClient(
			S2CMessageJSResponse.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), S2CMessageJSResponse::new),
			(payload, context) -> payload.handle(context)
		);
		*/
		
		// Client -> Server messages
		registrar.playToServer(
			C2SMessageMiniservConnect.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), C2SMessageMiniservConnect::new),
			(payload, context) -> payload.handle(context)
		);
		
		// TODO: Temporarily disabled - complex messages
		/*
		registrar.playToServer(
			C2SMessageScreenCtrl.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), C2SMessageScreenCtrl::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToServer(
			C2SMessageRedstoneCtrl.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), C2SMessageRedstoneCtrl::new),
			(payload, context) -> payload.handle(context)
		);
		
		registrar.playToServer(
			C2SMessageACQuery.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), C2SMessageACQuery::new),
			(payload, context) -> payload.handle(context)
		);
		*/
		
		registrar.playToServer(
			C2SMessageMinepadUrl.TYPE,
			StreamCodec.of((buf, msg) -> msg.write(buf), C2SMessageMinepadUrl::new),
			(payload, context) -> payload.handle(context)
		);
	}
	
	public static void init() {
		// Event-based registration - nothing to do here
	}
}

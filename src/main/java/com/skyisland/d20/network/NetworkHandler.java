package com.skyisland.d20.network;

import com.skyisland.d20.network.message.AdminTokenMessage;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	private static SimpleNetworkWrapper channel;
	
	private static int discriminator = 50;
	
	private static final String CHANNEL_NAME = "d20_adminchannel";
	
	public static SimpleNetworkWrapper getAdminChannel() {
		getInstance();
		return channel;
	}
	
	private static NetworkHandler instance;
	
	public static NetworkHandler getInstance() {
		if (instance == null)
			instance = new NetworkHandler();
		
		return instance;
	}
	
	public NetworkHandler() {
		
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_NAME);
		
		channel.registerMessage(AdminTokenMessage.Handler.class, AdminTokenMessage.class, discriminator++, Side.CLIENT);
	}
	
}

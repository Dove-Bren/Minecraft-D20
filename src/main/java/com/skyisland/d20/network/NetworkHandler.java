package com.skyisland.d20.network;

import com.skyisland.d20.network.message.AdminTokenMessage;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

public static SimpleNetworkWrapper channel;
	
	private static int discriminator = 50;
	
	private static final String CHANNEL_NAME = "d20_adminchannel";
	
	public Configuration base;
	
	public NetworkHandler(Configuration config) {
		this.base = config;
		
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_NAME);
		
		channel.registerMessage(AdminTokenMessage.Handler.class, AdminTokenMessage.class, discriminator++, Side.CLIENT);
	}
	
}

package com.skyisland.d20.network;

import com.skyisland.d20.network.message.AdminTokenMessage;
import com.skyisland.d20.network.message.RollRequestMessage;
import com.skyisland.d20.network.message.RollResultMessage;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	private static SimpleNetworkWrapper adminChannel;
	private static SimpleNetworkWrapper rollChannel;
	
	private static int discriminator = 50;
	
	private static final String CHANNEL_ADMIN_NAME = "d20_adminchannel";
	private static final String CHANNEL_ROLL_NAME = "d20_rollchannel";
	
	public static SimpleNetworkWrapper getAdminChannel() {
		getInstance();
		return adminChannel;
	}
	
	public static SimpleNetworkWrapper getRollChannel() {
		getInstance();
		return rollChannel;
	}
	
	private static NetworkHandler instance;
	
	public static NetworkHandler getInstance() {
		if (instance == null)
			instance = new NetworkHandler();
		
		return instance;
	}
	
	public NetworkHandler() {
		
		adminChannel = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_ADMIN_NAME);
		adminChannel.registerMessage(AdminTokenMessage.Handler.class, AdminTokenMessage.class, discriminator++, Side.CLIENT);
		
		rollChannel = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_ROLL_NAME);
		rollChannel.registerMessage(RollRequestMessage.Handler.class, RollRequestMessage.class, discriminator++, Side.SERVER);
		rollChannel.registerMessage(RollResultMessage.Handler.class, RollResultMessage.class, discriminator++, Side.CLIENT);
	}
	
}

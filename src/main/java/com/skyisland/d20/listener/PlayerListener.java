package com.skyisland.d20.listener;


import com.skyisland.d20.D20Mod;
import com.skyisland.d20.config.ModConfig;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerListener {

	public PlayerListener() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			
			//check admin list
			if (!ModConfig.config.getAdminsList().contains(event.player.getUniqueID()))
				return;
			
			D20Mod.logger.info("Sending admin-token to " + event.player.getName());
			D20Mod.proxy.sendAdminToken((EntityPlayerMP) event.player);
		}
	}
	
}

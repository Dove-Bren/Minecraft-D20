package com.skyisland.d20.listener;


import com.skyisland.d20.D20Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerListener {

	public PlayerListener() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			
			//check admin list
			if (!D20Mod.adminRegistry.isAdmin(event.player))
				return;
			
			D20Mod.logger.info("Sending admin-token to " + event.player.getName());
			D20Mod.proxy.sendAdminToken((EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public void onTest(PlayerContainerEvent.Open e) {
		//Update admin for single player
		if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().isSingleplayer())
			D20Mod.proxy.setAdmin();
			
			
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTest(GuiOpenEvent e) {
		System.out.println("open gui event ==================");
		//Update admin for single player
		if (Minecraft.getMinecraft().isSingleplayer())
			D20Mod.proxy.setAdmin();
			
			
	}
	
}

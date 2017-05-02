package com.skyisland.d20.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayHandler {

	private static DieOverlay dieOverlay;
	
	public OverlayHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onGuiDraw(RenderGameOverlayEvent.Post event) 
	{
		if (dieOverlay == null)
			dieOverlay = new DieOverlay(Minecraft.getMinecraft().thePlayer);
		
		dieOverlay.drawOverlay();
	}
}

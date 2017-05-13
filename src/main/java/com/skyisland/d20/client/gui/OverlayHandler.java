package com.skyisland.d20.client.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayHandler {

//	private static DieOverlay dieOverlay;
	private static RollerOverlay rollerOverlay;
	
	public OverlayHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onGuiDraw(BackgroundDrawnEvent event)//RenderGameOverlayEvent.Post event) 
	{
//		if (dieOverlay == null)
//			dieOverlay = new DieOverlay(Minecraft.getMinecraft().thePlayer);
		if (rollerOverlay == null)
			rollerOverlay = new RollerOverlay(Minecraft.getMinecraft().thePlayer);
		
		Minecraft mc = Minecraft.getMinecraft();
		final ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i1 = scaledresolution.getScaledWidth();
        int j1 = scaledresolution.getScaledHeight();
		final int mouseX = Mouse.getX() * i1 / mc.displayWidth;
        final int mouseY = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;
		
//		dieOverlay.drawOverlay(mouseX, mouseY);
		rollerOverlay.drawOverlay(mouseX, mouseY);
	}
}

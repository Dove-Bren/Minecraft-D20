package com.skyisland.d20.client.gui;

import com.skyisland.d20.D20Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiButton {
	
	public static interface ClickCB {
		public void onClick();
	}

	public static final ResourceLocation BACK_TEXT = new ResourceLocation(D20Mod.MODID + ":textures/gui/button.png");
	private static final int TEXT_WIDTH = 50;
	private static final int TEXT_HEIGHT = 40;
	private static final int BUTT_WIDTH = 20;
	private static final int BUTT_HEIGHT = 15;
	
	
	private String text;
	private ClickCB cb;
	private int x;
	private int y;
	
	public GuiButton(String text, ClickCB callback, int x, int y) {
		this.text = I18n.format(text);
		this.cb = callback;
		this.x = x;
		this.y = y;
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void draw(int mouseX, int mouseY) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mouseX > x && mouseX < x + BUTT_WIDTH
		 && mouseY > y && mouseY < y + BUTT_HEIGHT)
			GlStateManager.color(0.8f, 0.8f, 0.8f, 1.0f); //under mouse -- make darker
		else
			GlStateManager.color(1f, 1f, 1f, 1f); //no mouse
		
		
		mc.getTextureManager().bindTexture(BACK_TEXT);
		
		Gui.drawModalRectWithCustomSizedTexture(
				x, y, 0, 0,
				BUTT_WIDTH, BUTT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
		
		drawCenteredString(mc.fontRendererObj, this.text, x + (BUTT_WIDTH/2),
				y + (BUTT_HEIGHT / 2), 0xFFFFFFFF, false);
		
	}
	
	@SubscribeEvent
	public void onClick(MouseEvent e) {
		if (e.isButtonstate() && e.getButton() == 0) { //left click
			if (e.getX() > x && e.getX() < x + BUTT_WIDTH
					&& e.getY() > y && e.getY() < y + BUTT_HEIGHT)
			if (cb != null)
				cb.onClick();
		}
	}
	
	protected void drawString(FontRenderer fonter, String str, int x, int y, int color, boolean drop) {
    	fonter.drawString(str, x, y, color, drop);
    }
    
    protected void drawCenteredString(FontRenderer fonter, String str, int x, int y, int color, boolean drop) {
    	int centerx = x - (fonter.getStringWidth(str) / 2);
    	drawString(fonter, str, centerx, y, color, drop);
    }
	
}

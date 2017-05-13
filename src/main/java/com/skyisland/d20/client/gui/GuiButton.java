package com.skyisland.d20.client.gui;

import org.lwjgl.input.Mouse;

import com.skyisland.d20.D20Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiButton {
	
	public static interface ClickCB {
		public void onClick();
	}

	public static final ResourceLocation BACK_TEXT = new ResourceLocation(D20Mod.MODID + ":textures/gui/button.png");
//	private static final int TEXT_WIDTH = 80;
//	private static final int TEXT_HEIGHT = 40;
	private static final int DEFAULT_BUTT_WIDTH = 60;
	private static final int DEFAULT_BUTT_HEIGHT = 20;
	
	
	private String text;
	private ClickCB cb;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public GuiButton(String text, ClickCB callback, int x, int y) {
		this(text, callback, DEFAULT_BUTT_WIDTH, DEFAULT_BUTT_HEIGHT, x, y);
	}
	
	public GuiButton(String text, ClickCB callback, int width, int height, int x, int y) {
		this.text = I18n.format(text);
		this.cb = callback;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void draw(int mouseX, int mouseY) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mouseX > x && mouseX < x + width
		 && mouseY > y && mouseY < y + height)
			GlStateManager.color(0.8f, 0.8f, 0.8f, 1.0f); //under mouse -- make darker
		else
			GlStateManager.color(1f, 1f, 1f, 1f); //no mouse
		
		
		mc.getTextureManager().bindTexture(BACK_TEXT);
		
		Gui.drawModalRectWithCustomSizedTexture(
				x, y, 0, 0,
				width, height, width, height);
		
		drawCenteredString(mc.fontRendererObj, this.text, x + (width/2),
				y + (height / 2), 0x00, false);
		
	}
	
	@SubscribeEvent
	public void onClick(MouseInputEvent.Post event) {
		int mouseX = Mouse.getEventX() * event.getGui().width / event.getGui().mc.displayWidth;
        int mouseY = event.getGui().height - Mouse.getEventY() * event.getGui().height / event.getGui().mc.displayHeight - 1;
		int button = Mouse.getEventButton();
		
		if (Mouse.getEventButtonState() && button == 0) 
			if (mouseX > x && mouseX < x + width
					&& mouseY > y && mouseY < y + height)
				if (cb != null)
					cb.onClick();
		
	}
	
	protected void drawString(FontRenderer fonter, String str, int x, int y, int color, boolean drop) {
    	fonter.drawString(str, x, y, color, drop);
    }
    
    protected void drawCenteredString(FontRenderer fonter, String str, int x, int y, int color, boolean drop) {
    	int centerx = x - (fonter.getStringWidth(str) / 2);
    	int centery = y - (fonter.FONT_HEIGHT / 2);
    	drawString(fonter, str, centerx, centery, color, drop);
    }
	
}

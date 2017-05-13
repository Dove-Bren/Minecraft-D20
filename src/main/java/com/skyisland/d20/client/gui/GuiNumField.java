package com.skyisland.d20.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.skyisland.d20.D20Mod;
import com.skyisland.d20.config.ModConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Text field for gathering numbers
 * @author Skyler
 *
 */
public class GuiNumField {
	
	public static interface UpdateCB {
		public void onUpdate(int newValue);
	}

	public static final ResourceLocation BACK_TEXT = new ResourceLocation(D20Mod.MODID + ":textures/gui/textfield_back.png");
	
	private String text;
	private UpdateCB cb;
	private int blinkMax;
	private int x;
	private int y;
	private int width;
	private int height;

	private boolean selected;
	private int tickCount;
	private boolean dirty;
	private int value;
	private int maxLen;
	
	public GuiNumField(String initialText, UpdateCB callback, int width, int height, int x, int y, int blinkRate) {
		this.text = initialText;
		this.cb = callback;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.blinkMax = blinkRate;
		this.tickCount = 0;
		this.selected = false;
		
		value = 0;
		dirty = true;
		submitText(); //takes current text and tries to parse
		maxLen = 0; //will initialize the first time we draw
		
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Gets the current parsed value. If commit is true, will submit and parse the current text, even if
	 * it hasn't been submitted regularly.
	 * @param commit
	 * @return
	 */
	public int getValue(boolean commit) {
		if (dirty && commit)
			submitText();
		
		return value;
	}

	public void draw(int mouseX, int mouseY) {
		final int leftOffset = 3;
		final int topOffset = 2;
		final int cursorWidth = 2;
		final int cursorHeight = height - (topOffset * 2);
		Minecraft mc = Minecraft.getMinecraft();
		tickCount++;
		
		if (selected) {
			GlStateManager.color(0.8f, 0.8f, 0.8f, 1.0f);
		} else {
			if (mouseX > x && mouseX < x + width
			 && mouseY > y && mouseY < y + height)
				GlStateManager.color(0.9f, 0.9f, 0.9f, 1.0f); //under mouse -- make darker
			else
				GlStateManager.color(1f, 1f, 1f, 1f); //no mouse
		}
		
		
		mc.getTextureManager().bindTexture(BACK_TEXT);
		
		Gui.drawModalRectWithCustomSizedTexture(
				x, y, 0, 0,
				width, height, width, height);
		
    	drawString(mc.fontRendererObj, text, x + leftOffset, y + (mc.fontRendererObj.FONT_HEIGHT / 2),
    			0x00, false);
		
		if (selected && (tickCount % blinkMax) < blinkMax / 2) {
			//display blink cursor
			GlStateManager.color(1f, 1f, 1f, 1f);
			int offset = mc.fontRendererObj.getStringWidth(text);
			Gui.drawRect(x + leftOffset + offset, y + topOffset,
					x + leftOffset + offset + cursorWidth, y + topOffset + cursorHeight, 0xFF000000);
			
		}
		
		if (maxLen == 0) {
			//set it based on font size
			maxLen = (width - (2 * leftOffset)) / mc.fontRendererObj.getStringWidth("7"); //longest number?
			if (maxLen == 0)
				maxLen = 1;
		}
		
	}
	
	@SubscribeEvent
	public void onClick(MouseInputEvent.Pre event) {
		if (!D20Mod.proxy.isAdmin() || !ModConfig.config.showRollerGui()) {
			return;
		}
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiInventory))
			return;
		
		int mouseX = Mouse.getEventX() * event.getGui().width / event.getGui().mc.displayWidth;
        int mouseY = event.getGui().height - Mouse.getEventY() * event.getGui().height / event.getGui().mc.displayHeight - 1;
		int button = Mouse.getEventButton();
		
		if (Mouse.getEventButtonState() && button == 0) {
			submitText();
			selected = (mouseX > x && mouseX < x + width
					 && mouseY > y && mouseY < y + height);
			tickCount = 0;
		}
		
	}
	
	@SubscribeEvent
	public void onKey(KeyboardInputEvent.Pre event) {
		if (!D20Mod.proxy.isAdmin() || !ModConfig.config.showRollerGui()) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();

		if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiInventory))
			return;
		
		char c0 = Keyboard.getEventCharacter();
		int key = Keyboard.getEventKey();
		
		if (Keyboard.getEventKeyState() && (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_NUMPADENTER
				|| key == Keyboard.KEY_ESCAPE)) {
			submitText();
			return;
		}
		
        if (key == 0 && c0 >= 32 || Keyboard.getEventKeyState()) {
        	
        	if (key == Keyboard.KEY_BACK) {
    			//backspace!
        		if (text.isEmpty())
        			return;
    			text = text.substring(0, text.length() - 1);
    			dirty = true;
    			return;
    		}
        	
        	if (c0 >= 48 && c0 <= 57) //number
        	{
        		if (text.length() >= maxLen)
        			return;
        		dirty = true;
        		text += c0;
        		tickCount = 0; //reset cursor blink
    		}
        }
		
	}
	
	/**
	 * If text has changed, translates it into a number and runs update
	 */
	private void submitText() {
		if (!dirty)
			return;
		
		dirty = false;
		final int oVal = value;

		if (text.isEmpty()) {
			//empty string. just revert
			text = "" + value;
			return; 
		}
		
		try {
			value = Integer.parseInt(text);
			cb.onUpdate(value);
		} catch (NumberFormatException e) {
			D20Mod.logger.warn("Unable to parse default value of numfield: Can't make int from [" + text + "]."
					+ " Reverting to " + oVal + ".");
			text = "" + oVal;
			value = oVal;
		}
	}
	
	protected void drawString(FontRenderer fonter, String str, int x, int y, int color, boolean drop) {
    	fonter.drawString(str, x, y, color, drop);
    }
	
}

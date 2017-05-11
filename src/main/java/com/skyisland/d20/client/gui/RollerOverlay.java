package com.skyisland.d20.client.gui;

import com.skyisland.d20.D20Mod;
import com.skyisland.d20.config.ModConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RollerOverlay implements IOverlay {

	private static final ResourceLocation BACK_TEXT = new ResourceLocation(D20Mod.MODID, "textures/gui/roller_gui.png");
	
	private static final int TEXT_HEIGHT = 125;
	private static final int TEXT_WIDTH = 100;
	private static final int GUI_HEIGHT = 63;
	private static final int GUI_WIDTH = 50;
	private static final int GUI_XOFFSET = 20;
	private static final int GUI_YOFFSET = 20;
	
	private static final String PREFIX_UNLOCAL = "text.roller_gui";
	
	private EntityPlayer player;
	private GuiButton[] buttons;
	
	public RollerOverlay(EntityPlayer player) {
		this.player = player;
		
//		if (player != null)
//			MinecraftForge.EVENT_BUS.register(this);
//		else
//			System.out.println("player is null");
		
		buttons = new GuiButton[1]; //should be 3
		buttons[0] = new GuiButton(PREFIX_UNLOCAL + ".roll_20", new GuiButton.ClickCB(){

			@Override
			public void onClick() {
				D20Mod.logger.info("Roll D20 please!");
			}
			
			}, 5 + GUI_XOFFSET, 20 + GUI_YOFFSET);
		
		
	}
	
	@Override
	public void drawOverlay(int mouseX, int mouseY) {
		if (!D20Mod.proxy.isAdmin() || !ModConfig.config.showRollerGui()) {
			return;
		}
		
		
		Minecraft mc = Minecraft.getMinecraft();
		
//		GlStateManager.pushMatrix();
		
//		if (ModConfig.config.usePushpop())
//			GlStateManager.pushAttrib();
		GlStateManager.color(1f, 1f, 1f, 1f);
		
		
		mc.getTextureManager().bindTexture(BACK_TEXT);
		
		//GlStateManager.enableAlpha();
		//GlStateManager.enableBlend();
		
		Gui.drawModalRectWithCustomSizedTexture(
				GUI_XOFFSET, GUI_YOFFSET, 0, 0,
				GUI_WIDTH, GUI_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
		
		
		//GlStateManager.disableAlpha();
		
		for (GuiButton b : buttons)
			b.draw(mouseX, mouseY);
		
//		GlStateManager.popMatrix();
	}
	
}

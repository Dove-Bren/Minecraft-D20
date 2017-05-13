package com.skyisland.d20.client.gui;

import com.skyisland.d20.D20Mod;
import com.skyisland.d20.config.ModConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RollerOverlay implements IOverlay {

	private static final ResourceLocation BACK_TEXT = new ResourceLocation(D20Mod.MODID, "textures/gui/roller_gui.png");
	
	private static final int TEXT_HEIGHT = 125;
	private static final int TEXT_WIDTH = 100;
	private static final int GUI_HEIGHT = 125;
	private static final int GUI_WIDTH = 100;
	private static final int GUI_XOFFSET = 5;
	private static final int GUI_YOFFSET = 5;
	
	private static final String PREFIX_UNLOCAL = "text.roller_gui";
	
	private EntityPlayer player;
	private GuiButton[] buttons;
	private GuiNumField arbField;
	
	public RollerOverlay(EntityPlayer player) {
		this.player = player;
		
//		if (player != null)
//			MinecraftForge.EVENT_BUS.register(this);
//		else
//			System.out.println("player is null");
		
		buttons = new GuiButton[4];
		buttons[0] = new GuiButton(PREFIX_UNLOCAL + ".roll_20", new GuiButton.ClickCB(){

			@Override
			public void onClick() {
				D20Mod.proxy.sendRollRequest(20);
			}
			
			}, 60, 20, (GUI_WIDTH / 2) + GUI_XOFFSET + (-30), 20 + GUI_YOFFSET);

		buttons[1] = new GuiButton(PREFIX_UNLOCAL + ".roll_10", new GuiButton.ClickCB(){

			@Override
			public void onClick() {
				D20Mod.proxy.sendRollRequest(10);
			}
			
			}, 48, 15, (GUI_WIDTH / 2) + GUI_XOFFSET + (-24), 50 + GUI_YOFFSET);

		buttons[2] = new GuiButton(PREFIX_UNLOCAL + ".roll_4", new GuiButton.ClickCB(){

			@Override
			public void onClick() {
				D20Mod.proxy.sendRollRequest(4);
			}
			
			}, 48, 15, (GUI_WIDTH / 2) + GUI_XOFFSET + (-24), 70 + GUI_YOFFSET);

		buttons[3] = new GuiButton(PREFIX_UNLOCAL + ".roll_arb", new GuiButton.ClickCB(){

			@Override
			public void onClick() {
				D20Mod.proxy.sendRollRequest(
						arbField.getValue(true)
						);
			}
			
			}, 48, 15, 15 + (GUI_WIDTH / 2) + GUI_XOFFSET + (-24), 100 + GUI_YOFFSET);
		
		arbField = new GuiNumField("6", new GuiNumField.UpdateCB() {
			
			@Override
			public void onUpdate(int newValue) {
				; //do nothing
			}
		}, 30, 15, 5 + GUI_XOFFSET, 100 + GUI_YOFFSET, 80);
		
	}
	
	@Override
	public void drawOverlay(int mouseX, int mouseY) {
		if (!D20Mod.proxy.isAdmin() || !ModConfig.config.showRollerGui()) {
			return;
		}
		
		
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiInventory))
			return;
		
//		GlStateManager.pushMatrix();
		
//		if (ModConfig.config.usePushpop())
//			GlStateManager.pushAttrib();
		GlStateManager.color(1f, 1f, 1f, 1f);
		
		
		mc.getTextureManager().bindTexture(BACK_TEXT);
		
		//GlStateManager.enableAlpha();
		//GlStateManager.enableBlend();
		
		Gui.drawModalRectWithCustomSizedTexture(
				GUI_XOFFSET, GUI_YOFFSET, 0, 0,
				TEXT_WIDTH, TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
				//GUI_WIDTH, GUI_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
		
		
		//GlStateManager.disableAlpha();
		
		for (GuiButton b : buttons)
			b.draw(mouseX, mouseY);
		
		arbField.draw(mouseX, mouseY);
		
//		GlStateManager.popMatrix();
	}
	
}

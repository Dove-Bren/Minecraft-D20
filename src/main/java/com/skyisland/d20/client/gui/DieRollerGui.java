package com.skyisland.d20.client.gui;

import com.skyisland.d20.D20Mod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DieRollerGui {

	
	/**
	 * Roller container
	 */
	public static class DRollerContainer extends Container {
		
		public DRollerContainer() {
			// Construct slots for player to interact with
			// Brazier only needs '1' inventory slot to interact with
			// Uses slot ID 0
			this.addSlotToContainer(new Slot(brazier, 0, 80, 33));
			// Construct player inventory
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 9; x++) {
					this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + (x * 18), 84 + (y * 18)));
				}
			}
			// Construct player hotbar
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
			}
		}
		
		@Override
		public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
			ItemStack prev = null;	
			Slot slot = (Slot) this.inventorySlots.get(fromSlot);
			
			if (slot != null && slot.getHasStack()) {
				ItemStack cur = slot.getStack();
				prev = cur.copy();
				
				
				/** If we want additional behavior put it here **/
				/**if (fromSlot == 0) {
					// This is going FROM Brazier to player
					if (!this.mergeItemStack(cur, 9, 45, true))
						return null;
					else
						// From Player TO Brazier
						if (!this.mergeItemStack(cur, 0, 0, false)) {
							return null;
						}
				}**/
				
				if (cur.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}
				
				if (cur.stackSize == prev.stackSize) {
					return null;
				}
				slot.onPickupFromSlot(playerIn, cur);
			}
			
			return prev;
		}
		
		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			// TODO Auto-generated method stub
			return true;
		}

	}
	
	@SideOnly(Side.CLIENT)
	public static class DRollerGui extends GuiContainer {

		private static final ResourceLocation GuiImageLocation =
				new ResourceLocation(D20Mod.MODID + ":textures/gui/roller_gui.png");
		
		public DRollerGui(DRollerContainer roller) {
			super(roller);
		}
		
		@Override
		protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
			// TODO Draw Gui
			GlStateManager.color(1.0F,  1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(GuiImageLocation);
			int horizontalMargin = (width - xSize) / 2;
			int verticalMargin = (height - ySize) / 2;
			drawTexturedModalRect(horizontalMargin, verticalMargin, 0,0, xSize, ySize);
		}
		
	}
}

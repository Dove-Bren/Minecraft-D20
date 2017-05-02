/**
 * 
 */
package com.skyisland.d20.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * @author Skyler Manzanares
 *
 */
public class GuiHandler implements IGuiHandler {

	public enum Gui_Type {
		DIE_SCREEN //gui for starting a roll
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		// Check for the GUI type
		if (ID == Gui_Type.DIE_SCREEN.ordinal()) {
			//TODO check if admin
			return new DieRollerGui.DRollerContainer();
		} //else if ...
		
		return null;
	}

	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		// Check for GUI Type
		if (ID == Gui_Type.DIE_SCREEN.ordinal()) {
			return new DieRollerGui.DRollerGui(new DieRollerGui.DRollerContainer());
		} // else if ...
		
		return null;
	}

}

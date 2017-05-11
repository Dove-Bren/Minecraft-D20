package com.skyisland.d20.proxy;


import java.util.List;

import com.skyisland.d20.client.gui.OverlayHandler;
import com.skyisland.d20.die.Die;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class ClientProxy extends CommonProxy {
	
	private static final String MSG_ROLL_PREFIX = "You rolled: ";
	
	/**
	   * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
	   */
	@Override
	public void preInit() {
		super.preInit();
		
		bAdmin = false; //set admin mode off by default. Only turn it on on admin token msg
		
		; //create model registry
	}
	
	@Override
	public void init() {
		super.init();
	}

	/**
	 * Do your mod setup. Build whatever data structures you care about. Register recipes,
	 * send FMLInterModComms messages to other mods.
	 */
	public void load() {
		// register my Recipies
	}

	/**
	 * Handle interaction with other mods, complete your setup based on this.
	 */
	public void postInit() {
		new OverlayHandler();
	}
	
	@Override
	public void sendAdminToken(EntityPlayerMP player) {
		; //do nothing on client side
	}

	@Override
	public void sendRollResult(EntityPlayerMP player, int result) {
		; //do nothing on client
	}
	
	/**
	 * Executes a roll on the client side. Ignores input players list, and just prints out roll.
	 */
	@Override
	public void executeRoll(List<EntityPlayerMP> players, int side) {
		//on client side, do a local roll and print out result to chat
		int result = Die.roll(side);
		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString(MSG_ROLL_PREFIX + result));
	}
	
	@Override
	public void displayRoll(int result) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("SERVER: " + MSG_ROLL_PREFIX + result));
	}
}

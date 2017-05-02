package com.skyisland.d20.proxy;

import net.minecraft.entity.player.EntityPlayerMP;

public class ClientProxy extends CommonProxy {

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
		;
	}
	
	@Override
	public void sendAdminToken(EntityPlayerMP player) {
		; //do nothing on client side
	}
}

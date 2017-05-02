package com.skyisland.d20.proxy;

import com.skyisland.d20.network.NetworkHandler;
import com.skyisland.d20.network.message.AdminTokenMessage;

import net.minecraft.entity.player.EntityPlayerMP;

public class CommonProxy  {
	
    protected boolean bAdmin;
    
    /**
     * Checks and returns whether the current user is an admin.
     * Server is always admin. In addition, users specified in the server config
     * are also given an admin token from the server on login.
     * @return true when server will recognize as admin
     */
    public boolean isAdmin()
    {
    	return bAdmin;
    }
    
    /**
     * Sets current instance as running in admin mode.
     * This is server-side enforced, so setting without receiving message from
     * server won't allow non-admin users to use admin-mode. It will simply
     * cause client to act as if it were admin-mode and potentially crash
     */
    public void setAdmin()
    {
    	bAdmin = true;
    }
	
	/**
	 * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
	 */
	public void preInit()
	{
		setAdmin();
	}
	  
	public void init() {
		System.out.println("Network Proxy initializing.");
		//NetworkRegistry.INSTANCE.registerGuiHandler(D20Mod.instance, new GuiHandler());
	}
	
	/**
	 * Do your mod setup. Build whatever data structures you care about. Register recipes,
	 * send FMLInterModComms messages to other mods.
	 */
	public void load()
	{
		
	}
	
	/**
	 * Handle interaction with other mods, complete your setup based on this.
	 */
	public void postInit()
	{
		
	}

	public void sendAdminToken(EntityPlayerMP player) {
		NetworkHandler.channel.sendTo(new AdminTokenMessage(), player);
	}

	public boolean doDisplayRoller() {
		return false;
	}
	
	public void toggleRollerDisplay() {
		; //nothing to do. client only
	}
}

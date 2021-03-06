package com.skyisland.d20;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.skyisland.d20.config.ModConfig;
import com.skyisland.d20.listener.PlayerListener;
import com.skyisland.d20.network.NetworkHandler;
import com.skyisland.d20.proxy.CommonProxy;
import com.skyisland.d20.registry.AdminRegistry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = D20Mod.MODID, version = D20Mod.VERSION, guiFactory = "com.skyisland.d20.config.ConfigGuiFactory")
public class D20Mod
{
	
	@Instance(value = D20Mod.MODID) //Tell Forge what instance to use.
    public static D20Mod instance;
	
    @SidedProxy(clientSide="com.skyisland.d20.proxy.ClientProxy", serverSide="com.skyisland.d20.proxy.CommonProxy")
    public static CommonProxy proxy;
	
    public static final String MODID = "d20";
    public static final String VERSION = "1.0";
    public static Logger logger = LogManager.getLogger(MODID);
    
    public static PlayerListener playerListener;
    public static AdminRegistry adminRegistry;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	instance = this;
    	new ModConfig(new Configuration(event.getSuggestedConfigurationFile()));
    	adminRegistry = new AdminRegistry(event.getSuggestedConfigurationFile().getParentFile(), ModConfig.config.getAdminFileName());
    	playerListener = new PlayerListener();
    	NetworkHandler.getInstance();
    	proxy.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        proxy.init();
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    	proxy.postInit();
    }
}

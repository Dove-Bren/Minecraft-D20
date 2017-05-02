package com.skyisland.d20;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.skyisland.d20.config.ModConfig;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = D20Mod.MODID, version = D20Mod.VERSION)
public class D20Mod
{
    public static final String MODID = "D20";
    public static final String VERSION = "1.0";
    public static D20Mod instance;
    public static Logger logger = LogManager.getLogger(MODID);
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	instance = this;
    	new ModConfig(new Configuration(event.getSuggestedConfigurationFile()));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        
    }
}

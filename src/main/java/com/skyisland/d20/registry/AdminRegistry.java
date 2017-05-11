package com.skyisland.d20.registry;

import java.io.File;
import java.util.List;

import com.skyisland.d20.config.AdminFile;
import com.skyisland.d20.config.ModConfig;

import net.minecraft.entity.player.EntityPlayer;

/**
 * The official source of admin knowledge.
 * On client, is completely ignored.
 * @author Skyler
 *
 */
public class AdminRegistry {
	
	private List<String> adminIDs;
	
	public AdminRegistry(File baseDir, String fileName) {
		adminIDs = AdminFile.getAdmins(baseDir, fileName);
	}
	
	private boolean matches(String key) {
		for (String id : adminIDs) {
			if (key.trim().equalsIgnoreCase(id))
				return true;
		}
		
		return false;
	}
	
	public boolean isAdmin(EntityPlayer player) {
		if (ModConfig.config.useAdminNames())
			return matches(player.getName());
		
		return matches(player.getUniqueID().toString());
	}
}

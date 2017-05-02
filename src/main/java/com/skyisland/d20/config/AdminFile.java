package com.skyisland.d20.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.skyisland.d20.D20Mod;

public abstract class AdminFile {

	public static List<UUID> getAdmins(File base, String fileName) {
		File adminFile = new File(base, fileName);
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(adminFile));
			
			List<UUID> list = new LinkedList<UUID>();
			while (reader.ready()) {
				list.add(UUID.fromString(reader.readLine()));
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			D20Mod.logger.fatal("Unable to open admin file. No admins will exist (besides server");
			return new LinkedList<UUID>();
		} catch (IOException e) {
			e.printStackTrace();
			D20Mod.logger.fatal("Encounted fatal IO error while parsing admin list!");
			return new LinkedList<UUID>();
		}
		
		return new LinkedList<UUID>();
		
	}
	
}

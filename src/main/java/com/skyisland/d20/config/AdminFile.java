package com.skyisland.d20.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.skyisland.d20.D20Mod;

public abstract class AdminFile {
	
	public static final String escSeq = "--";
	
	private static List<UUID> internalIDs = null;
	
	private static String lastFile = null;

	public static List<UUID> getAdmins(File base, String fileName) {

		File adminFile = new File(base, fileName);
		if (internalIDs != null) {
			//see if requested file matches lastFile name
			if (lastFile != null && lastFile.equalsIgnoreCase(adminFile.getAbsolutePath()))
				return internalIDs; //same as requested file
		}
		
		D20Mod.logger.info("Loading admin file to cache...");
		
		//regardless of above, they requested a file we don't have cached. Load and cache it		
		
		if (!adminFile.exists()) {
			D20Mod.logger.info("Admin config file " + adminFile.getAbsolutePath() + " doesn't exist. Creating default admin file...");
			createDefaultAdminFile(adminFile);
		}
		
		List<UUID> list;
		D20Mod.logger.info("Opening admin file " + adminFile.getAbsolutePath());
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(adminFile));
			
			list = new LinkedList<UUID>();
			String line;
			UUID id = null;
			while (reader.ready()) {
				line = reader.readLine().trim();
				if (line.startsWith(escSeq))
					continue;
				
				try {
					id = UUID.fromString(line);
				} catch (IllegalArgumentException e) {
					D20Mod.logger.warn("Unable to parse UUID: [" + line + "]");
					continue;
				}
				list.add(id);
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			D20Mod.logger.fatal("Unable to open admin file. No admins will exist (besides server)");
			return new LinkedList<UUID>();
		} catch (IOException e) {
			e.printStackTrace();
			D20Mod.logger.fatal("Encounted fatal IO error while parsing admin list!");
			return new LinkedList<UUID>();
		}
		
		//cache returns
		internalIDs = list;
		lastFile = adminFile.getAbsolutePath();
		
		D20Mod.logger.info("Loaded " + list.size() + " admin IDs");
		return list;
		
	}
	

	public static void createDefaultAdminFile(File adminFile) {
		try {
			adminFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PrintWriter writer = new PrintWriter(adminFile);
			writer.println(escSeq + " lines beginning with " + escSeq + " are ignored.");
			writer.println(escSeq + " Add UUIDs here to signify users which are admins");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

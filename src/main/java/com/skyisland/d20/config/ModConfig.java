package com.skyisland.d20.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.skyisland.d20.D20Mod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;

/**
 * Config wrapper class. Ease of use class
 * @author Skyler
 *
 */
public class ModConfig {
	
	public static enum Key {
		ENABLED(Category.SERVER, "enabled", true, true, "Is this mod enabled? When turned off, all mechanics use regular MC mechanics and no events are caught."),
		ADMINS(Category.SERVER, "admin_file", "admin.txt", true, "Filename of the file that lists admins"),
		USE_ADMIN_NAMES(Category.SERVER, "use_admin_names", false, true, "If true, will match admins based on their name. Otherwise, will use UUIDs."),
		SHOW_TEXT(Category.DISPLAY, "show_text", false, false, "On rolls, should the result be given as text?"),
		SHOW_ROLL(Category.DISPLAY, "show_roll", true, false, "On rolls, should the die-rolling animation play?");
		
		public static enum Category {
			SERVER("server", "Core properties that MUST be syncronized bytween the server and client. Client values ignored"),
			DISPLAY("display", "Item tag information and gui display options"),
			TEST("test", "Options used just for debugging and development");
			
			private String categoryName;
			
			private String comment;
			
			private Category(String name, String tooltip) {
				categoryName = name;
				comment = tooltip;
			}
			
			public String getName() {
				return categoryName;
			}
			
			@Override
			public String toString() {
				return getName();
			}
			
			protected static void deployCategories(Configuration config) {
				for (Category cat : values()) {
					config.setCategoryComment(cat.categoryName, cat.comment);
					config.setCategoryRequiresWorldRestart(cat.categoryName, cat == SERVER);
				}
			}
			
		}
		
		private Category category;
		
		private String key;
		
		private String desc;
		
		private Object def;
		
		private boolean serverBound;
		
		private Key(Category category, String key, Object def, String desc) {
			this(category, key, def, false, desc);
		}
		
		private Key(Category category, String key, Object def, boolean serverBound, String desc) {
			this.category = category;
			this.key = key;
			this.desc = desc;
			this.def = def;
			this.serverBound = serverBound;
			
			if (!(def instanceof Float || def instanceof Integer || def instanceof Boolean
					|| def instanceof String)) {
				D20Mod.logger.warn("Config property defaults to a value type that's not supported: " + def.getClass());
			}
		}
		
		protected String getKey() {
			return key;
		}
		
		protected String getDescription() {
			return desc;
		}
		
		protected String getCategory() {
			return category.getName();
		}
		
		protected Object getDefault() {
			return def;
		}
		
		/**
		 * Returns whether this config value should be replaced by
		 * the server's values instead of the clients
		 * @return
		 */
		public boolean isServerBound() {
			return serverBound;
		}
		
		/**
		 * Returns whether this config option can be changed at runtime
		 * @return
		 */
		public boolean isRuntime() {
			if (category == Category.SERVER)
				return false;
			
			//add other cases as they come
			
			return true;
		}
		
		public void saveToNBT(ModConfig config, NBTTagCompound tag) {
			if (tag == null)
				tag = new NBTTagCompound();
			
			if (def instanceof Float)
				tag.setFloat(key, config.getFloatValue(this)); 
			else if (def instanceof Boolean)
				tag.setBoolean(key, config.getBooleanValue(this));
			else if (def instanceof Integer)
				tag.setInteger(key, config.getIntValue(this));
			else
				tag.setString(key, config.getStringValue(this));
		}

		public Object valueFromNBT(NBTTagCompound tag) {
			if (tag == null)
				return null;
			
			if (def instanceof Float)
				return tag.getFloat(key); 
			else if (def instanceof Boolean)
				return tag.getBoolean(key);
			else if (def instanceof Integer)
				return tag.getInteger(key);
			else
				return tag.getString(key);
		}
		
		public static Collection<Key> getCategoryKeys(Category category) {
			Set<Key> set = new HashSet<Key>();
			
			for (Key key : values()) {
				if (key.category == category)
					set.add(key);
			}
			
			return set;
		}
	}

	public static ModConfig config;
	
	private Configuration base;
	
	public ModConfig(Configuration base) {
		this.base = base;
		ModConfig.config = this;
		
		initConfig();
	}

	
	private void initConfig() {
		for (Key key : Key.values())
		if (!base.hasKey(key.getCategory(), key.getKey())) {
			if (key.getDefault() instanceof Float) {
				base.getFloat(key.getKey(), key.getCategory(), (Float) key.getDefault(),
						Float.MIN_VALUE, Float.MAX_VALUE, key.getDescription());
			}
			else if (key.getDefault() instanceof Boolean)
				base.getBoolean(key.getKey(), key.getCategory(), (Boolean) key.getDefault(),
						key.getDescription());
			else if (key.getDefault() instanceof Integer)
				base.getInt(key.getKey(), key.getCategory(), (Integer) key.getDefault(),
						Integer.MIN_VALUE, Integer.MAX_VALUE, key.getDescription());
			else
				base.getString(key.getKey(), key.getCategory(), key.getDefault().toString(),
						key.getDescription());
		}
		
		if (base.hasChanged())
			base.save();
	}
	
	/**************************
	 *   Underlying getters
	 *************************/
	

	protected boolean getBooleanValue(Key key) {
		//DOESN'T cast check. Know what you're doing before you do it
		
		return base.getBoolean(key.getKey(), key.getCategory(), (Boolean) key.getDefault(),
		key.getDescription());
	}
	
	protected float getFloatValue(Key key) {
		//DOESN'T cast check. Know what you're doing before you do it
		
		return base.getFloat(key.getKey(), key.getCategory(), (Float) key.getDefault(),
		Float.MIN_VALUE, Float.MAX_VALUE, key.getDescription());
	}
	
	protected int getIntValue(Key key) {
		//DOESN'T cast check. Know what you're doing before you do it
		
		return base.getInt(key.getKey(), key.getCategory(), (Integer) key.getDefault(),
		Integer.MIN_VALUE, Integer.MAX_VALUE, key.getDescription());
	}
	
	protected String getStringValue(Key key) {
		//DOESN'T cast check. Know what you're doing before you do it
		return base.getString(key.getKey(), key.getCategory(), (String) key.getDefault(),
				key.getDescription());
	}
	
	public boolean isEnabled() {
		return getBooleanValue(Key.ENABLED);
	}
	
	public String getAdminFileName() {
		return getStringValue(Key.ADMINS);
	}
	
	public boolean showText() {
		return getBooleanValue(Key.SHOW_TEXT);
	}
	
	public boolean showRoll() {
		return getBooleanValue(Key.SHOW_ROLL);
	}
	
	public boolean useAdminNames() {
		return getBooleanValue(Key.USE_ADMIN_NAMES);
	}
		
}

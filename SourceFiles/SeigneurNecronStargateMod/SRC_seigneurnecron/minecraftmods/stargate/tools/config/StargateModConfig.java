package seigneurnecron.minecraftmods.stargate.tools.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

import seigneurnecron.minecraftmods.stargate.StargateMod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

/**
 * @author Seigneur Necron
 */
public class StargateModConfig {
	
	// Field anotations :
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface Cfg {
		
		public String category() default Configuration.CATEGORY_GENERAL;
		
		public String comment() default "";
		
	}
	
	// The config object :
	
	private static Configuration config;
	
	// Start IDs :
	
	private static int nextBlockId = 3000;
	private static int nextItemId = 9000;
	
	// Configuration :
	
	@Cfg(comment = "Determines if debug messages have to be displayed in the launcher console.")
	public static boolean debug = true;
	
	@Cfg(comment = "Determines if players can craft naquadah alloy.")
	public static boolean canCraftNaquadahAlloy = true;
	
	@Cfg(comment = "Determines if players can craft stargates ! (Needs canCraftNaquadahAlloy = true)")
	public static boolean canCraftStargate = true;
	
	@Cfg(comment = "Determines if players can craft stargate shield consoles. (Needs canCraftNaquadahAlloy & canCraftStargate = true)")
	public static boolean canCraftStargateShield = true;
	
	@Cfg(comment = "Determines if players can craft teleporters. (Needs canCraftNaquadahAlloy = true)")
	public static boolean canCraftTeleporter = true;
	
	@Cfg(comment = "Determines if players can craft detectors. (Needs canCraftNaquadahAlloy = true)")
	public static boolean canCraftDetector = true;
	
	@Cfg(comment = "Determines if players can craft mob generators. (Needs canCraftNaquadahAlloy = true)")
	public static boolean canCraftMobGenerator = true;
	
	@Cfg(comment = "Determines if players can craft stuff level up tables. (Needs canCraftNaquadahAlloy = true)")
	public static boolean canCraftStuffLevelUpTable = true;
	
	@Cfg(comment = "Determines if players can craft self powered redstone lights.")
	public static boolean canCraftSelfPoweredRedstoneLight = true;
	
	@Cfg(comment = "Determines if players can craft naquadah tools and armors.")
	public static boolean canCraftToolsAndArmors = true;
	
	@Cfg(comment = "Determines if players can craft the (cheated) fire staff. (Needs canCraftToolsAndArmors = true)")
	public static boolean canCraftFireStaff = true;
	
	@Cfg(comment = "Determines if players can craft explosive fireballs.")
	public static boolean canCraftExplosiveFireBalls = true;
	
	@Cfg(comment = "Determines if players can craft the (over-cheated) explosive fire staff. (Needs canCraftToolsAndArmors & canCraftFireStaff & canCraftExplosiveFireBalls = true)")
	public static boolean canCraftExplosiveFireStaff = true;
	
	@Cfg(comment = "Determines if explosive fireballs can destroy blocks.")
	public static boolean canExplosiveFireBallsDestroyBlocks = true;
	
	@Cfg(comment = "Determines if bedrock blocks can be mined (by right-clicking) with a naquadah pickaxe (only if Y != 0).")
	public static boolean canNaquadahPickaxeMineBedrock = true;
	
	@Cfg(comment = "Determines if glass blocks and glass panes are droppped without silk touch.")
	public static boolean isGlassDropable = true;
	
	@Cfg(comment = "Determines the gate sounds : flase => MilkyWay gate sounds, true => Pegasus gate sounds.")
	public static boolean atlantisSounds = false;
	
	// Methods :
	
	public static void loadConfig(Configuration modConfig) {
		config = modConfig;
		
		try {
			config.load();
			Field[] fields = StargateModConfig.class.getFields();
			
			for(Field field : fields) {
				Cfg annotation = field.getAnnotation(Cfg.class);
				if(annotation != null) {
					Object object = field.get(null);
					String name = field.getName();
					String category = annotation.category();
					String comment = annotation.comment().isEmpty() ? null : annotation.comment();
					
					if(object instanceof Boolean) {
						boolean bool = ((Boolean) object).booleanValue();
						bool = config.get(category, name, bool, comment).getBoolean(bool);
						field.setBoolean(null, bool);
					}
					else if(object instanceof Integer) {
						int id = ((Integer) object).intValue();
						id = config.get(category, name, id, comment).getInt();
						field.setInt(null, id);
					}
					else if(object instanceof String) {
						String text = (String) object;
						text = config.get(category, name, text, comment).getString();
						field.set(null, text);
					}
				}
			}
		}
		catch(Exception argh) {
			StargateMod.debug("Error while loading configuration from the config file. Unloaded configuration parameters will be set to default values.", Level.SEVERE, true);
			argh.printStackTrace();
		}
	}
	
	public static int getBlockId(String blockName) {
		while(Block.blocksList[nextBlockId] != null) {
			nextBlockId++;
		}
		
		int id = config.getBlock(blockName, nextBlockId).getInt();
		
		if(id == nextBlockId) {
			nextBlockId++;
		}
		
		return id;
	}
	
	public static int getItemId(String itemName) {
		while(Item.itemsList[nextItemId + 256] != null) {
			nextItemId++;
		}
		
		int id = config.getItem(itemName, nextItemId).getInt();
		
		if(id == nextItemId) {
			nextItemId++;
		}
		
		return id;
	}
	
	public static void saveConfig() {
		config.save();
	}
	
}

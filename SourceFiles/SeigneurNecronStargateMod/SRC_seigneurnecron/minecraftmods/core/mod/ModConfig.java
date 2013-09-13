package seigneurnecron.minecraftmods.core.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;

/**
 * A mod configuration class which already handle all loading and saving operation. <br />
 * You just need to add fields with the correct annotation. <br />
 * Fields can be of type boolean, int, double, String, boolean[], int[], double[] or String[].
 * @author Seigneur Necron
 */
public abstract class ModConfig<M extends ModBase> {
	
	// Field anotations :
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	protected static @interface Config {
		
		public String category() default Configuration.CATEGORY_GENERAL;
		
		public String comment() default "";
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	protected static @interface ObfName {
		
		public String notObfuscatedName();
		
	}
	
	// Fields :
	
	protected final M mod;
	protected final Configuration config;
	protected int nextBlockId;
	protected int nextItemId;
	
	// Configuration fields :
	
	@Config(comment = "Determines the level of the logger. Possible values : ALL, FINEST, FINER, FINE, CONFIG, INFO, WARNING, SEVERE, OFF. Default : CONFIG. You should not choose OFF or SEVERE.")
	public String loggerLevel = ModBase.DEFAULT_LOGGER_LEVEL.getName();
	
	// Construtors :
	
	protected ModConfig(M mod, Configuration config, int firstBlockId, int firstItemId) {
		this.mod = mod;
		this.config = config;
		this.nextBlockId = firstBlockId;
		this.nextItemId = firstItemId;
	}
	
	// Methods :
	
	public void load() {
		try {
			this.config.load();
			Field[] fields = this.getClass().getFields();
			
			for(Field field : fields) {
				Config annotation = field.getAnnotation(Config.class);
				if(annotation != null) {
					Object object = field.get(this);
					String name = field.getName();
					String category = annotation.category();
					String comment = annotation.comment().isEmpty() ? null : annotation.comment();
					
					if(object instanceof Boolean) {
						boolean bool = ((Boolean) object).booleanValue();
						bool = this.config.get(category, name, bool, comment).getBoolean(bool);
						field.setBoolean(this, bool);
					}
					else if(object instanceof Integer) {
						int id = ((Integer) object).intValue();
						id = this.config.get(category, name, id, comment).getInt(id);
						field.setInt(this, id);
					}
					else if(object instanceof Double) {
						double value = ((Double) object).doubleValue();
						value = this.config.get(category, name, value, comment).getDouble(value);
						field.setDouble(this, value);
					}
					else if(object instanceof String) {
						String text = (String) object;
						text = this.config.get(category, name, text, comment).getString();
						field.set(this, text);
					}
					else if(object instanceof boolean[]) {
						boolean[] array = (boolean[]) object;
						array = this.config.get(category, name, array, comment).getBooleanList();
						field.set(this, array);
					}
					else if(object instanceof int[]) {
						int[] array = (int[]) object;
						array = this.config.get(category, name, array, comment).getIntList();
						field.set(this, array);
					}
					else if(object instanceof double[]) {
						double[] array = (double[]) object;
						array = this.config.get(category, name, array, comment).getDoubleList();
						field.set(this, array);
					}
					else if(object instanceof String[]) {
						String[] array = (String[]) object;
						array = this.config.get(category, name, array, comment).getStringList();
						field.set(this, array);
					}
				}
			}
			
			if(!SeigneurNecronMod.instance.getConfig().obfuscated) {
				for(Field field : fields) {
					ObfName annotation = field.getAnnotation(ObfName.class);
					if(annotation != null) {
						Object object = field.get(this);
						
						if(object instanceof String) {
							field.set(this, annotation.notObfuscatedName());
						}
					}
				}
			}
		}
		catch(Exception argh) {
			this.mod.log("Error while loading configuration from the config file. Unloaded configuration parameters will be set to default values.", Level.SEVERE);
			argh.printStackTrace();
		}
	}
	
	public int getBlockId(String blockName) {
		while(Block.blocksList[this.nextBlockId] != null) {
			this.nextBlockId++;
		}
		
		int id = this.config.getBlock(blockName, this.nextBlockId).getInt();
		
		if(id == this.nextBlockId) {
			this.nextBlockId++;
		}
		
		return id;
	}
	
	public int getItemId(String itemName) {
		while(Item.itemsList[this.nextItemId + 256] != null) {
			this.nextItemId++;
		}
		
		int id = this.config.getItem(itemName, this.nextItemId).getInt();
		
		if(id == this.nextItemId) {
			this.nextItemId++;
		}
		
		return id;
	}
	
	public void saveConfig() {
		this.config.save();
	}
	
}

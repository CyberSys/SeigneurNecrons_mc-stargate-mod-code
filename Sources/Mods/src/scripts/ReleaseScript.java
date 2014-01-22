package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import scripts.tools.CustomLogger;
import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public abstract class ReleaseScript {
	
	// Field anotations :
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	protected static @interface Config {
		
		// The default value.
		public String value();
		
	}
	
	// Constants :
	
	public static final char SLASH = File.separatorChar;
	
	public static final String TMP_FOLDER = "tmp";
	public static final String CONFIG_FILE = "config.txt";
	
	public static final String TAGS_FOLDER = "tags";
	public static final String RELEASE_MODS_FOLDER = "mods";
	public static final String RELEASE_RESOURCE_PACKS_FOLDER = "resourcepacks";
	public static final String RELEASE_SRC_FOLDER = "src";
	
	public static final String NECRON_CRAFT_64 = "NecronCraft64";
	public static final String SEIGNEUR_NECRON_MODS_TEXTURES_64 = "SeigneurNecronModsTextures64";
	public static final String VERSION = "_v";
	public static final String ZIP = ".zip";
	public static final String JAVA = ".java";
	
	public static final String ASSETS = "assets";
	public static final String MINECRAFT = "minecraft";
	public static final String MODS = "mods";
	public static final String RESOURCE_PACKS = "resourcepacks";
	
	// Configuration fields :
	
	@Config("1.0.0")
	public String version = "";
	
	@Config("C:\\Users\\Seigneur Necron\\Jeux\\PC\\Minecraft\\MinecraftModing\\SVN")
	public String svnFolderPath = "";
	
	@Config("C:\\Users\\Seigneur Necron\\Jeux\\PC\\Minecraft\\MCP")
	public String mcpFolderPath = "";
	
	@Config("C:\\Users\\Seigneur Necron\\Dropbox\\Minecraft\\MineCraftModing\\[1.6.4]\\InstallationFiles")
	public String dropboxFolderPath = "";
	
	@Config("C:\\Users\\Seigneur Necron\\AppData\\Roaming\\.minecraft")
	public String minecraftFolderPath = "";
	
	// Fields :
	
	protected final Logger logger;
	protected String task;
	
	protected File svnFolder;
	protected File mcpFolder;
	protected File dropboxFolder;
	protected File minecraftFolder;
	
	protected File tmpFolder;
	
	protected File releaseFolder;
	protected File releaseModsFolder;
	protected File releaseResourcePacksFolder;
	protected File releaseSrcFolder;
	
	// Constructors :
	
	protected ReleaseScript(String[] args) {
		this.logger = CustomLogger.getInstance();
		this.logger.info("Logger initialized.");
		
		try {
			this.task = "creating config file";
			
			(new File(CONFIG_FILE)).createNewFile();
			
			this.logger.info("Config file created.");
			
			// ----------------------------------------------------------------
			
			this.task = "loading config file";
			
			Field[] fields = this.getClass().getFields();
			Properties prop = new Properties();
			
			try(InputStream input = new FileInputStream(CONFIG_FILE)) {
				prop.load(input);
			}
			
			for(Field field : fields) {
				Config annotation = field.getAnnotation(Config.class);
				if(annotation != null) {
					String name = field.getName();
					String defaultValue = annotation.value();
					String value = prop.getProperty(name, defaultValue);
					
					if(!isValidConfigValue(value)) {
						value = defaultValue;
					}
					
					field.set(this, value);
					prop.setProperty(name, value);
				}
			}
			
			this.logger.info("Configuration loaded.");
			
			// ----------------------------------------------------------------
			
			this.task = "saving config file";
			
			try(OutputStream output = new FileOutputStream(CONFIG_FILE)) {
				prop.store(output, null);
			}
			
			this.logger.info("Configuration saved.");
			
			// ----------------------------------------------------------------
			
			this.task = "checking required folders";
			
			this.svnFolder = new File(this.svnFolderPath);
			this.mcpFolder = new File(this.mcpFolderPath);
			this.dropboxFolder = new File(this.dropboxFolderPath);
			this.minecraftFolder = new File(this.minecraftFolderPath);
			
			if(!(this.svnFolder.exists() && this.mcpFolder.exists() && this.minecraftFolder.exists())) {
				throw new FileNotFoundException("The files specified in the config file don't exist.");
			}
			
			this.logger.info("Required folder checked.");
			
			// ----------------------------------------------------------------
			
			this.task = "creating tmp folder";
			
			this.tmpFolder = new File(TMP_FOLDER);
			this.tmpFolder.mkdirs();
			FileTools.deleteFolderContent(this.tmpFolder);
			
			this.logger.info("Tmp folder created.");
			
			// ----------------------------------------------------------------
			
			this.task = "initializing folder variables";
			
			this.releaseFolder = new File(this.svnFolder, TAGS_FOLDER + SLASH + this.version);
			this.releaseModsFolder = new File(this.releaseFolder, RELEASE_MODS_FOLDER);
			this.releaseResourcePacksFolder = new File(this.releaseFolder, RELEASE_RESOURCE_PACKS_FOLDER);
			this.releaseSrcFolder = new File(this.releaseFolder, RELEASE_SRC_FOLDER);
			
			this.logger.info("Folder variables initialized.");
			
			// ----------------------------------------------------------------
			
			this.run(args);
			this.logger.info("Done.");
		}
		catch(Exception argh) {
			this.logger.log(Level.SEVERE, "Error while " + this.task + " :", argh);
		}
	}
	
	// Methods :
	
	protected abstract void run(String[] args) throws Exception;
	
	protected static boolean isValidConfigValue(String value) {
		for(char c : value.toCharArray()) {
			if(c != '.' || c != SLASH) {
				return true;
			}
		}
		
		return false;
	}
	
}

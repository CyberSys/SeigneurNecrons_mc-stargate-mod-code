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
 * Copyright (c) 2014, William Philbert (alias Seigneur Necron)<br />
 * All rights reserved.<br />
 * <br />
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:<br />
 * <br />
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.<br />
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.<br />
 * - Neither my name or pseudo may be used to endorse or promote products
 *   derived from this software without specific prior written permission.<br />
 * - You must give me credit for my work. Don't claim my work (edited or not) as
 *   completely your own work, or allow others to carry on believing the work is
 *   yours without correcting them.<br />
 * - Don't tell people they're free to use your work if it contains the work of
 *   others. (You don't have the right to grant permission to others unless it's
 *   all your own work.)<br />
 * - Don't make money on my work.<br />
 * <br />
 * This software is provided "as is" and any express or implied warranties are
 * disclaimed. In no event shall I be liable for any direct or indirect damages
 * caused from the use of this software.<br />
 * All damages caused from the use or misuse of this software fall on the user.
 * 
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
	
	// Common constants :
	
	protected static final char SLASH = File.separatorChar;
	protected static final String VERSION = "_v";
	protected static final String ZIP = ".zip";
	protected static final String JAVA = ".java";
	
	// Mods constants :
	
	protected static final String[] MODS_PACKAGES = {"core", "stargate", "dropableglass", "customsigns"};
	protected static final String[] MODS_CLASSES = {"SeigneurNecronMod", "StargateMod", "DropableGlassMod", "CustomSignsMod"};
	protected static final String[] MODS_IDS = {"seigneur_necron_mod_core", "seigneur_necron_stargate_mod", "seigneur_necron_dropable_glass_mod", "seigneur_necron_custom_signs_mod"};
	protected static final String[] MODS_NAMES = {"SeigneurNecronModCore", "SeigneurNecronStargateMod", "SeigneurNecronDropableGlassMod", "SeigneurNecronCustomSignsMod"};
	
	// Local files :
	
	protected static final String TMP_FOLDER = "tmp";
	protected static final String CONFIG_FILE = "config.txt";
	
	// Common files :
	
	protected static final String MINECRAFT = "minecraft";
	protected static final String SRC = "src";
	protected static final String MODS = "mods";
	protected static final String ASSETS = "assets";
	protected static final String RESOURCE_PACKS = "resourcepacks";
	protected static final String MAIN_PACKAGE = "seigneurnecron";
	protected static final String MODS_PACKAGE = "minecraftmods";
	
	// SVN files :
	
	protected static final String SVN_TRUNK = "trunk";
	protected static final String SVN_TAGS = "tags";
	protected static final String SVN_SOURCES = "Sources";
	protected static final String NECRON_CRAFT_64 = "NecronCraft64";
	protected static final String SVN_MODS = "Mods";
	protected static final String INFO = "info";
	
	protected static final String MCMOD_INFO = "mcmod.info";
	protected static final String SEIGNEUR_NECRON_MODS_TEXTURES_64 = "SeigneurNecronModsTextures64";
	protected static final String SEIGNEUR_NECRON_MODS_SCR = "SeigneurNecronModsSrc";
	protected static final String PACK_PNG = "pack.png";
	protected static final String PACK_MCMETA = "pack.mcmeta";
	protected static final String NECRON_CRAFT_MCMETA = "necroncraft.mcmeta";
	
	protected static final String LICENCES = "Licences";
	protected static final String LICENCE = "licence.txt";
	protected static final String TEXTURES_LICENCE = "textures_licence.txt";
	protected static final String MISA_LICENCE = "Misa_licence.txt";
	
	// MCP files :
	
	protected static final String MCP_REOBF = "reobf";
	protected static final String MCP_JARS = "jars";
	
	// Configuration fields :
	
	@Config("1.0.0")
	public String version = "";
	
	@Config("C:\\Users\\Seigneur Necron\\Jeux\\PC\\Minecraft\\MinecraftModing\\SVN")
	public String svnFolderPath = "";
	
	@Config("C:\\Users\\Seigneur Necron\\Jeux\\PC\\Minecraft\\MCP\\mcp")
	public String mcpFolderPath = "";
	
	@Config("C:\\Users\\Seigneur Necron\\Dropbox\\Minecraft\\MineCraftModing\\[1.6.4]\\InstallationFiles")
	public String dropboxFolderPath = "";
	
	@Config("C:\\Users\\Seigneur Necron\\AppData\\Roaming\\.minecraft")
	public String minecraftFolderPath = "";
	
	// Fields :
	
	protected final Logger logger = CustomLogger.getInstance();
	protected String task;
	
	protected File svnFolder;
	protected File mcpFolder;
	protected File dropboxFolder;
	protected File minecraftFolder;
	
	protected File tmpFolder;
	
	protected File svnTrunkFolder;
	protected File svnSourcesFolder;
	protected File svnLicencesFolder;
	protected File svnNecronCraftFolder;
	protected File svnModsFolder;
	protected File svnModsInfoFolder;
	protected File svnModsSrcFolder;
	protected File svnModsAssetsFolder;
	protected File svnMainPackage;
	protected File svnModsPackage;
	
	protected File svnTagsFolder;
	protected File releaseFolder;
	protected File releaseModsFolder;
	protected File releaseResourcePacksFolder;
	protected File releaseSrcFolder;
	
	protected File necronCraftZip;
	protected File modsTexturesZip;
	protected File srcZip;
	
	protected File mcpReobfFolder;
	protected File mcpReobfMinecraftFolder;
	protected File mcpReobfMainPackage;
	protected File mcpReobfModsPackage;
	protected File mcpJarsFolder;
	protected File mcpResourcePacksFolder;
	protected File mcpSrcFolder;
	protected File mcpSrcMinecraftFolder;
	protected File mcpSrcMainPackage;
	protected File mcpSrcAssetsFolder;
	
	protected File tmpAssetsFolder;
	protected File tmpMainPackage;
	protected File tmpModsPackage;
	
	protected File dropboxResourcePacksFolder;
	protected File dropboxModsFolder;
	
	protected File minecraftResourcePacksFolder;
	protected File minecraftModsFolder;
	
	// Constructors :
	
	protected ReleaseScript(String[] args) {
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
			
			this.svnTrunkFolder = new File(this.svnFolder, SVN_TRUNK);
			this.svnSourcesFolder = new File(this.svnTrunkFolder, SVN_SOURCES);
			this.svnLicencesFolder = new File(this.svnSourcesFolder, LICENCES);
			this.svnModsFolder = new File(this.svnSourcesFolder, SVN_MODS);
			this.svnModsInfoFolder = new File(this.svnModsFolder, INFO);
			this.svnModsSrcFolder = new File(this.svnModsFolder, SRC);
			this.svnModsAssetsFolder = new File(this.svnModsSrcFolder, ASSETS);
			this.svnMainPackage = new File(this.svnModsSrcFolder, MAIN_PACKAGE);
			this.svnModsPackage = new File(this.svnMainPackage, MODS_PACKAGE);
			this.svnNecronCraftFolder = new File(this.svnSourcesFolder, NECRON_CRAFT_64);
			
			this.svnTagsFolder = new File(this.svnFolder, SVN_TAGS);
			this.releaseFolder = new File(this.svnTagsFolder, this.version);
			this.releaseModsFolder = new File(this.releaseFolder, MODS);
			this.releaseResourcePacksFolder = new File(this.releaseFolder, RESOURCE_PACKS);
			this.releaseSrcFolder = new File(this.releaseFolder, SRC);
			
			this.necronCraftZip = new File(this.releaseResourcePacksFolder, NECRON_CRAFT_64 + VERSION + this.version + ZIP);
			this.modsTexturesZip = new File(this.releaseResourcePacksFolder, SEIGNEUR_NECRON_MODS_TEXTURES_64 + VERSION + this.version + ZIP);
			this.srcZip = new File(this.releaseSrcFolder, SEIGNEUR_NECRON_MODS_SCR + VERSION + this.version + ZIP);
			
			this.mcpReobfFolder = new File(this.mcpFolder, MCP_REOBF);
			this.mcpReobfMinecraftFolder = new File(this.mcpReobfFolder, MINECRAFT);
			this.mcpReobfMainPackage = new File(this.mcpReobfMinecraftFolder, MAIN_PACKAGE);
			this.mcpReobfModsPackage = new File(this.mcpReobfMainPackage, MODS_PACKAGE);
			this.mcpJarsFolder = new File(this.mcpFolder, MCP_JARS);
			this.mcpResourcePacksFolder = new File(this.mcpJarsFolder, RESOURCE_PACKS);
			this.mcpSrcFolder = new File(this.mcpFolder, SRC);
			this.mcpSrcMinecraftFolder = new File(this.mcpSrcFolder, MINECRAFT);
			this.mcpSrcMainPackage = new File(this.mcpSrcMinecraftFolder, MAIN_PACKAGE);
			this.mcpSrcAssetsFolder = new File(this.mcpSrcMinecraftFolder, ASSETS);
			
			this.tmpAssetsFolder = new File(this.tmpFolder, ASSETS);
			this.tmpMainPackage = new File(this.tmpFolder, MAIN_PACKAGE);
			this.tmpModsPackage = new File(this.tmpMainPackage, MODS_PACKAGE);
			
			this.dropboxResourcePacksFolder = new File(this.dropboxFolder, RESOURCE_PACKS);
			this.dropboxModsFolder = new File(this.dropboxFolder, MODS);
			
			this.minecraftResourcePacksFolder = new File(this.minecraftFolder, RESOURCE_PACKS);
			this.minecraftModsFolder = new File(this.minecraftFolder, MODS);
			
			this.logger.info("Folder variables initialized.");
			this.logger.info("---------------------------------------");
			
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

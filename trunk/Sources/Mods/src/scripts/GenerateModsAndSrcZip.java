package scripts;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class GenerateModsAndSrcZip extends ReleaseScript {
	
	// Constants :
	
	public static final String SVN_MODS_FOLDER = "trunk" + SLASH + "Sources" + SLASH + "Mods";
	public static final String SRC = "src";
	public static final String MCMOD_INFO_FOLDER = "mcmod_info";
	public static final String MCMOD_INFO = "mcmod.info";
	
	public static final String MAIN_PACKAGE = "seigneurnecron";
	public static final String MODS_PACKAGE = "minecraftmods";
	
	public static final String[] MODS_PACKAGES = {"core", "stargate", "dropableglass", "customsigns"};
	public static final String[] MODS_CLASSES = {"SeigneurNecronMod", "StargateMod", "DropableGlassMod", "CustomSignsMod"};
	public static final String[] MODS_IDS = {"seigneur_necron_mod_core", "seigneur_necron_stargate_mod", "seigneur_necron_dropable_glass_mod", "seigneur_necron_custom_signs_mod"};
	public static final String[] MODS_NAMES = {"SeigneurNecronModCore", "SeigneurNecronStargateMod", "SeigneurNecronDropableGlassMod", "SeigneurNecronCustomSignsMod"};
	
	public static final String MCP_REOBF_MINECRAFT_FOLDER = "mcp" + SLASH + "reobf" + SLASH + "minecraft";
	
	public static final String SEIGNEUR_NECRON_MODS_SCR = "SeigneurNecronModsSrc";
	
	// Constructors :
	
	public GenerateModsAndSrcZip(String[] args) {
		super(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "creating release folders.";
		
		this.releaseFolder.mkdirs();
		this.releaseModsFolder.mkdir();
		this.releaseSrcFolder.mkdir();
		
		this.logger.info("Release folders created.");
		
		// ----------------------------------------------------------------
		
		this.task = "cleaning old zips";
		
		FileTools.deleteFolderContent(this.releaseModsFolder);
		FileTools.deleteFolderContent(this.releaseSrcFolder);
		
		this.logger.info("Old zips cleaned.");
		
		// ----------------------------------------------------------------
		
		this.task = "creating src zip";
		this.logger.info("Creating src zip... (this might take a moment)");
		
		File svnModsFolder = new File(this.svnFolder, SVN_MODS_FOLDER);
		File svnModsAssetsFolder = new File(svnModsFolder, ASSETS);
		File svnMainPackageFolder = new File(svnModsFolder, SRC + SLASH + MAIN_PACKAGE);
		File svnModsPackageFolder = new File(svnMainPackageFolder, MODS_PACKAGE);
		
		FileUtils.copyDirectory(svnMainPackageFolder, new File(this.tmpFolder, MAIN_PACKAGE));
		FileUtils.copyDirectory(svnModsAssetsFolder, new File(this.tmpFolder, ASSETS));
		
		File srcZip = new File(this.releaseSrcFolder, SEIGNEUR_NECRON_MODS_SCR + "_" + this.version + ZIP);
		FileTools.createZip(this.tmpFolder, srcZip);
		this.logger.info("-> output : " + srcZip.getName());
		
		this.logger.info("Src zip created.");
		
		// ----------------------------------------------------------------
		
		this.task = "creating mod zips";
		this.logger.info("Creating mod zips... (this might take a moment)");
		
		File mcpReobfMinecraftFolder = new File(this.mcpFolder, MCP_REOBF_MINECRAFT_FOLDER);
		File mcpModsPackageFolder = new File(mcpReobfMinecraftFolder, MAIN_PACKAGE + SLASH + MODS_PACKAGE);
		File tmpModsPackageFolder = new File(this.tmpFolder, MAIN_PACKAGE + SLASH + MODS_PACKAGE);
		File svnMcmodInfoFolder = new File(svnModsFolder, MCMOD_INFO_FOLDER);
		
		for(int i = 0; i < MODS_PACKAGES.length; i++) {
			FileTools.deleteFolderContent(this.tmpFolder);
			FileUtils.copyDirectory(new File(mcpModsPackageFolder, MODS_PACKAGES[i]), new File(tmpModsPackageFolder, MODS_PACKAGES[i]));
			
			File assetFolder = new File(svnModsAssetsFolder, MODS_IDS[i]);
			
			if(assetFolder.exists()) {
				FileUtils.copyDirectory(assetFolder, new File(this.tmpFolder, ASSETS + SLASH + MODS_IDS[i]));
			}
			
			File modMainFile = new File(svnModsPackageFolder, MODS_PACKAGES[i] + SLASH + MODS_CLASSES[i] + JAVA);
			Matcher matcher = FileTools.findStringInFile(modMainFile, "public static final String VERSION \\= \"(\\[[^\\]]*\\]) ([^\\[]*) (\\[[^\\]]*\\])\";");
			
			if(matcher.find()) {
				String minecraftVersion = matcher.group(1);
				String modVersion = matcher.group(2);
				String dependenciesVersion = matcher.group(3);
				String completeVersion = minecraftVersion + " " + modVersion + " " + dependenciesVersion;
				
				File mcmod_info = new File(this.tmpFolder, MCMOD_INFO);
				FileUtils.copyFile(new File(svnMcmodInfoFolder, MODS_PACKAGES[i] + SLASH + MCMOD_INFO), mcmod_info);
				FileTools.replaceFirstInFile(mcmod_info, "INSERT_VERSION_HERE", completeVersion);
				
				File modZip = new File(this.releaseModsFolder, minecraftVersion + MODS_NAMES[i] + "_" + modVersion + dependenciesVersion + ZIP);
				FileTools.createZip(this.tmpFolder, modZip);
				this.logger.info("-> output : " + modZip.getName());
			}
			else {
				throw new IOException("Can't find the version of the mod in the file : " + modMainFile.getAbsolutePath());
			}
			
		}
		
		this.logger.info("Mods zips created.");
		
		// ----------------------------------------------------------------
		
		this.task = "cleaning tmp folder";
		
		FileTools.deleteFolderContent(this.tmpFolder);
		
		this.logger.info("Tmp folder cleaned.");
	}
	
}

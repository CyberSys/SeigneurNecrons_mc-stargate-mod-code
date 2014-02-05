package scripts;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class GenerateModsAndSrcZip extends ReleaseScript {
	
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
		
		this.task = "cleaning old src";
		
		FileTools.deleteFolderContent(this.svnMainPackage);
		
		this.logger.info("Old src cleaned.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying src from MCP";
		
		FileTools.copyDirectory(this.mcpSrcMainPackage, this.svnMainPackage);
		
		this.logger.info("Src copied from MCP.");
		
		// ----------------------------------------------------------------
		
		this.task = "cleaning old zips";
		
		FileTools.deleteFolderContent(this.releaseModsFolder);
		FileTools.deleteFolderContent(this.releaseSrcFolder);
		
		this.logger.info("Old zips cleaned.");
		
		// ----------------------------------------------------------------
		
		this.task = "creating src zip";
		this.logger.info("Creating src zip... (this might take a moment)");
		
		FileTools.copyFile(new File(this.svnLicencesFolder, LICENCE), new File(this.tmpFolder, LICENCE));
		FileTools.copyDirectory(this.svnMainPackage, this.tmpMainPackage);
		FileTools.copyDirectory(this.svnModsAssetsFolder, this.tmpAssetsFolder);
		
		FileTools.zipFolderContent(this.tmpFolder, this.srcZip);
		
		this.logger.info("Src zip created.");
		
		// ----------------------------------------------------------------
		
		this.task = "creating mod zips";
		this.logger.info("Creating mod zips... (this might take a moment)");
		
		for(int i = 0; i < MODS_PACKAGES.length; i++) {
			FileTools.deleteFolderContent(this.tmpFolder);
			FileTools.copyFile(new File(this.svnLicencesFolder, LICENCE), new File(this.tmpFolder, LICENCE));
			FileTools.copyDirectory(new File(this.mcpReobfModsPackage, MODS_PACKAGES[i]), new File(this.tmpModsPackage, MODS_PACKAGES[i]));
			
			File assetFolder = new File(this.svnModsAssetsFolder, MODS_IDS[i]);
			
			if(assetFolder.exists()) {
				FileTools.copyDirectory(assetFolder, new File(this.tmpAssetsFolder, MODS_IDS[i]));
			}
			
			File modMainFile = new File(this.svnModsPackage, MODS_PACKAGES[i] + SLASH + MODS_CLASSES[i] + JAVA);
			Matcher matcher = FileTools.findStringInFile(modMainFile, "public static final String VERSION \\= \"(\\[[^\\]]*\\]) ([^\\[]*) (\\[[^\\]]*\\])\";");
			
			if(matcher.find()) {
				String minecraftVersion = matcher.group(1);
				String modVersion = matcher.group(2);
				String dependenciesVersion = matcher.group(3);
				String completeVersion = minecraftVersion + " " + modVersion + " " + dependenciesVersion;
				
				File mcmod_info = new File(this.tmpFolder, MCMOD_INFO);
				FileTools.copyFile(new File(this.svnModsInfoFolder, MODS_PACKAGES[i] + SLASH + MCMOD_INFO), mcmod_info);
				FileTools.replaceFirstInFile(mcmod_info, "INSERT_VERSION_HERE", completeVersion);
				
				File modZip = new File(this.releaseModsFolder, minecraftVersion + MODS_NAMES[i] + "_" + modVersion + dependenciesVersion + ZIP);
				FileTools.zipFolderContent(this.tmpFolder, modZip);
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

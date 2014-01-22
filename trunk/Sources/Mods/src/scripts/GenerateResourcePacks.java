package scripts;

import java.io.File;

import org.apache.commons.io.FileUtils;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class GenerateResourcePacks extends ReleaseScript {
	
	// Constants :
	
	public static final String SVN_NECRON_CRAFT_FOLDER = "trunk" + SLASH + "Sources" + SLASH + "NecronCraft 64";
	public static final String NECRONCRAFT_MCMETA = "necroncraft.mcmeta";
	public static final String PACK_MCMETA = "pack.mcmeta";
	public static final String PACK_PNG = "pack.png";
	
	// Constructors :
	
	public GenerateResourcePacks(String[] args) {
		super(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "creating release folders.";
		
		this.releaseFolder.mkdirs();
		this.releaseResourcePacksFolder.mkdir();
		
		this.logger.info("Release folders created.");
		
		// ----------------------------------------------------------------
		
		this.task = "cleaning old zips";
		
		FileTools.deleteFolderContent(this.releaseResourcePacksFolder);
		
		this.logger.info("Old zips cleaned.");
		
		// ----------------------------------------------------------------
		
		this.task = "creating texture packs";
		this.logger.info("Creating texture packs... (this might take a moment)");
		
		File svnNecronCraftFolder = new File(this.svnFolder, SVN_NECRON_CRAFT_FOLDER);
		
		FileUtils.copyDirectory(new File(svnNecronCraftFolder, ASSETS), new File(this.tmpFolder, ASSETS));
		FileUtils.copyFile(new File(svnNecronCraftFolder, PACK_PNG), new File(this.tmpFolder, PACK_PNG));
		FileUtils.copyFile(new File(svnNecronCraftFolder, NECRONCRAFT_MCMETA), new File(this.tmpFolder, PACK_MCMETA));
		
		File necronCraftZip = new File(this.releaseResourcePacksFolder, NECRON_CRAFT_64 + VERSION + this.version + ZIP);
		FileTools.createZip(this.tmpFolder, necronCraftZip);
		this.logger.info("-> output : " + necronCraftZip.getName());
		
		FileUtils.copyFile(new File(svnNecronCraftFolder, PACK_MCMETA), new File(this.tmpFolder, PACK_MCMETA));
		FileUtils.deleteDirectory(new File(this.tmpFolder, ASSETS + SLASH + MINECRAFT));
		
		File modsTexturesZip = new File(this.releaseResourcePacksFolder, SEIGNEUR_NECRON_MODS_TEXTURES_64 + VERSION + this.version + ZIP);
		FileTools.createZip(this.tmpFolder, modsTexturesZip);
		this.logger.info("-> output : " + modsTexturesZip.getName());
		
		this.logger.info("Texture packs created.");
		
		// ----------------------------------------------------------------
		
		this.task = "cleaning tmp folder";
		
		FileTools.deleteFolderContent(this.tmpFolder);
		
		this.logger.info("Tmp folder cleaned.");
	}
	
}

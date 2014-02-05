package scripts;

import java.io.File;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class GenerateResourcePacks extends ReleaseScript {
	
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
		
		FileTools.copyDirectory(new File(this.svnNecronCraftFolder, ASSETS), new File(this.tmpFolder, ASSETS));
		FileTools.copyFile(new File(this.svnNecronCraftFolder, PACK_PNG), new File(this.tmpFolder, PACK_PNG));
		FileTools.copyFile(new File(this.svnNecronCraftFolder, NECRON_CRAFT_MCMETA), new File(this.tmpFolder, PACK_MCMETA));
		
		FileTools.zipFolderContent(this.tmpFolder, this.necronCraftZip);
		
		FileTools.copyFile(new File(this.svnLicencesFolder, TEXTURES_LICENCE), new File(this.tmpFolder, LICENCE));
		FileTools.copyFile(new File(this.svnLicencesFolder, MISA_LICENCE), new File(this.tmpFolder, MISA_LICENCE));
		FileTools.copyFile(new File(this.svnNecronCraftFolder, PACK_MCMETA), new File(this.tmpFolder, PACK_MCMETA));
		FileTools.deleteDirectory(new File(this.tmpFolder, ASSETS + SLASH + MINECRAFT));
		
		FileTools.zipFolderContent(this.tmpFolder, this.modsTexturesZip);
		
		this.logger.info("Texture packs created.");
		
		// ----------------------------------------------------------------
		
		this.task = "cleaning tmp folder";
		
		FileTools.deleteFolderContent(this.tmpFolder);
		
		this.logger.info("Tmp folder cleaned.");
	}
	
}

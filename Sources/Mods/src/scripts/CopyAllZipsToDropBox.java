package scripts;

import java.io.File;

import org.apache.commons.io.FileUtils;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class CopyAllZipsToDropBox extends ReleaseScript {
	
	// Constructors :
	
	public CopyAllZipsToDropBox(String[] args) {
		super(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "copying resource packs to DropBox";
		
		File dropboxResourcePacksFolder = new File(this.dropboxFolder, RESOURCE_PACKS);
		FileTools.deleteFolderContent(dropboxResourcePacksFolder);
		FileUtils.copyDirectory(this.releaseResourcePacksFolder, dropboxResourcePacksFolder);
		
		this.logger.info("Resource packs copied to DropBox.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying mods to DropBox";
		
		File dropboxModsFolder = new File(this.dropboxFolder, MODS);
		FileTools.deleteFolderContent(dropboxModsFolder);
		FileUtils.copyDirectory(this.releaseModsFolder, dropboxModsFolder);
		
		this.logger.info("Mods copied to DropBox.");
	}
	
}

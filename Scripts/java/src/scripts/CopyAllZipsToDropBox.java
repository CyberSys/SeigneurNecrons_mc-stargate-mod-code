package scripts;

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
		
		FileTools.deleteFolderContent(this.dropboxResourcePacksFolder);
		FileTools.copyDirectory(this.releaseResourcePacksFolder, this.dropboxResourcePacksFolder);
		
		this.logger.info("Resource packs copied to DropBox.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying mods to DropBox";
		
		FileTools.deleteFolderContent(this.dropboxModsFolder);
		FileTools.copyDirectory(this.releaseModsFolder, this.dropboxModsFolder);
		
		this.logger.info("Mods copied to DropBox.");
	}
	
}

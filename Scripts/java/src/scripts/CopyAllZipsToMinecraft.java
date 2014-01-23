package scripts;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class CopyAllZipsToMinecraft extends ReleaseScript {
	
	// Constructors :
	
	public CopyAllZipsToMinecraft(String[] args) {
		super(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "copying resource packs to Minecraft";
		
		FileTools.deleteFolderContent(this.minecraftResourcePacksFolder);
		FileTools.copyDirectory(this.releaseResourcePacksFolder, this.minecraftResourcePacksFolder);
		
		this.logger.info("Resource packs copied to Minecraft.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying mods to Minecraft";
		
		FileTools.deleteFolderContent(this.minecraftModsFolder);
		FileTools.copyDirectory(this.releaseModsFolder, this.minecraftModsFolder);
		
		this.logger.info("Mods copied to Minecraft.");
	}
	
}

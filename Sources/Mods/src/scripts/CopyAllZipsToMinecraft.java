package scripts;

import java.io.File;

import org.apache.commons.io.FileUtils;

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
		
		File minecraftResourcePacksFolder = new File(this.minecraftFolder, RESOURCE_PACKS);
		FileTools.deleteFolderContent(minecraftResourcePacksFolder);
		FileUtils.copyDirectory(this.releaseResourcePacksFolder, minecraftResourcePacksFolder);
		
		this.logger.info("Resource packs copied to Minecraft.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying mods to Minecraft";
		
		File minecraftModsFolder = new File(this.minecraftFolder, MODS);
		FileTools.deleteFolderContent(minecraftModsFolder);
		FileUtils.copyDirectory(this.releaseModsFolder, minecraftModsFolder);
		
		this.logger.info("Mods copied to Minecraft.");
	}
	
}

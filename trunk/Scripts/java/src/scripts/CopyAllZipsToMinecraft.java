package scripts;

import java.io.File;

import scripts.tools.FileTools;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
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
		
		File[] installedMods = this.minecraftModsFolder.listFiles();
		
		for(File file : installedMods) {
			if(file.isFile() && file.getName().contains("SeigneurNecron") && file.getName().endsWith(ZIP)) {
				file.delete();
			}
		}
		
		FileTools.copyDirectory(this.releaseModsFolder, this.minecraftModsFolder);
		
		this.logger.info("Mods copied to Minecraft.");
	}
	
}

package scripts;

import java.io.File;

import scripts.tools.FileTools;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class CopyResourcePacksToMcp extends ReleaseScript {
	
	// Constructors :
	
	public CopyResourcePacksToMcp(String[] args) {
		super(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "copying base assets to MCP.";
		
		for(int i = 0; i < MODS_PACKAGES.length; i++) {
			File assetFolder = new File(this.mcpSrcAssetsFolder, MODS_IDS[i]);
			
			if(assetFolder.exists()) {
				FileTools.deleteDirectory(assetFolder);
			}
		}
		
		FileTools.copyDirectory(this.svnModsAssetsFolder, this.mcpSrcAssetsFolder);
		
		this.logger.info("Base assets copied to MCP.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying resource packs to MCP";
		
		FileTools.deleteFolderContent(this.mcpResourcePacksFolder);
		FileTools.copyDirectory(this.releaseResourcePacksFolder, this.mcpResourcePacksFolder);
		
		this.logger.info("Resource packs copied to MCP.");
	}
	
}

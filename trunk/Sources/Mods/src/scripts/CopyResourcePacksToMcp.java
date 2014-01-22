package scripts;

import java.io.File;

import org.apache.commons.io.FileUtils;

import scripts.tools.FileTools;

/**
 * @author Seigneur Necron
 */
public class CopyResourcePacksToMcp extends ReleaseScript {
	
	// Constants :
	
	public static final String MCP_RESOURCE_PACKS_FOLDER = "mcp" + SLASH + "jars" + SLASH + "resourcepacks";
	
	// Constructors :
	
	public CopyResourcePacksToMcp(String[] args) {
		super(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "copying resource packs to MCP";
		
		File mcpResourcePacksFolder = new File(this.mcpFolder, MCP_RESOURCE_PACKS_FOLDER);
		FileTools.deleteFolderContent(mcpResourcePacksFolder);
		FileUtils.copyDirectory(this.releaseResourcePacksFolder, mcpResourcePacksFolder);
		
		this.logger.info("Resource packs copied to MCP.");
	}
	
}

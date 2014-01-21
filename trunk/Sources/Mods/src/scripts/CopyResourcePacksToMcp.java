package scripts;

/**
 * @author Seigneur Necron
 */
public class CopyResourcePacksToMcp extends ReleaseScript {
	
	// Constructors :
	
	public CopyResourcePacksToMcp(String[] args) {
		super(args);
	}
	
	// Main :
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new CopyResourcePacksToMcp(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "copying resource packs to MCP";
		
		// FIXME - Ressource packs.
		
		this.logger.info("Resource packs copied to MCP.");
	}
	
}

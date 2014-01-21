package scripts;

/**
 * @author Seigneur Necron
 */
public class CopyAllZipsToDropBox extends ReleaseScript {
	
	// Constructors :
	
	public CopyAllZipsToDropBox(String[] args) {
		super(args);
	}
	
	// Main :
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new CopyAllZipsToDropBox(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "copying resource packs to DropBox";
		
		// FIXME - Ressource packs.
		
		this.logger.info("Resource packs copied to DropBox.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying mods to DropBox";
		
		// FIXME - Mods.
		
		this.logger.info("Mods copied to DropBox.");
		
		// ----------------------------------------------------------------
		
		this.task = "copying src zip to DropBox";
		
		// FIXME - Src zip.
		
		this.logger.info("Src zip copied to DropBox.");
	}
	
}

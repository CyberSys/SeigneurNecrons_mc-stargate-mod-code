package scripts;

/**
 * @author Seigneur Necron
 */
public class GenerateZips extends ReleaseScript {
	
	// Constructors :
	
	public GenerateZips(String[] args) {
		super(args);
	}
	
	// Main :
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new GenerateZips(args);
	}
	
	// Methods :
	
	@Override
	protected void run(String[] args) throws Exception {
		this.task = "cleaning old zips";
		
		// FIXME - Clean old zips.
		
		this.logger.info("Cleaned old zips.");
		
		// ----------------------------------------------------------------
		
	}
	
}

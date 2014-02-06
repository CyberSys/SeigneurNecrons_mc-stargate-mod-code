package seigneurnecron.minecraftmods.stargate.tools.address;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class MalformedCoordinatesException extends Exception {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	// Constructors :
	
	public MalformedCoordinatesException() {
		super();
	}
	
	public MalformedCoordinatesException(String message) {
		super(message);
	}
	
}

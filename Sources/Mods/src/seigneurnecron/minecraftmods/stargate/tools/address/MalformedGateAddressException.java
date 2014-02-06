package seigneurnecron.minecraftmods.stargate.tools.address;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class MalformedGateAddressException extends Exception {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	// Constructors :
	
	public MalformedGateAddressException() {
		super();
	}
	
	public MalformedGateAddressException(String message) {
		super(message);
	}
	
}

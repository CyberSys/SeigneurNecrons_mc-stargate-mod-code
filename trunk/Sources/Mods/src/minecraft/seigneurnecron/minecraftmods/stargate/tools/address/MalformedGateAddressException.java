package seigneurnecron.minecraftmods.stargate.tools.address;

/**
 * @author Seigneur Necron
 */
public class MalformedGateAddressException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MalformedGateAddressException() {
		super();
	}
	
	public MalformedGateAddressException(String message) {
		super(message);
	}
	
}

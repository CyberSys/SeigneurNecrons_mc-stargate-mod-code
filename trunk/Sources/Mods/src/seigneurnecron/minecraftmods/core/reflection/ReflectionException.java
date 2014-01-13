package seigneurnecron.minecraftmods.core.reflection;

/**
 * @author Seigneur Necron
 */
public class ReflectionException extends Exception {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	// Constructors :
	
	public ReflectionException(Exception argh) {
		super("A problem occured while trying to get an obfuscated private field : " + argh.getMessage());
	}
	
}

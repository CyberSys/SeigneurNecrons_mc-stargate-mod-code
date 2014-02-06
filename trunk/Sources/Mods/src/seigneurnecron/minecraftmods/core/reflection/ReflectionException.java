package seigneurnecron.minecraftmods.core.reflection;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
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

package seigneurnecron.minecraftmods.stargate.tools.address;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;
import seigneurnecron.minecraftmods.stargate.tools.loadable.BlockCoordinates;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateZoneCoordinates;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public final class GateAddress {
	
	// Construtors :
	
	private GateAddress() {
		// This class don't have to be instanciated, it only contains static methods.
	}
	
	// Symbols :
	
	public static final List<Character> X_SYMBOLS = Arrays.asList(new Character[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'});
	public static final List<Character> Z_SYMBOLS = Arrays.asList(new Character[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'});
	public static final List<Character> Y_SYMBOLS = Arrays.asList(new Character[] {'v', 'w', 'x', 'y', 'z'});
	public static final char SPECIAL_SYMBOL = '@';
	
	public static final int NB_X_SYMBOLS = 3;
	public static final int OFFSET;
	private static final int MAX_FACTOR;
	
	static {
		int nbSymbols = X_SYMBOLS.size();
		int offset = 1;
		
		for(int i = 0; i < NB_X_SYMBOLS; i++) {
			offset *= nbSymbols - i;
		}
		
		OFFSET = offset / 2;
		MAX_FACTOR = offset / nbSymbols;
	}
	
	// Utility :
	
	private static <T> List<T> copy(List<T> src) {
		List<T> dest = new LinkedList<T>();
		
		for(T elt : src) {
			dest.add(elt);
		}
		
		return dest;
	}
	
	// Address => Coordinates :
	
	public static StargateZoneCoordinates toCoordinates(String address) throws MalformedGateAddressException {
		if(address.length() == 9 && address.charAt(8) == SPECIAL_SYMBOL) {
			address = address.substring(0, 8);
		}
		
		if(address.length() != 8) {
			throw new MalformedGateAddressException("Invalid address : " + address + ". A gate address must be composed of 8 symbols.");
		}
		
		int i = 0;
		
		int x = getXCoord(address.substring(i, i + NB_X_SYMBOLS), X_SYMBOLS);
		i += NB_X_SYMBOLS;
		
		int z = getXCoord(address.substring(i, i + NB_X_SYMBOLS), Z_SYMBOLS);
		i += NB_X_SYMBOLS;
		
		int y = getYCoord(address.charAt(i++));
		
		int dim = getDimension(address.charAt(i++));
		
		return new StargateZoneCoordinates(dim, x, y, z);
	}
	
	private static int getXCoord(String address, List<Character> symbols) throws MalformedGateAddressException {
		List<Character> availableSymbols = copy(symbols);
		int result = 0;
		
		for(int i = 0; i < NB_X_SYMBOLS; i++) {
			result *= availableSymbols.size();
			
			int index = availableSymbols.indexOf(address.charAt(i));
			
			if(index < 0) {
				throw new MalformedGateAddressException("Invalid symbol for X or Z coordinate : " + address + ".");
			}
			
			result += index;
			
			availableSymbols.remove(index);
		}
		
		return result - OFFSET;
	}
	
	private static int getYCoord(char address) throws MalformedGateAddressException {
		int index = Y_SYMBOLS.indexOf(address);
		
		if(index < 0) {
			throw new MalformedGateAddressException("Invalid symbol for Y coordinate : " + address + ".");
		}
		
		return index;
	}
	
	private static int getDimension(char address) throws MalformedGateAddressException {
		Dimension dimension = Dimension.byAddress(address);
		
		if(dimension == null) {
			throw new MalformedGateAddressException("Invalid symbol for dimension : " + address + ".");
		}
		
		return dimension.getValue();
	}
	
	// Coordinates => Address :
	
	public static String toAddress(BlockCoordinates blockCoords) throws MalformedCoordinatesException {
		StargateZoneCoordinates coords = blockCoords.getStargateZoneCoordinates();
		
		String xAddress = getXAddress(coords.x, X_SYMBOLS);
		String zAddress = getXAddress(coords.z, Z_SYMBOLS);
		char yAddress = getYAddress(coords.y);
		char dimAddress = getDimAddress(coords.dim);
		return xAddress + zAddress + yAddress + dimAddress;
	}
	
	private static String getXAddress(int coord, List<Character> symbols) throws MalformedCoordinatesException {
		coord += OFFSET;
		
		if(coord < 0 || coord >= 2 * OFFSET) {
			throw new MalformedCoordinatesException("Coordinate out of bounds for X or Z : " + coord + ".");
		}
		
		List<Character> availableSymbols = copy(symbols);
		int factor = MAX_FACTOR;
		String result = "";
		
		for(int i = 0; i < NB_X_SYMBOLS; i++) {
			int index = coord / factor;
			coord = coord % factor;
			
			result = result + availableSymbols.get(index);
			availableSymbols.remove(index);
			
			factor /= availableSymbols.size();
		}
		
		return result;
	}
	
	private static char getYAddress(int coord) throws MalformedCoordinatesException {
		if(coord < 0 || coord > Y_SYMBOLS.size()) {
			throw new MalformedCoordinatesException("Coordinate out of bounds for Y : " + coord + ".");
		}
		
		return Y_SYMBOLS.get(coord);
	}
	
	private static char getDimAddress(int value) throws MalformedCoordinatesException {
		Dimension dimension = Dimension.valueOf(value);
		
		if(dimension == null) {
			throw new MalformedCoordinatesException("Invalid dimension : " + value + ".");
		}
		
		return dimension.getAddress();
	}
	
	// Check methods :
	
	public static boolean isValidAddress(String address) {
		return isValidAddress(address, 0, false, false);
	}
	
	public static boolean isValidAddress(String address, boolean displayError) {
		return isValidAddress(address, 0, false, displayError);
	}
	
	public static boolean isValidAddressForDimension(String address, int dimension) {
		return isValidAddress(address, dimension, true, true);
	}
	
	protected static boolean isValidAddress(String address, int dimension, boolean checkDimension, boolean displayError) {
		try {
			StargateZoneCoordinates coords = toCoordinates(address);
			
			if(checkDimension && coords.dim != dimension) {
				StargateMod.instance.log("The address \"" + address + "\" isn't valid for the dimension.");
				return false;
			}
			
			return true;
		}
		catch(MalformedGateAddressException argh) {
			if(displayError) {
				StargateMod.instance.log(argh.getMessage());
			}
			return false;
		}
	}
	
	public static boolean isValidCoordinates(BlockCoordinates coords) {
		try {
			toAddress(coords);
		}
		catch(MalformedCoordinatesException argh) {
			return false;
		}
		
		return true;
	}
	
}

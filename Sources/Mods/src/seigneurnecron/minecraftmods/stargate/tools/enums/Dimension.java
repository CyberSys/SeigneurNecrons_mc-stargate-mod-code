package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum Dimension {
	
	// Constants :
	
	EARTH(0, '0', "Earth"),
	HELL(-1, '1', "Hell"),
	END(1, '2', "End");
	
	// Fields :
	
	private int value;
	private char address;
	private String toString;
	
	// Constructors :
	
	private Dimension(int value, char address, String toString) {
		this.value = value;
		this.address = address;
		this.toString = toString;
	}
	
	// Getters :
	
	public int getValue() {
		return this.value;
	}
	
	public char getAddress() {
		return this.address;
	}
	
	// Methods :
	
	@Override
	public String toString() {
		return this.toString;
	}
	
	public static Dimension valueOf(int value) {
		for(Dimension dimension : Dimension.values()) {
			if(dimension.value == value) {
				return dimension;
			}
		}
		return null;
	}
	
	public static Dimension byAddress(char address) {
		for(Dimension dimension : Dimension.values()) {
			if(dimension.address == address) {
				return dimension;
			}
		}
		return null;
	}
	
}

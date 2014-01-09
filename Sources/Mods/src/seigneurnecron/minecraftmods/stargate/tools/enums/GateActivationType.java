package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum GateActivationType {
	
	// Constants :
	
	FAILED(0, "Failed"),
	OUTPUT(3, "Output"),
	INPUT(4, "Input");
	
	// Fields :
	
	private int value;
	private String toString;
	
	// Constructors :
	
	private GateActivationType(int value, String toString) {
		this.value = value;
		this.toString = toString;
	}
	
	// Getters :
	
	public int getValue() {
		return this.value;
	}
	
	// Methods :
	
	@Override
	public String toString() {
		return this.toString;
	}
	
	public static GateActivationType valueOf(int value) {
		for(GateActivationType type : GateActivationType.values()) {
			if(type.value == value) {
				return type;
			}
		}
		return null;
	}
	
}

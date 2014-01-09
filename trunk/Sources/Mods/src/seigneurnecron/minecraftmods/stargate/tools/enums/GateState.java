package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum GateState {
	
	// Constants :
	
	BROKEN(0, "Broken"),
	OFF(1, "Off"),
	ACTIVATING(2, "Activating"),
	OUTPUT(3, "Output"),
	INPUT(4, "Input"),
	KAWOOSH(5, "Kawoosh");
	
	// Fields :
	
	private int value;
	private String toString;
	
	// Constructors :
	
	private GateState(int value, String toString) {
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
	
	public static GateState valueOf(int value) {
		for(GateState state : GateState.values()) {
			if(state.value == value) {
				return state;
			}
		}
		return null;
	}
	
}

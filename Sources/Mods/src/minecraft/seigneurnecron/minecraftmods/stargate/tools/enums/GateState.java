package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum GateState {
	
	BROKEN(0, "Broken"),
	OFF(1, "Off"),
	ACTIVATING(2, "Activating"),
	OUTPUT(3, "Output"),
	INPUT(4, "Input"),
	KAWOOSH(5, "Kawoosh");
	
	private int value;
	private String toString;
	
	private GateState(int value, String toString) {
		this.value = value;
		this.toString = toString;
	}
	
	public int getValue() {
		return this.value;
	}
	
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

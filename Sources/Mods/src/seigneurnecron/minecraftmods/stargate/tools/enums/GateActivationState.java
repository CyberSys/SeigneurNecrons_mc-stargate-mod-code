package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum GateActivationState {
	
	// Constants :
	
	E0(0, "E0"),
	E1(1, "E1"),
	E2(2, "E2"),
	E3(3, "E3"),
	E4(4, "E4"),
	E5(5, "E5"),
	E6(6, "E6"),
	E7(7, "E7"),
	E8(8, "E8"),
	E9(9, "E9");
	
	// Fields :
	
	private int value;
	private String toString;
	
	// Constructors :
	
	private GateActivationState(int value, String toString) {
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
	
	public static GateActivationState valueOf(int value) {
		for(GateActivationState state : GateActivationState.values()) {
			if(state.value == value) {
				return state;
			}
		}
		return null;
	}
	
}

package mods.stargate;

public enum GateActivationState {
	
	E0(0, "E0"), E1(1, "E1"), E2(2, "E2"), E3(3, "E3"), E4(4, "E4"), E5(5, "E5"), E6(6, "E6"), E7(7, "E7");
	
	private int value;
	private String toString;
	
	private GateActivationState(int value, String toString) {
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
	
	public static GateActivationState valueOf(int value) {
		for(GateActivationState status : GateActivationState.values()) {
			if(status.value == value) {
				return status;
			}
		}
		return null;
	}
	
}

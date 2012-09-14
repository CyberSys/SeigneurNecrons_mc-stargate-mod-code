package mods.necron.stargate;

public enum GateActivationType {
	
	FAILED(0, "Failed"), OUTPUT(3, "Output"), INPUT(4, "Input");
	
	private int value;
	private String toString;
	
	private GateActivationType(int value, String toString) {
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
	
	public static GateActivationType valueOf(int value) {
		for(GateActivationType status : GateActivationType.values()) {
			if(status.value == value) {
				return status;
			}
		}
		return null;
	}
	
}

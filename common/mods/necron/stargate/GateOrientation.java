package mods.necron.stargate;

public enum GateOrientation {
	
	X_AXIS(0, "Parallele a X"),
	Z_AXIS(1, "Parallele a Z"),
	ERROR(2, "Mal orientee");
	
	private int value;
	private String toString;
	
	private GateOrientation(int value, String toString) {
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
	
	public static GateOrientation valueOf(int value) {
		for(GateOrientation status : GateOrientation.values()) {
			if(status.value == value) {
				return status;
			}
		}
		return null;
	}
	
}

package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum GateOrientation {
	
	X_AXIS(0, "Parallel to X"),
	Z_AXIS(1, "Parallel to Z"),
	ERROR(2, "Incorrectly oriented");
	
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
		for(GateOrientation orientation : GateOrientation.values()) {
			if(orientation.value == value) {
				return orientation;
			}
		}
		return null;
	}
	
}

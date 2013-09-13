package seigneurnecron.minecraftmods.stargate.tools.enums;

/**
 * @author Seigneur Necron
 */
public enum GateActivationSequence {
	
	S7(7, "7 chevrons"),
	S8(8, "8 chevrons"),
	S9(9, "9 chevrons");
	
	private int value;
	private String toString;
	
	private GateActivationSequence(int value, String toString) {
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
	
	public static GateActivationSequence valueOf(int value) {
		for(GateActivationSequence sequence : GateActivationSequence.values()) {
			if(sequence.value == value) {
				return sequence;
			}
		}
		return null;
	}
	
}
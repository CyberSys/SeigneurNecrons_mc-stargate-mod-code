package seigneurnecron.minecraftmods.stargate.enums;

import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;

public enum GateKawooshState {
	
	K0(0, new int[] {}),
	K1(1, new int[] {}),
	K2(2, new int[] {}),
	K3(3, new int[] {}),
	K4(4, new int[] {}),
	K5(5, new int[] {}),
	K6(6, new int[] {}),
	K7(7, new int[] {1}),
	K8(8, new int[] {2, 1}),
	K9(9, new int[] {3, 2, 1}),
	K10(10, new int[] {4, 3, 2, 1}),
	K11(11, new int[] {4, 4, 3, 2, 1}),
	K12(12, new int[] {3, 4, 4, 3, 2, 1}),
	K13(13, new int[] {3, 4, 4, 4, 3, 2, 1}),
	K14(14, new int[] {3, 4, 4, 4, 4, 3, 2, 1}),
	K15(15, new int[] {3, 4, 4, 4, 4, 4, 3, 2}),
	K16(16, new int[] {3, 4, 4, 4, 4, 3, 2}),
	K17(17, new int[] {3, 4, 4, 4, 3, 2}),
	K18(18, new int[] {3, 4, 4, 3, 2}),
	K19(19, new int[] {4, 4, 3, 2}),
	K20(20, new int[] {4, 3, 2}),
	K21(21, new int[] {4, 3}),
	K22(22, new int[] {3}),
	K23(23, new int[] {});
	
	public static final int LAST_STEP = GateKawooshState.values().length - 1;
	public static final int FIRST_INSTABLE_STEP;
	
	static {
		int i = 0;
		int first = 0;
		boolean found = false;
		
		for(GateKawooshState state : GateKawooshState.values()) {
			state.value = i;
			
			if(!found) {
				if(state.size.length > 0) {
					found = true;
				}
				else {
					first++;
				}
			}
			
			i++;
		}
		
		FIRST_INSTABLE_STEP = first;
	}
	
	private int value;
	private int[] size;
	
	private GateKawooshState(int value, int[] size) {
		this.value = value;
		this.size = size;
		
		if(size.length > TileEntityStargateControl.MAX_KAWOOSH_LENGHT) {
			throw(new RuntimeException(new IllegalArgumentException("The size table of a kawoosh state must be beetwen 0 and " + TileEntityStargateControl.MAX_KAWOOSH_LENGHT)));
		}
		new RuntimeException("");
		for(int i : size) {
			if(i < 0 || i > TileEntityStargateControl.MAX_KAWOOSH_WIDTH) {
				throw(new RuntimeException(new IllegalArgumentException("The values in the size table of a kawoosh state must be beetwen 0 and " + TileEntityStargateControl.MAX_KAWOOSH_WIDTH)));
			}
		}
	}
	
	public int getValue() {
		return this.value;
	}
	
	public int getSize(int i) {
		if(i < 0 || i >= this.size.length) {
			return 0;
		}
		
		return this.size[i];
	}
	
	@Override
	public String toString() {
		return "Kawoosh step " + this.value;
	}
	
	public static GateKawooshState valueOf(int value) {
		for(GateKawooshState state : GateKawooshState.values()) {
			if(state.value == value) {
				return state;
			}
		}
		return null;
	}
	
}

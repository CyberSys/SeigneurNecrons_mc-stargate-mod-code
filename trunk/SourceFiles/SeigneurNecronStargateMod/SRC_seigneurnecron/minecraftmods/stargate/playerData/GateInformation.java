package seigneurnecron.minecraftmods.stargate.playerData;


public class GateInformation {
	
	public String name;
	public int code;
	
	public GateInformation() {
		this("", 0);
	}
	
	public GateInformation(String name, int code) {
		this.name = name;
		this.code = code;
	}
	
}

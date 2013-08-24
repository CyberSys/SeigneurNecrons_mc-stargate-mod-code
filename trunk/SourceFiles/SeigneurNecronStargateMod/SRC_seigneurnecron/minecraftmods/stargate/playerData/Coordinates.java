package seigneurnecron.minecraftmods.stargate.playerData;

public class Coordinates {
	
	public int x;
	public int y;
	public int z;
	public int dim;
	
	public Coordinates() {
		this(0, 0, 0, 0);
	}
	
	public Coordinates(int x, int y, int z, int dim) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim = dim;
	}
	
}

package mods.stargate;

public class TileEntityVortex extends TileEntityStargatePart {
	
	/**
	 * Retourne une repr�sentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityVortex[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate" + this.partOfGate + "]");
	}
	
}
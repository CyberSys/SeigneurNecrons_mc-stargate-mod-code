package mods.necron.stargate;

public class TileEntityVortex extends TileEntityStargatePart {
	
	/**
	 * Retourne une representation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityVortex[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate" + this.partOfGate + "]");
	}
	
}

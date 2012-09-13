package mods.stargate;

public class TileEntityNaquadaAlliage extends TileEntityStargatePart {
	
	/**
	 * Retourne une représentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityNaquadaAlliage[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate" + this.partOfGate + "]");
	}
	
}

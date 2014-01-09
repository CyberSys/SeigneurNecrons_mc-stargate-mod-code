package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockFastStargate3 extends BlockFastStargate2 {
	
	// Constructors :
	
	public BlockFastStargate3(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	protected int getMaxY() {
		return 14;
	}
	
	@Override
	protected int getWidth() {
		return 9;
	}
	
	@Override
	protected int getLenght() {
		return 10;
	}
	
	@Override
	protected int getDhdDistance() {
		return 6;
	}
	
	@Override
	protected boolean dhdLight() {
		return true;
	}
	
	@Override
	protected int getBlockId(int i, int j, int k) {
		int blockId = super.getBlockId(i, j, k);
		
		if(blockId == 0 && (i == this.getMaxY() || Math.abs(j) == this.getLenght() || Math.abs(k) == this.getWidth())) {
			return Block.stoneBrick.blockID;
		}
		
		return blockId;
	}
	
	@Override
	protected void addDecoration(World world, int xCoord, int yCoord, int zCoord, int side, int xAxis, int zAxis) {
		super.addDecoration(world, xCoord, yCoord, zCoord, side, xAxis, zAxis);
		
		int x = (xAxis * (this.getLenght() - 3)) + (zAxis * (this.getWidth() - 3));
		int z = (xAxis * (this.getWidth() - 3)) + (zAxis * (this.getLenght() - 3));
		
		int[] yCoords = {-1, this.getMaxY()};
		int[] xCoords = {x, -x};
		int[] zCoords = {z, -z};
		
		for(int i : yCoords) {
			int y = yCoord + i;
			
			if(y > 0 && y < 256) {
				for(int j : xCoords) {
					for(int k : zCoords) {
						x = xCoord + j;
						z = zCoord + k;
						world.setBlock(x, y, z, StargateMod.block_selfPoweredRedstoneLight.blockID);
					}
				}
			}
		}
	}
	
}

package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockFastStargate2 extends BlockFastStargate {
	
	public BlockFastStargate2(String name) {
		super(name);
	}
	
	protected int getMinY() {
		return -5;
	}
	
	protected int getMaxY() {
		return 12;
	}
	
	protected int getWidth() {
		return 3;
	}
	
	protected int getLenght() {
		return 9;
	}
	
	protected int getGlassMinY() {
		return -4;
	}
	
	protected int getGlassWidth() {
		return 2;
	}
	
	protected int getGlassLenght() {
		return 8;
	}
	
	protected int getDhdDistance() {
		return 3;
	}
	
	protected boolean dhdLight() {
		return false;
	}
	
	protected int getBlockId(int i, int j, int k) {
		if(i < 0) {
			if(i >= this.getGlassMinY() && Math.abs(j) <= this.getGlassLenght() && Math.abs(k) <= this.getGlassWidth()) {
				return Block.glass.blockID;
			}
			
			return Block.stoneBrick.blockID;
		}
		
		return 0;
	}
	
	@Override
	protected void addDecoration(World world, int xCoord, int yCoord, int zCoord, int side, int xAxis, int zAxis) {
		int x, y, z;
		
		for(int i = this.getMinY(); i <= this.getMaxY(); i++) {
			y = yCoord + i;
			for(int j = -this.getLenght(); j <= this.getLenght(); j++) {
				for(int k = -this.getWidth(); k <= this.getWidth(); k++) {
					x = xCoord + (xAxis * j) + (zAxis * k);
					z = zCoord + (xAxis * k) + (zAxis * j);
					world.setBlock(x, y, z, this.getBlockId(i, j, k));
				}
			}
		}
		
		x = xCoord + (zAxis * this.getDhdDistance());
		z = zCoord + (xAxis * this.getDhdDistance());
		
		y = yCoord + 1;
		world.setBlock(x, y, z, StargateMod.block_dhdPanel.blockID, side, 3);
		
		y--;
		world.setBlock(x, y, z, StargateMod.block_dhdBase.blockID, side, 3);
		
		if(this.dhdLight()) {
			y--;
			world.setBlock(x, y, z, Block.glass.blockID);
			
			y--;
			world.setBlock(x, y, z, StargateMod.block_selfPoweredRedstoneLight.blockID);
		}
	}
	
}

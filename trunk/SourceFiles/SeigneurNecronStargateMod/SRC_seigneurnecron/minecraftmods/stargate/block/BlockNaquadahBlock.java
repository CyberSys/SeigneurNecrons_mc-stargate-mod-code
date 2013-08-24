package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.world.World;

public class BlockNaquadahBlock extends BlockNaquadahMade {
	
	public BlockNaquadahBlock(String name) {
		super(name);
	}
	
	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
	
}

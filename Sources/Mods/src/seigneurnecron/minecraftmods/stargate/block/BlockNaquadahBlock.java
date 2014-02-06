package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.world.World;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class BlockNaquadahBlock extends BlockNaquadahMade {
	
	// Constructors :
	
	public BlockNaquadahBlock(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
	
}

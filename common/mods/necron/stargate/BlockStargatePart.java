package mods.necron.stargate;

import net.minecraft.src.Material;
import net.minecraft.src.World;

public abstract class BlockStargatePart extends BlockStargate {
	
	protected BlockStargatePart(int id, int textureIndex, Material material, String name) {
		super(id, textureIndex, material, name);
	}
	
	/**
	 * Retourne la TileEntityMasterChevron de la porte a laquelle apartient le block, si elle existe.
	 */
	protected TileEntityMasterChevron getMasterChevron(World world, int x, int y, int z) {
		return ((TileEntityStargatePart) world.getBlockTileEntity(x, y, z)).getMasterChevron();
	}
	
}

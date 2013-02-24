package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMasterChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;

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

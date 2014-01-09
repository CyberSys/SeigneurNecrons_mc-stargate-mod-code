package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;

/**
 * @author Seigneur Necron
 */
public abstract class BlockStargatePart extends BlockContainerStargate {
	
	// Constructors :
	
	protected BlockStargatePart(String name, Material material) {
		super(name, material);
	}
	
	// Methods :
	
	/**
	 * Returns the TileEntityStargateControl of the gate which this block belongs, if it exists.
	 */
	protected TileEntityStargateControl getControlUnit(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && tileEntity instanceof TileEntityStargatePart) {
			return ((TileEntityStargatePart) world.getBlockTileEntity(x, y, z)).getGateControlUnit();
		}
		
		return null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityStargatePart();
	}
	
}

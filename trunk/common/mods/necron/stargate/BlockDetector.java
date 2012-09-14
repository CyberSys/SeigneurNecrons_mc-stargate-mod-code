package mods.necron.stargate;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class BlockDetector extends BlockGui {
	
	public BlockDetector(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	/**
	 * Is this block powering the block on the specified side.
	 */
	@Override
	public boolean isPoweringTo(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		return side >= 2 && side <= 5 && tileEntity != null && tileEntity instanceof TileEntityDetector && ((TileEntityDetector) tileEntity).isProvidingPower();
	}
	
	/**
	 * Is this block indirectly powering the block on the specified side
	 */
	@Override
	public boolean isIndirectlyPoweringTo(World world, int x, int y, int z, int side) {
		return this.isPoweringTo(world, x, y, z, side);
	}
	
	/**
	 * Can this block provide power.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	/**
	 * Return true if the block is a normal, solid cube.
	 * This determines indirect power state, entity ejection from blocks, and a few others.
	 */
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}
	
	/**
	 * each class overrdies this to return a new tileEntity
	 */
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityDetector();
	}
	
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityDetector && ((TileEntityDetector) tileEntity).isProvidingPower()) {
			return this.blockIndexInTexture + 1;
		}
		return this.blockIndexInTexture;
	}
	
}

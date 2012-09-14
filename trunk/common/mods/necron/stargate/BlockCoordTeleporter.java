package mods.necron.stargate;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class BlockCoordTeleporter extends BlockCoord {
	
	public BlockCoordTeleporter(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	/**
	 * each class overrdies this to return a new tileEntity
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCoordTeleporter();
	}
	
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		int metadata = iBlockAccess.getBlockMetadata(x, y, z);
		if(side == metadata) {
			return this.blockIndexInTexture;
		}
		return StargateMod.naquadaAlliage.blockIndexInTexture;
	}
	
}

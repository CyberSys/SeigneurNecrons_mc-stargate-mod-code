package mods.necron.stargate;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class BlockPanelDhd extends BlockPanel {
	
	public BlockPanelDhd(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		
		if(!world.isRemote && world.getBlockId(x, y - 1, z) == StargateMod.dhdCoord.blockID) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y - 1, z);
			
			if(tileEntity != null && tileEntity instanceof TileEntityCoordDhd) {
				((TileEntityCoordDhd) tileEntity).activateGate();
			}
		}
		return true;
	}
	
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side == 1) {
			return this.blockIndexInTexture;
		}
		return StargateMod.naquadaAlliage.blockIndexInTexture;
	}
	
}

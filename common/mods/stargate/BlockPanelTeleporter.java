package mods.stargate;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class BlockPanelTeleporter extends BlockPanel {
	
	public BlockPanelTeleporter(int id, int textureIndex, String name) {
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
		
		if(!world.isRemote && world.getBlockId(x, y-1, z) == StargateMod.teleporterCoord.blockID) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y-1, z);
			
			if(tileEntity != null && tileEntity instanceof TileEntityCoordTeleporter) {
				((TileEntityCoordTeleporter) tileEntity).teleportPlayer(player);
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
			int blockIndexInTexture = this.blockIndexInTexture;
			
			int metadata = iBlockAccess.getBlockMetadata(x, y, z);
			if(metadata == 4) {
				blockIndexInTexture += 1;
			}
			else if(metadata == 2) {
				blockIndexInTexture += 2;
			}
			else if(metadata == 5) {
				blockIndexInTexture += 3;
			}
			
			TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y-1, z);
			if(tileEntity != null && tileEntity instanceof TileEntityCoordTeleporter && ((TileEntityCoordTeleporter) tileEntity).destinationValide()) {
				blockIndexInTexture += 16;
			}
			
			return blockIndexInTexture;
		}
		return StargateMod.naquadaAlliage.blockIndexInTexture;
	}
	
}

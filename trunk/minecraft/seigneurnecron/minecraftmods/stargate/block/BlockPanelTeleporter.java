package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordTeleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPanelTeleporter extends BlockPanel {
	
	public BlockPanelTeleporter(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(!world.isRemote && world.getBlockId(x, y - 1, z) == StargateMod.teleporterCoord.blockID) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y - 1, z);
			
			if(tileEntity != null && tileEntity instanceof TileEntityCoordTeleporter) {
				((TileEntityCoordTeleporter) tileEntity).teleportPlayer(player);
			}
		}
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
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
			
			TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y - 1, z);
			if(tileEntity != null && tileEntity instanceof TileEntityCoordTeleporter && ((TileEntityCoordTeleporter) tileEntity).destinationValide()) {
				blockIndexInTexture += 16;
			}
			
			return blockIndexInTexture;
		}
		return StargateMod.naquadahAlloy.blockIndexInTexture;
	}
	
}

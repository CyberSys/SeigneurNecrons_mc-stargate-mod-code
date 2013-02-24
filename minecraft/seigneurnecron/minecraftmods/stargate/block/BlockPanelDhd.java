package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordDhd;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPanelDhd extends BlockPanel {
	
	public BlockPanelDhd(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(!world.isRemote && world.getBlockId(x, y - 1, z) == StargateMod.dhdCoord.blockID) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y - 1, z);
			
			if(tileEntity != null && tileEntity instanceof TileEntityCoordDhd) {
				((TileEntityCoordDhd) tileEntity).activateGate();
			}
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side == 1) {
			return this.blockIndexInTexture;
		}
		return StargateMod.naquadahAlloy.blockIndexInTexture;
	}
	
}

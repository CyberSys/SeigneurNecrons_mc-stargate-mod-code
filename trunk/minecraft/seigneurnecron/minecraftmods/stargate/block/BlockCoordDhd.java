package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoordDhd extends BlockCoord {
	
	public BlockCoordDhd(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityCoordDhd();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		int metadata = iBlockAccess.getBlockMetadata(x, y, z);
		if(side == metadata) {
			TileEntityCoordDhd tileEntity = (TileEntityCoordDhd) iBlockAccess.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity.isLinkedToGate()) {
				return this.blockIndexInTexture + 1;
			}
			return this.blockIndexInTexture;
		}
		return StargateMod.naquadahAlloy.blockIndexInTexture;
	}
	
	@Override
	protected boolean guiOk(TileEntity tileEntity) {
		return(tileEntity instanceof TileEntityCoordDhd);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity) {
		return new GuiDhd((TileEntityCoordDhd) tileEntity);
	}
}

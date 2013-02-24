package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoordTeleporter extends BlockCoord {
	
	public BlockCoordTeleporter(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCoordTeleporter();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		int metadata = iBlockAccess.getBlockMetadata(x, y, z);
		if(side == metadata) {
			return this.blockIndexInTexture;
		}
		return StargateMod.naquadahAlloy.blockIndexInTexture;
	}
	
	@Override
	protected boolean guiOk(TileEntity tileEntity) {
		return(tileEntity instanceof TileEntityCoordTeleporter);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity) {
		return new GuiTeleporter((TileEntityCoordTeleporter) tileEntity);
	}
	
}

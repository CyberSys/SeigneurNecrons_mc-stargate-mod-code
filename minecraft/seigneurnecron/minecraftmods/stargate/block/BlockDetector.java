package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDetector extends BlockGuiScreen {
	
	public BlockDetector(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public boolean isProvidingStrongPower(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		return side >= 2 && side <= 5 && tileEntity != null && tileEntity instanceof TileEntityDetector && ((TileEntityDetector) tileEntity).isProvidingPower();
	}
	
	@Override
	public boolean isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return this.isProvidingStrongPower(world, x, y, z, side);
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityDetector();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityDetector && ((TileEntityDetector) tileEntity).isProvidingPower()) {
			return this.blockIndexInTexture + 1;
		}
		return this.blockIndexInTexture;
	}
	
	@Override
	protected boolean guiOk(TileEntity tileEntity) {
		return(tileEntity instanceof TileEntityDetector);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity) {
		return new GuiDetector((TileEntityDetector) tileEntity);
	}
	
}

package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.gui.GuiDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockDetector extends BlockGuiScreen {
	
	protected Icon blockActiveIcon;
	
	public BlockDetector(String name) {
		super(name);
	}
	
	@Override
	public int isProvidingStrongPower(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		return (side >= 2 && side <= 5 && tileEntity != null && tileEntity instanceof TileEntityDetector && ((TileEntityDetector) tileEntity).isProvidingPower()) ? 15 : 0;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
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
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.blockActiveIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_detector + "_active");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && tileEntity instanceof TileEntityDetector && ((TileEntityDetector) tileEntity).isProvidingPower()) {
			return this.blockActiveIcon;
		}
		
		return this.blockIcon;
	}
	
	@Override
	protected boolean tileEntityOk(TileEntity tileEntity) {
		return tileEntity instanceof TileEntityDetector;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntity tileEntity, EntityPlayer player) {
		return new GuiDetector((TileEntityDetector) tileEntity);
	}
	
}

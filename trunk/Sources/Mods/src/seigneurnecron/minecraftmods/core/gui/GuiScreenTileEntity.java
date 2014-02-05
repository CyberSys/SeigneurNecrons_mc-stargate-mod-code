package seigneurnecron.minecraftmods.core.gui;

import seigneurnecron.minecraftmods.core.tileentity.TileEntityBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiScreenTileEntity<T extends TileEntityBasic> extends GuiScreenBasic {
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected T tileEntity;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	protected GuiScreenTileEntity(T tileEntity) {
		super();
		this.tileEntity = tileEntity;
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected boolean isGuiValid() {
		return super.isGuiValid() && (this.tileEntity == this.tileEntity.worldObj.getBlockTileEntity(this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord));
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		this.tileEntity.update();
	}
	
}

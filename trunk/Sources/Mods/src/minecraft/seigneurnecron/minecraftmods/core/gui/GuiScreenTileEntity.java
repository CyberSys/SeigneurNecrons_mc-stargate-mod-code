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
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.screenBasic";
	
	public static final String ENTER = INV_NAME + ".enter";
	public static final String TAB = INV_NAME + ".tab";
	public static final String ESC = INV_NAME + ".esc";
	
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

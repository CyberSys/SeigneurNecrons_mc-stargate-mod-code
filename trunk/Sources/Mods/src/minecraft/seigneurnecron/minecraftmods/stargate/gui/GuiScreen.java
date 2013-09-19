package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen.INV_NAME;
import seigneurnecron.minecraftmods.core.gui.Screen;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiScreen<T extends TileEntityGuiScreen> extends Screen {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
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
	
	protected GuiScreen(T tileEntity) {
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
		this.tileEntity.setEditable(true);
	}
	
}

package seigneurnecron.minecraftmods.stargate.client.gui;

import seigneurnecron.minecraftmods.stargate.client.gui.tools.Screen;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiScreen<T extends TileEntityGuiScreen> extends Screen {
	
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

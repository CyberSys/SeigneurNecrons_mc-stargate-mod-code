package seigneurnecron.minecraftmods.stargate.gui;

import seigneurnecron.minecraftmods.core.gui.GuiContainerOneLine;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsolePanel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiContainerConsolePanel<T extends ContainerConsolePanel<?>> extends GuiContainerOneLine<T> {
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	protected GuiContainerConsolePanel(T container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected boolean isGuiValid() {
		return super.isGuiValid() && this.container.inventory.tileEntity.isIntact() && this.container.inventory.console.isValid();
	}
	
}

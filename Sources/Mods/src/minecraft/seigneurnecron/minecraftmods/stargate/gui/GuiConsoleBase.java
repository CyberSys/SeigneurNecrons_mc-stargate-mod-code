package seigneurnecron.minecraftmods.stargate.gui;

import net.minecraft.entity.player.InventoryPlayer;
import seigneurnecron.minecraftmods.core.gui.GuiContainerOneLine;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiConsoleBase extends GuiContainerOneLine<ContainerConsoleBase> {
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiConsoleBase(InventoryPlayer inventoryPlayer, InventoryConsoleBase inventory) {
		super(new ContainerConsoleBase(inventoryPlayer, inventory));
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	// FIXME - implementer cette interface.
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		// TODO - dessiner les panels.
	}
	
	@Override
	protected void initComponents() {
		// TODO - initialiser les composants.
	}
	
}

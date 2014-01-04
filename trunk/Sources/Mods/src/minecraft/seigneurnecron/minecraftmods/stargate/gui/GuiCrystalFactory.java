package seigneurnecron.minecraftmods.stargate.gui;

import net.minecraft.entity.player.InventoryPlayer;
import seigneurnecron.minecraftmods.core.gui.GuiContainerOneLine;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryCrystalFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiCrystalFactory extends GuiContainerOneLine<ContainerCrystalFactory> /*implements ListProviderGui<SoulCount>*/ {
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiCrystalFactory(InventoryPlayer inventoryPlayer, InventoryCrystalFactory inventory) {
		super(new ContainerCrystalFactory(inventoryPlayer, inventory));
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

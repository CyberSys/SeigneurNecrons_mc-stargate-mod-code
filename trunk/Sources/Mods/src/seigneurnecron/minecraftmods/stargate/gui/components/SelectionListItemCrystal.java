package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BLUE;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;

/**
 * @author Seigneur Necron
 */
public class SelectionListItemCrystal extends SelectionListInventory<ItemCrystal> {
	
	// Constructors :
	
	public SelectionListItemCrystal(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<ItemCrystal> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
	}
	
	// Methods :
	
	@Override
	protected String getName(ItemCrystal element) {
		return element.getStatName();
	}
	
	@Override
	protected String getInfo(ItemCrystal element) {
		return element.shortcut;
	}
	
	@Override
	protected int getInfoColor(ItemCrystal element) {
		return BLUE;
	}
	
}

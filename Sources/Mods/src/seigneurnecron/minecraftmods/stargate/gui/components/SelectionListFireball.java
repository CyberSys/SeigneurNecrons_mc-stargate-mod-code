package seigneurnecron.minecraftmods.stargate.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;

/**
 * @author Seigneur Necron
 */
public class SelectionListFireball extends SelectionListInventory<Item> {
	
	// Constructors :
	
	public SelectionListFireball(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<Item> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
	}
	
	// Methods :
	
	@Override
	protected String getName(Item element) {
		return element.getStatName();
	}
	
	@Override
	protected String getInfo(Item element) {
		return "-";
	}
	
}

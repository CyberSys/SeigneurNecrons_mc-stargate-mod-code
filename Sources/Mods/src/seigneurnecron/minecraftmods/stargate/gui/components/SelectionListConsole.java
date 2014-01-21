package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BLUE;

import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;

import com.google.common.collect.Multiset;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListConsole extends SelectionListInventory<Entry<Multiset<ItemCrystal>, Class<? extends Console>>> {
	
	// Constructors :
	
	public SelectionListConsole(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<Entry<Multiset<ItemCrystal>, Class<? extends Console>>> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
	}
	
	// Methods :
	
	@Override
	protected String getName(Entry<Multiset<ItemCrystal>, Class<? extends Console>> element) {
		return Console.getTranslatedConsoleName(element.getValue());
	}
	
	@Override
	protected String getInfo(Entry<Multiset<ItemCrystal>, Class<? extends Console>> element) {
		StringBuilder builder = new StringBuilder();
		
		for(ItemCrystal crystal : element.getKey()) {
			builder.append(crystal.shortcut);
			builder.append("  ");
		}
		
		return builder.toString();
	}
	
	@Override
	protected int getInfoColor(Entry<Multiset<ItemCrystal>, Class<? extends Console>> element) {
		return BLUE;
	}
	
}

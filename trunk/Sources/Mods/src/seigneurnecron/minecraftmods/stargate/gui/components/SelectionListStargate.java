package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.ORANGE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PURPLE;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.address.MalformedGateAddressException;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListStargate extends SelectionListInventory<Stargate> {
	
	// Constructors :
	
	public SelectionListStargate(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<Stargate> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
	}
	
	// Methods :
	
	@Override
	protected String getName(Stargate stargate) {
		return stargate.name;
	}
	
	@Override
	protected String getInfo(Stargate stargate) {
		return stargate.address;
	}
	
	@Override
	protected int getInfoColor(Stargate stargate) {
		try {
			int dim = GateAddress.toCoordinates(stargate.address).dim;
			return (dim == Dimension.EARTH.getValue()) ? BLUE : (dim == Dimension.HELL.getValue()) ? ORANGE : (dim == Dimension.END.getValue()) ? PURPLE : GRAY;
		}
		catch(MalformedGateAddressException argh) {
			return GRAY;
		}
	}
	
}

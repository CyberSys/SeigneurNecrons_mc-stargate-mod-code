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
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListTeleporter extends SelectionListInventory<Teleporter> {
	
	// Constructors :
	
	public SelectionListTeleporter(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<Teleporter> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
	}
	
	// Methods :
	
	@Override
	protected String getName(Teleporter teleporter) {
		return teleporter.name;
	}
	
	@Override
	protected String getInfo(Teleporter teleporter) {
		return teleporter.toString();
	}
	
	@Override
	protected int getInfoColor(Teleporter teleporter) {
		int dim = teleporter.dim;
		return (dim == Dimension.EARTH.getValue()) ? BLUE : (dim == Dimension.HELL.getValue()) ? ORANGE : (dim == Dimension.END.getValue()) ? PURPLE : GRAY;
	}
	
}

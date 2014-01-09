package seigneurnecron.minecraftmods.stargate.gui.components;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListTeleporter extends SelectionListInventory<Teleporter> {
	
	// Constructors :
	
	public SelectionListTeleporter(ListProviderGui<Teleporter> gui, int xPos, int yPos, int width, int height, EntityPlayer player) {
		super(gui, xPos, yPos, width, height, player);
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
	
}

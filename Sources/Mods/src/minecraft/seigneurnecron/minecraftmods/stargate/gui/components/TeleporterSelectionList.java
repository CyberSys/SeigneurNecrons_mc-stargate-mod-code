package seigneurnecron.minecraftmods.stargate.gui.components;

import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.SelectionList;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class TeleporterSelectionList extends SelectionList<Teleporter> {
	
	public TeleporterSelectionList(ListProviderGui<Teleporter> gui, int xPos, int yPos, int width, int height) {
		super(gui, xPos, yPos, width, height);
	}
	
	@Override
	protected String getName(Teleporter teleporter) {
		return teleporter.name;
	}
	
	@Override
	protected String getInfo(Teleporter teleporter) {
		return teleporter.toString();
	}
	
}

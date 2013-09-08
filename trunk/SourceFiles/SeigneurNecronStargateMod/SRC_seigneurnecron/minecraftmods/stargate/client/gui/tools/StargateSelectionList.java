package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateSelectionList extends SelectionList<Stargate> {
	
	public StargateSelectionList(ListProviderGui<Stargate> gui, int xPos, int yPos, int width, int height) {
		super(gui, xPos, yPos, width, height);
	}
	
	@Override
	protected String getName(Stargate stargate) {
		return stargate.name;
	}
	
	@Override
	protected String getInfo(Stargate stargate) {
		return stargate.address;
	}
	
}

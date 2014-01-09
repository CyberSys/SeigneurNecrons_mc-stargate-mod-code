package seigneurnecron.minecraftmods.stargate.gui.components;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListStargate extends SelectionListInventory<Stargate> {
	
	// Constructors :
	
	public SelectionListStargate(ListProviderGui<Stargate> gui, int xPos, int yPos, int width, int height, EntityPlayer player) {
		super(gui, xPos, yPos, width, height, player);
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
	
}

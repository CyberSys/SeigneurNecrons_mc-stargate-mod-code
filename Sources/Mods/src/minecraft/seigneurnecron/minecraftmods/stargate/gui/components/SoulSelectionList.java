package seigneurnecron.minecraftmods.stargate.gui.components;

import net.minecraft.entity.EntityList;
import net.minecraft.util.StatCollector;
import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.SelectionList;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SoulSelectionList extends SelectionList<SoulCount> {
	
	public SoulSelectionList(ListProviderGui<SoulCount> gui, int xPos, int yPos, int width, int height) {
		super(gui, xPos, yPos, width, height);
	}
	
	@Override
	protected String getName(SoulCount soulCount) {
		return StatCollector.translateToLocal("entity." + EntityList.getStringFromID(soulCount.id) + ".name");
	}
	
	@Override
	protected String getInfo(SoulCount soulCount) {
		return String.valueOf(soulCount.count);
	}
}

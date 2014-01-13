package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProviderSelectTwoLines<T extends Object> extends ListProviderSelect<T> {
	
	public FontRenderer getFirstFontRenderer();
	
	public FontRenderer getSecondFontRenderer();
	
}

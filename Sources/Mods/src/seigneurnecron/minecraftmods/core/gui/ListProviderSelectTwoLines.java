package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProviderSelectTwoLines<T extends Object> extends ListProviderSelect<T> {
	
	public FontRenderer getFirstFontRenderer();
	
	public FontRenderer getSecondFontRenderer();
	
}

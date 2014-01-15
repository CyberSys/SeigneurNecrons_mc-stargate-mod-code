package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProviderText extends ListProvider<String> {
	
	public FontRenderer getFontRenderer();
	
}

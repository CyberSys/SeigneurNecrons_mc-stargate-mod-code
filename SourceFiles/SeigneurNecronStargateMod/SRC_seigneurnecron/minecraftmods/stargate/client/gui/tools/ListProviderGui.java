package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProviderGui<T extends Object> {
	
	public List<T> getList();
	
	public int getSelectedIndex();
	
	public void setSelectedIndex(int index);
	
	public void onElementDoubleClicked();
	
	public FontRenderer getFirstFontRenderer();
	
	public FontRenderer getSecondFontRenderer();
	
}

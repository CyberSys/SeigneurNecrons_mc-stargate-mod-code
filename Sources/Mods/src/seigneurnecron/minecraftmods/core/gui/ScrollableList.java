package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class ScrollableList<T extends ListProvider<? extends Object>> extends ScrollableAbstractList {
	
	// Fields :
	
	protected final T listProvider;
	
	// Constructors :
	
	protected ScrollableList(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, T listProvider) {
		super(parent, xPos, yPos, width, height, minecraft);
		this.listProvider = listProvider;
	}
	
	// Methods :
	
	@Override
	public int getSize() {
		return this.listProvider.getList().size();
	}
	
}

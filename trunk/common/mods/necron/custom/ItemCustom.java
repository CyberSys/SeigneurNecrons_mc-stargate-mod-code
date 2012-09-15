package mods.necron.custom;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemCustom extends Item {
	
	public ItemCustom(int id, int iconIdex, String name, CreativeTabs tab) {
		super(id);
		this.setIconIndex(iconIdex);
		this.setItemName(name);
		this.setTabToDisplayOn(tab);
	}
	
	public ItemCustom(int id, int iconIdex, String name) {
		this(id, iconIdex, name, CreativeTabs.tabMaterials);
	}
	
	/**
	 * Grabs the current texture file used for this block.
	 */
	@Override
	public String getTextureFile() {
		return CustomMod.itemTextureFile;
	}
	
}

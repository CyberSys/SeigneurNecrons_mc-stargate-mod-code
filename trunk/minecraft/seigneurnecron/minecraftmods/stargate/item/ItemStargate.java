package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import seigneurnecron.minecraftmods.stargate.StargateMod;

public class ItemStargate extends Item {
	
	public ItemStargate(int id, int iconIdex, String name, CreativeTabs tab) {
		super(id);
		this.setIconIndex(iconIdex);
		this.setItemName(name);
		this.setCreativeTab(tab);
	}
	
	public ItemStargate(int id, int iconIdex, String name) {
		this(id, iconIdex, name, CreativeTabs.tabMaterials);
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.itemTextureFile;
	}
	
}

package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNaquadahShovel extends ItemSpade {
	
	public ItemNaquadahShovel(int id, int iconIdex, String name) {
		super(id, StargateMod.naquadahToolMaterial);
		this.setIconIndex(iconIdex);
		this.setItemName(name);
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.itemTextureFile;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}

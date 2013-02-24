package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNaquadahBoots extends ItemArmor {
	
	public ItemNaquadahBoots(int id, int iconIdex, String name) {
		super(id, StargateMod.naquadahArmorMaterial, 3, 3);
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

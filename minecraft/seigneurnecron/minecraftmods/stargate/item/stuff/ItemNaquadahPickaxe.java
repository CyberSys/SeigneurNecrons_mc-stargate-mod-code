package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNaquadahPickaxe extends ItemPickaxe {
	
	public ItemNaquadahPickaxe(int id, int iconIdex, String name) {
		super(id, StargateMod.naquadahToolMaterial);
		this.setIconIndex(iconIdex);
		this.setItemName(name);
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.itemTextureFile;
	}
	
	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {
		return block.equals(Block.obsidian) ? 100.0F : super.getStrVsBlock(itemStack, block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}

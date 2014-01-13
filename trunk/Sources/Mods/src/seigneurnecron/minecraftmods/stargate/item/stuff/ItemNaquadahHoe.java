package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemNaquadahHoe extends ItemHoe {
	
	// Constructors :
	
	public ItemNaquadahHoe(String name) {
		super(StargateMod.instance.getConfig().getItemId(name), StargateMod.naquadahToolMaterial);
		this.setUnlocalizedName(name);
		this.setTextureName(name);
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getIconString());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}

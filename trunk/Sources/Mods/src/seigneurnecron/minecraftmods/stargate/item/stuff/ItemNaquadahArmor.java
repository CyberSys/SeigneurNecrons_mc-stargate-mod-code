package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class ItemNaquadahArmor extends ItemArmor {
	
	// Constructors :
	
	public ItemNaquadahArmor(String name, int renderId, int armorType) {
		super(StargateMod.instance.getConfig().getItemId(name), StargateMod.naquadahArmorMaterial, renderId, armorType);
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
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		if(layer == 1) {
			return StargateMod.instance.getAssetPrefix() + "textures/models/armor/naquadah_layer_1.png";
		}
		else if(layer == 2) {
			return StargateMod.instance.getAssetPrefix() + "textures/models/armor/naquadah_layer_2.png";
		}
		
		return null;
	}
	
}

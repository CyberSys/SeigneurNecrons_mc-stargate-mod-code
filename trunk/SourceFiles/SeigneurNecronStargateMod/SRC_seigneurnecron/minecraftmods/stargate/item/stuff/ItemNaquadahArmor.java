package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class ItemNaquadahArmor extends ItemArmor {
	
	public static final String ARMOR_TEXTURE_FILE = StargateMod.ASSETS_PREFIX + "textures/models/armor/naquadah_layer_1.png";
	public static final String LEGINGS_TEXTURE_FILE = StargateMod.ASSETS_PREFIX + "textures/models/armor/naquadah_layer_2.png";
	
	public ItemNaquadahArmor(String name, int renderId, int armorType) {
		super(StargateModConfig.getItemId(name), StargateMod.naquadahArmorMaterial, renderId, armorType);
		this.setUnlocalizedName(name);
		this.func_111206_d(name); // setIconName(name)
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111208_A()); // getIconName()
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		if(layer == 1) {
			return ARMOR_TEXTURE_FILE;
		}
		else if(layer == 2) {
			return LEGINGS_TEXTURE_FILE;
		}
		
		return null;
	}
	
}

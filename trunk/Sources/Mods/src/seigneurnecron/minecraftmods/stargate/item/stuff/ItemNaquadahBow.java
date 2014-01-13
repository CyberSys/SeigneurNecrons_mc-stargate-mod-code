package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemNaquadahBow extends ItemBow {
	
	// Fields :
	
	private Icon[] bowIcons = new Icon[3];
	
	// Constructors :
	
	public ItemNaquadahBow(String name) {
		super(StargateMod.instance.getConfig().getItemId(name));
		this.setUnlocalizedName(name);
		this.setTextureName(name);
		this.setMaxDamage(0);
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getIconString());
		
		for(int i = 0; i < this.bowIcons.length; ++i) {
			this.bowIcons[i] = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getIconString() + "_pulling" + i);
		}
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if(player.getItemInUse() != null) {
			int Pulling = stack.getMaxItemUseDuration() - useRemaining;
			
			if(Pulling >= 18) {
				return this.bowIcons[2];
			}
			else if(Pulling > 13) {
				return this.bowIcons[1];
			}
			else if(Pulling > 0) {
				return this.bowIcons[0];
			}
		}
		
		return this.itemIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}

package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemNaquadahBow extends ItemBow {
	
	private Icon[] bowIcons = new Icon[3];
	
	public ItemNaquadahBow(String name) {
		super(StargateModConfig.getItemId(name));
		this.setUnlocalizedName(name);
		this.func_111206_d(name); // setIconName(name)
		this.setMaxDamage(0);
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111208_A()); // getIconName()
		
		for(int i = 0; i < this.bowIcons.length; ++i) {
			this.bowIcons[i] = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111208_A() + "_pulling" + i);
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

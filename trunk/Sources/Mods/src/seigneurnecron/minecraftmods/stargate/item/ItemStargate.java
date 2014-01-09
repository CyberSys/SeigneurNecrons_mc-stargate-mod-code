package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemStargate extends Item {
	
	// Constructors :
	
	public ItemStargate(String name) {
		super(StargateMod.instance.getConfig().getItemId(name));
		this.setUnlocalizedName(name);
		this.func_111206_d(name); // setIconName(name)
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.func_111208_A()); // getIconName()
	}
	
}

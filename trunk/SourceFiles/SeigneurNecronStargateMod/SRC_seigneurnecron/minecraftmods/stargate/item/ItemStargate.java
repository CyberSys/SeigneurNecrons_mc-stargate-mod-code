package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemStargate extends Item {
	
	public ItemStargate(String name) {
		super(StargateModConfig.getItemId(name));
		this.setUnlocalizedName(name);
		this.func_111206_d(name); // setIconName(name)
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111208_A()); // getIconName()
	}
	
}

package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShieldRemote extends ItemStargate {
	
	public ItemShieldRemote(String name) {
		super(name);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if(!world.isRemote) {
			// FIXME - ouvrir une interface, uniquement s'il y a une porte a proximite.
			// FIXME - dans l'interface, indiquer si le bouclier de la porte d'arrivee est actif.
			// FIXME - dans le champ, mettre comme valeur par defaut : 0;
		}
		
		return itemStack;
	}
	
}

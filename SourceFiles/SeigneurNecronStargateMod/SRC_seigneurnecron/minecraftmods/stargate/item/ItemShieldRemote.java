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
			// FIXME - ouvrir une interface si (il y a une porte a proximite) && ((on ne connait pas deja le code) || (le code n'est plus valide) || (shift click)).
			// FIXME - dans l'interface, indiquer si le bouclier de la porte d'arrivee est actif et si le code est valide.
			// FIXME - dans le champ, mettre comme valeur par defaut : 0;
		}
		
		return itemStack;
	}
	
}

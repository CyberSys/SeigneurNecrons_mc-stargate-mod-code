package seigneurnecron.minecraftmods.stargate.item.fireball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballExplosiveStable;

/**
 * @author Seigneur Necron
 */
public class ItemFireballExplosiveStable extends ItemFireballBasic {
	
	// Constructors :
	
	public ItemFireballExplosiveStable(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityFireballExplosiveStable(world, entityPlayer);
	}
	
}

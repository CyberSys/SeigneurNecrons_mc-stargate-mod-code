package seigneurnecron.minecraftmods.stargate.item.fireball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNapalm;

/**
 * @author Seigneur Necron
 */
public class ItemFireballNapalm extends ItemFireballBasic {
	
	// Constructors :
	
	public ItemFireballNapalm(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityFireballNapalm(world, entityPlayer);
	}
	
}

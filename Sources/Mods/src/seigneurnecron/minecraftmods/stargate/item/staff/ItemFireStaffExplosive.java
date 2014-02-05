package seigneurnecron.minecraftmods.stargate.item.staff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballExplosive;

/**
 * @author Seigneur Necron
 */
public class ItemFireStaffExplosive extends ItemFireStaffBasic {
	
	// Constructors :
	
	public ItemFireStaffExplosive(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityFireballExplosive(world, entityPlayer);
	}
	
}

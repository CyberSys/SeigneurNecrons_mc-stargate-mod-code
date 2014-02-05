package seigneurnecron.minecraftmods.stargate.item.staff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNuke;

/**
 * @author Seigneur Necron
 */
public class ItemFireStaffNuke extends ItemFireStaffBasic {
	
	// Constructors :
	
	public ItemFireStaffNuke(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityFireballNuke(world, entityPlayer);
	}
	
}

package seigneurnecron.minecraftmods.stargate.item.staff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;

public class ItemExplosiveFireStaff extends ItemFireStaff {
	
	public ItemExplosiveFireStaff(String name) {
		super(name);
	}
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityCustomExplosiveFireBall(world, entityPlayer);
	}
	
}

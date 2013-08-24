package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;

public class ItemCustomExplosiveFireBall extends ItemCustomFireBall {
	
	public ItemCustomExplosiveFireBall(String name) {
		super(name);
	}
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityCustomExplosiveFireBall(world, entityPlayer);
	}
	
}

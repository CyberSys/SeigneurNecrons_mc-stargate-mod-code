package seigneurnecron.minecraftmods.stargate.dispenserBehavior;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;

public class DispenserBehaviorCustomExplosiveFireBall extends DispenserBehaviorCustomFireBall {
	
	@Override
	protected Entity getProjectile(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		return new EntityCustomExplosiveFireBall(world, x, y, z, motionX, motionY, motionZ);
	}
	
}

package seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;

/**
 * @author Seigneur Necron
 */
public class DispenserBehaviorCustomExplosiveFireBall extends DispenserBehaviorCustomFireBall {
	
	// Methods :
	
	@Override
	protected Entity getProjectile(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		return new EntityCustomExplosiveFireBall(world, x, y, z, motionX, motionY, motionZ);
	}
	
}

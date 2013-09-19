package seigneurnecron.minecraftmods.stargate.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * @author Seigneur Necron
 */
public class EntityNuke extends EntityCustomExplosiveFireBall {
	
	public EntityNuke(World world) {
		super(world);
	}
	
	public EntityNuke(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityNuke(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	@Override
	protected float getSize() {
		return 1F;
	}
	
	@Override
	protected float exlosionSize() {
		return 10;
	}
	
}

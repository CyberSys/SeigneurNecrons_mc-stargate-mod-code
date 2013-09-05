package seigneurnecron.minecraftmods.stargate.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;

public class EntityCustomExplosiveFireBall extends EntityCustomFireBall {
	
	public EntityCustomExplosiveFireBall(World world) {
		super(world);
	}
	
	public EntityCustomExplosiveFireBall(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityCustomExplosiveFireBall(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	@Override
	protected final void onImpactWithEntity(Entity entity) {
		super.onImpactWithEntity(entity);
		this.createExplosion(entity.posX, entity.posY, entity.posZ);
	}
	
	@Override
	protected final void onImpactWithBlock(int x, int y, int z) {
		this.createExplosion(x + 0.5, y + 0.5, z + 0.5);
	}
	
	protected final void createExplosion(double x, double y, double z) {
		this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.exlosionSize(), this.isFlaming(), StargateModConfig.canExplosiveFireBallsDestroyBlocks && this.destroyBlocks());
	}
	
	protected float exlosionSize() {
		return 2;
	}
	
	protected boolean isFlaming() {
		return true;
	}
	
	protected boolean destroyBlocks() {
		return true;
	}
	
}

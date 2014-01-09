package seigneurnecron.minecraftmods.stargate.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * @author Seigneur Necron
 */
public class EntityNapalm extends EntityNuke {
	
	// Constructors :
	
	public EntityNapalm(World world) {
		super(world);
	}
	
	public EntityNapalm(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityNapalm(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	// Methods :
	
	@Override
	protected boolean destroyBlocks() {
		return false;
	}
	
}

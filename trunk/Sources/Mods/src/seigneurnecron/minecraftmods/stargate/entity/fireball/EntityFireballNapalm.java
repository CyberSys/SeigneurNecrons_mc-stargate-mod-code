package seigneurnecron.minecraftmods.stargate.entity.fireball;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * @author Seigneur Necron
 */
public class EntityFireballNapalm extends EntityFireballNuke {
	
	// Constructors :
	
	public EntityFireballNapalm(World world) {
		super(world);
	}
	
	public EntityFireballNapalm(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityFireballNapalm(World world, IBlockSource iBlockSource) {
		super(world, iBlockSource);
	}
	
	public EntityFireballNapalm(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	// Methods :
	
	@Override
	protected boolean destroyBlocks() {
		return false;
	}
	
}

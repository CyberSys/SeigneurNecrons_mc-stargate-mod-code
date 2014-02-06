package seigneurnecron.minecraftmods.stargate.entity.fireball;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class EntityFireballExplosiveStable extends EntityFireballExplosive {
	
	// Constructors :
	
	public EntityFireballExplosiveStable(World world) {
		super(world);
	}
	
	public EntityFireballExplosiveStable(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityFireballExplosiveStable(World world, IBlockSource iBlockSource) {
		super(world, iBlockSource);
	}
	
	public EntityFireballExplosiveStable(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	// Methods :
	
	@Override
	protected double getDispenserRandomFactor() {
		return 0.0;
	}
	
	@Override
	protected boolean canTakeKnowback() {
		return false;
	}
	
}

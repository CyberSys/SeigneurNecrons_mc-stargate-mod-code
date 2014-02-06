package seigneurnecron.minecraftmods.stargate.entity.fireball;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class EntityFireballNuke extends EntityFireballExplosive {
	
	// Constructors :
	
	public EntityFireballNuke(World world) {
		super(world);
	}
	
	public EntityFireballNuke(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityFireballNuke(World world, IBlockSource iBlockSource) {
		super(world, iBlockSource);
	}
	
	public EntityFireballNuke(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	// Methods :
	
	@Override
	protected float getSize() {
		return 1F;
	}
	
	@Override
	protected float exlosionSize() {
		return 10;
	}
	
}

package seigneurnecron.minecraftmods.stargate.entity.fireball;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet28EntityVelocity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.mod.ModBase;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class EntityFireBallBoosted extends EntityFireballBasic {
	
	// Constructors :
	
	public EntityFireBallBoosted(World world) {
		super(world);
	}
	
	public EntityFireBallBoosted(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityFireBallBoosted(World world, IBlockSource iBlockSource) {
		super(world, iBlockSource);
	}
	
	public EntityFireBallBoosted(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
		
		double factor = 0.4;
		entityLivingBase.addVelocity(-this.motionX * factor, -this.motionY * factor, -this.motionZ * factor);
		
		if(entityLivingBase.motionY > -0.5) {
			entityLivingBase.fallDistance = 0;
		}
		
		ModBase.sendPacketToAllPlayers(new Packet28EntityVelocity(entityLivingBase));
	}
	
	// Methods :
	
	@Override
	protected double getDispenserRandomFactor() {
		return 0.01;
	}
	
	@Override
	protected double getPlayerRandomFactor() {
		return 0.01;
	}
	
	@Override
	protected double getSpeedFactor() {
		return 1.0;
	}
	
}

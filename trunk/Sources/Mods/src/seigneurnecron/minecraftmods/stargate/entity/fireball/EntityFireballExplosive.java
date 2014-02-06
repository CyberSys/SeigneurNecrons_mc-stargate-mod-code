package seigneurnecron.minecraftmods.stargate.entity.fireball;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class EntityFireballExplosive extends EntityFireballBasic {
	
	// Constructors :
	
	public EntityFireballExplosive(World world) {
		super(world);
	}
	
	public EntityFireballExplosive(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	
	public EntityFireballExplosive(World world, IBlockSource iBlockSource) {
		super(world, iBlockSource);
	}
	
	public EntityFireballExplosive(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	
	// Methods :
	
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
		this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.exlosionSize(), this.isFlaming(), StargateMod.instance.getConfig().canExplosiveFireBallsDestroyBlocks && this.destroyBlocks());
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

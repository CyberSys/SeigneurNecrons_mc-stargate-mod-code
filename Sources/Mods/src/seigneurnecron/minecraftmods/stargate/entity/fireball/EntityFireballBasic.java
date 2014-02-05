package seigneurnecron.minecraftmods.stargate.entity.fireball;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomDamageSourceIndirect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class EntityFireballBasic extends Entity {
	
	// NBTTags names :
	
	private static final String X_TILE = "xTile";
	private static final String Y_TILE = "yTile";
	private static final String Z_TILE = "zTile";
	private static final String IN_TILE = "inTile";
	private static final String IN_GROUND = "inGround";
	private static final String DIRECTION = "direction";
	private static final String FIXED_DIRECTION = "fixedDirection";
	
	// Constants :
	
	private static final float ENTITY_PLAYER_OFFSET = 1.62F;
	private static final float MULTIPLIER_1 = 0.4F;
	
	// Fields :
	
	protected int xTile = -1;
	protected int yTile = -1;
	protected int zTile = -1;
	protected int inTile = 0;
	protected boolean inGround = false;
	protected int ticksAlive;
	protected int ticksInAir = 0;
	
	public EntityLivingBase shootingEntity;
	
	protected double fixedMotionX = 0;
	protected double fixedMotionY = 0;
	protected double fixedMotionZ = 0;
	
	// Constructors :
	
	public EntityFireballBasic(World world) {
		super(world);
		this.setSize(this.getSize(), this.getSize());
	}
	
	public EntityFireballBasic(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		this(world);
		this.initPositionAndMotion(x, y, z, motionX, motionY, motionZ, this.rotationYaw, this.rotationPitch);
	}
	
	public EntityFireballBasic(World world, IBlockSource iBlockSource) {
		this(world);
		
		EnumFacing enumfacing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
		IPosition iposition = BlockDispenser.getIPositionFromBlockSource(iBlockSource);
		
		double x = iposition.getX() + enumfacing.getFrontOffsetX() * 0.3F;
		double y = iposition.getY() + enumfacing.getFrontOffsetX() * 0.3F;
		double z = iposition.getZ() + enumfacing.getFrontOffsetZ() * 0.3F;
		
		double motionX = enumfacing.getFrontOffsetX();
		double motionY = enumfacing.getFrontOffsetY();
		double motionZ = enumfacing.getFrontOffsetZ();
		
		double randomFactor = this.getDispenserRandomFactor();
		
		if(randomFactor != 0.0) {
			Random random = world.rand;
			motionX += random.nextGaussian() * randomFactor;
			motionY += random.nextGaussian() * randomFactor;
			motionZ += random.nextGaussian() * randomFactor;
		}
		
		this.initPositionAndMotion(x, y, z, motionX, motionY, motionZ, this.rotationYaw, this.rotationPitch);
	}
	
	public EntityFireballBasic(World world, EntityLivingBase entityLivingBase) {
		this(world);
		
		this.shootingEntity = entityLivingBase;
		double motionX = -MathHelper.sin(entityLivingBase.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entityLivingBase.rotationPitch / 180.0F * (float) Math.PI) * MULTIPLIER_1;
		double motionY = -MathHelper.sin((entityLivingBase.rotationPitch) / 180.0F * (float) Math.PI) * MULTIPLIER_1;
		double motionZ = MathHelper.cos(entityLivingBase.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entityLivingBase.rotationPitch / 180.0F * (float) Math.PI) * MULTIPLIER_1;
		
		double randomFactor = this.getPlayerRandomFactor();
		
		if(randomFactor != 0.0) {
			Random random = world.rand;
			motionX += random.nextGaussian() * randomFactor;
			motionY += random.nextGaussian() * randomFactor;
			motionZ += random.nextGaussian() * randomFactor;
		}
		
		this.initPositionAndMotion(entityLivingBase.posX, entityLivingBase.posY + ENTITY_PLAYER_OFFSET, entityLivingBase.posZ, motionX, motionY, motionZ, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch);
	}
	
	// Methods :
	
	protected void initPositionAndMotion(double x, double y, double z, double motionX, double motionY, double motionZ, float rotationYaw, float rotationPitch) {
		double acc3D = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		double speedFactor = this.getSpeedFactor();
		
		this.motionX = motionX / acc3D * speedFactor;
		this.motionY = motionY / acc3D * speedFactor;
		this.motionZ = motionZ / acc3D * speedFactor;
		this.yOffset = 0.0F;
		
		this.fixedMotionX = this.motionX;
		this.fixedMotionY = this.motionY;
		this.fixedMotionZ = this.motionZ;
		
		this.setLocationAndAngles(x, y, z, rotationYaw, rotationPitch);
		this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
	}
	
	protected double getDispenserRandomFactor() {
		return 0.05;
	}
	
	protected double getPlayerRandomFactor() {
		return 0.0;
	}
	
	protected double getSpeedFactor() {
		return 0.5;
	}
	
	protected boolean canTakeKnowback() {
		return true;
	}
	
	protected float getSize() {
		return 0.3125F;
	}
	
	@Override
	protected void entityInit() {
		// Nothing to do.
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return distance < d1 * d1;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void onUpdate() {
		if(!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.blockExists((int) this.posX, (int) this.posY, (int) this.posZ))) {
			this.setDead();
		}
		else {
			// Ugly turnaround for explosion shockwaves :
			if(!this.canTakeKnowback()) {
				this.motionX = this.fixedMotionX;
				this.motionY = this.fixedMotionY;
				this.motionZ = this.fixedMotionZ;
			}
			
			super.onUpdate();
			this.setFire(1);
			
			if(this.inGround) {
				int blockId = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
				
				if(blockId == this.inTile) {
					++this.ticksAlive;
					
					if(this.ticksAlive == 600) {
						this.setDead();
					}
					
					return;
				}
				
				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksAlive = 0;
				this.ticksInAir = 0;
			}
			else {
				++this.ticksInAir;
			}
			
			Vec3 startPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 endPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition globalCollisionPosition = this.worldObj.rayTraceBlocks_do_do(startPos, endPos, true, true);
			startPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			endPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			
			if(globalCollisionPosition != null) {
				endPos = this.worldObj.getWorldVec3Pool().getVecFromPool(globalCollisionPosition.hitVec.xCoord, globalCollisionPosition.hitVec.yCoord, globalCollisionPosition.hitVec.zCoord);
			}
			
			Entity entity = null;
			List<Entity> entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double minDistanceToCollision = 0.0D;
			Iterator<Entity> iterator = entityList.iterator();
			
			while(iterator.hasNext()) {
				Entity entityListElement = iterator.next();
				
				if(entityListElement.canBeCollidedWith() && (!entityListElement.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
					float expendSize = 0.3F;
					AxisAlignedBB boudingBox = entityListElement.boundingBox.expand(expendSize, expendSize, expendSize);
					MovingObjectPosition collisionPosition = boudingBox.calculateIntercept(startPos, endPos);
					
					if(collisionPosition != null) {
						double distanceToCollision = startPos.distanceTo(collisionPosition.hitVec);
						
						if(distanceToCollision < minDistanceToCollision || minDistanceToCollision == 0.0D) {
							entity = entityListElement;
							minDistanceToCollision = distanceToCollision;
						}
					}
				}
			}
			
			if(entity != null) {
				globalCollisionPosition = new MovingObjectPosition(entity);
			}
			
			if(globalCollisionPosition != null) {
				this.onImpact(globalCollisionPosition);
			}
			
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float motionXZ = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			
			for(this.rotationPitch = (float) (Math.atan2(this.motionY, motionXZ) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				// this loop only decreases this.prevRotationPitch.
			}
			
			while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}
			
			while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}
			
			while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}
			
			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float deceleration = 1F;
			
			if(this.isInWater()) {
				for(int i = 0; i < 4; ++i) {
					float var18 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var18, this.posY - this.motionY * var18, this.posZ - this.motionZ * var18, this.motionX, this.motionY, this.motionZ);
				}
				
				if(Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ) > 0.4) {
					deceleration = 0.8F;
				}
			}
			
			this.motionX *= deceleration;
			this.motionY *= deceleration;
			this.motionZ *= deceleration;
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
			
			// Ugly turnaround for explosion shockwaves :
			if(!this.canTakeKnowback()) {
				this.fixedMotionX = this.motionX;
				this.fixedMotionY = this.motionY;
				this.fixedMotionZ = this.motionZ;
			}
		}
	}
	
	private void onImpact(MovingObjectPosition movingObjectPosition) {
		if(!this.worldObj.isRemote) {
			if(movingObjectPosition.entityHit != null) {
				this.onImpactWithEntity(movingObjectPosition.entityHit);
			}
			else {
				int x = movingObjectPosition.blockX;
				int y = movingObjectPosition.blockY;
				int z = movingObjectPosition.blockZ;
				
				switch(movingObjectPosition.sideHit) {
					case 0:
						--y;
						break;
					case 1:
						++y;
						break;
					case 2:
						--z;
						break;
					case 3:
						++z;
						break;
					case 4:
						--x;
						break;
					case 5:
						++x;
				}
				
				this.onImpactWithBlock(x, y, z);
			}
			
			this.setDead();
		}
	}
	
	protected void onImpactWithEntity(Entity entity) {
		if(!entity.isImmuneToFire() && entity.attackEntityFrom(EntityCustomDamageSourceIndirect.causeFireballDamage(this, this.shootingEntity), 5)) {
			entity.setFire(5);
		}
	}
	
	protected void onImpactWithBlock(int x, int y, int z) {
		if(this.worldObj.isAirBlock(x, y, z)) {
			this.worldObj.setBlock(x, y, z, Block.fire.blockID);
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort(X_TILE, (short) this.xTile);
		compound.setShort(Y_TILE, (short) this.yTile);
		compound.setShort(Z_TILE, (short) this.zTile);
		compound.setByte(IN_TILE, (byte) this.inTile);
		compound.setByte(IN_GROUND, (byte) (this.inGround ? 1 : 0));
		
		compound.setTag(DIRECTION, this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
		compound.setTag(FIXED_DIRECTION, this.newDoubleNBTList(new double[] {this.fixedMotionX, this.fixedMotionY, this.fixedMotionZ}));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.xTile = compound.getShort(X_TILE);
		this.yTile = compound.getShort(Y_TILE);
		this.zTile = compound.getShort(Z_TILE);
		this.inTile = compound.getByte(IN_TILE) & 255;
		this.inGround = compound.getByte(IN_GROUND) == 1;
		
		if(compound.hasKey(DIRECTION) && compound.hasKey(FIXED_DIRECTION)) {
			NBTTagList direction = compound.getTagList(DIRECTION);
			this.motionX = ((NBTTagDouble) direction.tagAt(0)).data;
			this.motionY = ((NBTTagDouble) direction.tagAt(1)).data;
			this.motionZ = ((NBTTagDouble) direction.tagAt(2)).data;
			
			direction = compound.getTagList(FIXED_DIRECTION);
			this.fixedMotionX = ((NBTTagDouble) direction.tagAt(0)).data;
			this.fixedMotionY = ((NBTTagDouble) direction.tagAt(1)).data;
			this.fixedMotionZ = ((NBTTagDouble) direction.tagAt(2)).data;
		}
		else {
			this.setDead();
		}
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float par2) {
		this.setBeenAttacked();
		
		if(this.canTakeKnowback() && damageSource.getEntity() != null) {
			Vec3 var3 = damageSource.getEntity().getLookVec();
			
			if(var3 != null) {
				this.motionX = var3.xCoord;
				this.motionY = var3.yCoord;
				this.motionZ = var3.zCoord;
			}
			
			if(damageSource.getEntity() instanceof EntityLiving) {
				this.shootingEntity = (EntityLiving) damageSource.getEntity();
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
	
	@Override
	public float getBrightness(float par1) {
		return 1.0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}
	
}

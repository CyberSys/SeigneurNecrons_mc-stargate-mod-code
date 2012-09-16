package mods.necron.custom;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityDamageSourceIndirect;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagDouble;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntityCustomFireBall extends Entity {
	
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private boolean inGround = false;
	public EntityLiving shootingEntity;
	private int ticksAlive;
	private int ticksInAir = 0;
	public double accelerationX = 0;
	public double accelerationY = 0;
	public double accelerationZ = 0;
	
	public EntityCustomFireBall(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}
	
	public EntityCustomFireBall(World world, double x, double y, double z, double accX, double accY, double accZ) {
		this(world);
		
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.setPosition(x, y, z);
		
		double acc3D = (double) MathHelper.sqrt_double(accX * accX + accY * accY + accZ * accZ);
		this.accelerationX = accX / acc3D * 0.1D;
		this.accelerationY = accY / acc3D * 0.1D;
		this.accelerationZ = accZ / acc3D * 0.1D;
	}
	
	public EntityCustomFireBall(World world, EntityLiving entityLiving, double accX, double accY, double accZ) {
		this(world);
		
		this.shootingEntity = entityLiving;
		this.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
		this.setPosition(this.posX, this.posY, this.posZ);
		
		this.yOffset = 0.0F;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		
		double acc3D = (double) MathHelper.sqrt_double(accX * accX + accY * accY + accZ * accZ);
		this.accelerationX = accX / acc3D * 0.1D;
		this.accelerationY = accY / acc3D * 0.1D;
		this.accelerationZ = accZ / acc3D * 0.1D;
	}
	
	protected void entityInit() {
	}
	
	/**
	 * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
	 * length * 64 * renderDistanceWeight Args: distance
	 */
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return distance < var3 * var3;
	}
	
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		if(!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.blockExists((int) this.posX, (int) this.posY, (int) this.posZ))) {
			this.setDead();
		}
		else {
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
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.ticksAlive = 0;
				this.ticksInAir = 0;
			}
			else {
				++this.ticksInAir;
			}
			
			Vec3 startPos = Vec3.getVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 endPos = Vec3.getVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition globalCollisionPosition = this.worldObj.rayTraceBlocks_do_do(startPos, endPos, true, true);
			startPos = Vec3.getVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			endPos = Vec3.getVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			
			if(globalCollisionPosition != null) {
				endPos = Vec3.getVec3Pool().getVecFromPool(globalCollisionPosition.hitVec.xCoord, globalCollisionPosition.hitVec.yCoord, globalCollisionPosition.hitVec.zCoord);
			}
			
			Entity entity = null;
			List entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double minDistanceToCollision = 0.0D;
			Iterator iterator = entityList.iterator();
			
			while(iterator.hasNext()) {
				Entity entityListElement = (Entity) iterator.next();
				
				if(entityListElement.canBeCollidedWith() && (!entityListElement.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
					float expendSize = 0.3F;
					AxisAlignedBB boudingBox = entityListElement.boundingBox.expand((double) expendSize, (double) expendSize, (double) expendSize);
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
			
			for(this.rotationPitch = (float) (Math.atan2(this.motionY, (double) motionXZ) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				;
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
			float var17 = 0.95F;
			
			if(this.isInWater()) {
				for(int var19 = 0; var19 < 4; ++var19) {
					float var18 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) var18, this.posY - this.motionY * (double) var18, this.posZ - this.motionZ * (double) var18, this.motionX, this.motionY, this.motionZ);
				}
				
				var17 = 0.8F;
			}
			
			this.motionX += this.accelerationX;
			this.motionY += this.accelerationY;
			this.motionZ += this.accelerationZ;
			this.motionX *= (double) var17;
			this.motionY *= (double) var17;
			this.motionZ *= (double) var17;
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.setPosition(this.posX, this.posY, this.posZ);
		}
	}
	
	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if(!this.worldObj.isRemote) {
			if(par1MovingObjectPosition.entityHit != null) {
				// FIXME - pour l'instant, la boule de feu ne fait aucun degat aux entites.
				//par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6);
			}
			
			// FIXME - pour l'instant, la boule de feu ne detruit pas le decor (ajout de false).
			this.worldObj.newExplosion((Entity) null, this.posX, this.posY, this.posZ, 1.0F, true, false);
			this.setDead();
		}
	}
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setShort("xTile", (short) this.xTile);
		par1NBTTagCompound.setShort("yTile", (short) this.yTile);
		par1NBTTagCompound.setShort("zTile", (short) this.zTile);
		par1NBTTagCompound.setByte("inTile", (byte) this.inTile);
		par1NBTTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
		
		if(par1NBTTagCompound.hasKey("direction")) {
			NBTTagList var2 = par1NBTTagCompound.getTagList("direction");
			this.motionX = ((NBTTagDouble) var2.tagAt(0)).data;
			this.motionY = ((NBTTagDouble) var2.tagAt(1)).data;
			this.motionZ = ((NBTTagDouble) var2.tagAt(2)).data;
		}
		else {
			this.setDead();
		}
	}
	
	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith() {
		return true;
	}
	
	public float getCollisionBorderSize() {
		return 1.0F;
	}
	
	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		this.setBeenAttacked();
		
		if(par1DamageSource.getEntity() != null) {
			Vec3 var3 = par1DamageSource.getEntity().getLookVec();
			
			if(var3 != null) {
				this.motionX = var3.xCoord;
				this.motionY = var3.yCoord;
				this.motionZ = var3.zCoord;
				this.accelerationX = this.motionX * 0.1D;
				this.accelerationY = this.motionY * 0.1D;
				this.accelerationZ = this.motionZ * 0.1D;
			}
			
			if(par1DamageSource.getEntity() instanceof EntityLiving) {
				this.shootingEntity = (EntityLiving) par1DamageSource.getEntity();
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
	
	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float par1) {
		return 1.0F;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {
		return 15728880;
	}
	
}

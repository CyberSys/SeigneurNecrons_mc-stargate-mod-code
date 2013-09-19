package seigneurnecron.minecraftmods.stargate.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class EntityCustomFireBall extends Entity {
	
	private static final float entityPlayerOffset = 1.62F;
	private static final float multiplier1 = 0.4F;
	private static final double multiplier2 = 0.5D;
	
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private boolean inGround = false;
	public EntityLivingBase shootingEntity;
	private int ticksAlive;
	private int ticksInAir = 0;
	
	public EntityCustomFireBall(World world) {
		super(world);
		this.setSize(this.getSize(), this.getSize());
	}
	
	public EntityCustomFireBall(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		this(world);
		this.initPositionAndMotion(x, y, z, motionX, motionY, motionZ, this.rotationYaw, this.rotationPitch);
	}
	
	public EntityCustomFireBall(World world, EntityLivingBase entityLivingBase) {
		this(world);
		
		this.shootingEntity = entityLivingBase;
		double motionX = -MathHelper.sin(entityLivingBase.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entityLivingBase.rotationPitch / 180.0F * (float) Math.PI) * multiplier1;
		double motionY = -MathHelper.sin((entityLivingBase.rotationPitch) / 180.0F * (float) Math.PI) * multiplier1;
		double motionZ = MathHelper.cos(entityLivingBase.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(entityLivingBase.rotationPitch / 180.0F * (float) Math.PI) * multiplier1;
		
		this.initPositionAndMotion(entityLivingBase.posX, entityLivingBase.posY + entityPlayerOffset, entityLivingBase.posZ, motionX, motionY, motionZ, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch);
	}
	
	protected void initPositionAndMotion(double x, double y, double z, double motionX, double motionY, double motionZ, float rotationYaw, float rotationPitch) {
		double acc3D = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		this.motionX = motionX / acc3D * multiplier2;
		this.motionY = motionY / acc3D * multiplier2;
		this.motionZ = motionZ / acc3D * multiplier2;
		this.yOffset = 0.0F;
		
		this.setLocationAndAngles(x, y, z, rotationYaw, rotationPitch);
		this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
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
		}
	}
	
	private void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if(!this.worldObj.isRemote) {
			if(par1MovingObjectPosition.entityHit != null) {
				this.onImpactWithEntity(par1MovingObjectPosition.entityHit);
			}
			else {
				int x = par1MovingObjectPosition.blockX;
				int y = par1MovingObjectPosition.blockY;
				int z = par1MovingObjectPosition.blockZ;
				
				switch(par1MovingObjectPosition.sideHit) {
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
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setShort("xTile", (short) this.xTile);
		par1NBTTagCompound.setShort("yTile", (short) this.yTile);
		par1NBTTagCompound.setShort("zTile", (short) this.zTile);
		par1NBTTagCompound.setByte("inTile", (byte) this.inTile);
		par1NBTTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
	}
	
	@Override
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
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		this.setBeenAttacked();
		
		if(par1DamageSource.getEntity() != null) {
			Vec3 var3 = par1DamageSource.getEntity().getLookVec();
			
			if(var3 != null) {
				this.motionX = var3.xCoord;
				this.motionY = var3.yCoord;
				this.motionZ = var3.zCoord;
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

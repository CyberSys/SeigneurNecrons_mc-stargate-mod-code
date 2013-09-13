package seigneurnecron.minecraftmods.core.entity;

import java.util.List;
import java.util.logging.Level;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.SeigneurNecronModConfig;
import seigneurnecron.minecraftmods.core.reflection.Reflection;
import seigneurnecron.minecraftmods.core.reflection.ReflectionException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This class extends EntityFishHook and allows to create custom fishing rods. <br />
 * The original {@link EntityFishHook} don't allows to create custom fishing rod because of a failed test which destroy the fish hook entity if the fishing rod isn't the original. <br />
 * The whole onUpdate() method including the content of super.onUpdate() had to be rewrited just to change one line... <br />
 * In top of that, reflection had to be used to get a lot of private fields used in that method ! -__- <br />
 * If the methods/hierarchy of the super classes change, this method will have to be rewrited !
 * @author Seigneur Necron
 */
public class EntityCustomFishHook extends EntityFishHook {
	
	// Fields :
	
	private final SeigneurNecronModConfig config;
	
	// Constructors :
	
	public EntityCustomFishHook(World world) {
		super(world);
		this.config = SeigneurNecronMod.instance.getConfig();
	}
	
	@SideOnly(Side.CLIENT)
	public EntityCustomFishHook(World world, double x, double y, double z, EntityPlayer entityPlayer) {
		super(world, x, y, z, entityPlayer);
		this.config = SeigneurNecronMod.instance.getConfig();
	}
	
	public EntityCustomFishHook(World world, EntityPlayer entityPlayer) {
		super(world, entityPlayer);
		this.config = SeigneurNecronMod.instance.getConfig();
	}
	
	// Getters - use reflexion to get super class private fields :
	
	protected int getXTile() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_xTile);
	}
	
	protected int getYTile() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_yTile);
	}
	
	protected int getZTile() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_zTile);
	}
	
	protected int getInTile() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_inTile);
	}
	
	protected boolean getInGround() throws ReflectionException {
		return Reflection.getBoolean(EntityFishHook.class, this, this.config.entityFishHook_inGround);
	}
	
	protected int getTicksInGround() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_ticksInGround);
	}
	
	protected int getTicksInAir() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_ticksInAir);
	}
	
	protected int getTicksCatchable() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_ticksCatchable);
	}
	
	protected int getFishPosRotationIncrements() throws ReflectionException {
		return Reflection.getInt(EntityFishHook.class, this, this.config.entityFishHook_fishPosRotationIncrements);
	}
	
	protected double getFishX() throws ReflectionException {
		return Reflection.getDouble(EntityFishHook.class, this, this.config.entityFishHook_fishX);
	}
	
	protected double getFishY() throws ReflectionException {
		return Reflection.getDouble(EntityFishHook.class, this, this.config.entityFishHook_fishY);
	}
	
	protected double getFishZ() throws ReflectionException {
		return Reflection.getDouble(EntityFishHook.class, this, this.config.entityFishHook_fishZ);
	}
	
	protected double getFishYaw() throws ReflectionException {
		return Reflection.getDouble(EntityFishHook.class, this, this.config.entityFishHook_fishYaw);
	}
	
	protected double getFishPitch() throws ReflectionException {
		return Reflection.getDouble(EntityFishHook.class, this, this.config.entityFishHook_fishPitch);
	}
	
	// Setters - use reflexion to set super class private fields :
	
	protected void setXTile(int xTile) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_xTile, xTile);
	}
	
	protected void setYTile(int yTile) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_yTile, yTile);
	}
	
	protected void setZTile(int zTile) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_zTile, zTile);
	}
	
	protected void setInTile(int inTile) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_inTile, inTile);
	}
	
	protected void setInGround(boolean inGround) throws ReflectionException {
		Reflection.setBoolean(EntityFishHook.class, this, this.config.entityFishHook_inGround, inGround);
	}
	
	protected void setTicksInGround(int ticksInGround) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_ticksInGround, ticksInGround);
	}
	
	protected void setTicksInAir(int ticksInAir) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_ticksInAir, ticksInAir);
	}
	
	protected void setTicksCatchable(int ticksCatchable) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_ticksCatchable, ticksCatchable);
	}
	
	protected void setFishPosRotationIncrements(int fishPosRotationIncrements) throws ReflectionException {
		Reflection.setInt(EntityFishHook.class, this, this.config.entityFishHook_fishPosRotationIncrements, fishPosRotationIncrements);
	}
	
	protected void setFishX(double fishX) throws ReflectionException {
		Reflection.setDouble(EntityFishHook.class, this, this.config.entityFishHook_fishX, fishX);
	}
	
	protected void setFishY(double fishY) throws ReflectionException {
		Reflection.setDouble(EntityFishHook.class, this, this.config.entityFishHook_fishY, fishY);
	}
	
	protected void setFishZ(double fishZ) throws ReflectionException {
		Reflection.setDouble(EntityFishHook.class, this, this.config.entityFishHook_fishZ, fishZ);
	}
	
	protected void setFishYaw(double fishYaw) throws ReflectionException {
		Reflection.setDouble(EntityFishHook.class, this, this.config.entityFishHook_fishYaw, fishYaw);
	}
	
	protected void setFishPitch(double fishPitch) throws ReflectionException {
		Reflection.setDouble(EntityFishHook.class, this, this.config.entityFishHook_fishPitch, fishPitch);
	}
	
	// Changed method :
	
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		try {
			this.onEntityUpdate();
			
			if(this.getFishPosRotationIncrements() > 0) {
				double d0 = this.posX + (this.getFishX() - this.posX) / this.getFishPosRotationIncrements();
				double d1 = this.posY + (this.getFishY() - this.posY) / this.getFishPosRotationIncrements();
				double d2 = this.posZ + (this.getFishZ() - this.posZ) / this.getFishPosRotationIncrements();
				double d3 = MathHelper.wrapAngleTo180_double(this.getFishYaw() - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + d3 / this.getFishPosRotationIncrements());
				this.rotationPitch = (float) (this.rotationPitch + (this.getFishPitch() - this.rotationPitch) / this.getFishPosRotationIncrements());
				this.setFishPosRotationIncrements(this.getFishPosRotationIncrements() - 1);
				this.setPosition(d0, d1, d2);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
			else {
				if(!this.worldObj.isRemote) {
					ItemStack itemstack = this.angler.getCurrentEquippedItem();
					
					// The line :
					if(this.angler.isDead || !this.angler.isEntityAlive() || itemstack == null || !(itemstack.getItem() instanceof ItemFishingRod) || this.getDistanceSqToEntity(this.angler) > 1024.0D) {
						this.setDead();
						this.angler.fishEntity = null;
						return;
					}
					
					if(this.bobber != null) {
						if(!this.bobber.isDead) {
							this.posX = this.bobber.posX;
							this.posY = this.bobber.boundingBox.minY + this.bobber.height * 0.8D;
							this.posZ = this.bobber.posZ;
							return;
						}
						
						this.bobber = null;
					}
				}
				
				if(this.shake > 0) {
					--this.shake;
				}
				
				if(this.getInGround()) {
					int i = this.worldObj.getBlockId(this.getXTile(), this.getYTile(), this.getZTile());
					
					if(i == this.getInTile()) {
						this.setTicksInGround(this.getTicksInGround() + 1);
						
						if(this.getTicksInGround() == 1200) {
							this.setDead();
						}
						
						return;
					}
					
					this.setInGround(false);
					this.motionX *= this.rand.nextFloat() * 0.2F;
					this.motionY *= this.rand.nextFloat() * 0.2F;
					this.motionZ *= this.rand.nextFloat() * 0.2F;
					this.setTicksInGround(0);
					this.setTicksInAir(0);
				}
				else {
					this.setTicksInAir(this.getTicksInAir());
				}
				
				Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
				Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
				MovingObjectPosition movingobjectposition = this.worldObj.clip(vec3, vec31);
				vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
				vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
				
				if(movingobjectposition != null) {
					vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
				}
				
				Entity entity = null;
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
				double d4 = 0.0D;
				double d5;
				
				for(int j = 0; j < list.size(); ++j) {
					Entity entity1 = (Entity) list.get(j);
					
					if(entity1.canBeCollidedWith() && (entity1 != this.angler || this.getTicksInAir() >= 5)) {
						float f = 0.3F;
						AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
						MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
						
						if(movingobjectposition1 != null) {
							d5 = vec3.distanceTo(movingobjectposition1.hitVec);
							
							if(d5 < d4 || d4 == 0.0D) {
								entity = entity1;
								d4 = d5;
							}
						}
					}
				}
				
				if(entity != null) {
					movingobjectposition = new MovingObjectPosition(entity);
				}
				
				if(movingobjectposition != null) {
					if(movingobjectposition.entityHit != null) {
						if(movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.angler), 0.0F)) {
							this.bobber = movingobjectposition.entityHit;
						}
					}
					else {
						this.setInGround(true);
					}
				}
				
				if(!this.getInGround()) {
					this.moveEntity(this.motionX, this.motionY, this.motionZ);
					float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
					this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
					
					for(this.rotationPitch = (float) (Math.atan2(this.motionY, f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
						//
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
					float f2 = 0.92F;
					
					if(this.onGround || this.isCollidedHorizontally) {
						f2 = 0.5F;
					}
					
					byte b0 = 5;
					double d6 = 0.0D;
					
					for(int k = 0; k < b0; ++k) {
						double d7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (k + 0) / b0 - 0.125D + 0.125D;
						double d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (k + 1) / b0 - 0.125D + 0.125D;
						AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d7, this.boundingBox.minZ, this.boundingBox.maxX, d8, this.boundingBox.maxZ);
						
						if(this.worldObj.isAABBInMaterial(axisalignedbb1, Material.water)) {
							d6 += 1.0D / b0;
						}
					}
					
					if(d6 > 0.0D) {
						if(this.getTicksCatchable() > 0) {
							this.setTicksCatchable(this.getTicksCatchable() - 1);
						}
						else {
							short short1 = 500;
							
							if(this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
								short1 = 300;
							}
							
							if(this.rand.nextInt(short1) == 0) {
								this.setTicksCatchable(this.rand.nextInt(30) + 10);
								this.motionY -= 0.20000000298023224D;
								this.playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
								float f3 = MathHelper.floor_double(this.boundingBox.minY);
								int l;
								float f4;
								float f5;
								
								for(l = 0; l < 1.0F + this.width * 20.0F; ++l) {
									f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
									f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
									this.worldObj.spawnParticle("bubble", this.posX + f5, f3 + 1.0F, this.posZ + f4, this.motionX, this.motionY - this.rand.nextFloat() * 0.2F, this.motionZ);
								}
								
								for(l = 0; l < 1.0F + this.width * 20.0F; ++l) {
									f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
									f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
									this.worldObj.spawnParticle("splash", this.posX + f5, f3 + 1.0F, this.posZ + f4, this.motionX, this.motionY, this.motionZ);
								}
							}
						}
					}
					
					if(this.getTicksCatchable() > 0) {
						this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2D;
					}
					
					d5 = d6 * 2.0D - 1.0D;
					this.motionY += 0.03999999910593033D * d5;
					
					if(d6 > 0.0D) {
						f2 = (float) (f2 * 0.9D);
						this.motionY *= 0.8D;
					}
					
					this.motionX *= f2;
					this.motionY *= f2;
					this.motionZ *= f2;
					this.setPosition(this.posX, this.posY, this.posZ);
				}
			}
		}
		catch(ReflectionException argh) {
			SeigneurNecronMod.instance.log(argh.getMessage(), Level.SEVERE);
			super.onUpdate();
		}
	}
	
}

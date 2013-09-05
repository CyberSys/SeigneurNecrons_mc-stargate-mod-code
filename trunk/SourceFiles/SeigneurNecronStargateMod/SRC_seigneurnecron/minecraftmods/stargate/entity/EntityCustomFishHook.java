package seigneurnecron.minecraftmods.stargate.entity;

import java.lang.reflect.Field;
import java.util.List;

import seigneurnecron.minecraftmods.stargate.StargateMod;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * SeigneurNecron's Stargate Mod main class.
 * @author Seigneur Necron
 */
public class EntityCustomFishHook extends EntityFishHook {
	
	// Fields obfuscated names :
	
	private static String X_TILE = StargateMod.obfuscated ? "field_70202_d" : "xTile";
	private static String Y_TILE = StargateMod.obfuscated ? "field_70203_e" : "yTile";
	private static String Z_TILE = StargateMod.obfuscated ? "field_70200_f" : "zTile";
	private static String IN_TILE = StargateMod.obfuscated ? "field_70201_g" : "inTile";
	private static String IN_GROUND = StargateMod.obfuscated ? "field_70214_h" : "inGround";
	private static String TICKS_IN_GROUND = StargateMod.obfuscated ? "field_70216_i" : "ticksInGround";
	private static String TICKS_IN_AIR = StargateMod.obfuscated ? "field_70211_j" : "ticksInAir";
	private static String TICKS_CATCHABLE = StargateMod.obfuscated ? "field_70219_an" : "ticksCatchable";
	private static String FISH_POS_ROTATION_INCREMENTS = StargateMod.obfuscated ? "field_70217_ao" : "fishPosRotationIncrements";
	private static String FISH_X = StargateMod.obfuscated ? "field_70218_ap" : "fishX";
	private static String FISH_Y = StargateMod.obfuscated ? "field_70210_aq" : "fishY";
	private static String FISH_Z = StargateMod.obfuscated ? "field_70209_ar" : "fishZ";
	private static String FISH_YAW = StargateMod.obfuscated ? "field_70208_as" : "fishYaw";
	private static String FISH_PITCH = StargateMod.obfuscated ? "field_70207_at" : "fishPitch";
	
	// Builders - just call super class builders :
	
	public EntityCustomFishHook(World world) {
		super(world);
	}
	
	@SideOnly(Side.CLIENT)
	public EntityCustomFishHook(World world, double x, double y, double z, EntityPlayer entityPlayer) {
		super(world, x, y, z, entityPlayer);
	}
	
	public EntityCustomFishHook(World world, EntityPlayer entityPlayer) {
		super(world, entityPlayer);
	}
	
	// Getters - use reflexion to get super class private fields :
	
	protected int getXTile() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(X_TILE);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected int getYTile() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(Y_TILE);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected int getZTile() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(Z_TILE);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected int getInTile() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(IN_TILE);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected boolean getInGround() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(IN_GROUND);
		field.setAccessible(true);
		return field.getBoolean(this);
	}
	
	protected int getTicksInGround() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(TICKS_IN_GROUND);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected int getTicksInAir() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(TICKS_IN_AIR);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected int getTicksCatchable() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(TICKS_CATCHABLE);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected int getFishPosRotationIncrements() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_POS_ROTATION_INCREMENTS);
		field.setAccessible(true);
		return field.getInt(this);
	}
	
	protected double getFishX() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_X);
		field.setAccessible(true);
		return field.getDouble(this);
	}
	
	protected double getFishY() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_Y);
		field.setAccessible(true);
		return field.getDouble(this);
	}
	
	protected double getFishZ() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_Z);
		field.setAccessible(true);
		return field.getDouble(this);
	}
	
	protected double getFishYaw() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_YAW);
		field.setAccessible(true);
		return field.getDouble(this);
	}
	
	protected double getFishPitch() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_PITCH);
		field.setAccessible(true);
		return field.getDouble(this);
	}
	
	// Setters - use reflexion to set super class private fields :
	
	protected void setXTile(int xTile) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(X_TILE);
		field.setAccessible(true);
		field.setInt(this, xTile);
	}
	
	protected void setYTile(int yTile) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(Y_TILE);
		field.setAccessible(true);
		field.setInt(this, yTile);
	}
	
	protected void setZTile(int zTile) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(Z_TILE);
		field.setAccessible(true);
		field.setInt(this, zTile);
	}
	
	protected void setInTile(int inTile) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(IN_TILE);
		field.setAccessible(true);
		field.setInt(this, inTile);
	}
	
	protected void setInGround(boolean inGround) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(IN_GROUND);
		field.setAccessible(true);
		field.setBoolean(this, inGround);
	}
	
	protected void setTicksInGround(int ticksInGround) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(TICKS_IN_GROUND);
		field.setAccessible(true);
		field.setInt(this, ticksInGround);
	}
	
	protected void setTicksInAir(int ticksInAir) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(TICKS_IN_AIR);
		field.setAccessible(true);
		field.setInt(this, ticksInAir);
	}
	
	protected void setTicksCatchable(int ticksCatchable) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(TICKS_CATCHABLE);
		field.setAccessible(true);
		field.setInt(this, ticksCatchable);
	}
	
	protected void setFishPosRotationIncrements(int fishPosRotationIncrements) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_POS_ROTATION_INCREMENTS);
		field.setAccessible(true);
		field.setInt(this, fishPosRotationIncrements);
	}
	
	protected void setFishX(double fishX) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_X);
		field.setAccessible(true);
		field.setDouble(this, fishX);
	}
	
	protected void setFishY(double fishY) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_Y);
		field.setAccessible(true);
		field.setDouble(this, fishY);
	}
	
	protected void setFishZ(double fishZ) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_Z);
		field.setAccessible(true);
		field.setDouble(this, fishZ);
	}
	
	protected void setFishYaw(double fishYaw) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_YAW);
		field.setAccessible(true);
		field.setDouble(this, fishYaw);
	}
	
	protected void setFishPitch(double fishPitch) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EntityFishHook.class.getDeclaredField(FISH_PITCH);
		field.setAccessible(true);
		field.setDouble(this, fishPitch);
	}
	
	// Changed method - just for one stupid failed test -__- :
	
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
						this.setTicksInGround(getTicksInGround() + 1);
						
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
		catch(NoSuchFieldException argh) {
			argh.printStackTrace();
			super.onUpdate();
		}
		catch(SecurityException argh) {
			argh.printStackTrace();
			super.onUpdate();
		}
		catch(IllegalArgumentException argh) {
			argh.printStackTrace();
			super.onUpdate();
		}
		catch(IllegalAccessException argh) {
			argh.printStackTrace();
			super.onUpdate();
		}
	}
}

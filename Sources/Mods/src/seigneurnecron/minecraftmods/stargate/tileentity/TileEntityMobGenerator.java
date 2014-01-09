package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryMobGenerator;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;

/**
 * @author Seigneur Necron
 */
public class TileEntityMobGenerator extends TileEntityContainerStargate<InventoryMobGenerator> {
	
	// NBTTags names :
	
	private static final String POWERED = "powered";
	private static final String DELAY = "delay";
	
	// Constants :
	
	/**
	 * Initial spawn delay.
	 */
	private static final int INIT_DELAY = 300;
	
	/**
	 * The range in which the generator can spawn mobs.
	 */
	private static final int SPAWN_RANGE = 4;
	
	// Fields :
	
	/**
	 * Indicates whether the generator is powered by a redstone signal.
	 */
	private boolean powered = false;
	
	/**
	 * The delay until next spawn.
	 */
	private int delay = INIT_DELAY;
	
	// Getters :
	
	/**
	 * Indicates whether the generator is powered by a redstone signal.
	 * @return true if the generator is powered by a redstone signal, else false.
	 */
	public boolean isPowered() {
		return this.powered;
	}
	
	// Setters :
	
	/**
	 * Updates the power state of the generator (informs clients).
	 * @param powered - true if the generator is powered by a redstone signal, else false.
	 */
	public void setPowered(boolean powered) {
		if(this.powered != powered) {
			this.powered = powered;
			this.setChanged();
			this.update();
		}
	}
	
	// Methods :
	
	@Override
	protected InventoryMobGenerator getNewInventory() {
		return new InventoryMobGenerator(this);
	}
	
	private void resetDelay() {
		this.delay = INIT_DELAY;
	}
	
	public void onCrystalChanged() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			this.setChanged();
			this.update();
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!this.worldObj.isRemote) {
			
			if(this.delay > 0) {
				--this.delay;
				return;
			}
			
			if(this.powered && this.getInventory().isCrystalInserted()) {
				ItemSoulCrystalFull crystal = (ItemSoulCrystalFull) this.getInventory().getCrystal().getItem();
				boolean spawnSucces = false;
				
				for(int i = 0; i < crystal.spawnCount; ++i) {
					Entity entity = EntityList.createEntityByID(crystal.monsterId, this.worldObj);
					int nbEntities = this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(SPAWN_RANGE * 2, SPAWN_RANGE, SPAWN_RANGE * 2)).size();
					
					if(nbEntities >= crystal.maxMob) {
						this.resetDelay();
						return;
					}
					
					double x = this.xCoord + (2 * this.worldObj.rand.nextDouble() - 1) * SPAWN_RANGE;
					double y = this.yCoord + this.worldObj.rand.nextInt(3) - 1;
					double z = this.zCoord + (2 * this.worldObj.rand.nextDouble() - 1) * SPAWN_RANGE;
					entity.setLocationAndAngles(x, y, z, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
					
					if(entity instanceof EntityLiving) {
						EntityLiving entityLiving = (EntityLiving) entity;
						
						if(entityLiving.getCanSpawnHere()) {
							entityLiving.func_110161_a(null); // initCreature(EntityLivingData)
							this.worldObj.spawnEntityInWorld(entityLiving);
							this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);
							entityLiving.spawnExplosionParticle();
							spawnSucces = true;
						}
					}
				}
				
				if(spawnSucces) {
					this.resetDelay();
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.powered = compound.getBoolean(POWERED);
		this.delay = compound.getInteger(DELAY);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean(POWERED, this.powered);
		compound.setInteger(DELAY, this.delay);
	}
	
}

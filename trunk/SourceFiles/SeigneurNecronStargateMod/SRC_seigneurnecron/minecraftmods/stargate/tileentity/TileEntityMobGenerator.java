package seigneurnecron.minecraftmods.stargate.tileentity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;

/**
 * @author Seigneur Necron
 */
public class TileEntityMobGenerator extends TileEntityGuiContainer {
	
	public static final String INV_NAME = "container.mobGenerator";
	
	/**
	 * Initial spawn delay.
	 */
	private static final int INIT_DELAY = 300;
	
	/**
	 * The range in which the generator can spawn mobs.
	 */
	private static final int SPAWN_RANGE = 4;
	
	/**
	 * The unique inventory slot, where you can insert a soul crystal.
	 */
	private ItemStack crystal;
	
	/**
	 * Indicates whether the generator is powered by a redstone signal.
	 */
	private boolean powered = false;
	
	/**
	 * The delay until next spawn.
	 */
	private int delay = INIT_DELAY;
	
	/**
	 * Indicates whether a soul crystal is inserted in the generator.
	 * @return true if a crystal is inserted, else false.
	 */
	public boolean isCrystalInserted() {
		return(this.crystal != null && this.crystal.getItem() instanceof ItemSoulCrystal);
	}
	
	/**
	 * Indicates whether the generator is powered by a redstone signal.
	 * @return true if the generator is powered by a redstone signal, else false.
	 */
	public boolean isPowered() {
		return this.powered;
	}
	
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
	
	/**
	 * Updates the crystal slot (informs clients).
	 * @param crystal - the new item to insert in the crystal slot.
	 */
	private void setCrystal(ItemStack crystal) {
		if(!ItemStack.areItemStacksEqual(this.crystal, crystal)) {
			this.crystal = crystal;
			
			if(!this.worldObj.isRemote) {
				this.setChanged();
			}
		}
	}
	
	private void resetDelay() {
		this.delay = INIT_DELAY;
	}
	
	@Override
	public String getInvName() {
		return INV_NAME;
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? this.crystal : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setCrystal(itemStack);
			this.update();
		}
	}
	
	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote) {
			
			if(this.delay > 0) {
				--this.delay;
				return;
			}
			
			if(this.powered && this.crystal != null && this.crystal.getItem() instanceof ItemSoulCrystal) {
				ItemSoulCrystal crystal = (ItemSoulCrystal) this.crystal.getItem();
				boolean spawnSucces = false;
				
				for(int i = 0; i < crystal.getSpawnCount(); ++i) {
					Entity entity = EntityList.createEntityByID(crystal.getMonsterId(), this.worldObj);
					int nbEntities = this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(SPAWN_RANGE * 2, SPAWN_RANGE, SPAWN_RANGE * 2)).size();
					
					if(nbEntities >= crystal.getMaxMob()) {
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
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.powered = par1NBTTagCompound.getBoolean("powered");
		this.delay = par1NBTTagCompound.getInteger("delay");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("powered", this.powered);
		par1NBTTagCompound.setInteger("delay", this.delay);
	}
	
	@Override
	protected void getEntityData(DataOutputStream output) throws IOException {
		super.getEntityData(output);
		
		output.writeBoolean(this.powered);
		output.writeInt(this.delay);
		
		output.writeInt((this.crystal != null) ? this.crystal.itemID : -1);
	}
	
	@Override
	protected void loadEntityData(DataInputStream input) throws IOException {
		super.loadEntityData(input);
		
		this.powered = input.readBoolean();
		this.delay = input.readInt();
		
		int itemId = input.readInt();
		Item item = (itemId > 0 && itemId < Item.itemsList.length) ? Item.itemsList[itemId] : null;
		this.setCrystal((item != null) ? new ItemStack(item) : null);
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack) {
		if(index == 0 && !(itemstack.getItem() instanceof ItemSoulCrystal)) {
			return false;
		}
		
		return true;
	}
	
}

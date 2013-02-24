package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;

public class TileEntityMobGenerator extends TileEntityGuiContainer {
	
	/**
	 * Delai de spawn initial.
	 */
	private static final int INIT_DELAY = 300;
	
	/**
	 * La portee a laquelle le generateur peut faire spawn des mobs.
	 */
	private static final int SPAWN_RANGE = 4;
	
	/**
	 * L'unique emplacement de l'inventaire, servant a inserer un cristal dans le generateur.
	 */
	private ItemStack crystal;
	
	/**
	 * Indique si le generateur est alimente par un signal de redstone.
	 */
	private boolean powered = false;
	
	/**
	 * Le delai avant le prochain spawn.
	 */
	private int delay = INIT_DELAY;
	
	/**
	 * Indique si un cristal d'ame est insere dans le generateur.
	 * @return true si il y a bien un cristal, false sinon.
	 */
	public boolean isCrystalInserted() {
		return(this.crystal != null && this.crystal.getItem() instanceof ItemSoulCrystal);
	}
	
	/**
	 * Indique si le generateur est alimente par un signal de redstone.
	 * @return true si le generateur est alimente par un signal de redstone, false sinon.
	 */
	public boolean isPowered() {
		return this.powered;
	}
	
	/**
	 * Met a jour l'etat d'alimentation du generateur (previens les clients).
	 * @param powered - true si le generateur est alimente par un signal de redstone, false sinon.
	 */
	public void setPowered(boolean powered) {
		if(this.powered != powered) {
			this.powered = powered;
			this.updateClients();
		}
	}
	
	/**
	 * Met a jour le cristal insere dans le generateur (previens les clients).
	 * @param crystal - le nouvel objet a inserer dans le generateur.
	 */
	private void setCrystal(ItemStack crystal) {
		if(!ItemStack.areItemStacksEqual(this.crystal, crystal)) {
			this.crystal = crystal;
			this.updateClients();
		}
	}
	
	private void resetDelay() {
		this.delay = INIT_DELAY;
	}
	
	@Override
	public String getInvName() {
		return "container.mobGenerator";
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
					Entity entity = EntityList.createEntityByName(crystal.getMonsterName(), this.worldObj);
					int nbEntities = this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(SPAWN_RANGE * 2, SPAWN_RANGE, SPAWN_RANGE * 2)).size();
					
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
							entityLiving.initCreature();
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
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.powered);
		writeInt(list, this.delay);
		
		writeInt(list, (this.crystal != null) ? this.crystal.itemID : -1);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.powered = readBoolean(list);
			this.delay = readInt(list);
			
			int itemId = readInt(list);
			Item item = (itemId > 0 && itemId < Item.itemsList.length) ? Item.itemsList[itemId] : null;
			this.setCrystal((item != null) ? new ItemStack(item) : null);
			
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean isCorrectId(int id) {
		return(super.isCorrectId(id) || id == StargatePacketHandler.packetId_CloseGuiMobGenerator);
	}
	
	@Override
	public String toString() {
		String crystal = null;
		
		Item item = this.crystal.getItem();
		if(item instanceof ItemSoulCrystal) {
			crystal = ((ItemSoulCrystal) item).getMonsterName();
		}
		
		return("TileEntityMobGenerator[cristal: " + crystal + "; powered: " + this.powered + "]");
	}
	
}

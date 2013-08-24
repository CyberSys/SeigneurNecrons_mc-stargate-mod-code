package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public class TileEntityBaseShieldConsole extends TileEntityBaseStargateConsole {
	
	public static final String INV_NAME = "container.shieldConsole";
	
	/**
	 * Indicates whether the shield must be activated/deactivted when sending informations to the gate.
	 */
	private boolean panelActivated = true;
	
	/**
	 * Indicates whether the shield must be automatically activated.
	 */
	private boolean shieldAutomated = false;
	
	/**
	 * The code which deactivates the shield.
	 */
	private int code = 0;
	
	/**
	 * Indicates whether the shield must be activated/deactivted when sending informations to the gate.
	 * @return true if the panel has been activated, false if just sending informations.
	 */
	public boolean isPanelActivated() {
		return this.panelActivated;
	}
	
	/**
	 * Indicates whether the shield must be automatically activated.
	 * @return true if the shield must be automatically activated, else false.
	 */
	public boolean isShieldAutomated() {
		return this.shieldAutomated;
	}
	
	/**
	 * Returns the code which deactivates the shield.
	 * @return the code which deactivates the shield.
	 */
	public int getCode() {
		return this.code;
	}
	
	/**
	 * Indicates whether the shield must be activated/deactivted when sending informations to the gate.
	 * @param panelActivated - true if the panel has been activated, false if just sending informations.
	 */
	private void setPanelActivated(boolean panelActivated) {
		this.panelActivated = panelActivated;
	}
	
	/**
	 * Sets the automatic shield activation on or off.
	 * @param shieldAutomated - true if the shield must be automatically activated, else false.
	 */
	public void setShieldAutomated(boolean shieldAutomated) {
		this.shieldAutomated = shieldAutomated;
	}
	
	/**
	 * Sets the code which deactivates the shield.
	 * @param code - the code which deactivates the shield.
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * Send informations to the gate without activating/deactivating the shield.
	 */
	private void sendChangesToGate() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			this.setPanelActivated(false);
			this.activate(null, 0, 0, 0, 0);
			this.setPanelActivated(true);
		}
	}
	
	@Override
	protected void sendCustomCommand(TileEntityStargateControl tileEntity) {
		tileEntity.onShieldConsoleActivated(this.panelActivated, this.shieldAutomated, this.code);
	}
	
	@Override
	protected void informStargateOfConsoleDestruction(TileEntityStargateControl tileEntity) {
		tileEntity.onShieldConsoleDestroyed();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.shieldAutomated = par1NBTTagCompound.getBoolean("shieldAutomated");
		this.code = par1NBTTagCompound.getInteger("code");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("shieldAutomated", this.shieldAutomated);
		par1NBTTagCompound.setInteger("code", this.code);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.shieldAutomated);
		writeInt(list, this.code);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.shieldAutomated = readBoolean(list);
			this.code = readInt(list);
			
			this.updateClients();
			this.updateBlockTexture();
			this.sendChangesToGate();
			return true;
		}
		return false;
	}
	
}

package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.SHIELD;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.SHIELD_AUTOMATED;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.SHIELD_CODE;
import net.minecraft.block.Block;
import net.minecraft.network.packet.Packet;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class TileEntityBaseShieldConsole extends TileEntityBaseStargateConsole {
	
	public static final String INV_NAME = "container.shieldConsole";
	
	/**
	 * Activates/deactivates the shield of stargate connected to this console.
	 * @param activate - true if the shield must be activated, false if it must be deactivated.
	 */
	public void activateShield(boolean activate) {
		if(!this.worldObj.isRemote) {
			TileEntityStargateControl stargate = this.getStargateControl();
			
			if(stargate != null) {
				stargate.onShieldConsoleActivated(activate);
			}
		}
	}
	
	/**
	 * Changes the automated shield state of the stargate connected to this console.
	 * @param shieldAutomated - indicates whether the shield must be automatically activated.
	 */
	public void changeShieldAutomated(boolean shieldAutomated) {
		if(!this.worldObj.isRemote) {
			TileEntityStargateControl stargate = this.getStargateControl();
			
			if(stargate != null) {
				stargate.changeShieldAutomated(shieldAutomated);
			}
		}
	}
	
	/**
	 * Changes the code which deactivates the shield of the stargate connected to this console.
	 * @param code - the code which deactivates the shield.
	 */
	public void changeShieldCode(int code) {
		if(!this.worldObj.isRemote) {
			TileEntityStargateControl stargate = this.getStargateControl();
			
			if(stargate != null) {
				stargate.changeShieldCode(code);
			}
		}
	}
	
	/**
	 * Returns a shield command packet. This is used to activate/deactivate the shield of stargate connected to this console.
	 * @param activate - true if the shield must be activated, false if it must be deactivated.
	 * @return a shield command packet.
	 */
	public Packet getShieldPacket(boolean activate) {
		return this.getCommandPacket(SHIELD, activate);
	}
	
	/**
	 * Returns a shield automated command packet. This is used to change the automated shield state of the stargate connected to this console.
	 * @param shieldAutomated - indicates whether the shield must be automatically activated.
	 * @return a shield automated command packet.
	 */
	public Packet getShieldAutomatedPacket(boolean shieldAutomated) {
		return this.getCommandPacket(SHIELD_AUTOMATED, shieldAutomated);
	}
	
	/**
	 * Returns a shield command packet. This is used to change the code which deactivates the shield of the stargate connected to this console.
	 * @param code - the code which deactivates the shield.
	 * @return a shield code command packet.
	 */
	public Packet getShieldCodePacket(int code) {
		return this.getCommandPacket(SHIELD_CODE, code);
	}
	
	@Override
	protected void onStargateConsoleDestroyed(TileEntityStargateControl stargate) {
		stargate.onShieldConsoleDestroyed();
	}
	
	@Override
	public Block getBaseBlock() {
		return StargateMod.block_shieldConsoleBase;
	}
	
	@Override
	public Block getPanelBlock() {
		return StargateMod.block_shieldConsolePanel;
	}
	
}

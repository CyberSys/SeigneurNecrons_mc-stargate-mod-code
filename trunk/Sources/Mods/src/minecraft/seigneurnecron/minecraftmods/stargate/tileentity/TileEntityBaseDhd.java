package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.block.Block;
import net.minecraft.network.packet.Packet;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;

/**
 * @author Seigneur Necron
 */
public class TileEntityBaseDhd extends TileEntityBaseStargateConsole {
	
	public static final String INV_NAME = "container.dhd";
	
	/**
	 * Activates the stargate connected to this DHD.
	 * @param address - the address of the gate to contact.
	 */
	public void activateStargate(String address) {
		if(!this.worldObj.isRemote) {
			TileEntityStargateControl stargate = this.getStargateControl();
			
			if(stargate != null) {
				stargate.onDhdOpenCommand(address);
			}
		}
	}
	
	/**
	 * Closes the stargate connected to this DHD.
	 */
	public void closeStargate() {
		if(!this.worldObj.isRemote) {
			TileEntityStargateControl stargate = this.getStargateControl();
			
			if(stargate != null) {
				stargate.onDhdCloseCommand();
			}
		}
	}
	
	/**
	 * Returns a stargate open command packet. This is used to activate the stargate connected to this DHD.
	 * @param address - the address of the gate to contact.
	 * @return a stargate open command packet.
	 */
	public Packet getStargateOpenPacket(String address) {
		return this.getCommandPacket(StargateCommandPacketMapping.getInstance().STARGATE_OPEN, address);
	}
	
	/**
	 * Returns a stargate open command packet. This is used to activate the stargate connected to this DHD.
	 * @return a stargate open command packet.
	 */
	public Packet getStargateClosePacket() {
		return this.getCommandPacket(StargateCommandPacketMapping.getInstance().STARGATE_CLOSE);
	}
	
	@Override
	protected void onStargateConsoleDestroyed(TileEntityStargateControl stargate) {
		// Nothing here.
	}
	
	@Override
	public Block getBaseBlock() {
		return StargateMod.block_dhdBase;
	}
	
	@Override
	public Block getPanelBlock() {
		return StargateMod.block_dhdPanel;
	}
	
}

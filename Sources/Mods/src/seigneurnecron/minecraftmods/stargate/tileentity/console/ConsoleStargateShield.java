package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldConsole;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;

/**
 * @author Seigneur Necron
 */
public class ConsoleStargateShield extends ConsoleStargate {
	
	// Constructors :
	
	public ConsoleStargateShield(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(world.isRemote) {
			ModLoader.openGUI(player, new GuiShieldConsole(this.tileEntity, player, this));
		}
		
		return true;
	}
	
	/**
	 * Activates/deactivates the shield of stargate connected to this console.
	 * @param activate - true if the shield must be activated, false if it must be deactivated.
	 */
	public void activateShield(boolean activate) {
		if(!this.tileEntity.worldObj.isRemote) {
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
		if(!this.tileEntity.worldObj.isRemote) {
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
		if(!this.tileEntity.worldObj.isRemote) {
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
		return this.tileEntity.getCommandPacket(StargateCommandPacketMapping.getInstance().SHIELD, activate);
	}
	
	/**
	 * Returns a shield automated command packet. This is used to change the automated shield state of the stargate connected to this console.
	 * @param shieldAutomated - indicates whether the shield must be automatically activated.
	 * @return a shield automated command packet.
	 */
	public Packet getShieldAutomatedPacket(boolean shieldAutomated) {
		return this.tileEntity.getCommandPacket(StargateCommandPacketMapping.getInstance().SHIELD_AUTOMATED, shieldAutomated);
	}
	
	/**
	 * Returns a shield code command packet. This is used to change the code which deactivates the shield of the stargate connected to this console.
	 * @param code - the code which deactivates the shield.
	 * @return a shield code command packet.
	 */
	public Packet getShieldCodePacket(int code) {
		return this.tileEntity.getCommandPacket(StargateCommandPacketMapping.getInstance().SHIELD_CODE, code);
	}
	
	@Override
	protected void onStargateConsoleDestroyed(TileEntityStargateControl stargate) {
		stargate.onShieldConsoleDestroyed();
	}
	
}

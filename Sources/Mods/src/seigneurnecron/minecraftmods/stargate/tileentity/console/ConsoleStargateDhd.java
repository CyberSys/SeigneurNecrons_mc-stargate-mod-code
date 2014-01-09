package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;

/**
 * @author Seigneur Necron
 */
public class ConsoleStargateDhd extends ConsoleStargate {
	
	// Constructors :
	
	public ConsoleStargateDhd(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(world.isRemote) {
			ModLoader.openGUI(player, new GuiDhd(this.tileEntity, player, this));
		}
		
		return true;
	}
	
	/**
	 * Activates the stargate connected to this DHD.
	 * @param address - the address of the gate to contact.
	 */
	public void activateStargate(String address) {
		if(!this.tileEntity.worldObj.isRemote) {
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
		if(!this.tileEntity.worldObj.isRemote) {
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
		return this.tileEntity.getCommandPacket(StargateCommandPacketMapping.getInstance().STARGATE_OPEN, address);
	}
	
	/**
	 * Returns a stargate open command packet. This is used to activate the stargate connected to this DHD.
	 * @return a stargate open command packet.
	 */
	public Packet getStargateClosePacket() {
		return this.tileEntity.getCommandPacket(StargateCommandPacketMapping.getInstance().STARGATE_CLOSE);
	}
	
}

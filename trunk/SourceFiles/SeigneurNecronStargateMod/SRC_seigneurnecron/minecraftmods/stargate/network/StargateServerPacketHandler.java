package seigneurnecron.minecraftmods.stargate.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargate;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;

/**
 * @author Seigneur Necron
 */
public class StargateServerPacketHandler extends StargatePacketHandler {
	
	@Override
	protected void handleCommandPacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int id = input.readInt();
			int dim = input.readInt();
			int x = input.readInt();
			int y = input.readInt();
			int z = input.readInt();
			
			World world = this.getWorldForDimension(dim);
			
			if(world != null) {
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				
				if(tileEntity != null && tileEntity instanceof TileEntityStargate) {
					String commandName = getNameFromCommandPacketId(id);
					
					if(commandName == TELEPORT && tileEntity instanceof TileEntityBaseTeleporter) {
						int xTp = input.readInt();
						int yTp = input.readInt();
						int zTp = input.readInt();
						((TileEntityBaseTeleporter) tileEntity).teleportPlayer((EntityPlayerMP) player, xTp, yTp, zTp);
					}
					else if(commandName == STARGATE_OPEN && tileEntity instanceof TileEntityBaseDhd) {
						String address = input.readUTF();
						((TileEntityBaseDhd) tileEntity).activateStargate(address);
					}
					else if(commandName == STARGATE_CLOSE && tileEntity instanceof TileEntityBaseDhd) {
						((TileEntityBaseDhd) tileEntity).closeStargate();
					}
					else if(commandName == STARGATE_CREATE && tileEntity instanceof TileEntityStargateControl) {
						String address = input.readUTF();
						((TileEntityStargateControl) tileEntity).createGate(address);
					}
					else if(commandName == SHIELD && tileEntity instanceof TileEntityBaseShieldConsole) {
						boolean activate = input.readBoolean();
						((TileEntityBaseShieldConsole) tileEntity).activateShield(activate);
					}
					else if(commandName == SHIELD_AUTOMATED && tileEntity instanceof TileEntityBaseShieldConsole) {
						boolean shieldAutomated = input.readBoolean();
						((TileEntityBaseShieldConsole) tileEntity).changeShieldAutomated(shieldAutomated);
					}
					else if(commandName == SHIELD_CODE && tileEntity instanceof TileEntityBaseShieldConsole) {
						int code = input.readInt();
						((TileEntityBaseShieldConsole) tileEntity).changeShieldCode(code);
					}
					else {
						StargateMod.debug("Error while reading a command packet : wrong id.", Level.WARNING, true);
					}
				}
			}
			
			input.close();
		}
		catch(IOException argh) {
			StargateMod.debug("Error while reading in a DataInputStream. Couldn't read a command packet.", Level.SEVERE, true);
			argh.printStackTrace();
		}
	}
	
	@Override
	public World getWorldForDimension(int dim) {
		return StargateMod.getServerWorldForDimension(dim);
	}
	
}

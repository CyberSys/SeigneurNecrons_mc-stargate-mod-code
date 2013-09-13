package seigneurnecron.minecraftmods.core.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityCommand extends TileEntityBasic {
	
	/**
	 * Returns a command packet.
	 * @param commandName - the command name.
	 * @param parameters - an array containing the parameters of the command.
	 * @return a command packet.
	 */
	public Packet getCommandPacket(int commandId, Object... parameters) {
		byte[] data;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		
		try {
			this.getTileEntityBasicData(output, commandId);
			
			for(Object object : parameters) {
				if(object instanceof Boolean) {
					output.writeBoolean((Boolean) object);
				}
				else if(object instanceof Integer) {
					output.writeInt((Integer) object);
				}
				else if(object instanceof Float) {
					output.writeFloat((Float) object);
				}
				else if(object instanceof Double) {
					output.writeDouble((Double) object);
				}
				else if(object instanceof String) {
					output.writeUTF((String) object);
				}
				else {
					throw new IOException("The type of this command parameter isn't compatible : " + object.getClass().getSimpleName());
				}
			}
			
			output.close();
			data = outputStream.toByteArray();
		}
		catch(IOException argh) {
			data = new byte[] {};
			SeigneurNecronMod.instance.log("Error while writing in a DataOutputStream. Couldn't create a command packet.", Level.SEVERE);
			argh.printStackTrace();
		}
		
		return new Packet250CustomPayload(this.getCommandChanel(), data);
	}
	
	/**
	 * Returns the chanel used to send/receive command packets.
	 * @return the chanel used to send/receive command packets.
	 */
	protected abstract String getCommandChanel();
	
}

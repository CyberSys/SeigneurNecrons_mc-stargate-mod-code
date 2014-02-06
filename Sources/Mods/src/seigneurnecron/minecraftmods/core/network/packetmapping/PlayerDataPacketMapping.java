package seigneurnecron.minecraftmods.core.network.packetmapping;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.IExtendedEntityProperties;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.entitydata.EntityData;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class PlayerDataPacketMapping extends PacketMapping<String> {
	
	// Constructors :
	
	protected PlayerDataPacketMapping(String chanel) {
		super(chanel);
	}
	
	// Methods :
	
	@Override
	public int minPacketLenght() {
		return 4; // id => 1 * intSize.
	}
	
	@Override
	public void handlePacket(INetworkManager manager, Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int id = input.readInt();
			IExtendedEntityProperties property = player.getExtendedProperties(this.getData(id));
			
			if(property instanceof EntityData) {
				((EntityData) property).loadProperties(input);
			}
			
			input.close();
		}
		catch(IOException argh) {
			SeigneurNecronMod.instance.log("Error while reading in a DataInputStream. Couldn't read a player data packet.", Level.SEVERE);
			argh.printStackTrace();
			return;
		}
	}
	
}

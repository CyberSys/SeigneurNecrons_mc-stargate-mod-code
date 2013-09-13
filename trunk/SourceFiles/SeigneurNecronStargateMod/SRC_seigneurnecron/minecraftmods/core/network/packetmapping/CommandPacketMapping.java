package seigneurnecron.minecraftmods.core.network.packetmapping;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.core.tileentity.TileEntityCommand;

/**
 * @author Seigneur Necron
 */
public class CommandPacketMapping extends PacketMapping<Command> {
	
	// Constructors :
	
	protected CommandPacketMapping(String chanel) {
		super(chanel);
	}
	
	// Methods :
	
	@Override
	public int minPacketLenght() {
		return 20; // id + dim + x + y + z => 5 * intSize.
	}
	
	@Override
	public void handlePacket(INetworkManager manager, Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int id = input.readInt();
			int dim = input.readInt();
			int x = input.readInt();
			int y = input.readInt();
			int z = input.readInt();
			
			World world = ModBase.getServerWorldForDimension(dim);
			
			if(world != null) {
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				
				if(tileEntity != null && tileEntity instanceof TileEntityCommand) {
					TileEntityCommand tileEntityCommand = (TileEntityCommand) tileEntity;
					Command command = this.getData(id);
					
					if(command != null) {
						command.run(tileEntityCommand, input, player);
					}
					else {
						SeigneurNecronMod.instance.log("Error while reading a command packet : wrong id.", Level.WARNING);
					}
				}
			}
			
			input.close();
		}
		catch(IOException argh) {
			SeigneurNecronMod.instance.log("Error while reading in a DataInputStream. Couldn't read a command packet.", Level.SEVERE);
			argh.printStackTrace();
		}
	}
	
}

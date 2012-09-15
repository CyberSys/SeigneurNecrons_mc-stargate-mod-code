package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldClient;
import cpw.mods.fml.common.network.Player;

public class StargateClientPacketHandler extends StargatePacketHandler {
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		// La longueur du packet doit etre au moins de 20 (id + dim + x + y + z).
		if(packet != null && packet.data != null && packet.length >= 20) {
			LinkedList<Byte> list = TileEntityStargate.arrayToList(packet.data);
			int id = TileEntityStargate.readInt(list);
			
			if(TileEntityStargate.isMapped(id)) {
				// Cas où le packet est un packet de mise a jours de tile entity :
				/*DEBUG*///StargateMod.debug("Client: packet reçu - maj tile entity (id = " + id + ")", true);
				int dim = TileEntityStargate.readInt(list);
				int x = TileEntityStargate.readInt(list);
				int y = TileEntityStargate.readInt(list);
				int z = TileEntityStargate.readInt(list);
				
				this.updateTileEntity(manager, packet, dim, x, y, z);
			}
		}
	}
	
	private void updateTileEntity(NetworkManager manager, Packet250CustomPayload packet, int dim, int x, int y, int z) {
		WorldClient world = ModLoader.getMinecraftInstance().theWorld;
		if(world != null && world.getWorldInfo().getDimension() == dim) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityStargate) {
				((TileEntityStargate) tileEntity).onDataPacket(manager, packet);
			}
		}
	}
	
}

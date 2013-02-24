package seigneurnecron.minecraftmods.stargate.client.network;

import java.util.LinkedList;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargate;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StargateClientPacketHandler extends StargatePacketHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// La longueur du packet doit etre au moins de 4 (int id).
		if(packet != null && packet.data != null && packet.length >= 4) {
			LinkedList<Byte> list = arrayToList(packet.data);
			int id = readInt(list);
			
			if(isMapped(id)) {
				// Cas ou le packet est un packet de mise a jours de tile entity.
				this.handleTileEntityUpdate(manager, packet);
			}
		}
	}
	
	private void handleTileEntityUpdate(INetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		readInt(list); // id
		int dim = readInt(list);
		int x = readInt(list);
		int y = readInt(list);
		int z = readInt(list);
		
		WorldClient world = ModLoader.getMinecraftInstance().theWorld;
		if(world != null && world.getWorldInfo().getDimension() == dim) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityStargate) {
				((TileEntityStargate) tileEntity).onDataPacket(manager, packet);
			}
		}
	}
	
}

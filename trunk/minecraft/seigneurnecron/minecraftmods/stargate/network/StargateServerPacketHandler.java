package seigneurnecron.minecraftmods.stargate.network;

import java.util.LinkedList;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.common.network.Player;

public class StargateServerPacketHandler extends StargatePacketHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// On verifie que le packet est bien un packet de ce mode.
		if(packet != null && packet.channel.equals(StargateMod.chanel)) {
			this.handlePacket(manager, packet, player);
		}
	}
	
	private void handlePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// La longueur du packet doit etre au moins de 4 (int id).
		if(packet.data != null && packet.length >= 4) {
			LinkedList<Byte> list = arrayToList(packet.data);
			int id = readInt(list);
			
			if(id == packetId_CloseGuiTeleporter || id == packetId_CloseGuiDhd || id == packetId_CloseGuiDetector) {
				// Cas ou le packet proviens de la fermeture d'une interface.
				this.updateTileEntityGui(manager, packet);
			}
		}
	}
	
	private void updateTileEntityGui(INetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		readInt(list); // id
		int dim = readInt(list);
		int x = readInt(list);
		int y = readInt(list);
		int z = readInt(list);
		
		WorldServer world = ModLoader.getMinecraftServerInstance().worldServerForDimension(dim);
		if(world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityGuiScreen) {
				TileEntityGuiScreen tileEntityGui = (TileEntityGuiScreen) tileEntity;
				tileEntityGui.onDataPacket(manager, packet);
				tileEntityGui.setEditable(true);
			}
		}
	}
	
}

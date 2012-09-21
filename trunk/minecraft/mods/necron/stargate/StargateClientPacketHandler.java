package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldClient;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.Player;

@SideOnly(Side.CLIENT)
public class StargateClientPacketHandler extends StargatePacketHandler {
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		// La longueur du packet doit etre au moins de 4 (int id).
		if(packet != null && packet.data != null && packet.length >= 4) {
			LinkedList<Byte> list = arrayToList(packet.data);
			int id = readInt(list);
			
			if(isMapped(id)) {
				// Cas ou le packet est un packet de mise a jours de tile entity.
				this.handleTileEntityUpdate(manager, packet);
			}
			else if(id == packetId_TeleportEntity) {
				// Cas ou le packet est un packet de teleportation d'entity.
				StargateMod.debug("Client: packet recu - teleportation", true);
				this.handleEntityTeleport(manager, packet);
			}
		}
	}
	
	private void handleTileEntityUpdate(NetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		int id = readInt(list);
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
	
	private void handleEntityTeleport(NetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		int id = readInt(list);
		double posX = readDouble(list);
		double posY = readDouble(list);
		double posZ = readDouble(list);
		float rotationYaw = readFloat(list);
		float rotationPitch = readFloat(list);
		double motionX = readDouble(list);
		double motionY = readDouble(list);
		double motionZ = readDouble(list);
		
		Entity entity = StargateMod.proxy.getClientPlayer().entityId == id ? StargateMod.proxy.getClientPlayer() : StargateMod.proxy.getClientWorld().getEntityByID(id);
		if(entity != null) {
			StargateMod.debug("joueur :" + (entity instanceof EntityPlayer), true);
			entity.setPositionAndRotation2(posX, posY, posZ, rotationYaw, rotationPitch, 3);
			entity.setVelocity(motionX, motionY, motionZ);
		}
		else {
			StargateMod.debug("entity == null", true);
		}
	}
	
}

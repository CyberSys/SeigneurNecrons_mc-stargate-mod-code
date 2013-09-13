package seigneurnecron.minecraftmods.core.entitydata;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import seigneurnecron.minecraftmods.core.mod.ModBase;

/**
 * @author Seigneur Necron
 */
public abstract class PlayerData extends EntityData<EntityPlayer> {
	
	// Constructor :
	
	protected PlayerData(EntityPlayer player) {
		super(player);
	}
	
	// Packet system :
	
	@Override
	protected void sendPacketToClients(Packet packet) {
		ModBase.sendPacketToPlayer(packet, this.entity);
	}
	
}

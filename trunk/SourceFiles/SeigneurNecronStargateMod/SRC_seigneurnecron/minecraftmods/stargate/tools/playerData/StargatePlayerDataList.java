package seigneurnecron.minecraftmods.stargate.tools.playerdata;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.entitydata.PlayerDataList;
import seigneurnecron.minecraftmods.core.loadable.Loadable;
import seigneurnecron.minecraftmods.core.network.packetmapping.PlayerDataPacketMapping;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargatePlayerDataPacketMapping;

/**
 * @author Seigneur Necron
 */
public abstract class StargatePlayerDataList<T extends Loadable> extends PlayerDataList<T> {
	
	// Constructors :
	
	protected StargatePlayerDataList(EntityPlayer player) {
		super(player);
	}
	
	// Packet system :
	
	@Override
	protected PlayerDataPacketMapping getPacketMapping() {
		return StargatePlayerDataPacketMapping.getInstance();
	}
	
}
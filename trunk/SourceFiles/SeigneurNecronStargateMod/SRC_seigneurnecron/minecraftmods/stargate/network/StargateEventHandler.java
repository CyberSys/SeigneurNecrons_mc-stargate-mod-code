package seigneurnecron.minecraftmods.stargate.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import seigneurnecron.minecraftmods.stargate.playerData.StargatePlayerProperties;

public class StargateEventHandler {
	
	@ForgeSubscribe
	public void onEntityConstructing(EntityConstructing event) {
		if(event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(StargatePlayerProperties.IDENTIFIER, new StargatePlayerProperties());
		}
	}
	
}

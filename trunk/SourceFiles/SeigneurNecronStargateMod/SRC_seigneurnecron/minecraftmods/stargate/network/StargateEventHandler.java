package seigneurnecron.minecraftmods.stargate.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerStargateData;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerTeleporterData;

/**
 * @author Seigneur Necron
 */
public class StargateEventHandler {
	
	@ForgeSubscribe
	public void onEntityConstructing(EntityConstructing event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			if(PlayerTeleporterData.get(player) == null) {
				PlayerTeleporterData.register(player);
			}
			
			if(PlayerStargateData.get(player) == null) {
				PlayerStargateData.register(player);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			PlayerTeleporterData.loadProxyData(player);
			
			PlayerStargateData.loadProxyData(player);
		}
	}
	
	@ForgeSubscribe
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			PlayerTeleporterData.saveProxyData(player);
			
			PlayerStargateData.saveProxyData(player);
		}
	}
	
}

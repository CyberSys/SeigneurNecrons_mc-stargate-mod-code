package seigneurnecron.minecraftmods.stargate.event;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import seigneurnecron.minecraftmods.stargate.entity.damagesource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerStargateData;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerTeleporterData;

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
	
	@ForgeSubscribe
	public void onLivingDropsEvent(LivingDropsEvent event) {
		if(!event.entity.worldObj.isRemote && event.source == CustomDamageSource.iris && event.entity instanceof EntityLiving) {
			event.setCanceled(true);
		}
	}
	
}

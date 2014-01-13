package seigneurnecron.minecraftmods.stargate.event;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.entity.damagesource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.item.ItemSoul;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerSoulCountData;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerStargateData;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerTeleporterData;

/**
 * @author Seigneur Necron
 */
public class StargateEventHandler {
	
	// NBTTags names
	
	private static final String TAG = "tag";
	private static final String MONSTER_ID = "monsterId";
	
	// Constants :
	
	private static final Random RAND = new Random();
	
	// Methods :
	
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
			
			if(PlayerSoulCountData.get(player) == null) {
				PlayerSoulCountData.register(player);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			PlayerTeleporterData.loadProxyData(player);
			
			PlayerStargateData.loadProxyData(player);
			
			PlayerSoulCountData.loadProxyData(player);
		}
	}
	
	@ForgeSubscribe
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			PlayerTeleporterData.saveProxyData(player);
			
			PlayerStargateData.saveProxyData(player);
			
			PlayerSoulCountData.saveProxyData(player);
		}
	}
	
	@ForgeSubscribe
	public void onLivingDropsEvent(LivingDropsEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityLiving) {
			if(event.source == CustomDamageSource.IRIS) {
				event.setCanceled(true);
			}
			else {
				int monsterId = EntityList.getEntityID(event.entity);
				ItemSoulCrystalFull crystal = ItemSoulCrystalFull.getCrystalFromMonsterId(monsterId);
				
				if(crystal != null && RAND.nextDouble() < crystal.soulDropProba) {
					NBTTagCompound tag = new NBTTagCompound(TAG);
					tag.setInteger(MONSTER_ID, monsterId);
					
					ItemStack itemStack = new ItemStack(StargateMod.item_soul);
					itemStack.setTagCompound(tag);
					
					EntityItem entityItem = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, itemStack);
					event.drops.add(entityItem);
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityItemPickupEvent(EntityItemPickupEvent event) {
		if(!event.entity.worldObj.isRemote) {
			ItemStack itemStack = event.item.getEntityItem();
			
			if(itemStack.getItem() instanceof ItemSoul) {
				if(itemStack.hasTagCompound()) {
					int monsterId = itemStack.getTagCompound().getInteger(MONSTER_ID);
					
					if(monsterId != 0) {
						PlayerSoulCountData playerData = PlayerSoulCountData.get(event.entityPlayer);
						List<SoulCount> list = playerData.getDataList();
						boolean ok = false;
						
						for(SoulCount soulCount : list) {
							if(soulCount.id == monsterId) {
								soulCount.count++;
								playerData.syncProperties();
								ok = true;
								break;
							}
						}
						
						if(!ok) {
							playerData.addElementAndSync(new SoulCount(monsterId, 0));
						}
					}
				}
				
				event.setCanceled(true);
				event.item.setDead();
			}
		}
	}
	
}

package seigneurnecron.minecraftmods.core.teleportation;

import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet28EntityVelocity;
import net.minecraft.network.packet.Packet34EntityTeleport;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldServer;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.reflection.Reflection;
import seigneurnecron.minecraftmods.core.reflection.ReflectionException;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * <br />
 * This class is used to teleport entities at the given coordinates, optionally between dimensions, without creating or searching a portal. <br />
 * It is used to teleport entities without using the vanilla mechanism (hell and end portals). <br />
 * The code calling this class methods must ensure itself that the required stuff (a portal, a teleporter, or whatever you want) is available at the given coordinates.
 * @author Seigneur Necron
 */
public final class Teleporter {
	
	// Constructors :
	
	private Teleporter() {
		// This is an utility class. It don't have to be instanciated.
	}
	
	// Methods :
	
	/**
	 * Teleports the entity to the given dimension and coordinates, without creating or searching a portal. <br />
	 * This is used to teleport entities without using the vanilla mechanism (hell and end portals). <br />
	 * The code calling this method must ensure itself that the required stuff (a portal, a teleporter, or whatever you want) is available at the given coordinates.
	 * @param entity - the entity to teleport.
	 * @param newDimension - the dimension.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 */
	public static Entity teleportEntity(Entity entity, int newDimension, double x, double y, double z, float rotationYaw, float rotationPitch, double xMotion, double yMotion, double zMotion) {
		if(!entity.isDead) {
			int oldDimension = entity.dimension;
			boolean changedDimension = oldDimension != newDimension;
			
			MinecraftServer minecraftserver = MinecraftServer.getServer();
			WorldServer oldWorld = minecraftserver.worldServerForDimension(oldDimension);
			WorldServer newWorld = minecraftserver.worldServerForDimension(newDimension);
			
			if(changedDimension) {
				if(entity instanceof EntityPlayerMP) {
					EntityPlayerMP player = (EntityPlayerMP) entity;
					
					player.dimension = newDimension;
					player.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(player.dimension, (byte) player.worldObj.difficultySetting, newWorld.getWorldInfo().getTerrainType(), newWorld.getHeight(), player.theItemInWorldManager.getGameType()));
					oldWorld.removePlayerEntityDangerously(player);
					player.isDead = false;
					
					transferEntityToWorld(entity, newWorld, x, y, z, rotationYaw, rotationPitch);
					
					ServerConfigurationManager configurationManager = player.mcServer.getConfigurationManager();
					
					configurationManager.func_72375_a(player, oldWorld);
					player.playerNetServerHandler.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
					player.theItemInWorldManager.setWorld(newWorld);
					configurationManager.updateTimeAndWeatherForPlayer(player, newWorld);
					configurationManager.syncPlayerInventory(player);
					Iterator iterator = player.getActivePotionEffects().iterator();
					
					while(iterator.hasNext()) {
						PotionEffect potioneffect = (PotionEffect) iterator.next();
						player.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(player.entityId, potioneffect));
					}
					
					GameRegistry.onPlayerChangedDimension(player);
				}
				else {
					if(entity instanceof EntityMinecartContainer) {
						try {
							Reflection.setBoolean(EntityMinecartContainer.class, entity, SeigneurNecronMod.instance.getConfig().entityMinecartContainer_dropContentWhenDead, false);
						}
						catch(ReflectionException argh) {
							SeigneurNecronMod.instance.log(argh.getMessage(), Level.SEVERE);
						}
					}
					
					entity.dimension = newDimension;
					entity.worldObj.removeEntity(entity);
					entity.isDead = false;
					
					transferEntityToWorld(entity, newWorld, x, y, z, rotationYaw, rotationPitch);
					
					Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), newWorld);
					
					if(newEntity != null) {
						newEntity.copyDataFrom(entity, true);
						newEntity.setLocationAndAngles(x, y, z, rotationYaw, rotationPitch);
						setEntityMotion(newEntity, xMotion, yMotion, zMotion);
						newWorld.spawnEntityInWorld(newEntity);
					}
					
					entity.isDead = true;
					
					oldWorld.resetUpdateEntityTick();
					newWorld.resetUpdateEntityTick();
					
					entity = newEntity;
				}
			}
			else {
				// Teleports the entity.
				if(entity instanceof EntityPlayerMP) {
					EntityPlayerMP player = (EntityPlayerMP) entity;
					player.playerNetServerHandler.setPlayerLocation(x, y, z, rotationYaw, rotationPitch);
				}
				else {
					entity.setLocationAndAngles(x, y, z, rotationYaw, rotationPitch);
					ModBase.sendPacketToAllPlayers(new Packet34EntityTeleport(entity));
				}
				
				// Updates the entity velocity.
				setEntityMotion(entity, xMotion, yMotion, zMotion);
				ModBase.sendPacketToAllPlayers(new Packet28EntityVelocity(entity));
			}
			
			return entity;
		}
		
		return null;
	}
	
	private static void transferEntityToWorld(Entity entity, WorldServer newWorld, double x, double y, double z, float rotationYaw, float rotationPitch) {
		if(entity.isEntityAlive()) {
			newWorld.spawnEntityInWorld(entity);
			entity.setLocationAndAngles(x, y, z, rotationYaw, rotationPitch);
			newWorld.updateEntityWithOptionalForce(entity, false);
		}
		
		entity.setWorld(newWorld);
	}
	
	private static void setEntityMotion(Entity entity, double xMotion, double yMotion, double zMotion) {
		entity.motionX = xMotion;
		entity.motionY = yMotion;
		entity.motionZ = zMotion;
	}
	
}

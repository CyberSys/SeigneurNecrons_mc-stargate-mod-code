package seigneurnecron.minecraftmods.core.mod;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.SeigneurNecronModConfig;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * A class which can be extended to make a mod main class. A lot of work is already done by that class and the {@link ModConfig} class. <br />
 * You <b>MUST</b> override the <b>preInit</b>, <b>init</b> and <b>postInit</b> methods and add the <b>@EventHandler</b> annotation on them. <br />
 * Take a look at the {@link SeigneurNecronMod} and {@link SeigneurNecronModConfig} classes for an exemple of how to make a basic mod.
 * @author Seigneur Necron
 * @param <M> the mod class.
 * @param <C> the mod config class.
 */
public abstract class ModBase<M extends ModBase, C extends ModConfig> {
	
	// Constants :
	
	public static final Level DEFAULT_LOGGER_LEVEL = Level.WARNING;
	
	// Mod basic informations :
	
	/**
	 * The mod asset prefix.
	 */
	private String assetPrefix = "";
	
	/**
	 * Returns the mod asset prefix.
	 * @return the mod asset prefix.
	 */
	public String getAssetPrefix() {
		return this.assetPrefix;
	}
	
	/**
	 * Returns the mod id. <br />
	 * Here, you must return the mod id that you put in the "@Mod" annotation.
	 * @return the mod id.
	 */
	protected abstract String getModId();
	
	/**
	 * Initialize the mod asset prefix using the mod id.
	 */
	protected void initAssetPrefix() {
		this.assetPrefix = this.getModId().toLowerCase() + ":";
	}
	
	// Logger :
	
	/**
	 * The logger of this mod.
	 */
	private Logger logger;
	
	/**
	 * Create a logger for this mod.
	 * @return the logger of this mod.
	 */
	protected Logger createModLogger() {
		return Logger.getLogger(this.getModId());
	}
	
	/**
	 * Returns the mod logger.
	 * @return the mod logger.
	 */
	public Logger getLogger() {
		return this.logger;
	}
	
	// Configuration :
	
	/**
	 * The configuration of this mod.
	 */
	private C config;
	
	/**
	 * Create a configuration for this mod.
	 * @param event - the preinitialization event.
	 * @return the configuration of this mod.
	 */
	protected abstract C createModConfig(FMLPreInitializationEvent event);
	
	/**
	 * Returns the mod configuration.
	 * @return the mod configuration.
	 */
	public C getConfig() {
		return this.config;
	}
	
	// Chunk loader :
	
	/**
	 * Create a chunk manager for this mod.
	 * @return - the chunk manager of this mod.
	 */
	protected LoadingCallback createChunkManager() {
		return null;
	}
	
	// Initialization :
	
	/**
	 * The preInit methods of the extending classes MUST override and call this.
	 * @param event - the preinitialization event.
	 */
	protected void preInit(FMLPreInitializationEvent event) {
		this.initAssetPrefix();
		
		this.logger = this.createModLogger();
		this.logger.setParent(FMLLog.getLogger());
		
		this.config = this.createModConfig(event);
		this.config.load();
		
		Level level;
		
		try {
			level = Level.parse(this.config.loggerLevel);
		}
		catch(IllegalArgumentException argh) {
			this.log("The logger level specified in " + this.getModId() + ".cfg isn't valid. Level set to " + DEFAULT_LOGGER_LEVEL.getName() + ".", Level.WARNING);
			level = DEFAULT_LOGGER_LEVEL;
		}
		
		this.logger.setLevel(level);
		
		LoadingCallback chunkManager = this.createChunkManager();
		
		if(chunkManager != null) {
			ForgeChunkManager.setForcedChunkLoadingCallback(this, chunkManager);
		}
	}
	
	/**
	 * The init methods of the extending classes MUST override and call this.
	 * @param event - the initialization event.
	 */
	protected void init(FMLInitializationEvent event) {
		// Nothing here.
	}
	
	/**
	 * The postInit methods of the extending classes MUST override and call this.
	 * @param event - the postinitialization event.
	 */
	protected void postInit(FMLPostInitializationEvent event) {
		this.config.saveConfig();
	}
	
	// Registry methods :
	
	/**
	 * Registers a block.
	 * @param block - the block to register.
	 */
	public void registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
	}
	
	/**
	 * Registers a shaped recipe.
	 * @param output - the output item stack.
	 * @param params - the items used in the recipe.
	 */
	protected void addRecipe(ItemStack output, Object... params) {
		GameRegistry.addRecipe(output, params);
	}
	
	/**
	 * Registers a shapeless recipe.
	 * @param output - the output item stack.
	 * @param params - the items used in the recipe.
	 */
	protected void addShapelessRecipe(ItemStack output, Object... params) {
		GameRegistry.addShapelessRecipe(output, params);
	}
	
	/**
	 * Registers a smelting recipe.
	 * @param output - the ouput item stack.
	 * @param input - the input item id.
	 */
	protected void addSmelting(ItemStack output, int input) {
		GameRegistry.addSmelting(input, output, 1);
	}
	
	/**
	 * Registers an dispenser behavior for the given item.
	 * @param item - the item.
	 * @param behavior - the dispenser behavior.
	 */
	protected void registerDispenserBehavior(Item item, IBehaviorDispenseItem behavior) {
		ModLoader.addDispenserBehavior(item, behavior);
	}
	
	/**
	 * Registers a tileEntity.
	 * @param tileEntityClass - a tileEntity class.
	 * @param id - a unique name.
	 */
	protected void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, id);
	}
	
	/**
	 * Regisers an entity.
	 * @param entityClass - the entity class.
	 * @param entityName - a unique name for the entity.
	 * @param id - a mod specific ID for the entity
	 * @param trackingRange - the range at which MC will send tracking updates.
	 * @param updateFrequency - the frequency of tracking updates.
	 * @param sendsVelocityUpdates - whether to send velocity information packets as well.
	 */
	protected void registerEntitiy(Class<? extends Entity> entityClass, String entityName, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, id, this, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	/**
	 * Registers an event handler.
	 * @param eventHandler - an event handler instance.
	 */
	protected void registerEventHandler(Object eventHandler) {
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}
	
	/**
	 * Registers a gui handler.
	 * @param handler - a gui handler instance.
	 */
	protected void registerGuiHandler(IGuiHandler handler) {
		NetworkRegistry.instance().registerGuiHandler(this, handler);
	}
	
	/**
	 * Registers a world generator.
	 * @param generator - a world generator instance.
	 */
	protected void registerWorldGenerator(IWorldGenerator generator) {
		GameRegistry.registerWorldGenerator(generator);
	}
	
	/**
	 * Changes the tool and the harvest level needed to harvest a block.
	 * @param block - the block to register.
	 * @param toolClass - the tool class to register as able to remove this block. You may register the same block multiple times with different tool classes, if multiple tool types can be used to harvest this block.
	 * @param harvestLevel - the minimum tool harvest level required to successfully harvest the block.
	 */
	protected void setBlockHarvestLevel(Block block, String toolClass, int harvestLevel) {
		MinecraftForge.setBlockHarvestLevel(block, toolClass, harvestLevel);
	}
	
	/**
	 * Adds english and french translations for a block or an item.
	 * @param instance - a block or an item.
	 * @param enName - the english translation.
	 * @param frName - the french translation.
	 */
	protected void addName(Object instance, String enName, String frName) {
		LanguageRegistry.instance().addNameForObject(instance, "en_US", enName);
		LanguageRegistry.instance().addNameForObject(instance, "en_UK", enName);
		LanguageRegistry.instance().addNameForObject(instance, "fr_FR", frName);
		LanguageRegistry.instance().addNameForObject(instance, "fr_CA", frName);
	}
	
	/**
	 * Adds english and french translations for a string.
	 * @param key - a string that can be used in the gui.
	 * @param enName - the english translation.
	 * @param frName - the french translation.
	 */
	protected void addName(String key, String enName, String frName) {
		LanguageRegistry.instance().addStringLocalization(key, "en_US", enName);
		LanguageRegistry.instance().addStringLocalization(key, "en_UK", enName);
		LanguageRegistry.instance().addStringLocalization(key, "fr_FR", frName);
		LanguageRegistry.instance().addStringLocalization(key, "fr_CA", frName);
	}
	
	// Logger methods :
	
	/**
	 * Logs a message at the default message level (INFO).
	 * @param message - the string to log.
	 */
	public void log(String message) {
		this.log(message, Level.INFO);
	}
	
	/**
	 * Logs a message at the given message level.
	 * @param message - the message to log.
	 * @param level - the message level.
	 */
	public void log(String message, Level level) {
		this.logger.log(level, message);
	}
	
	// Utility methods - world :
	
	/**
	 * Returns the server world corresponding to the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the server world corresponding to the dimension id if it exists, else null.
	 */
	public static WorldServer getServerWorldForDimension(int dim) {
		return ModLoader.getMinecraftServerInstance().worldServerForDimension(dim);
	}
	
	/**
	 * Returns the client world corresponding to the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the client world corresponding to the dimension id if it exists, else null.
	 */
	public static WorldClient getClientWorldForDimension(int dim) {
		WorldClient world = ModLoader.getMinecraftInstance().theWorld;
		
		if(world != null && world.provider.dimensionId != dim) {
			return null;
		}
		
		return world;
	}
	
	/**
	 * Returns the world corresponding to that side and the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the world corresponding to the side and the dimension id if it exists, else null.
	 */
	public static World getSideWorldForDimension(int dim) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if(side == Side.SERVER) {
			return getServerWorldForDimension(dim);
		}
		else {
			return getClientWorldForDimension(dim);
		}
	}
	
	// Utility methods - packets :
	
	/**
	 * Sends a packet from the client to the server.
	 * @param packet - the packet to send.
	 */
	public static void sendPacketToServer(Packet packet) {
		PacketDispatcher.sendPacketToServer(packet);
	}
	
	/**
	 * Sends a packet from the server to all players.
	 * @param packet - the packet to send.
	 */
	public static void sendPacketToAllPlayers(Packet packet) {
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}
	
	/**
	 * Sends a packet from the server to all players in the specified dimension.
	 * @param packet - the packet to send.
	 * @param dimension - the dimension in which the players have to be.
	 */
	public static void sendPacketToAllPlayersInDimension(Packet packet, int dimension) {
		PacketDispatcher.sendPacketToAllInDimension(packet, dimension);
	}
	
	/**
	 * Sends a packet from the server to a player.
	 * @param packet - the packet to send.
	 * @param player - the player.
	 */
	public static void sendPacketToPlayer(Packet packet, EntityPlayer player) {
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}
	
}

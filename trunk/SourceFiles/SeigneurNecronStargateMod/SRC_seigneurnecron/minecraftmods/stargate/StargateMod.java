package seigneurnecron.minecraftmods.stargate;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.registerCommandPackets;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.registerPlayerDataPacket;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.registerTileEntityPacket;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.stargate.block.BlockBaseDhd;
import seigneurnecron.minecraftmods.stargate.block.BlockBaseShieldConsole;
import seigneurnecron.minecraftmods.stargate.block.BlockBaseTeleporter;
import seigneurnecron.minecraftmods.stargate.block.BlockChevron;
import seigneurnecron.minecraftmods.stargate.block.BlockChevronOn;
import seigneurnecron.minecraftmods.stargate.block.BlockDetector;
import seigneurnecron.minecraftmods.stargate.block.BlockFastStargate;
import seigneurnecron.minecraftmods.stargate.block.BlockFastStargate2;
import seigneurnecron.minecraftmods.stargate.block.BlockFastStargate3;
import seigneurnecron.minecraftmods.stargate.block.BlockKawoosh;
import seigneurnecron.minecraftmods.stargate.block.BlockMobGenerator;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahAlloy;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahBlock;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahOre;
import seigneurnecron.minecraftmods.stargate.block.BlockPanelDhd;
import seigneurnecron.minecraftmods.stargate.block.BlockPanelShieldConsole;
import seigneurnecron.minecraftmods.stargate.block.BlockPanelTeleporter;
import seigneurnecron.minecraftmods.stargate.block.BlockSelfPoweredRedstoneLight;
import seigneurnecron.minecraftmods.stargate.block.BlockShield;
import seigneurnecron.minecraftmods.stargate.block.BlockStargateControl;
import seigneurnecron.minecraftmods.stargate.block.BlockStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.block.BlockVortex;
import seigneurnecron.minecraftmods.stargate.block.material.VortexMaterial;
import seigneurnecron.minecraftmods.stargate.block.replace.BlockGlassDropable;
import seigneurnecron.minecraftmods.stargate.block.replace.BlockGlassPaneDropable;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiBase;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiDetector;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiShieldConsole;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.client.network.StargateClientPacketHandler;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFishHook;
import seigneurnecron.minecraftmods.stargate.entity.EntityNapalm;
import seigneurnecron.minecraftmods.stargate.entity.EntityNuke;
import seigneurnecron.minecraftmods.stargate.entity.damageSource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.entity.dispenserBehavior.DispenserBehaviorCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.entity.dispenserBehavior.DispenserBehaviorCustomFireBall;
import seigneurnecron.minecraftmods.stargate.item.ItemCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.item.ItemCustomFireBall;
import seigneurnecron.minecraftmods.stargate.item.ItemEmptySoulCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemShieldRemote;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemStargate;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemExplosiveFireStaff;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaff;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemNapalmStaff;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemNukeStaff;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahAxe;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahBoots;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahBow;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahFishingRod;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahHelmet;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahHoe;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahLegs;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahLighter;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahPickaxe;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahPlate;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahShears;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahShovel;
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahSword;
import seigneurnecron.minecraftmods.stargate.network.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.network.StargateEventHandler;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.network.StargateServerPacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerStargateData;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerTeleporterData;
import seigneurnecron.minecraftmods.stargate.world.NaquadahGenerator;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

// #### TODOs ############################################################################################### //
//                                                                                                            //
// TODO - Travel between dimensions. (8 and 9 chevrons activation sequences are allready done)                //
//                                                                                                            //
// TODO - New coordinates system.                                                                             //
// - 3 chevrons x 15 symbols for X (15 * 14 * 13 = 2730 chunks)                                               //
// - 3 chevrons x 15 symbols for Z (15 * 14 * 13 = 2730 chunks)                                               //
// - 1 chevron x 5 symbols for Y (5 groups of 3 chunks - the last chunk isn't reachable)                      //
// - 1 chevron x 3 symbols for the dimension (3 dimensions)                                                   //
// - 1 chevron x 1 symbol for the special address mode                                                        //
// Special addresses choosed by users can be assigned to gates, using the GUI of the StargateControl block.   //
// With the 9th chevron, it is possible to :                                                                  //
// - put several gates in a single chunk.                                                                     //
// - put a gate in a chunk beyon the normal limit ( -2730 / 2 < X and Z < 2730 / 2 - 1).                      //
// - separate a gate from the normal network to make a gate address difficult to find.                        //
//                                                                                                            //
// TODO - Find a way to get the list of the texture files in the "sign" folder.                               //
// This would make very easy to add new custom textures for sign.                                             //
// The list of textures must be updated when the user changes the texture pack.                               //
//                                                                                                            //
// ########################################################################################################## //

// FIXME - trouver comment enregistrer des infos dans le monde.

// FIXME - lors de la connexion d'un client a un server, verifier que les configurations sont les meme.
// Refuser la connexion si les configurations sont differentes.
// Les id de blocs/item sont final, pas de remapage possible.

// FIXME - ajouter les sons manquants - CF autres fixme.

// FIXME - remettre obfuscated a true.
// FIXME - verifier que la balise @author est bien dans toutes les classes.

/**
 * SeigneurNecron's Stargate Mod main class.
 * @author Seigneur Necron
 */
@Mod(modid = StargateMod.MOD_ID, name = StargateMod.MOD_NAME, version = StargateMod.VERSION)
@NetworkMod(channels = {StargateMod.CHANEL_TILE_ENTITY, StargateMod.CHANEL_COMMANDS, StargateMod.CHANEL_PLAYER_DATA}, clientSideRequired = true, serverSideRequired = true, packetHandler = StargatePacketHandler.class, clientPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.CHANEL_TILE_ENTITY, StargateMod.CHANEL_COMMANDS, StargateMod.CHANEL_PLAYER_DATA}, packetHandler = StargateClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.CHANEL_TILE_ENTITY, StargateMod.CHANEL_COMMANDS, StargateMod.CHANEL_PLAYER_DATA}, packetHandler = StargateServerPacketHandler.class))
public class StargateMod {
	
	// Stargate mod basic informations :
	
	public static final String MOD_ID = "seigneur_necron_stargate_mod";
	public static final String MOD_NAME = "SeigneurNecron's Stargate Mod";
	public static final String VERSION = "[1.6.2] v3.1.0";
	
	public static final String CHANEL_TILE_ENTITY = "SNSM_TileEntity";
	public static final String CHANEL_COMMANDS = "SNSM_Commands";
	public static final String CHANEL_PLAYER_DATA = "SNSM_PlayerData";
	
	@Instance(StargateMod.MOD_ID)
	public static StargateMod instance;
	
	@SidedProxy(clientSide = "seigneurnecron.minecraftmods.stargate.client.network.StargateClientProxy", serverSide = "seigneurnecron.minecraftmods.stargate.network.StargateCommonProxy")
	public static StargateCommonProxy proxy;
	
	// Debug mod :
	
	private static Logger logger;
	private static final StringBuffer logBuffer = new StringBuffer();
	public static final boolean obfuscated = false; // FIXME - remettre a true.
	
	// Stargate assets :
	
	public static final String ASSETS_PREFIX = MOD_ID.toLowerCase() + ":";
	
	// Some constants :
	
	public static final float RESISTANT_BLOCKS_HARDNESS = 10.0F;
	public static final float RESISTANT_BLOCKS_RESISTANCE = 2000.0F;
	
	// Materials :
	
	public static final Material material_vortex = new VortexMaterial();
	public static final String naquadahMaterialName = "Naquadah";
	public static EnumToolMaterial naquadahToolMaterial;
	public static EnumArmorMaterial naquadahArmorMaterial;
	
	// Creative tabs :
	
	public static CreativeTabs stargateBlocksTab;
	public static CreativeTabs stargateItemsTab;
	
	public static final String stargateBlocksTabName = "stargateBlocksTab";
	public static final String stargateItemsTabName = "stargateItemsTab";
	
	// Blocks :
	
	public static Block block_naquadahOre;
	public static Block block_naquadahBlock;
	public static Block block_naquadahAlloy;
	public static Block block_chevronOff;
	public static Block block_chevronOn;
	public static Block block_stargateControl;
	public static Block block_vortex;
	public static Block block_shield;
	public static Block block_shieldedVortex;
	public static Block block_kawoosh;
	public static Block block_dhdPanel;
	public static Block block_dhdBase;
	public static Block block_teleporterPanel;
	public static Block block_teleporterBase;
	public static Block block_shieldConsolePanel;
	public static Block block_shieldConsoleBase;
	public static Block block_detector;
	public static Block block_mobGenerator;
	public static Block block_stuffLevelUpTable;
	public static Block block_selfPoweredRedstoneLight;
	public static Block block_fastStargate;
	public static Block block_fastStargate2;
	public static Block block_fastStargate3;
	
	// Modified base blocks :
	
	public static Block block_glassDropable;
	public static Block block_thinGlassDropable;
	
	// Items :
	
	public static Item item_naquadahOre;
	public static Item item_naquadahIngot;
	public static Item item_crystal;
	public static Item item_crystalStargate;
	public static Item item_crystalDhd;
	public static Item item_crystalTeleporter;
	public static Item item_crystalScanner;
	public static Item item_crystalSoulEmpty;
	public static Item item_crystalCreation;
	public static Item item_crystalShield;
	public static Item item_chevronCompound;
	public static Item item_stargateControlUnit;
	public static Item item_dhdControlUnit;
	public static Item item_teleporterControlUnit;
	public static Item item_detectorControlUnit;
	public static Item item_mobGeneratorControlUnit;
	public static Item item_stuffLevelUpTableControlUnit;
	public static Item item_shieldControlUnit;
	public static Item item_dhdPanel;
	public static Item item_touchScreen;
	public static Item item_shieldRemote;
	
	public static Item item_naquadahPickaxe;
	public static Item item_naquadahShovel;
	public static Item item_naquadahAxe;
	public static Item item_naquadahHoe;
	public static Item item_naquadahSword;
	public static Item item_naquadahBow;
	public static Item item_naquadahLighter;
	public static Item item_naquadahShears;
	public static Item item_naquadahFishingRod;
	public static Item item_fireStaff;
	public static Item item_explosiveFireStaff;
	public static Item item_nukeStaff;
	public static Item item_napalmStaff;
	public static Item item_customFireBall;
	public static Item item_customExplosiveFireBall;
	
	public static Item item_naquadahHelmet;
	public static Item item_naquadahPlate;
	public static Item item_naquadahLegs;
	public static Item item_naquadahBoots;
	
	public static Item item_crystalSoulChicken;
	public static Item item_crystalSoulCow;
	public static Item item_crystalSoulMooshroom;
	public static Item item_crystalSoulPig;
	public static Item item_crystalSoulSheep;
	public static Item item_crystalSoulOcelot;
	public static Item item_crystalSoulWolf;
	public static Item item_crystalSoulIronGolem;
	public static Item item_crystalSoulSnowman;
	public static Item item_crystalSoulBlaze;
	public static Item item_crystalSoulCreeper;
	public static Item item_crystalSoulEnderman;
	public static Item item_crystalSoulGiantZombie;
	public static Item item_crystalSoulSilverfish;
	public static Item item_crystalSoulSkeleton;
	public static Item item_crystalSoulSpider;
	public static Item item_crystalSoulCaveSpider;
	public static Item item_crystalSoulWitch;
	public static Item item_crystalSoulWither;
	public static Item item_crystalSoulZombie;
	public static Item item_crystalSoulPigZombie;
	public static Item item_crystalSoulSquid;
	public static Item item_crystalSoulDragon;
	public static Item item_crystalSoulGhast;
	public static Item item_crystalSoulSlime;
	public static Item item_crystalSoulMagmaCube;
	public static Item item_crystalSoulBat;
	public static Item item_crystalSoulVillager;
	public static Item item_crystalSoulHorse;
	
	// Block names :
	
	public static final String blockName_naquadahOre = "block_naquadahOre";
	public static final String blockName_naquadahBlock = "block_naquadahBlock";
	public static final String blockName_naquadahAlloy = "block_naquadahAlloy";
	public static final String blockName_chevron = "block_chevron";
	public static final String blockName_chevronOff = blockName_chevron + "Off";
	public static final String blockName_chevronOn = blockName_chevron + "On";
	public static final String blockName_stargateControl = "block_stargateControl";
	public static final String blockName_vortex = "block_vortex";
	public static final String blockName_shield = "block_shield";
	public static final String blockName_shieldedVortex = "block_shieldedVortex";
	public static final String blockName_kawoosh = "block_kawoosh";
	public static final String blockName_dhdPanel = "block_dhdPanel";
	public static final String blockName_dhdBase = "block_dhdBase";
	public static final String blockName_teleporterPanel = "block_teleporterPanel";
	public static final String blockName_teleporterBase = "block_teleporterBase";
	public static final String blockName_shieldConsolePanel = "block_shieldConsolePanel";
	public static final String blockName_shieldConsoleBase = "block_shieldConsoleBase";
	public static final String blockName_detector = "block_detector";
	public static final String blockName_mobGenerator = "block_mobGenerator";
	public static final String blockName_stuffLevelUpTable = "block_stuffLevelUpTable";
	public static final String blockName_selfPoweredRedstoneLight = "block_selfPoweredRedstoneLight";
	public static final String blockName_fastStargate = "block_fastStargate";
	public static final String blockName_fastStargate2 = "block_fastStargate2";
	public static final String blockName_fastStargate3 = "block_fastStargate3";
	
	// Item names :
	
	public static final String itemName_naquadahOre = "item_naquadahOre";
	public static final String itemName_naquadahIngot = "item_naquadahIngot";
	public static final String itemName_crystal = "item_crystal";
	public static final String itemName_crystalStargate = "item_crystalStargate";
	public static final String itemName_crystalDhd = "item_crystalDhd";
	public static final String itemName_crystalTeleporter = "item_crystalTeleporter";
	public static final String itemName_crystalScanner = "item_crystalScanner";
	public static final String itemName_crystalSoulEmpty = "item_crystalSoulEmpty";
	public static final String itemName_crystalCreation = "item_crystalCreation";
	public static final String itemName_crystalShield = "item_crystalShield";
	public static final String itemName_chevronCompound = "item_chevronCompound";
	public static final String itemName_stargateControlUnit = "item_stargateControlUnit";
	public static final String itemName_dhdControlUnit = "item_dhdControlUnit";
	public static final String itemName_teleporterControlUnit = "item_teleporterControlUnit";
	public static final String itemName_detectorControlUnit = "item_detectorControlUnit";
	public static final String itemName_mobGeneratorControlUnit = "item_mobGeneratorControlUnit";
	public static final String itemName_stuffLevelUpTableControlUnit = "item_stuffLevelUpTableControlUnit";
	public static final String itemName_shieldControlUnit = "item_shieldControlUnit";
	public static final String itemName_dhdPanel = "item_dhdPanel";
	public static final String itemName_touchScreen = "item_touchScreen";
	public static final String itemName_shieldRemote = "item_shieldRemote";
	
	public static final String itemName_naquadahPickaxe = "item_naquadahPickaxe";
	public static final String itemName_naquadahShovel = "item_naquadahShovel";
	public static final String itemName_naquadahAxe = "item_naquadahAxe";
	public static final String itemName_naquadahHoe = "item_naquadahHoe";
	public static final String itemName_naquadahSword = "item_naquadahSword";
	public static final String itemName_naquadahBow = "item_naquadahBow";
	public static final String itemName_naquadahLighter = "item_naquadahLighter";
	public static final String itemName_naquadahShears = "item_naquadahShears";
	public static final String itemName_naquadahFishingRod = "item_naquadahFishingRod";
	public static final String itemName_fireStaff = "item_fireStaff";
	public static final String itemName_nukeStaff = "item_nukeStaff";
	public static final String itemName_napalmStaff = "item_napalmStaff";
	public static final String itemName_explosiveFireStaff = "item_explosiveFireStaff";
	public static final String itemName_customFireBall = "item_customFireBall";
	public static final String itemName_customExplosiveFireBall = "item_customExplosiveFireBall";
	
	public static final String itemName_naquadahHelmet = "item_naquadahHelmet";
	public static final String itemName_naquadahPlate = "item_naquadahPlate";
	public static final String itemName_naquadahLegs = "item_naquadahLegs";
	public static final String itemName_naquadahBoots = "item_naquadahBoots";
	
	public static final String itemName_crystalSoul = "item_crystalSoul";
	
	// Initialization :
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		initLogger();
		StargateModConfig.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
		registerPackets();
		proxy.registerRenderers();
		proxy.registerSounds();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		registerCreativeTabs();
		registerBlocks();
		registerItems();
		registerRecipes();
		registerDispenserBehaviors();
		registerTileEntities();
		this.registerEntities();
		registerEventHandler();
		registerGuiHandler();
		registerWorldGenerator();
		setBlocksHarvestLevel();
		registerNames();
		StargateModConfig.saveConfig();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		// Post-Initialization code such as mod hooks
	}
	
	private static void initLogger() {
		logger = Logger.getLogger(MOD_ID);
		logger.setParent(FMLLog.getLogger());
		logger.setLevel(Level.ALL);
	}
	
	private static void registerPackets() {
		registerTileEntityPacket(TileEntityBaseDhd.class);
		registerTileEntityPacket(TileEntityBaseTeleporter.class);
		registerTileEntityPacket(TileEntityBaseShieldConsole.class);
		registerTileEntityPacket(TileEntityChevron.class);
		registerTileEntityPacket(TileEntityDetector.class);
		registerTileEntityPacket(TileEntityMobGenerator.class);
		registerTileEntityPacket(TileEntityStargateControl.class);
		registerTileEntityPacket(TileEntityStargatePart.class);
		
		registerPlayerDataPacket(PlayerTeleporterData.class);
		registerPlayerDataPacket(PlayerStargateData.class);
		
		registerCommandPackets();
	}
	
	private static void registerCreativeTabs() {
		stargateBlocksTab = new CreativeTabs(stargateBlocksTabName) {
			
			@Override
			public ItemStack getIconItemStack() {
				return new ItemStack(StargateMod.block_chevronOn);
			}
			
		};
		
		stargateItemsTab = new CreativeTabs(stargateItemsTabName) {
			
			@Override
			public ItemStack getIconItemStack() {
				return new ItemStack(StargateMod.item_naquadahIngot);
			}
			
		};
	}
	
	private static void registerBlocks() {
		block_naquadahOre = new BlockNaquadahOre(blockName_naquadahOre);
		block_naquadahBlock = new BlockNaquadahBlock(blockName_naquadahBlock);
		block_naquadahAlloy = new BlockNaquadahAlloy(blockName_naquadahAlloy);
		block_chevronOff = new BlockChevron(blockName_chevronOff);
		block_chevronOn = new BlockChevronOn(blockName_chevronOn);
		block_stargateControl = new BlockStargateControl(blockName_stargateControl);
		block_vortex = new BlockVortex(blockName_vortex);
		block_shield = new BlockShield(blockName_shield);
		block_shieldedVortex = new BlockShield(blockName_shieldedVortex);
		block_kawoosh = new BlockKawoosh(blockName_kawoosh);
		block_dhdPanel = new BlockPanelDhd(blockName_dhdPanel);
		block_dhdBase = new BlockBaseDhd(blockName_dhdBase);
		block_teleporterPanel = new BlockPanelTeleporter(blockName_teleporterPanel);
		block_teleporterBase = new BlockBaseTeleporter(blockName_teleporterBase);
		block_shieldConsolePanel = new BlockPanelShieldConsole(blockName_shieldConsolePanel);
		block_shieldConsoleBase = new BlockBaseShieldConsole(blockName_shieldConsoleBase);
		block_detector = new BlockDetector(blockName_detector);
		block_mobGenerator = new BlockMobGenerator(blockName_mobGenerator);
		block_stuffLevelUpTable = new BlockStuffLevelUpTable(blockName_stuffLevelUpTable);
		block_selfPoweredRedstoneLight = new BlockSelfPoweredRedstoneLight(blockName_selfPoweredRedstoneLight);
		block_fastStargate = new BlockFastStargate(blockName_fastStargate);
		block_fastStargate2 = new BlockFastStargate2(blockName_fastStargate2);
		block_fastStargate3 = new BlockFastStargate3(blockName_fastStargate3);
		
		if(StargateModConfig.isGlassDropable) {
			Block.blocksList[Block.glass.blockID] = null;
			block_glassDropable = new BlockGlassDropable();
			
			Block.blocksList[Block.thinGlass.blockID] = null;
			block_thinGlassDropable = new BlockGlassPaneDropable();
		}
	}
	
	private static void registerItems() {
		int naquadaArmorRenderIndex = proxy.addArmor(StargateMod.naquadahMaterialName);
		naquadahToolMaterial = EnumHelper.addToolMaterial(naquadahMaterialName, 4, 0, 10, 4, 25);
		naquadahArmorMaterial = EnumHelper.addArmorMaterial(naquadahMaterialName, 0, new int[] {3, 8, 6, 3}, 25);
		
		item_naquadahOre = new ItemStargate(itemName_naquadahOre);
		item_naquadahIngot = new ItemStargate(itemName_naquadahIngot);
		item_crystal = new ItemStargate(itemName_crystal);
		item_crystalStargate = new ItemStargate(itemName_crystalStargate);
		item_crystalDhd = new ItemStargate(itemName_crystalDhd);
		item_crystalTeleporter = new ItemStargate(itemName_crystalTeleporter);
		item_crystalScanner = new ItemStargate(itemName_crystalScanner);
		item_crystalSoulEmpty = new ItemEmptySoulCrystal(itemName_crystalSoulEmpty);
		item_crystalCreation = new ItemStargate(itemName_crystalCreation);
		item_crystalShield = new ItemStargate(itemName_crystalShield);
		item_chevronCompound = new ItemStargate(itemName_chevronCompound);
		item_stargateControlUnit = new ItemStargate(itemName_stargateControlUnit);
		item_dhdControlUnit = new ItemStargate(itemName_dhdControlUnit);
		item_teleporterControlUnit = new ItemStargate(itemName_teleporterControlUnit);
		item_detectorControlUnit = new ItemStargate(itemName_detectorControlUnit);
		item_mobGeneratorControlUnit = new ItemStargate(itemName_mobGeneratorControlUnit);
		item_stuffLevelUpTableControlUnit = new ItemStargate(itemName_stuffLevelUpTableControlUnit);
		item_shieldControlUnit = new ItemStargate(itemName_shieldControlUnit);
		item_dhdPanel = new ItemStargate(itemName_dhdPanel);
		item_touchScreen = new ItemStargate(itemName_touchScreen);
		item_shieldRemote = new ItemShieldRemote(itemName_shieldRemote);
		
		item_naquadahPickaxe = new ItemNaquadahPickaxe(itemName_naquadahPickaxe);
		item_naquadahShovel = new ItemNaquadahShovel(itemName_naquadahShovel);
		item_naquadahAxe = new ItemNaquadahAxe(itemName_naquadahAxe);
		item_naquadahHoe = new ItemNaquadahHoe(itemName_naquadahHoe);
		item_naquadahSword = new ItemNaquadahSword(itemName_naquadahSword);
		item_naquadahBow = new ItemNaquadahBow(itemName_naquadahBow);
		item_naquadahLighter = new ItemNaquadahLighter(itemName_naquadahLighter);
		item_naquadahShears = new ItemNaquadahShears(itemName_naquadahShears);
		item_naquadahFishingRod = new ItemNaquadahFishingRod(itemName_naquadahFishingRod);
		item_fireStaff = new ItemFireStaff(itemName_fireStaff);
		item_explosiveFireStaff = new ItemExplosiveFireStaff(itemName_explosiveFireStaff);
		item_nukeStaff = new ItemNukeStaff(itemName_nukeStaff);
		item_napalmStaff = new ItemNapalmStaff(itemName_napalmStaff);
		item_customFireBall = new ItemCustomFireBall(itemName_customFireBall);
		item_customExplosiveFireBall = new ItemCustomExplosiveFireBall(itemName_customExplosiveFireBall);
		
		item_naquadahHelmet = new ItemNaquadahHelmet(itemName_naquadahHelmet, naquadaArmorRenderIndex);
		item_naquadahPlate = new ItemNaquadahPlate(itemName_naquadahPlate, naquadaArmorRenderIndex);
		item_naquadahLegs = new ItemNaquadahLegs(itemName_naquadahLegs, naquadaArmorRenderIndex);
		item_naquadahBoots = new ItemNaquadahBoots(itemName_naquadahBoots, naquadaArmorRenderIndex);
		
		item_crystalSoulChicken = new ItemSoulCrystal(EntityChicken.class);
		item_crystalSoulCow = new ItemSoulCrystal(EntityCow.class);
		item_crystalSoulMooshroom = new ItemSoulCrystal(EntityMooshroom.class);
		item_crystalSoulPig = new ItemSoulCrystal(EntityPig.class);
		item_crystalSoulSheep = new ItemSoulCrystal(EntitySheep.class);
		item_crystalSoulOcelot = new ItemSoulCrystal(EntityOcelot.class);
		item_crystalSoulWolf = new ItemSoulCrystal(EntityWolf.class);
		item_crystalSoulIronGolem = new ItemSoulCrystal(EntityIronGolem.class, 2, 4);
		item_crystalSoulSnowman = new ItemSoulCrystal(EntitySnowman.class, 2, 6);
		item_crystalSoulBlaze = new ItemSoulCrystal(EntityBlaze.class);
		item_crystalSoulCreeper = new ItemSoulCrystal(EntityCreeper.class);
		item_crystalSoulEnderman = new ItemSoulCrystal(EntityEnderman.class, 2, 4);
		item_crystalSoulGiantZombie = new ItemSoulCrystal(EntityGiantZombie.class, 1, 2);
		item_crystalSoulSilverfish = new ItemSoulCrystal(EntitySilverfish.class);
		item_crystalSoulSkeleton = new ItemSoulCrystal(EntitySkeleton.class);
		item_crystalSoulSpider = new ItemSoulCrystal(EntitySpider.class);
		item_crystalSoulCaveSpider = new ItemSoulCrystal(EntityCaveSpider.class);
		item_crystalSoulWitch = new ItemSoulCrystal(EntityWitch.class, 1, 2);
		item_crystalSoulWither = new ItemSoulCrystal(EntityWither.class, 1, 1);
		item_crystalSoulZombie = new ItemSoulCrystal(EntityZombie.class);
		item_crystalSoulPigZombie = new ItemSoulCrystal(EntityPigZombie.class);
		item_crystalSoulSquid = new ItemSoulCrystal(EntitySquid.class);
		item_crystalSoulDragon = new ItemSoulCrystal(EntityDragon.class, 1, 1);
		item_crystalSoulGhast = new ItemSoulCrystal(EntityGhast.class, 1, 2);
		item_crystalSoulSlime = new ItemSoulCrystal(EntitySlime.class);
		item_crystalSoulMagmaCube = new ItemSoulCrystal(EntityMagmaCube.class);
		item_crystalSoulBat = new ItemSoulCrystal(EntityBat.class, 6, 12);
		item_crystalSoulVillager = new ItemSoulCrystal(EntityVillager.class, 1);
		item_crystalSoulHorse = new ItemSoulCrystal(EntityHorse.class, 2);
	}
	
	private static void registerRecipes() {
		boolean canCraftCrystal = false;
		boolean canCraftCreationCrystal = false;
		
		addSmelting(item_naquadahOre.itemID, new ItemStack(item_naquadahIngot));
		addRecipe(new ItemStack(block_naquadahBlock), new Object[] {"NNN", "NNN", "NNN", 'N', item_naquadahIngot});
		addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalStargate});
		addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalDhd});
		addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalTeleporter});
		addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalScanner});
		addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalSoulEmpty});
		addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalCreation});
		addRecipe(new ItemStack(block_dhdPanel), new Object[] {"P", 'P', block_shieldConsolePanel});
		addRecipe(new ItemStack(Block.redstoneLampIdle), new Object[] {"L", 'L', block_selfPoweredRedstoneLight});
		addRecipe(new ItemStack(item_customFireBall), new Object[] {"F", 'F', Item.fireballCharge});
		addRecipe(new ItemStack(Item.fireballCharge), new Object[] {"F", 'F', item_customExplosiveFireBall});
		
		if(StargateModConfig.canCraftNaquadahAlloy) {
			addRecipe(new ItemStack(block_naquadahAlloy), new Object[] {"NFN", "FRF", "NFN", 'N', item_naquadahIngot, 'F', Item.ingotIron, 'R', Item.redstone});
			
			if(StargateModConfig.canCraftStargate) {
				addRecipe(new ItemStack(item_crystalStargate), new Object[] {"CR_", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				addRecipe(new ItemStack(item_crystalDhd), new Object[] {"C_R", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				addRecipe(new ItemStack(item_chevronCompound), new Object[] {"NCN", "_N_", 'N', item_naquadahIngot, 'C', item_crystal});
				addRecipe(new ItemStack(item_stargateControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalStargate});
				addRecipe(new ItemStack(item_dhdControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalDhd});
				addRecipe(new ItemStack(item_dhdPanel), new Object[] {"GGG", "NCN", 'G', Block.glass, 'N', item_naquadahIngot, 'C', item_crystal});
				addRecipe(new ItemStack(block_chevronOff), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_chevronCompound});
				addRecipe(new ItemStack(block_stargateControl), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_stargateControlUnit});
				addRecipe(new ItemStack(block_dhdBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_dhdControlUnit});
				addRecipe(new ItemStack(block_dhdPanel), new Object[] {"P", "N", 'N', block_naquadahAlloy, 'P', item_dhdPanel});
				
				if(StargateModConfig.canCraftStargateShield) {
					addRecipe(new ItemStack(item_crystalShield), new Object[] {"C__", "___", "_R_", 'C', item_crystal, 'R', Item.redstone});
					addRecipe(new ItemStack(item_shieldControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalShield});
					addRecipe(new ItemStack(block_shieldConsoleBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_shieldControlUnit});
					addRecipe(new ItemStack(block_shieldConsolePanel), new Object[] {"P", 'P', block_dhdPanel});
				}
				
				canCraftCrystal = true;
			}
			
			if(StargateModConfig.canCraftTeleporter) {
				addRecipe(new ItemStack(item_crystalTeleporter), new Object[] {"C__", "R__", "___", 'C', item_crystal, 'R', Item.redstone});
				addRecipe(new ItemStack(item_teleporterControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalTeleporter});
				addRecipe(new ItemStack(item_touchScreen), new Object[] {"GGG", "RCR", 'G', Block.glass, 'R', Item.redstone, 'C', item_crystal});
				addRecipe(new ItemStack(block_teleporterBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_teleporterControlUnit});
				addRecipe(new ItemStack(block_teleporterPanel), new Object[] {"S", "N", 'N', block_naquadahAlloy, 'S', item_touchScreen});
				
				canCraftCrystal = true;
			}
			
			if(StargateModConfig.canCraftDetector) {
				addRecipe(new ItemStack(item_crystalScanner), new Object[] {"C__", "_R_", "___", 'C', item_crystal, 'R', Item.redstone});
				addRecipe(new ItemStack(item_detectorControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalScanner});
				addRecipe(new ItemStack(block_detector), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_detectorControlUnit});
				
				canCraftCrystal = true;
			}
			
			if(StargateModConfig.canCraftMobGenerator) {
				addRecipe(new ItemStack(item_crystalSoulEmpty), new Object[] {"C__", "__R", "___", 'C', item_crystal, 'R', Item.redstone});
				addRecipe(new ItemStack(item_mobGeneratorControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalSoulEmpty});
				addRecipe(new ItemStack(block_mobGenerator), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_mobGeneratorControlUnit});
				
				canCraftCrystal = true;
			}
			
			if(StargateModConfig.canCraftStuffLevelUpTable) {
				addRecipe(new ItemStack(item_stuffLevelUpTableControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalCreation});
				addRecipe(new ItemStack(block_stuffLevelUpTable), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_stuffLevelUpTableControlUnit});
				
				canCraftCreationCrystal = true;
			}
			
			if(StargateModConfig.canCraftSelfPoweredRedstoneLight) {
				addRecipe(new ItemStack(block_selfPoweredRedstoneLight), new Object[] {"L", 'L', Block.redstoneLampIdle});
			}
		}
		
		if(StargateModConfig.canCraftToolsAndArmors) {
			addRecipe(new ItemStack(item_naquadahPickaxe), new Object[] {"NNN", "_S_", "_S_", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahShovel), new Object[] {"N", "S", "S", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahAxe), new Object[] {"NN", "SN", "S_", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahHoe), new Object[] {"NN", "S_", "S_", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahSword), new Object[] {"N", "N", "S", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahHelmet), new Object[] {"NNN", "N_N", "___", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahPlate), new Object[] {"N_N", "NNN", "NNN", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahLegs), new Object[] {"NNN", "N_N", "N_N", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahBoots), new Object[] {"___", "N_N", "N_N", 'N', item_naquadahIngot, 'S', Item.stick});
			addRecipe(new ItemStack(item_naquadahBow), new Object[] {"_NS", "N_S", "_NS", 'N', item_naquadahIngot, 'S', Item.silk});
			addRecipe(new ItemStack(item_naquadahLighter), new Object[] {"D", "N", 'N', item_naquadahIngot, 'D', Item.diamond});
			addRecipe(new ItemStack(item_naquadahShears), new Object[] {"_N", "N_", 'N', item_naquadahIngot});
			addRecipe(new ItemStack(item_naquadahFishingRod), new Object[] {"__N", "_NS", "N_S", 'N', item_naquadahIngot, 'S', Item.silk});
			
			if(StargateModConfig.canCraftFireStaff) {
				addRecipe(new ItemStack(item_fireStaff), new Object[] {"F", "C", "L", 'L', item_naquadahLighter, 'C', item_crystalCreation, 'F', item_customFireBall});
				
				if(StargateModConfig.canCraftExplosiveFireBalls && StargateModConfig.canCraftExplosiveFireStaff) {
					addRecipe(new ItemStack(item_explosiveFireStaff), new Object[] {"F", "C", "L", 'L', item_naquadahLighter, 'C', item_crystalCreation, 'F', item_customExplosiveFireBall});
				}
				
				canCraftCreationCrystal = true;
			}
		}
		
		if(StargateModConfig.canCraftExplosiveFireBalls) {
			addRecipe(new ItemStack(item_customExplosiveFireBall), new Object[] {"F", 'F', item_customFireBall});
		}
		else {
			addRecipe(new ItemStack(Item.fireballCharge), new Object[] {"F", 'F', item_customFireBall});
		}
		
		if(canCraftCreationCrystal) {
			addRecipe(new ItemStack(item_crystalCreation), new Object[] {"C__", "___", "R__", 'C', item_crystal, 'R', Item.redstone});
			
			canCraftCrystal = true;
		}
		
		if(canCraftCrystal) {
			addRecipe(new ItemStack(item_crystal), new Object[] {"R", "D", "D", 'D', Item.diamond, 'R', Item.redstone});
		}
	}
	
	private static void registerDispenserBehaviors() {
		ModLoader.addDispenserBehavior(item_customFireBall, new DispenserBehaviorCustomFireBall());
		ModLoader.addDispenserBehavior(item_customExplosiveFireBall, new DispenserBehaviorCustomExplosiveFireBall());
	}
	
	private static void registerTileEntities() {
		registerTileEntity(TileEntityBaseDhd.class, "DhdBase");
		registerTileEntity(TileEntityBaseShieldConsole.class, "ShieldConsoleBase");
		registerTileEntity(TileEntityBaseTeleporter.class, "TeleporterBase");
		registerTileEntity(TileEntityChevron.class, "Chevron");
		registerTileEntity(TileEntityDetector.class, "Detector");
		registerTileEntity(TileEntityMobGenerator.class, "MobGenerator");
		registerTileEntity(TileEntityStargateControl.class, "StargateControl");
		registerTileEntity(TileEntityStargatePart.class, "StargatePart");
		registerTileEntity(TileEntityStuffLevelUpTable.class, "StuffLevelUpTable");
	}
	
	private void registerEntities() {
		EntityRegistry.registerModEntity(EntityCustomFishHook.class, "customFishHook", 0, this, 250, 5, true);
		EntityRegistry.registerModEntity(EntityCustomFireBall.class, "customFireBall", 1, this, 250, 5, true);
		EntityRegistry.registerModEntity(EntityCustomExplosiveFireBall.class, "customExplosiveFireBall", 2, this, 250, 5, true);
		EntityRegistry.registerModEntity(EntityNuke.class, "nuke", 3, this, 250, 5, true);
		EntityRegistry.registerModEntity(EntityNapalm.class, "napalm", 4, this, 250, 5, true);
	}
	
	private static void registerEventHandler() {
		MinecraftForge.EVENT_BUS.register(new StargateEventHandler());
	}
	
	private static void registerGuiHandler() {
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
	}
	
	private static void registerWorldGenerator() {
		GameRegistry.registerWorldGenerator(new NaquadahGenerator());
	}
	
	private static void setBlocksHarvestLevel() {
		int naquadahHarvestLevel = StargateModConfig.canCraftToolsAndArmors ? 4 : 3;
		
		setBlockHarvestLevel(block_naquadahOre, "pickaxe", 3);
		setBlockHarvestLevel(block_naquadahBlock, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_naquadahAlloy, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_chevronOff, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_stargateControl, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_dhdPanel, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_dhdBase, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_teleporterPanel, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_teleporterBase, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_shieldConsolePanel, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_shieldConsoleBase, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_detector, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_mobGenerator, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_stuffLevelUpTable, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_fastStargate, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_fastStargate2, "pickaxe", naquadahHarvestLevel);
		setBlockHarvestLevel(block_fastStargate3, "pickaxe", naquadahHarvestLevel);
	}
	
	private static void registerNames() {
		// Tabs :
		addName("itemGroup." + stargateBlocksTabName, "Stargate blocks", "Blocs stargate");
		addName("itemGroup." + stargateItemsTabName, "Stargate items", "Items stargate");
		
		// Blocks :
		addName(block_naquadahOre, "Naquadah ore", "Minerai de naquadah");
		addName(block_naquadahBlock, "Naquadah block", "Block de naquadah");
		addName(block_naquadahAlloy, "Naquadah alloy", "Alliage de naquadah");
		addName(block_chevronOff, "Chevron", "Chevron");
		addName(block_chevronOn, "Chevron (Active)", "Chevron (Active)");
		addName(block_stargateControl, "Stargate control unit", "Unite de controle de porte des eloiles");
		addName(block_vortex, "Vortex", "Vortex");
		addName(block_shield, "Shield", "Bouclier");
		addName(block_shieldedVortex, "Shielded vortex", "Vortex avec bouclier");
		addName(block_kawoosh, "Kawoosh", "Kawoosh");
		addName(block_dhdPanel, "DHD control panel", "Panneau de controle de DHD");
		addName(block_dhdBase, "DHD base", "Socle de DHD");
		addName(block_teleporterPanel, "Teleporter control panel", "Panneau de controle de teleporteur");
		addName(block_teleporterBase, "Teleporter base", "Socle de teleporteur");
		addName(block_shieldConsolePanel, "Shield console control panel", "Panneau de console de controle de bouclier");
		addName(block_shieldConsoleBase, "Shield console base", "Socle de console de controle de bouclier");
		addName(block_detector, "Detector", "Detecteur");
		addName(block_mobGenerator, "Mob generator", "Generateur de mobs");
		addName(block_stuffLevelUpTable, "Stuff levelup table", "Table d'amelioration d'equipment");
		addName(block_selfPoweredRedstoneLight, "Self powered redstone light", "Lampe a redstone auto-alimentee");
		addName(block_fastStargate, "Fast Stargate v1", "Fast Stargate v1");
		addName(block_fastStargate2, "Fast Stargate v2", "Fast Stargate v2");
		addName(block_fastStargate3, "Fast Stargate v3", "Fast Stargate v3");
		
		// Items :
		addName(item_naquadahOre, "Naquadah ore", "Minerai de naquadah");
		addName(item_naquadahIngot, "Naquadah ingot", "Lingot de naquadah");
		addName(item_crystal, "Crystal", "Cristal");
		addName(item_crystalStargate, "Stargate control crystal", "Cristal de controle de porte des etoiles");
		addName(item_crystalDhd, "DHD control crystal", "Cristal de controle de DHD");
		addName(item_crystalTeleporter, "Teleporter crystal", "Cristal de teleporteur");
		addName(item_crystalScanner, "Scanner crystal", "Cristal de scanner");
		addName(item_crystalSoulEmpty, "Empty soul crystal", "Cristal d'ame vide");
		addName(item_crystalCreation, "Materialization crystal", "Cristal de materialisation");
		addName(item_crystalShield, "Shield crystal", "Cristal de bouclier");
		addName(item_chevronCompound, "Chevron compound", "Composant de chevron");
		addName(item_stargateControlUnit, "Stargate control unit", "Unite de controle de porte des eloiles");
		addName(item_dhdControlUnit, "DHD control unit", "Unite de controle de DHD");
		addName(item_teleporterControlUnit, "Teleporter control unit", "Unite de controle de teleporteur");
		addName(item_detectorControlUnit, "Detector control unit", "Unite de controle de detecteur");
		addName(item_mobGeneratorControlUnit, "Mob generator control unit", "Unite de controle de generateur de mobs");
		addName(item_stuffLevelUpTableControlUnit, "Stuff levelup table control unit", "Unite de controle de table d'amelioration d'equipment");
		addName(item_shieldControlUnit, "Shield control unit", "Unite de controle de bouclier");
		addName(item_dhdPanel, "DHD control panel", "Panneau de controle de DHD");
		addName(item_touchScreen, "Teleporter touch screen", "Ecran tactile de teleporteur");
		addName(item_shieldRemote, "Shield remote", "Telecommande du bouclier");
		
		addName(item_naquadahPickaxe, "Naquadah pickaxe", "Pioche de naquadah");
		addName(item_naquadahShovel, "Naquadah shovel", "Pelle de naquadah");
		addName(item_naquadahAxe, "Naquadah axe", "Hache de naquadah");
		addName(item_naquadahHoe, "Naquadah hoe", "Houe de naquadah");
		addName(item_naquadahSword, "Naquadah sword", "Epee de naquadah");
		addName(item_naquadahHelmet, "Naquadah helmet", "Haume de naquadah");
		addName(item_naquadahPlate, "Naquadah plate", "Armure de naquadah");
		addName(item_naquadahLegs, "Naquadah legs", "Jambieres de naquadah");
		addName(item_naquadahBoots, "Naquadah boots", "Bottes de naquadah");
		addName(item_naquadahBow, "Naquadah bow", "Arc de naquadah");
		addName(item_naquadahLighter, "Naquadah ligher", "Briquet de naquadah");
		addName(item_naquadahShears, "Naquadah shears", "Cisailles de naquadah");
		addName(item_naquadahFishingRod, "Naquadah fishing rod", "Canne a peche de naquadah");
		addName(item_fireStaff, "Fire staff", "Baguette de feu");
		addName(item_explosiveFireStaff, "Explosive fire staff", "Baguette de feu explosive");
		addName(item_nukeStaff, "Nuke staff - wtf!?", "Baguette nucleaire - wtf!?");
		addName(item_napalmStaff, "Napalm staff - wtf!?", "Baguette napalm - wtf!?");
		addName(item_customFireBall, "Fireball v2 - stargate compatible", "Boule de feu v2 - stargate compatible");
		addName(item_customExplosiveFireBall, "Fireball v3 - explosive", "Boule de feu v3 - explosive");
		
		addName(item_crystalSoulChicken, "Chicken soul crystal", "Cristal d'ame de poulet");
		addName(item_crystalSoulCow, "Cow soul crystal", "Cristal d'ame de vache");
		addName(item_crystalSoulMooshroom, "Mooshroom soul crystal", "Cristal d'ame de champimheu");
		addName(item_crystalSoulPig, "Pig soul crystal", "Cristal d'ame de cochon");
		addName(item_crystalSoulSheep, "Sheep soul crystal", "Cristal d'ame de mouton");
		addName(item_crystalSoulOcelot, "Ocelot soul crystal", "Cristal d'ame de chat sauvage");
		addName(item_crystalSoulWolf, "Wolf soul crystal", "Cristal d'ame de loup");
		addName(item_crystalSoulIronGolem, "Iron golem soul crystal", "Cristal d'ame de golem de fer");
		addName(item_crystalSoulSnowman, "Snowman soul crystal", "Cristal d'ame de bonhomme de neige");
		addName(item_crystalSoulBlaze, "Blaze soul crystal", "Cristal d'ame d'elementaire de feu");
		addName(item_crystalSoulCreeper, "Creeper soul crystal", "Cristal d'ame de creeper");
		addName(item_crystalSoulEnderman, "Enderman soul crystal", "Cristal d'ame d'enderman");
		addName(item_crystalSoulGiantZombie, "Giant zombie soul crystal", "Cristal d'ame de zombie geant");
		addName(item_crystalSoulSilverfish, "Silver fish soul crystal", "Cristal d'ame de poisson d'argent");
		addName(item_crystalSoulSkeleton, "Skeleton soul crystal", "Cristal d'ame de skelette");
		addName(item_crystalSoulSpider, "Spider soul crystal", "Cristal d'ame d'areignee");
		addName(item_crystalSoulCaveSpider, "Cave spider soul crystal", "Cristal d'ame d'areignee venimeuse");
		addName(item_crystalSoulWitch, "Witch soul crystal", "Cristal d'ame de sorciere");
		addName(item_crystalSoulWither, "Wither soul crystal", "Cristal d'ame de Wither");
		addName(item_crystalSoulZombie, "Zombie soul crystal", "Cristal d'ame de zombie");
		addName(item_crystalSoulPigZombie, "Pig-zombie soul crystal", "Cristal d'ame de cochon-zombie");
		addName(item_crystalSoulSquid, "Squid soul crystal", "Cristal d'ame de poulpe");
		addName(item_crystalSoulDragon, "Dragon soul crystal", "Cristal d'ame de Dragon");
		addName(item_crystalSoulGhast, "Ghast soul crystal", "Cristal d'ame de ghast");
		addName(item_crystalSoulSlime, "Slime soul crystal", "Cristal d'ame de blob");
		addName(item_crystalSoulMagmaCube, "Magma cube soul crystal", "Cristal d'ame de blob de lave");
		addName(item_crystalSoulBat, "Bat soul crystal", "Cristal d'ame de chauve-souris");
		addName(item_crystalSoulVillager, "Villager soul crystal", "Cristal d'ame de villageois");
		addName(item_crystalSoulHorse, "Horse soul crystal", "Cristal d'ame de cheval");
		
		// Inventory :
		addName(TileEntityBaseTeleporter.INV_NAME, "Teleporter", "Teleporteur");
		addName(TileEntityBaseDhd.INV_NAME, "DHD", "DHD");
		addName(TileEntityBaseShieldConsole.INV_NAME, "Shield console", "Console du bouclier");
		addName(TileEntityDetector.INV_NAME, "Detector", "Detecteur");
		addName(TileEntityMobGenerator.INV_NAME, "Mob generator", "Generateur de mobs");
		addName(TileEntityStuffLevelUpTable.INV_NAME, "Stuff level up table", "Table d'amelioration d'equipement");
		
		addName(GuiBase.DESTINATION, "Destination", "Destination");
		addName(GuiBase.NAME, "Name", "Nom");
		addName(GuiBase.ADD_THIS, "Add to the list", "Ajouter a la liste");
		addName(GuiBase.ADD, "Add", "Ajouter");
		addName(GuiBase.DELETE, "Delete", "Supprimer");
		addName(GuiBase.OVERWRITE, "Overwrite", "Ecraser");
		addName(GuiBase.TAB, "Tab", "Tab");
		addName(GuiBase.ALL, "All", "Tous");
		
		addName(GuiTeleporter.COORDINATES, "Coordinates", "Coordonnees");
		addName(GuiTeleporter.TELEPORT, "Teleport (ENTER)", "Teleportation (ENTRER)");
		addName(GuiTeleporter.IN_RANGE, "In range", "A portee");
		addName(GuiTeleporter.MESSAGE_OK, "Coordinates in range", "Coordonnees a portee");
		addName(GuiTeleporter.MESSAGE_OUT_OF_RANGE, "Coordinates out of range", "Coordonnes trop eloignees");
		addName(GuiTeleporter.MESSAGE_INVALID, "Invalid coordinates", "Coordonnees non valides");
		
		addName(GuiDhd.ADDRESS, "Address", "Adresse");
		addName(GuiDhd.ACTIVATE, "Activate (ENTER)", "Activer (ENTRER)");
		addName(GuiDhd.EARTH, "Earth", "Terre");
		addName(GuiDhd.HELL, "Hell", "Enfer");
		addName(GuiDhd.END, "End", "End");
		
		addName(GuiShieldConsole.CURRENT_CODE, "Current code", "Code actuel");
		addName(GuiShieldConsole.SHIELD_ON, "Shield : ON", "Bouclier : ON");
		addName(GuiShieldConsole.SHIELD_OFF, "Shield : OFF", "Bouclier : OFF");
		addName(GuiShieldConsole.SHIELD_DISCONNECTED, "Shield : disconnected", "Bouclier : deconnecte");
		addName(GuiShieldConsole.SHIELD_SWITCH, "Shield (ENTER)", "Bouclier (ENTRER)");
		addName(GuiShieldConsole.AUTO_SHIELD_ON, "Automatic shield : ON", "Bouclier automtique : ON");
		addName(GuiShieldConsole.AUTO_SHIELD_OFF, "Automatic shield : OFF", "Bouclier automatique : OFF");
		addName(GuiShieldConsole.AUTO_SHIELD_DISCONNECTED, "Automatic shield : disconnected", "Bouclier automatique : deconnecte");
		addName(GuiShieldConsole.AUTO_SHIELD_SWITCH, "Automatic shield (TAB)", "Bouclier automatique (TAB)");
		addName(GuiShieldConsole.CHANGE_CODE, "Change code", "Changer le code");
		
		addName(GuiDetector.RANGE, "Range", "Portee");
		addName(GuiDetector.RANGE_LIMITS, "min range = 1, max range = 10", "portee min = 1, portee max = 10");
		addName(GuiDetector.INVERTED_OUTPUT, "Inverted output.", "Sortie inversee.");
		addName(GuiDetector.NORMAL_OUTPUT, "Normal output", "Sortie normale");
		addName(GuiDetector.INVERT_BUTTON, "Invert output (TAB)", "Inverser la sortie (TAB)");
		
		addName(GuiStuffLevelUpTable.POWER, "Power", "Puissance");
		
		// Damage sources :
		addName("death.attack." + CustomDamageSource.kawoosh.getDamageType(), "%1$s tried to swim into the kawoosh.", "%1$s a essaye de nager dans le kawoosh.");
		addName("death.attack." + CustomDamageSource.iris.getDamageType(), "%1$s couldn't exit hyperspace.", "%1$s n'a pas pu sortir de l'hyper-espace.");
	}
	
	private static void addRecipe(ItemStack output, Object... params) {
		GameRegistry.addRecipe(output, params);
	}
	
	private static void addSmelting(int input, ItemStack output) {
		GameRegistry.addSmelting(input, output, 1);
	}
	
	private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, id);
	}
	
	private static void setBlockHarvestLevel(Block block, String toolClass, int harvestLevel) {
		MinecraftForge.setBlockHarvestLevel(block, toolClass, harvestLevel);
	}
	
	private static void addName(Object instance, String enName, String frName) {
		LanguageRegistry.instance().addNameForObject(instance, "en_US", enName);
		LanguageRegistry.instance().addNameForObject(instance, "en_UK", enName);
		LanguageRegistry.instance().addNameForObject(instance, "fr_FR", frName);
		LanguageRegistry.instance().addNameForObject(instance, "fr_CA", frName);
	}
	
	private static void addName(String key, String enName, String frName) {
		LanguageRegistry.instance().addStringLocalization(key, "en_US", enName);
		LanguageRegistry.instance().addStringLocalization(key, "en_UK", enName);
		LanguageRegistry.instance().addStringLocalization(key, "fr_FR", frName);
		LanguageRegistry.instance().addStringLocalization(key, "fr_CA", frName);
	}
	
	// Methods :
	
	public static void registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
	}
	
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
	
	/**
	 * Logs a string, at INFO level, only if debug mode is active.
	 * @param text - the string to log.
	 * @param logNow - indicates whether the string should be logged immediately, or stored until the rest of the line get in.
	 */
	public static void debug(String text, boolean logNow) {
		debug(text, Level.INFO, logNow, 1);
	}
	
	/**
	 * Logs a string n times, at INFO level, only if debug mode is active.
	 * @param text - the string to log.
	 * @param logNow - indicates whether the string should be logged immediately, or stored until the rest of the line get in.
	 * @param nb - the number of times the string has to be loged.
	 */
	public static void debug(String text, boolean logNow, int n) {
		debug(text, Level.INFO, logNow, n);
	}
	
	/**
	 * Logs a string, at the given message level, only if debug mode is active.
	 * @param text - the string to log.
	 * @param level - the message level.
	 * @param logNow - indicates whether the string should be logged immediately, or stored until the rest of the line get in.
	 */
	public static void debug(String text, Level level, boolean logNow) {
		debug(text, level, logNow, 1);
	}
	
	/**
	 * Logs a string n times, at the given message level, only if debug mode is active.
	 * @param text - the string to log.
	 * @param level - the message level.
	 * @param logNow - indicates whether the string should be logged immediately, or stored until the rest of the line get in.
	 * @param nb - the number of times the string has to be loged.
	 */
	public static void debug(String text, Level level, boolean logNow, int n) {
		if(StargateModConfig.debug) {
			for(int i = 0; i < n; ++i) {
				logBuffer.append(text);
			}
			if(logNow) {
				logger.log(level, logBuffer.toString());
				logBuffer.delete(0, logBuffer.length());
			}
		}
	}
	
}

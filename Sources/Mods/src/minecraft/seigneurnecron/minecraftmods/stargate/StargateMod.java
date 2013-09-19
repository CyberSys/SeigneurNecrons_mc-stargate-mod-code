package seigneurnecron.minecraftmods.stargate;

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
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.entity.EntityCustomFishHook;
import seigneurnecron.minecraftmods.core.mod.ModBase;
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
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityNapalm;
import seigneurnecron.minecraftmods.stargate.entity.EntityNuke;
import seigneurnecron.minecraftmods.stargate.entity.damagesource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior.DispenserBehaviorCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior.DispenserBehaviorCustomFireBall;
import seigneurnecron.minecraftmods.stargate.event.StargateEventHandler;
import seigneurnecron.minecraftmods.stargate.gui.GuiBase;
import seigneurnecron.minecraftmods.stargate.gui.GuiDetector;
import seigneurnecron.minecraftmods.stargate.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.gui.GuiScreen;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldConsole;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldRemote;
import seigneurnecron.minecraftmods.stargate.gui.GuiStargateControl;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.gui.components.DhdPanel;
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
import seigneurnecron.minecraftmods.stargate.network.StargateCommonPacketHandler;
import seigneurnecron.minecraftmods.stargate.network.StargateServerPacketHandler;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.sound.StargateSounds;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tools.worlddata.StargateChunkLoader;
import seigneurnecron.minecraftmods.stargate.world.NaquadahGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

// FIXME - Wiki.

// FIXME - Font texture

// FIXME - StuffLevelUpTable icons textures.

// FIXME - Refaire les zip de textures et les mettre dans dropbox. Mettre NecronCraft64.zip dans MCP.

/**
 * SeigneurNecron's Stargate Mod main class.
 * @author Seigneur Necron
 */
@Mod(modid = StargateMod.MOD_ID, name = StargateMod.MOD_NAME, version = StargateMod.VERSION, dependencies = "required-after:" + SeigneurNecronMod.MOD_ID)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, packetHandler = StargateCommonPacketHandler.class, channels = {StargateMod.CHANEL_TILE_ENTITY, StargateMod.CHANEL_PLAYER_DATA}, serverPacketHandlerSpec = @SidedPacketHandler(packetHandler = StargateServerPacketHandler.class, channels = {StargateMod.CHANEL_COMMANDS}))
public class StargateMod extends ModBase<StargateMod, StargateModConfig> {
	
	// Stargate mod basic informations :
	
	public static final String MOD_ID = "seigneur_necron_stargate_mod";
	public static final String MOD_NAME = "SeigneurNecron's Stargate Mod";
	public static final String VERSION = "[1.6.2] v3.1.3 [core v1.0.0]";
	
	public static final String CHANEL_TILE_ENTITY = "SNSM_TileEntity";
	public static final String CHANEL_COMMANDS = "SNSM_Commands";
	public static final String CHANEL_PLAYER_DATA = "SNSM_PlayerData";
	
	@Override
	protected String getModId() {
		return MOD_ID;
	}
	
	// Instance :
	
	@Instance(StargateMod.MOD_ID)
	public static StargateMod instance;
	
	@SidedProxy(clientSide = "seigneurnecron.minecraftmods.stargate.proxy.StargateClientProxy", serverSide = "seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy")
	public static StargateCommonProxy proxy;
	
	private static StargateSounds sounds;
	
	public static StargateSounds getSounds() {
		return sounds;
	}
	
	// Configuration :
	
	@Override
	protected StargateModConfig createModConfig(FMLPreInitializationEvent event) {
		return new StargateModConfig(this, new Configuration(event.getSuggestedConfigurationFile()));
	}
	
	// Chunk loader :
	
	@Override
	protected LoadingCallback createChunkManager() {
		return StargateChunkLoader.getInstance();
	}
	
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
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		this.registerRenderers();
		this.registerSounds();
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		this.registerCreativeTabs();
		this.registerBlocks();
		this.registerItems();
		this.registerRecipes();
		this.registerDispenserBehaviors();
		this.registerTileEntities();
		this.registerEntities();
		this.registerEventHandlers();
		this.registerGuiHandler();
		this.registerWorldGenerators();
		this.setBlocksHarvestLevel();
		this.registerNames();
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	protected void registerRenderers() {
		proxy.registerRenderers();
	}
	
	protected void registerSounds() {
		sounds = new StargateSounds();
		proxy.registerSounds();
	}
	
	protected void registerCreativeTabs() {
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
	
	protected void registerBlocks() {
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
	}
	
	protected void registerItems() {
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
	
	protected void registerRecipes() {
		boolean canCraftCrystal = false;
		boolean canCraftCreationCrystal = false;
		
		this.addSmelting(new ItemStack(item_naquadahIngot), item_naquadahOre.itemID);
		
		this.addRecipe(new ItemStack(block_naquadahBlock), new Object[] {"NNN", "NNN", "NNN", 'N', item_naquadahIngot});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalStargate});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalDhd});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalTeleporter});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalScanner});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalSoulEmpty});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalCreation});
		this.addRecipe(new ItemStack(block_dhdPanel), new Object[] {"P", 'P', block_shieldConsolePanel});
		this.addRecipe(new ItemStack(Block.redstoneLampIdle), new Object[] {"L", 'L', block_selfPoweredRedstoneLight});
		this.addRecipe(new ItemStack(Item.fireballCharge), new Object[] {"F", 'F', item_customExplosiveFireBall});
		
		if(this.getConfig().canCraftNaquadahAlloy) {
			this.addRecipe(new ItemStack(block_naquadahAlloy), new Object[] {"NFN", "FRF", "NFN", 'N', item_naquadahIngot, 'F', Item.ingotIron, 'R', Item.redstone});
			
			if(this.getConfig().canCraftStargate) {
				this.addRecipe(new ItemStack(item_crystalStargate), new Object[] {"CR_", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_crystalDhd), new Object[] {"C_R", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_chevronCompound), new Object[] {"NCN", "_N_", 'N', item_naquadahIngot, 'C', item_crystal});
				this.addRecipe(new ItemStack(item_stargateControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalStargate});
				this.addRecipe(new ItemStack(item_dhdControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalDhd});
				this.addRecipe(new ItemStack(item_dhdPanel), new Object[] {"GGG", "NCN", 'G', Block.glass, 'N', item_naquadahIngot, 'C', item_crystal});
				this.addRecipe(new ItemStack(block_chevronOff), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_chevronCompound});
				this.addRecipe(new ItemStack(block_stargateControl), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_stargateControlUnit});
				this.addRecipe(new ItemStack(block_dhdBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_dhdControlUnit});
				this.addRecipe(new ItemStack(block_dhdPanel), new Object[] {"P", "N", 'N', block_naquadahAlloy, 'P', item_dhdPanel});
				
				if(this.getConfig().canCraftStargateShield) {
					this.addRecipe(new ItemStack(item_crystalShield), new Object[] {"C__", "___", "_R_", 'C', item_crystal, 'R', Item.redstone});
					this.addRecipe(new ItemStack(item_shieldControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalShield});
					this.addRecipe(new ItemStack(block_shieldConsoleBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_shieldControlUnit});
					this.addRecipe(new ItemStack(block_shieldConsolePanel), new Object[] {"P", 'P', block_dhdPanel});
				}
				
				canCraftCrystal = true;
			}
			
			if(this.getConfig().canCraftTeleporter) {
				this.addRecipe(new ItemStack(item_crystalTeleporter), new Object[] {"C__", "R__", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_teleporterControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalTeleporter});
				this.addRecipe(new ItemStack(item_touchScreen), new Object[] {"GGG", "RCR", 'G', Block.glass, 'R', Item.redstone, 'C', item_crystal});
				this.addRecipe(new ItemStack(block_teleporterBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_teleporterControlUnit});
				this.addRecipe(new ItemStack(block_teleporterPanel), new Object[] {"S", "N", 'N', block_naquadahAlloy, 'S', item_touchScreen});
				
				canCraftCrystal = true;
			}
			
			if(this.getConfig().canCraftDetector) {
				this.addRecipe(new ItemStack(item_crystalScanner), new Object[] {"C__", "_R_", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_detectorControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalScanner});
				this.addRecipe(new ItemStack(block_detector), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_detectorControlUnit});
				
				canCraftCrystal = true;
			}
			
			if(this.getConfig().canCraftMobGenerator) {
				this.addRecipe(new ItemStack(item_crystalSoulEmpty), new Object[] {"C__", "__R", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_mobGeneratorControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalSoulEmpty});
				this.addRecipe(new ItemStack(block_mobGenerator), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_mobGeneratorControlUnit});
				
				canCraftCrystal = true;
			}
			
			if(this.getConfig().canCraftStuffLevelUpTable) {
				this.addRecipe(new ItemStack(item_stuffLevelUpTableControlUnit), new Object[] {"RRR", "RCR", "RRR", 'R', Item.redstone, 'C', item_crystalCreation});
				this.addRecipe(new ItemStack(block_stuffLevelUpTable), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_stuffLevelUpTableControlUnit});
				
				canCraftCreationCrystal = true;
			}
			
			if(this.getConfig().canCraftSelfPoweredRedstoneLight) {
				this.addRecipe(new ItemStack(block_selfPoweredRedstoneLight), new Object[] {"L", 'L', Block.redstoneLampIdle});
			}
		}
		
		if(this.getConfig().canCraftToolsAndArmors) {
			this.addRecipe(new ItemStack(item_naquadahPickaxe), new Object[] {"NNN", "_S_", "_S_", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahShovel), new Object[] {"N", "S", "S", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahAxe), new Object[] {"NN", "SN", "S_", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahHoe), new Object[] {"NN", "S_", "S_", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahSword), new Object[] {"N", "N", "S", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahHelmet), new Object[] {"NNN", "N_N", "___", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahPlate), new Object[] {"N_N", "NNN", "NNN", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahLegs), new Object[] {"NNN", "N_N", "N_N", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahBoots), new Object[] {"___", "N_N", "N_N", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahBow), new Object[] {"_NS", "N_S", "_NS", 'N', item_naquadahIngot, 'S', Item.silk});
			this.addRecipe(new ItemStack(item_naquadahLighter), new Object[] {"D", "N", 'N', item_naquadahIngot, 'D', Item.diamond});
			this.addRecipe(new ItemStack(item_naquadahShears), new Object[] {"_N", "N_", 'N', item_naquadahIngot});
			this.addRecipe(new ItemStack(item_naquadahFishingRod), new Object[] {"__N", "_NS", "N_S", 'N', item_naquadahIngot, 'S', Item.silk});
			
			if(this.getConfig().canCraftFireBalls && this.getConfig().canCraftFireStaff) {
				this.addRecipe(new ItemStack(item_fireStaff), new Object[] {"F", "C", "L", 'L', item_naquadahLighter, 'C', item_crystalCreation, 'F', item_customFireBall});
				
				if(this.getConfig().canCraftExplosiveFireBalls && this.getConfig().canCraftExplosiveFireStaff) {
					this.addRecipe(new ItemStack(item_explosiveFireStaff), new Object[] {"F", "C", "L", 'L', item_naquadahLighter, 'C', item_crystalCreation, 'F', item_customExplosiveFireBall});
				}
				
				canCraftCreationCrystal = true;
			}
		}
		
		if(this.getConfig().canCraftFireBalls) {
			this.addRecipe(new ItemStack(item_customFireBall), new Object[] {"F", 'F', Item.fireballCharge});
			
			if(this.getConfig().canCraftExplosiveFireBalls) {
				this.addRecipe(new ItemStack(item_customExplosiveFireBall), new Object[] {"F", 'F', item_customFireBall});
			}
			else {
				this.addRecipe(new ItemStack(Item.fireballCharge), new Object[] {"F", 'F', item_customFireBall});
			}
		}
		
		if(canCraftCreationCrystal) {
			this.addRecipe(new ItemStack(item_crystalCreation), new Object[] {"C__", "___", "R__", 'C', item_crystal, 'R', Item.redstone});
			
			canCraftCrystal = true;
		}
		
		if(canCraftCrystal) {
			this.addRecipe(new ItemStack(item_crystal), new Object[] {"R", "D", "D", 'D', Item.diamond, 'R', Item.redstone});
		}
	}
	
	protected void registerDispenserBehaviors() {
		this.registerDispenserBehavior(item_customFireBall, new DispenserBehaviorCustomFireBall());
		this.registerDispenserBehavior(item_customExplosiveFireBall, new DispenserBehaviorCustomExplosiveFireBall());
	}
	
	protected void registerTileEntities() {
		this.registerTileEntity(TileEntityBaseDhd.class, "DhdBase");
		this.registerTileEntity(TileEntityBaseShieldConsole.class, "ShieldConsoleBase");
		this.registerTileEntity(TileEntityBaseTeleporter.class, "TeleporterBase");
		this.registerTileEntity(TileEntityChevron.class, "Chevron");
		this.registerTileEntity(TileEntityDetector.class, "Detector");
		this.registerTileEntity(TileEntityMobGenerator.class, "MobGenerator");
		this.registerTileEntity(TileEntityStargateControl.class, "StargateControl");
		this.registerTileEntity(TileEntityStargatePart.class, "StargatePart");
		this.registerTileEntity(TileEntityStuffLevelUpTable.class, "StuffLevelUpTable");
	}
	
	protected void registerEntities() {
		this.registerEntitiy(EntityCustomFishHook.class, "customFishHook", 0, 250, 5, true);
		this.registerEntitiy(EntityCustomFireBall.class, "customFireBall", 1, 250, 5, true);
		this.registerEntitiy(EntityCustomExplosiveFireBall.class, "customExplosiveFireBall", 2, 250, 5, true);
		this.registerEntitiy(EntityNuke.class, "nuke", 3, 250, 5, true);
		this.registerEntitiy(EntityNapalm.class, "napalm", 4, 250, 5, true);
	}
	
	protected void registerEventHandlers() {
		this.registerEventHandler(new StargateEventHandler());
	}
	
	protected void registerGuiHandler() {
		this.registerGuiHandler(proxy);
	}
	
	protected void registerWorldGenerators() {
		this.registerWorldGenerator(new NaquadahGenerator());
	}
	
	protected void setBlocksHarvestLevel() {
		int naquadahHarvestLevel = this.getConfig().canCraftToolsAndArmors ? 4 : 3;
		
		this.setBlockHarvestLevel(block_naquadahOre, "pickaxe", 3);
		this.setBlockHarvestLevel(block_naquadahBlock, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_naquadahAlloy, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_chevronOff, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_stargateControl, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_dhdPanel, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_dhdBase, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_teleporterPanel, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_teleporterBase, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_shieldConsolePanel, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_shieldConsoleBase, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_detector, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_mobGenerator, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_stuffLevelUpTable, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_fastStargate, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_fastStargate2, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_fastStargate3, "pickaxe", naquadahHarvestLevel);
	}
	
	protected void registerNames() {
		// Tabs :
		this.addName("itemGroup." + stargateBlocksTabName, "Stargate blocks", "Blocs stargate");
		this.addName("itemGroup." + stargateItemsTabName, "Stargate items", "Items stargate");
		
		// Blocks :
		this.addName(block_naquadahOre, "Naquadah ore", "Minerai de naquadah");
		this.addName(block_naquadahBlock, "Naquadah block", "Block de naquadah");
		this.addName(block_naquadahAlloy, "Naquadah alloy", "Alliage de naquadah");
		this.addName(block_chevronOff, "Chevron", "Chevron");
		this.addName(block_chevronOn, "Chevron (Active)", "Chevron (Active)");
		this.addName(block_stargateControl, "Stargate control unit", "Unite de controle de porte des eloiles");
		this.addName(block_vortex, "Vortex", "Vortex");
		this.addName(block_shield, "Shield", "Bouclier");
		this.addName(block_shieldedVortex, "Shielded vortex", "Vortex avec bouclier");
		this.addName(block_kawoosh, "Kawoosh", "Kawoosh");
		this.addName(block_dhdPanel, "DHD control panel", "Panneau de controle de DHD");
		this.addName(block_dhdBase, "DHD base", "Socle de DHD");
		this.addName(block_teleporterPanel, "Teleporter control panel", "Panneau de controle de teleporteur");
		this.addName(block_teleporterBase, "Teleporter base", "Socle de teleporteur");
		this.addName(block_shieldConsolePanel, "Shield console control panel", "Panneau de console de controle de bouclier");
		this.addName(block_shieldConsoleBase, "Shield console base", "Socle de console de controle de bouclier");
		this.addName(block_detector, "Detector", "Detecteur");
		this.addName(block_mobGenerator, "Mob generator", "Generateur de mobs");
		this.addName(block_stuffLevelUpTable, "Stuff levelup table", "Table d'amelioration d'equipment");
		this.addName(block_selfPoweredRedstoneLight, "Self powered redstone light", "Lampe a redstone auto-alimentee");
		this.addName(block_fastStargate, "Fast Stargate v1", "Fast Stargate v1");
		this.addName(block_fastStargate2, "Fast Stargate v2", "Fast Stargate v2");
		this.addName(block_fastStargate3, "Fast Stargate v3", "Fast Stargate v3");
		
		// Items :
		this.addName(item_naquadahOre, "Naquadah ore", "Minerai de naquadah");
		this.addName(item_naquadahIngot, "Naquadah ingot", "Lingot de naquadah");
		this.addName(item_crystal, "Crystal", "Cristal");
		this.addName(item_crystalStargate, "Stargate control crystal", "Cristal de controle de porte des etoiles");
		this.addName(item_crystalDhd, "DHD control crystal", "Cristal de controle de DHD");
		this.addName(item_crystalTeleporter, "Teleporter crystal", "Cristal de teleporteur");
		this.addName(item_crystalScanner, "Scanner crystal", "Cristal de scanner");
		this.addName(item_crystalSoulEmpty, "Empty soul crystal", "Cristal d'ame vide");
		this.addName(item_crystalCreation, "Materialization crystal", "Cristal de materialisation");
		this.addName(item_crystalShield, "Shield crystal", "Cristal de bouclier");
		this.addName(item_chevronCompound, "Chevron compound", "Composant de chevron");
		this.addName(item_stargateControlUnit, "Stargate control unit", "Unite de controle de porte des eloiles");
		this.addName(item_dhdControlUnit, "DHD control unit", "Unite de controle de DHD");
		this.addName(item_teleporterControlUnit, "Teleporter control unit", "Unite de controle de teleporteur");
		this.addName(item_detectorControlUnit, "Detector control unit", "Unite de controle de detecteur");
		this.addName(item_mobGeneratorControlUnit, "Mob generator control unit", "Unite de controle de generateur de mobs");
		this.addName(item_stuffLevelUpTableControlUnit, "Stuff levelup table control unit", "Unite de controle de table d'amelioration d'equipment");
		this.addName(item_shieldControlUnit, "Shield control unit", "Unite de controle de bouclier");
		this.addName(item_dhdPanel, "DHD control panel", "Panneau de controle de DHD");
		this.addName(item_touchScreen, "Teleporter touch screen", "Ecran tactile de teleporteur");
		this.addName(item_shieldRemote, "Shield remote", "Telecommande du bouclier");
		
		this.addName(item_naquadahPickaxe, "Naquadah pickaxe", "Pioche de naquadah");
		this.addName(item_naquadahShovel, "Naquadah shovel", "Pelle de naquadah");
		this.addName(item_naquadahAxe, "Naquadah axe", "Hache de naquadah");
		this.addName(item_naquadahHoe, "Naquadah hoe", "Houe de naquadah");
		this.addName(item_naquadahSword, "Naquadah sword", "Epee de naquadah");
		this.addName(item_naquadahHelmet, "Naquadah helmet", "Haume de naquadah");
		this.addName(item_naquadahPlate, "Naquadah plate", "Armure de naquadah");
		this.addName(item_naquadahLegs, "Naquadah legs", "Jambieres de naquadah");
		this.addName(item_naquadahBoots, "Naquadah boots", "Bottes de naquadah");
		this.addName(item_naquadahBow, "Naquadah bow", "Arc de naquadah");
		this.addName(item_naquadahLighter, "Naquadah ligher", "Briquet de naquadah");
		this.addName(item_naquadahShears, "Naquadah shears", "Cisailles de naquadah");
		this.addName(item_naquadahFishingRod, "Naquadah fishing rod", "Canne a peche de naquadah");
		this.addName(item_fireStaff, "Fire staff", "Baguette de feu");
		this.addName(item_explosiveFireStaff, "Explosive fire staff", "Baguette de feu explosive");
		this.addName(item_nukeStaff, "Nuke staff - wtf!?", "Baguette nucleaire - wtf!?");
		this.addName(item_napalmStaff, "Napalm staff - wtf!?", "Baguette napalm - wtf!?");
		this.addName(item_customFireBall, "Fireball v2 - stargate compatible", "Boule de feu v2 - stargate compatible");
		this.addName(item_customExplosiveFireBall, "Fireball v3 - explosive", "Boule de feu v3 - explosive");
		
		this.addName(item_crystalSoulChicken, "Chicken soul crystal", "Cristal d'ame de poulet");
		this.addName(item_crystalSoulCow, "Cow soul crystal", "Cristal d'ame de vache");
		this.addName(item_crystalSoulMooshroom, "Mooshroom soul crystal", "Cristal d'ame de champimheu");
		this.addName(item_crystalSoulPig, "Pig soul crystal", "Cristal d'ame de cochon");
		this.addName(item_crystalSoulSheep, "Sheep soul crystal", "Cristal d'ame de mouton");
		this.addName(item_crystalSoulOcelot, "Ocelot soul crystal", "Cristal d'ame de chat sauvage");
		this.addName(item_crystalSoulWolf, "Wolf soul crystal", "Cristal d'ame de loup");
		this.addName(item_crystalSoulIronGolem, "Iron golem soul crystal", "Cristal d'ame de golem de fer");
		this.addName(item_crystalSoulSnowman, "Snowman soul crystal", "Cristal d'ame de bonhomme de neige");
		this.addName(item_crystalSoulBlaze, "Blaze soul crystal", "Cristal d'ame d'elementaire de feu");
		this.addName(item_crystalSoulCreeper, "Creeper soul crystal", "Cristal d'ame de creeper");
		this.addName(item_crystalSoulEnderman, "Enderman soul crystal", "Cristal d'ame d'enderman");
		this.addName(item_crystalSoulGiantZombie, "Giant zombie soul crystal", "Cristal d'ame de zombie geant");
		this.addName(item_crystalSoulSilverfish, "Silver fish soul crystal", "Cristal d'ame de poisson d'argent");
		this.addName(item_crystalSoulSkeleton, "Skeleton soul crystal", "Cristal d'ame de skelette");
		this.addName(item_crystalSoulSpider, "Spider soul crystal", "Cristal d'ame d'areignee");
		this.addName(item_crystalSoulCaveSpider, "Cave spider soul crystal", "Cristal d'ame d'areignee venimeuse");
		this.addName(item_crystalSoulWitch, "Witch soul crystal", "Cristal d'ame de sorciere");
		this.addName(item_crystalSoulWither, "Wither soul crystal", "Cristal d'ame de Wither");
		this.addName(item_crystalSoulZombie, "Zombie soul crystal", "Cristal d'ame de zombie");
		this.addName(item_crystalSoulPigZombie, "Pig-zombie soul crystal", "Cristal d'ame de cochon-zombie");
		this.addName(item_crystalSoulSquid, "Squid soul crystal", "Cristal d'ame de poulpe");
		this.addName(item_crystalSoulDragon, "Dragon soul crystal", "Cristal d'ame de Dragon");
		this.addName(item_crystalSoulGhast, "Ghast soul crystal", "Cristal d'ame de ghast");
		this.addName(item_crystalSoulSlime, "Slime soul crystal", "Cristal d'ame de blob");
		this.addName(item_crystalSoulMagmaCube, "Magma cube soul crystal", "Cristal d'ame de blob de lave");
		this.addName(item_crystalSoulBat, "Bat soul crystal", "Cristal d'ame de chauve-souris");
		this.addName(item_crystalSoulVillager, "Villager soul crystal", "Cristal d'ame de villageois");
		this.addName(item_crystalSoulHorse, "Horse soul crystal", "Cristal d'ame de cheval");
		
		// Inventory :
		this.addName(TileEntityBaseTeleporter.INV_NAME, "Teleporter", "Teleporteur");
		this.addName(TileEntityBaseDhd.INV_NAME, "DHD", "DHD");
		this.addName(TileEntityBaseShieldConsole.INV_NAME, "Shield console", "Console du bouclier");
		this.addName(TileEntityDetector.INV_NAME, "Detector", "Detecteur");
		this.addName(TileEntityMobGenerator.INV_NAME, "Mob generator", "Generateur de mobs");
		this.addName(TileEntityStargateControl.INV_NAME, "Stargate", "Stargate");
		this.addName(TileEntityStuffLevelUpTable.INV_NAME, "Stuff level up table", "Table d'amelioration d'equipement");
		
		this.addName(GuiScreen.ENTER, " (ENTER)", " (ENTRER)");
		this.addName(GuiScreen.TAB, " (TAB)", " (TAB)");
		this.addName(GuiScreen.ESC, " (ESC)", " (ECHAP)");
		
		this.addName(GuiBase.DESTINATION, "Destination", "Destination");
		this.addName(GuiBase.NAME, "Name", "Nom");
		this.addName(GuiBase.ADD_THIS, "Add to the list", "Ajouter a la liste");
		this.addName(GuiBase.ADD, "Add", "Ajouter");
		this.addName(GuiBase.DELETE, "Delete", "Supprimer");
		this.addName(GuiBase.OVERWRITE, "Overwrite", "Ecraser");
		this.addName(GuiBase.TAB, "Tab", "Tab");
		this.addName(GuiBase.ALL, "All", "Tous");
		
		this.addName(GuiTeleporter.COORDINATES, "Coordinates", "Coordonnees");
		this.addName(GuiTeleporter.TELEPORT, "Teleport", "Teleportation");
		this.addName(GuiTeleporter.IN_RANGE, "In range", "A portee");
		this.addName(GuiTeleporter.MESSAGE_OK, "Coordinates in range", "Coordonnees a portee");
		this.addName(GuiTeleporter.MESSAGE_OUT_OF_RANGE, "Coordinates out of range", "Coordonnes trop eloignees");
		this.addName(GuiTeleporter.MESSAGE_INVALID, "Invalid coordinates", "Coordonnees non valides");
		
		this.addName(GuiDhd.ADDRESS, "Address", "Adresse");
		this.addName(GuiDhd.ACTIVATE, "Activate", "Activer");
		this.addName(GuiDhd.CLOSE, "Close", "Fermer");
		this.addName(GuiDhd.EARTH, "Earth", "Terre");
		this.addName(GuiDhd.HELL, "Hell", "Enfer");
		this.addName(GuiDhd.END, "End", "End");
		this.addName(DhdPanel.RESET, "Reset", "Reset");
		
		this.addName(GuiShieldConsole.CURRENT_CODE, "Current code", "Code actuel");
		this.addName(GuiShieldConsole.SHIELD_ON, "Shield : ON", "Bouclier : ON");
		this.addName(GuiShieldConsole.SHIELD_OFF, "Shield : OFF", "Bouclier : OFF");
		this.addName(GuiShieldConsole.SHIELD_DISCONNECTED, "Shield : disconnected", "Bouclier : deconnecte");
		this.addName(GuiShieldConsole.SHIELD_SWITCH, "Shield", "Bouclier");
		this.addName(GuiShieldConsole.AUTO_SHIELD_ON, "Automatic shield : ON", "Bouclier automtique : ON");
		this.addName(GuiShieldConsole.AUTO_SHIELD_OFF, "Automatic shield : OFF", "Bouclier automatique : OFF");
		this.addName(GuiShieldConsole.AUTO_SHIELD_DISCONNECTED, "Automatic shield : disconnected", "Bouclier automatique : deconnecte");
		this.addName(GuiShieldConsole.AUTO_SHIELD_SWITCH, "Automatic shield", "Bouclier automatique");
		this.addName(GuiShieldConsole.CHANGE_CODE, "Change code", "Changer le code");
		
		this.addName(GuiStargateControl.DEFAULT_ADDRESS, "Default address", "Adresse par defaut");
		this.addName(GuiStargateControl.CUSTOM_ADDRESS, "Custom address", "Adresse speciale");
		this.addName(GuiStargateControl.CREATE_WITH_DEFAULT_ADDRESS, "Create with the default address", "Creer avec l'adresse par defaut");
		this.addName(GuiStargateControl.CREATE_WITH_CUSTOM_ADDRESS, "Create with a custom address", "Creer avec une adresse speciale");
		this.addName(GuiStargateControl.ADDRESS_SELECTION, "Address selection", "Choix d'une adresse");
		
		this.addName(GuiDetector.RANGE, "Range", "Portee");
		this.addName(GuiDetector.RANGE_LIMITS, "min range = 1, max range = 10", "portee min = 1, portee max = 10");
		this.addName(GuiDetector.INVERTED_OUTPUT, "Inverted output.", "Sortie inversee.");
		this.addName(GuiDetector.NORMAL_OUTPUT, "Normal output", "Sortie normale");
		this.addName(GuiDetector.INVERT_BUTTON, "Invert output", "Inverser la sortie");
		
		this.addName(GuiShieldRemote.INV_NAME, "Shield remote", "Telecommande du bouclier");
		this.addName(GuiShieldRemote.CODE, "Code", "Code");
		this.addName(GuiShieldRemote.SEND_CODE, "Send code", "Envoyer le code");
		
		this.addName(GuiStuffLevelUpTable.POWER, "Power", "Puissance");
		
		// Damage sources :
		this.addName("death.attack." + CustomDamageSource.kawoosh.getDamageType(), "%1$s tried to swim into the kawoosh.", "%1$s a essaye de nager dans le kawoosh.");
		this.addName("death.attack." + CustomDamageSource.iris.getDamageType(), "%1$s couldn't exit hyperspace.", "%1$s n'a pas pu sortir de l'hyper-espace.");
	}
	
}

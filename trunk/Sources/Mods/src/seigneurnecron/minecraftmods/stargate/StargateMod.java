package seigneurnecron.minecraftmods.stargate;

import static seigneurnecron.minecraftmods.stargate.tileentity.console.Console.CONSOLE_INFO_PREFIX;
import static seigneurnecron.minecraftmods.stargate.tileentity.console.Console.CONSOLE_NAME_PREFIX;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
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
import seigneurnecron.minecraftmods.stargate.block.BlockChevron;
import seigneurnecron.minecraftmods.stargate.block.BlockChevronOn;
import seigneurnecron.minecraftmods.stargate.block.BlockConsoleBase;
import seigneurnecron.minecraftmods.stargate.block.BlockConsolePanel;
import seigneurnecron.minecraftmods.stargate.block.BlockDetector;
import seigneurnecron.minecraftmods.stargate.block.BlockFastStargate;
import seigneurnecron.minecraftmods.stargate.block.BlockFastStargate2;
import seigneurnecron.minecraftmods.stargate.block.BlockFastStargate3;
import seigneurnecron.minecraftmods.stargate.block.BlockKawoosh;
import seigneurnecron.minecraftmods.stargate.block.BlockMobGenerator;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahAlloy;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahBlock;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahOre;
import seigneurnecron.minecraftmods.stargate.block.BlockSelfPoweredRedstoneLight;
import seigneurnecron.minecraftmods.stargate.block.BlockShield;
import seigneurnecron.minecraftmods.stargate.block.BlockStargateControl;
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
import seigneurnecron.minecraftmods.stargate.gui.GuiConsoleBase;
import seigneurnecron.minecraftmods.stargate.gui.GuiDetector;
import seigneurnecron.minecraftmods.stargate.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.gui.GuiMobGenerator;
import seigneurnecron.minecraftmods.stargate.gui.GuiScreenConsolePanel;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldConsole;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldRemote;
import seigneurnecron.minecraftmods.stargate.gui.GuiSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStargateControl;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.gui.components.DhdPanel;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryMobGenerator;
import seigneurnecron.minecraftmods.stargate.inventory.InventorySoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.item.ItemCustomFireBall;
import seigneurnecron.minecraftmods.stargate.item.ItemShieldRemote;
import seigneurnecron.minecraftmods.stargate.item.ItemSoul;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
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
import seigneurnecron.minecraftmods.stargate.item.stuff.ItemNaquadahLeggings;
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
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleDefault;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateShield;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleTeleporter;
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
	public static final String VERSION = "[1.6.4] v3.2.0 [core v1.1.0]";
	
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
	public static Block block_consoleBase;
	public static Block block_consolePanel;
	public static Block block_detector;
	public static Block block_mobGenerator;
	public static Block block_selfPoweredRedstoneLight;
	public static Block block_fastStargate;
	public static Block block_fastStargate2;
	public static Block block_fastStargate3;
	
	// Items :
	
	public static Item item_naquadahOre;
	public static Item item_naquadahIngot;
	public static Item item_chevronCompound;
	public static Item item_touchScreen;
	public static Item item_shieldRemote;
	public static ItemSoul item_soul;
	
	public static Item item_naquadahHelmet;
	public static Item item_naquadahPlate;
	public static Item item_naquadahLeggings;
	public static Item item_naquadahBoots;
	
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
	
	public static ItemCrystal item_crystal;
	public static ItemCrystal item_crystalStargate;
	public static ItemCrystal item_crystalStargateInterface;
	public static ItemCrystal item_crystalDhd;
	public static ItemCrystal item_crystalShield;
	public static ItemCrystal item_crystalTeleporter;
	public static ItemCrystal item_crystalScanner;
	public static ItemCrystal item_crystalEnchantment;
	public static ItemCrystal item_crystalStuffData;
	public static ItemCrystal item_crystalSoulData;
	public static ItemSoulCrystal item_crystalSoulEmpty;
	
	public static ItemSoulCrystalFull item_crystalSoulChicken;
	public static ItemSoulCrystalFull item_crystalSoulCow;
	public static ItemSoulCrystalFull item_crystalSoulMooshroom;
	public static ItemSoulCrystalFull item_crystalSoulPig;
	public static ItemSoulCrystalFull item_crystalSoulSheep;
	public static ItemSoulCrystalFull item_crystalSoulOcelot;
	public static ItemSoulCrystalFull item_crystalSoulWolf;
	public static ItemSoulCrystalFull item_crystalSoulIronGolem;
	public static ItemSoulCrystalFull item_crystalSoulSnowman;
	public static ItemSoulCrystalFull item_crystalSoulBlaze;
	public static ItemSoulCrystalFull item_crystalSoulCreeper;
	public static ItemSoulCrystalFull item_crystalSoulEnderman;
	public static ItemSoulCrystalFull item_crystalSoulSilverfish;
	public static ItemSoulCrystalFull item_crystalSoulSkeleton;
	public static ItemSoulCrystalFull item_crystalSoulSpider;
	public static ItemSoulCrystalFull item_crystalSoulCaveSpider;
	public static ItemSoulCrystalFull item_crystalSoulZombie;
	public static ItemSoulCrystalFull item_crystalSoulPigZombie;
	public static ItemSoulCrystalFull item_crystalSoulSquid;
	public static ItemSoulCrystalFull item_crystalSoulGhast;
	public static ItemSoulCrystalFull item_crystalSoulSlime;
	public static ItemSoulCrystalFull item_crystalSoulMagmaCube;
	public static ItemSoulCrystalFull item_crystalSoulBat;
	public static ItemSoulCrystalFull item_crystalSoulVillager;
	public static ItemSoulCrystalFull item_crystalSoulHorse;
	
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
	public static final String blockName_consoleBase = "block_consoleBase";
	public static final String blockName_consolePanel = "block_consolePanel";
	public static final String blockName_detector = "block_detector";
	public static final String blockName_mobGenerator = "block_mobGenerator";
	public static final String blockName_selfPoweredRedstoneLight = "block_selfPoweredRedstoneLight";
	public static final String blockName_fastStargate = "block_fastStargate";
	public static final String blockName_fastStargate2 = "block_fastStargate2";
	public static final String blockName_fastStargate3 = "block_fastStargate3";
	
	// Item names :
	
	public static final String itemName_naquadahOre = "item_naquadahOre";
	public static final String itemName_naquadahIngot = "item_naquadahIngot";
	public static final String itemName_chevronCompound = "item_chevronCompound";
	public static final String itemName_touchScreen = "item_touchScreen";
	public static final String itemName_shieldRemote = "item_shieldRemote";
	public static final String itemName_soul = "item_soul";
	
	public static final String itemName_naquadahHelmet = "item_naquadahHelmet";
	public static final String itemName_naquadahPlate = "item_naquadahPlate";
	public static final String itemName_naquadahLeggings = "item_naquadahLegs";
	public static final String itemName_naquadahBoots = "item_naquadahBoots";
	
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
	
	public static final String itemName_crystal = "item_crystal";
	public static final String itemName_crystalStargate = "item_crystalStargate";
	public static final String itemName_crystalStargateInterface = "item_crystalStargateInterface";
	public static final String itemName_crystalDhd = "item_crystalDhd";
	public static final String itemName_crystalShield = "item_crystalShield";
	public static final String itemName_crystalTeleporter = "item_crystalTeleporter";
	public static final String itemName_crystalScanner = "item_crystalScanner";
	public static final String itemName_crystalEnchantment = "item_crystalEnchantment";
	public static final String itemName_crystalStuffData = "item_crystalStuffData";
	public static final String itemName_crystalSoulData = "item_crystalSoulData";
	public static final String itemName_crystalSoulEmpty = "item_crystalSoulEmpty";
	
	public static final String itemName_crystalSoul = "item_crystalSoul";
	
	// Console names :
	
	public static final String CONSOLE_DEFAULT = "DefaultConsole";
	public static final String CONSOLE_TELEPORTER = "Teleporter";
	public static final String CONSOLE_STARGATE_DHD = "StargateDhd";
	public static final String CONSOLE_STARGATE_SHIELD = "StargateShield";
	public static final String CONSOLE_STUFF_LEVEL_UP_TABLE = "StuffLevelUpTable";
	public static final String CONSOLE_SOUL_CRYSTAL_FACTORY = "SoulCrystalFactory";
	
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
		this.registerConsoles();
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
		block_consoleBase = new BlockConsoleBase(blockName_consoleBase);
		block_consolePanel = new BlockConsolePanel(blockName_consolePanel);
		block_detector = new BlockDetector(blockName_detector);
		block_mobGenerator = new BlockMobGenerator(blockName_mobGenerator);
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
		item_chevronCompound = new ItemStargate(itemName_chevronCompound);
		item_touchScreen = new ItemStargate(itemName_touchScreen);
		item_shieldRemote = new ItemShieldRemote(itemName_shieldRemote);
		item_soul = new ItemSoul(itemName_soul);
		
		item_naquadahHelmet = new ItemNaquadahHelmet(itemName_naquadahHelmet, naquadaArmorRenderIndex);
		item_naquadahPlate = new ItemNaquadahPlate(itemName_naquadahPlate, naquadaArmorRenderIndex);
		item_naquadahLeggings = new ItemNaquadahLeggings(itemName_naquadahLeggings, naquadaArmorRenderIndex);
		item_naquadahBoots = new ItemNaquadahBoots(itemName_naquadahBoots, naquadaArmorRenderIndex);
		
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
		
		item_crystal = new ItemCrystal(itemName_crystal, "Cr");
		item_crystalStargate = new ItemCrystal(itemName_crystalStargate, "SGC");
		item_crystalStargateInterface = new ItemCrystal(itemName_crystalStargateInterface, "SGI");
		item_crystalDhd = new ItemCrystal(itemName_crystalDhd, "DHD");
		item_crystalShield = new ItemCrystal(itemName_crystalShield, "Sh");
		item_crystalTeleporter = new ItemCrystal(itemName_crystalTeleporter, "Te");
		item_crystalScanner = new ItemCrystal(itemName_crystalScanner, "Sc");
		item_crystalEnchantment = new ItemCrystal(itemName_crystalEnchantment, "En");
		item_crystalStuffData = new ItemCrystal(itemName_crystalStuffData, "DSt");
		item_crystalSoulData = new ItemCrystal(itemName_crystalSoulData, "DSo");
		item_crystalSoulEmpty = new ItemSoulCrystal(itemName_crystalSoulEmpty, "SE");
		
		final int D1 = ItemSoulCrystalFull.DEFAULT_SPAWN_COUNT;
		final int D2 = ItemSoulCrystalFull.DEFAULT_MAX_MOB;
		final int D3 = ItemSoulCrystalFull.DEFAULT_NEEDED_SOUL;
		final double D4 = ItemSoulCrystalFull.DEFAULT_SOUL_DROP_PROBA;
		
		item_crystalSoulChicken = new ItemSoulCrystalFull(EntityChicken.class);
		item_crystalSoulCow = new ItemSoulCrystalFull(EntityCow.class);
		item_crystalSoulMooshroom = new ItemSoulCrystalFull(EntityMooshroom.class, D1, D2, 100, D4);
		item_crystalSoulPig = new ItemSoulCrystalFull(EntityPig.class);
		item_crystalSoulSheep = new ItemSoulCrystalFull(EntitySheep.class);
		item_crystalSoulOcelot = new ItemSoulCrystalFull(EntityOcelot.class, D1, D2, 100, D4);
		item_crystalSoulWolf = new ItemSoulCrystalFull(EntityWolf.class, D1, D2, 100, D4);
		item_crystalSoulIronGolem = new ItemSoulCrystalFull(EntityIronGolem.class, 2, 4, 100, D4);
		item_crystalSoulSnowman = new ItemSoulCrystalFull(EntitySnowman.class, 2, 6, 100, D4);
		item_crystalSoulBlaze = new ItemSoulCrystalFull(EntityBlaze.class, D1, D2, 100, D4);
		item_crystalSoulCreeper = new ItemSoulCrystalFull(EntityCreeper.class);
		item_crystalSoulEnderman = new ItemSoulCrystalFull(EntityEnderman.class, 2, 4, D3, D4);
		item_crystalSoulSilverfish = new ItemSoulCrystalFull(EntitySilverfish.class, D1, D2, 50, 1);
		item_crystalSoulSkeleton = new ItemSoulCrystalFull(EntitySkeleton.class);
		item_crystalSoulSpider = new ItemSoulCrystalFull(EntitySpider.class);
		item_crystalSoulCaveSpider = new ItemSoulCrystalFull(EntityCaveSpider.class);
		item_crystalSoulZombie = new ItemSoulCrystalFull(EntityZombie.class);
		item_crystalSoulPigZombie = new ItemSoulCrystalFull(EntityPigZombie.class);
		item_crystalSoulSquid = new ItemSoulCrystalFull(EntitySquid.class);
		item_crystalSoulGhast = new ItemSoulCrystalFull(EntityGhast.class, 1, 2, 100, 1);
		item_crystalSoulSlime = new ItemSoulCrystalFull(EntitySlime.class);
		item_crystalSoulMagmaCube = new ItemSoulCrystalFull(EntityMagmaCube.class, D1, D2, 100, D4);
		item_crystalSoulBat = new ItemSoulCrystalFull(EntityBat.class, 6, 12, 100, D4);
		item_crystalSoulVillager = new ItemSoulCrystalFull(EntityVillager.class, 1, D2, 50, 1);
		item_crystalSoulHorse = new ItemSoulCrystalFull(EntityHorse.class, 2, D2, 50, 1);
	}
	
	protected void registerConsoles() {
		ArrayList<ItemCrystal> crystalList;
		
		crystalList = new ArrayList<ItemCrystal>();
		Console.registerConsole(crystalList, ConsoleDefault.class, CONSOLE_DEFAULT);
		
		crystalList = new ArrayList<ItemCrystal>();
		crystalList.add(item_crystalTeleporter);
		Console.registerConsole(crystalList, ConsoleTeleporter.class, CONSOLE_TELEPORTER);
		
		crystalList = new ArrayList<ItemCrystal>();
		crystalList.add(item_crystalStargateInterface);
		crystalList.add(item_crystalDhd);
		Console.registerConsole(crystalList, ConsoleStargateDhd.class, CONSOLE_STARGATE_DHD);
		
		crystalList = new ArrayList<ItemCrystal>();
		crystalList.add(item_crystalStargateInterface);
		crystalList.add(item_crystalShield);
		Console.registerConsole(crystalList, ConsoleStargateShield.class, CONSOLE_STARGATE_SHIELD);
		
		crystalList = new ArrayList<ItemCrystal>();
		crystalList.add(item_crystalEnchantment);
		crystalList.add(item_crystalStuffData);
		Console.registerConsole(crystalList, ConsoleStuffLevelUpTable.class, CONSOLE_STUFF_LEVEL_UP_TABLE);
		
		crystalList = new ArrayList<ItemCrystal>();
		crystalList.add(item_crystalEnchantment);
		crystalList.add(item_crystalSoulData);
		Console.registerConsole(crystalList, ConsoleSoulCrystalFactory.class, CONSOLE_SOUL_CRYSTAL_FACTORY);
	}
	
	protected void registerRecipes() {
		boolean canCraftConsole = false;
		boolean canCraftCrystal = false;
		boolean canCraftEnchantmentCrystal = false;
		
		this.addSmelting(new ItemStack(item_naquadahIngot), item_naquadahOre.itemID);
		
		this.addRecipe(new ItemStack(block_naquadahBlock), new Object[] {"NNN", "NNN", "NNN", 'N', item_naquadahIngot});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalStargate});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalStargateInterface});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalDhd});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalShield});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalTeleporter});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalScanner});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalEnchantment});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalStuffData});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalSoulData});
		this.addRecipe(new ItemStack(item_crystal), new Object[] {"C", 'C', item_crystalSoulEmpty});
		this.addRecipe(new ItemStack(Block.redstoneLampIdle), new Object[] {"L", 'L', block_selfPoweredRedstoneLight});
		this.addRecipe(new ItemStack(Item.fireballCharge), new Object[] {"F", 'F', item_customExplosiveFireBall});
		
		if(this.getConfig().canCraftNaquadahAlloy) {
			this.addRecipe(new ItemStack(block_naquadahAlloy), new Object[] {"NIN", "I_I", "NIN", 'N', item_naquadahIngot, 'I', Item.ingotIron});
			
			if(this.getConfig().canCraftStargate) {
				this.addRecipe(new ItemStack(item_crystalStargate), new Object[] {"CR_", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_crystalStargateInterface), new Object[] {"CRR", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_crystalDhd), new Object[] {"C_R", "___", "___", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_chevronCompound), new Object[] {"NCN", "_N_", 'N', item_naquadahIngot, 'C', item_crystal});
				this.addRecipe(new ItemStack(block_chevronOff), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_chevronCompound});
				this.addRecipe(new ItemStack(block_stargateControl), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_crystalStargate});
				
				if(this.getConfig().canCraftStargateShield) {
					this.addRecipe(new ItemStack(item_crystalShield), new Object[] {"C__", "__R", "___", 'C', item_crystal, 'R', Item.redstone});
					this.addRecipe(new ItemStack(item_shieldRemote), new Object[] {"CS", "IR", 'C', item_crystalShield, 'S', item_touchScreen, 'I', Item.ingotIron, 'R', Item.redstone});
				}
				
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftTeleporter) {
				this.addRecipe(new ItemStack(item_crystalTeleporter), new Object[] {"C__", "R__", "___", 'C', item_crystal, 'R', Item.redstone});
				
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftDetector) {
				this.addRecipe(new ItemStack(item_crystalScanner), new Object[] {"C__", "___", "R__", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(block_detector), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_crystalScanner});
				
				canCraftCrystal = true;
			}
			
			if(this.getConfig().canCraftStuffLevelUpTable) {
				this.addRecipe(new ItemStack(item_crystalStuffData), new Object[] {"C__", "RR_", "___", 'C', item_crystal, 'R', Item.redstone});
				
				canCraftEnchantmentCrystal = true;
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftMobGenerator) {
				this.addRecipe(new ItemStack(item_crystalSoulEmpty), new Object[] {"C__", "___", "_R_", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(item_crystalSoulData), new Object[] {"C__", "_R_", "_R_", 'C', item_crystal, 'R', Item.redstone});
				this.addRecipe(new ItemStack(block_mobGenerator), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_crystalSoulEmpty});
				
				canCraftEnchantmentCrystal = true;
				canCraftConsole = true;
			}
			
			if(canCraftConsole) {
				this.addRecipe(new ItemStack(item_touchScreen), new Object[] {"GGG", "RCR", 'G', Block.glass, 'R', Item.redstone, 'C', item_crystal});
				this.addRecipe(new ItemStack(block_consolePanel), new Object[] {"S", "N", 'N', block_naquadahAlloy, 'S', item_touchScreen});
				this.addRecipe(new ItemStack(block_consoleBase), new Object[] {"C", "N", 'N', block_naquadahAlloy, 'C', item_crystal});
				
				canCraftCrystal = true;
			}
		}
		
		if(this.getConfig().canCraftSelfPoweredRedstoneLight) {
			this.addRecipe(new ItemStack(block_selfPoweredRedstoneLight), new Object[] {"L", 'L', Block.redstoneLampIdle});
		}
		
		if(this.getConfig().canCraftToolsAndArmors) {
			this.addRecipe(new ItemStack(item_naquadahPickaxe), new Object[] {"NNN", "_S_", "_S_", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahShovel), new Object[] {"N", "S", "S", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahAxe), new Object[] {"NN", "SN", "S_", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahHoe), new Object[] {"NN", "S_", "S_", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahSword), new Object[] {"N", "N", "S", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahHelmet), new Object[] {"NNN", "N_N", "___", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahPlate), new Object[] {"N_N", "NNN", "NNN", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahLeggings), new Object[] {"NNN", "N_N", "N_N", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahBoots), new Object[] {"___", "N_N", "N_N", 'N', item_naquadahIngot, 'S', Item.stick});
			this.addRecipe(new ItemStack(item_naquadahBow), new Object[] {"_NS", "N_S", "_NS", 'N', item_naquadahIngot, 'S', Item.silk});
			this.addRecipe(new ItemStack(item_naquadahLighter), new Object[] {"D", "N", 'N', item_naquadahIngot, 'D', Item.diamond});
			this.addRecipe(new ItemStack(item_naquadahShears), new Object[] {"_N", "N_", 'N', item_naquadahIngot});
			this.addRecipe(new ItemStack(item_naquadahFishingRod), new Object[] {"__N", "_NS", "N_S", 'N', item_naquadahIngot, 'S', Item.silk});
			
			if(this.getConfig().canCraftFireBalls && this.getConfig().canCraftFireStaff) {
				this.addRecipe(new ItemStack(item_fireStaff), new Object[] {"F", "C", "L", 'L', item_naquadahLighter, 'C', item_crystalEnchantment, 'F', item_customFireBall});
				
				if(this.getConfig().canCraftExplosiveFireBalls && this.getConfig().canCraftExplosiveFireStaff) {
					this.addRecipe(new ItemStack(item_explosiveFireStaff), new Object[] {"F", "C", "L", 'L', item_naquadahLighter, 'C', item_crystalEnchantment, 'F', item_customExplosiveFireBall});
				}
				
				canCraftEnchantmentCrystal = true;
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
		
		if(canCraftEnchantmentCrystal) {
			this.addRecipe(new ItemStack(item_crystalEnchantment), new Object[] {"C__", "_R_", "___", 'C', item_crystal, 'R', Item.redstone});
			
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
		this.registerTileEntity(TileEntityChevron.class, "SeigneurNecron_StargateMod_Chevron");
		this.registerTileEntity(TileEntityConsoleBase.class, "SeigneurNecron_StargateMod_ConsoleBase");
		this.registerTileEntity(TileEntityDetector.class, "SeigneurNecron_StargateMod_Detector");
		this.registerTileEntity(TileEntityMobGenerator.class, "SeigneurNecron_StargateMod_MobGenerator");
		this.registerTileEntity(TileEntityStargateControl.class, "SeigneurNecron_StargateMod_StargateControl");
		this.registerTileEntity(TileEntityStargatePart.class, "SeigneurNecron_StargateMod_StargatePart");
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
		this.setBlockHarvestLevel(block_chevronOn, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_stargateControl, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_consoleBase, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_consolePanel, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_detector, "pickaxe", naquadahHarvestLevel);
		this.setBlockHarvestLevel(block_mobGenerator, "pickaxe", naquadahHarvestLevel);
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
		this.addName(block_consoleBase, "Console base", "Socle de console");
		this.addName(block_consolePanel, "Control panel", "Panneau de controle");
		this.addName(block_detector, "Detector", "Detecteur");
		this.addName(block_mobGenerator, "Mob generator", "Generateur de mobs");
		this.addName(block_selfPoweredRedstoneLight, "Self powered redstone light", "Lampe a redstone auto-alimentee");
		this.addName(block_fastStargate, "Fast Stargate v1", "Fast Stargate v1");
		this.addName(block_fastStargate2, "Fast Stargate v2", "Fast Stargate v2");
		this.addName(block_fastStargate3, "Fast Stargate v3", "Fast Stargate v3");
		
		// Items :
		this.addName(item_naquadahOre, "Naquadah ore", "Minerai de naquadah");
		this.addName(item_naquadahIngot, "Naquadah ingot", "Lingot de naquadah");
		this.addName(item_chevronCompound, "Chevron compound", "Composant de chevron");
		this.addName(item_touchScreen, "Touch screen", "Ecran tactile");
		this.addName(item_shieldRemote, "Shield remote", "Telecommande du bouclier");
		this.addName(item_soul, "Soul", "Ame");
		
		this.addName(item_naquadahHelmet, "Naquadah helmet", "Haume de naquadah");
		this.addName(item_naquadahPlate, "Naquadah plate", "Armure de naquadah");
		this.addName(item_naquadahLeggings, "Naquadah leggings", "Jambieres de naquadah");
		this.addName(item_naquadahBoots, "Naquadah boots", "Bottes de naquadah");
		
		this.addName(item_naquadahPickaxe, "Naquadah pickaxe", "Pioche de naquadah");
		this.addName(item_naquadahShovel, "Naquadah shovel", "Pelle de naquadah");
		this.addName(item_naquadahAxe, "Naquadah axe", "Hache de naquadah");
		this.addName(item_naquadahHoe, "Naquadah hoe", "Houe de naquadah");
		this.addName(item_naquadahSword, "Naquadah sword", "Epee de naquadah");
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
		
		this.addName(item_crystal, "Crystal", "Cristal");
		this.addName(item_crystalStargate, "Stargate control crystal", "Cristal de controle de porte des etoiles");
		this.addName(item_crystalStargateInterface, "Stargate interface crystal", "Cristal d'interfacage a la porte des etoiles");
		this.addName(item_crystalDhd, "DHD control crystal", "Cristal de controle de DHD");
		this.addName(item_crystalShield, "Shield crystal", "Cristal de bouclier");
		this.addName(item_crystalTeleporter, "Teleporter crystal", "Cristal de teleporteur");
		this.addName(item_crystalScanner, "Scanner crystal", "Cristal de scanner");
		this.addName(item_crystalEnchantment, "Enchantment crystal", "Cristal d'enchantement");
		this.addName(item_crystalStuffData, "Data crystal - Stuff", "Cristal de donnees - equipement");
		this.addName(item_crystalSoulData, "Data crystal - Souls", "Cristal de donnees - ames");
		this.addName(item_crystalSoulEmpty, "Empty soul crystal", "Cristal d'ame vide");
		
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
		this.addName(item_crystalSoulSilverfish, "Silver fish soul crystal", "Cristal d'ame de poisson d'argent");
		this.addName(item_crystalSoulSkeleton, "Skeleton soul crystal", "Cristal d'ame de skelette");
		this.addName(item_crystalSoulSpider, "Spider soul crystal", "Cristal d'ame d'areignee");
		this.addName(item_crystalSoulCaveSpider, "Cave spider soul crystal", "Cristal d'ame d'areignee venimeuse");
		this.addName(item_crystalSoulZombie, "Zombie soul crystal", "Cristal d'ame de zombie");
		this.addName(item_crystalSoulPigZombie, "Pig-zombie soul crystal", "Cristal d'ame de cochon-zombie");
		this.addName(item_crystalSoulSquid, "Squid soul crystal", "Cristal d'ame de poulpe");
		this.addName(item_crystalSoulGhast, "Ghast soul crystal", "Cristal d'ame de ghast");
		this.addName(item_crystalSoulSlime, "Slime soul crystal", "Cristal d'ame de blob");
		this.addName(item_crystalSoulMagmaCube, "Magma cube soul crystal", "Cristal d'ame de blob de lave");
		this.addName(item_crystalSoulBat, "Bat soul crystal", "Cristal d'ame de chauve-souris");
		this.addName(item_crystalSoulVillager, "Villager soul crystal", "Cristal d'ame de villageois");
		this.addName(item_crystalSoulHorse, "Horse soul crystal", "Cristal d'ame de cheval");
		
		// Inventory :
		
		this.addName(GuiScreenConsolePanel.DESTINATION, "Destination", "Destination");
		this.addName(GuiScreenConsolePanel.NAME, "Name", "Nom");
		this.addName(GuiScreenConsolePanel.ADD_THIS, "Add to the list", "Ajouter a la liste");
		this.addName(GuiScreenConsolePanel.ADD, "Add", "Ajouter");
		this.addName(GuiScreenConsolePanel.DELETE, "Delete", "Supprimer");
		this.addName(GuiScreenConsolePanel.OVERWRITE, "Overwrite", "Ecraser");
		this.addName(GuiScreenConsolePanel.TAB, "Tab", "Tab");
		this.addName(GuiScreenConsolePanel.ALL, "All", "Tous");
		
		this.addName(GuiTeleporter.INV_NAME, "Teleporter", "Teleporteur");
		this.addName(GuiTeleporter.COORDINATES, "Coordinates", "Coordonnees");
		this.addName(GuiTeleporter.TELEPORT, "Teleport", "Teleportation");
		this.addName(GuiTeleporter.IN_RANGE, "In range", "A portee");
		this.addName(GuiTeleporter.MESSAGE_OK, "Coordinates in range", "Coordonnees a portee");
		this.addName(GuiTeleporter.MESSAGE_OUT_OF_RANGE, "Coordinates out of range", "Coordonnes trop eloignees");
		this.addName(GuiTeleporter.MESSAGE_INVALID, "Invalid coordinates", "Coordonnees non valides");
		
		this.addName(GuiDhd.INV_NAME, "DHD", "DHD");
		this.addName(GuiDhd.ADDRESS, "Address", "Adresse");
		this.addName(GuiDhd.ACTIVATE, "Activate", "Activer");
		this.addName(GuiDhd.CLOSE, "Close", "Fermer");
		this.addName(GuiDhd.EARTH, "Earth", "Terre");
		this.addName(GuiDhd.HELL, "Hell", "Enfer");
		this.addName(GuiDhd.END, "End", "End");
		this.addName(DhdPanel.RESET, "Reset", "Reset");
		
		this.addName(GuiShieldConsole.INV_NAME, "Shield console", "Console du bouclier");
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
		
		this.addName(GuiStargateControl.INV_NAME, "Stargate", "Stargate");
		this.addName(GuiStargateControl.DEFAULT_ADDRESS, "Default address", "Adresse par defaut");
		this.addName(GuiStargateControl.CUSTOM_ADDRESS, "Custom address", "Adresse speciale");
		this.addName(GuiStargateControl.CREATE_WITH_DEFAULT_ADDRESS, "Create with the default address", "Creer avec l'adresse par defaut");
		this.addName(GuiStargateControl.CREATE_WITH_CUSTOM_ADDRESS, "Create with a custom address", "Creer avec une adresse speciale");
		this.addName(GuiStargateControl.ADDRESS_SELECTION, "Address selection", "Choix d'une adresse");
		
		this.addName(GuiDetector.INV_NAME, "Detector", "Detecteur");
		this.addName(GuiDetector.RANGE, "Range", "Portee");
		this.addName(GuiDetector.RANGE_LIMITS, "min range = 1, max range = 10", "portee min = 1, portee max = 10");
		this.addName(GuiDetector.INVERTED_OUTPUT, "Inverted output.", "Sortie inversee.");
		this.addName(GuiDetector.NORMAL_OUTPUT, "Normal output", "Sortie normale");
		this.addName(GuiDetector.INVERT_BUTTON, "Invert output", "Inverser la sortie");
		
		this.addName(GuiShieldRemote.INV_NAME, "Shield remote", "Telecommande du bouclier");
		this.addName(GuiShieldRemote.CODE, "Code", "Code");
		this.addName(GuiShieldRemote.SEND_CODE, "Send code", "Envoyer le code");
		
		this.addName(InventoryConsoleBase.INV_NAME, "Console crystal slots", "Emplacements de cristaux");
		this.addName(GuiConsoleBase.INFO, "Insert crystals to configure the console. Basic crystals are crafted with diamonds. You can configure crystals with redstone...", "Inserez des crystaux pour configurer la console. Les crystaux basiques sont fabriques a partir de diamant. Vous pouvez configurer les crystaux avec de la redstone...");
		
		this.addName(InventorySoulCrystalFactory.INV_NAME, "Soul crystal factory", "Table de creation de cristaux d'ame");
		this.addName(GuiSoulCrystalFactory.CREATE, "Create", "Creer");
		this.addName(GuiSoulCrystalFactory.SOULS, "Souls", "Ames");
		this.addName(GuiSoulCrystalFactory.CYSTAL_READY, "Soul crystal cready.", "Cristal d'ame pret.");
		this.addName(GuiSoulCrystalFactory.INSERT_CRYSTAL, "Insert an empty soul crystal.", "Inserer un crystal d'ame vide.");
		
		this.addName(InventoryMobGenerator.INV_NAME, "Mob generator", "Generateur de mobs");
		this.addName(GuiMobGenerator.INFO, "The mob generator needs a soul crystal and redstone power to function.", "Le generateur de mob a besoin d'un cristal d'ame et de courant redstone pour fonctionner.");
		
		this.addName(InventoryStuffLevelUpTable.INV_NAME, "Stuff level up table", "Table d'amelioration d'equipement");
		this.addName(GuiStuffLevelUpTable.MAGIC_POWER, "Magic power", "Puissance magique");
		this.addName(GuiStuffLevelUpTable.COST, "Cost", "Cout");
		this.addName(GuiStuffLevelUpTable.LEVELS, "Levels", "Niveaux");
		this.addName(GuiStuffLevelUpTable.ENCHANT, "Enchant", "Enchanter");
		
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_DEFAULT, "Default console", "Console par defaut");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_TELEPORTER, "Teleporter", "Teleporteur");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STARGATE_DHD, "Dhd", "Dhd");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STARGATE_SHIELD, "Stargate Shield", "Console de bouclier");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STUFF_LEVEL_UP_TABLE, "Stuff level up table", "Table d'amelioration d'equipement");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_SOUL_CRYSTAL_FACTORY, "Soul crystal factory", "Table de creation de cristaux d'ame");
		
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_DEFAULT, "This console provides you a list of valid crystal sets", "Cette console vous affiche la liste des combinaisons de cristaux valides.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_TELEPORTER, "This console allows you to teleport yourself to another teleporter. It also allows you to acces the list of registered teleporters and to add/modify/delete elements in this list.", "Cette console vous permet de vous teleporter vers un autre teleporteur. Elle vous permet egalement de consulter la liste des teleporteurs enregistres, ainsi que d'y ajouter, modifier ou supprimer des elements.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STARGATE_DHD, "This console allows you to activate/deactivate a stargate. It also allows you to acces the list of registered stargates and to add/modify/delete elements in this list.", "Cette console vous permet d'activer ou de desactiver une porte des etoiles. Elle vous permet egalement de consulter la liste des portes enregistres, ainsi que d'y ajouter, modifier ou supprimer des elements.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STARGATE_SHIELD, "This console allows you to activate/deactivate the shield of a stargate. It also allows you to enable/disable the auto shield mode and to change the code which is used to deactivate the shield from the other side.", "Cette console vous permet d'activer ou de desactiver le bouclier d'une porte des etoiles. Elle vous permet egalement d'activer ou de desactiver le mode bouclier automatique et de changer le code permettant de desactiver le bouclier depuis l'autre cote.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STUFF_LEVEL_UP_TABLE, "This console allows you to upgrade you stuff by choosing the enchantments you want to add.", "Cette console vous permet d'ameliorer votre equipement en choisissant les enchantements que vous vouler ajouter.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_SOUL_CRYSTAL_FACTORY, "This console allows you to spend the souls you have collected to create crystals which can spawn monsters when inserted in a mob generator.", "Cette console vous permet d'utiliser les ames que vous avez recolte pour creer des cristaux pouvant faire aparaitre des monstres s'ils sont inseres dans un generateur de mobs.");
		
		// Damage sources :
		this.addName("death.attack." + CustomDamageSource.KAWOOSH.getDamageType(), "%1$s tried to swim into the kawoosh.", "%1$s a essaye de nager dans le kawoosh.");
		this.addName("death.attack." + CustomDamageSource.IRIS.getDamageType(), "%1$s couldn't exit hyperspace.", "%1$s n'a pas pu sortir de l'hyper-espace.");
	}
	
}

package seigneurnecron.minecraftmods.stargate;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl.NB_CHEVRON;
import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl.NB_NAQUADAH_ALLOY_BLOCKS;
import static seigneurnecron.minecraftmods.stargate.tileentity.console.Console.CONSOLE_INFO_PREFIX;
import static seigneurnecron.minecraftmods.stargate.tileentity.console.Console.CONSOLE_NAME_PREFIX;
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
import seigneurnecron.minecraftmods.stargate.block.BlockCrystalFactory;
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
import seigneurnecron.minecraftmods.stargate.entity.damagesource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior.DispenserBehaviorFireballBasic;
import seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior.DispenserBehaviorFireballExplosive;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballBasic;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballExplosive;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNapalm;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNuke;
import seigneurnecron.minecraftmods.stargate.event.StargateEventHandler;
import seigneurnecron.minecraftmods.stargate.gui.GuiConsoleBase;
import seigneurnecron.minecraftmods.stargate.gui.GuiCrystalFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiDefaultConsole;
import seigneurnecron.minecraftmods.stargate.gui.GuiDetector;
import seigneurnecron.minecraftmods.stargate.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.gui.GuiFireballFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiMobGenerator;
import seigneurnecron.minecraftmods.stargate.gui.GuiScreenConsolePanel;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldConsole;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldRemote;
import seigneurnecron.minecraftmods.stargate.gui.GuiSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStargateControl;
import seigneurnecron.minecraftmods.stargate.gui.GuiStargateFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.gui.components.DhdPanel;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryFireballFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryMobGenerator;
import seigneurnecron.minecraftmods.stargate.inventory.InventorySoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStargateFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemShieldRemote;
import seigneurnecron.minecraftmods.stargate.item.ItemSoul;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.item.ItemStargate;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballBasic;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballBoosted;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballExplosive;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballExplosiveStable;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballNapalm;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballNuke;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaffBasic;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaffBoosted;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaffExplosive;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaffExplosiveStable;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaffNapalm;
import seigneurnecron.minecraftmods.stargate.item.staff.ItemFireStaffNuke;
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
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleDefault;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleFireballFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateShield;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleTeleporter;
import seigneurnecron.minecraftmods.stargate.tools.worlddata.StargateChunkLoader;
import seigneurnecron.minecraftmods.stargate.world.NaquadahGenerator;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

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
	public static final String VERSION = "[1.6.4] v3.2.1 [core 1.1.1]";
	
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
	public static Block block_crystalFactory;
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
	
	public static ItemFireStaffBasic item_fireStaffBasic;
	public static ItemFireStaffBasic item_fireStaffBoosted;
	public static ItemFireStaffBasic item_fireStaffExplosive;
	public static ItemFireStaffBasic item_fireStaffExplosiveStable;
	public static ItemFireStaffBasic item_fireStaffNuke;
	public static ItemFireStaffBasic item_fireStaffNapalm;
	
	public static ItemFireballBasic item_fireBallBasic;
	public static ItemFireballBasic item_fireBallBoosted;
	public static ItemFireballBasic item_fireBallExplosive;
	public static ItemFireballBasic item_fireBallExplosiveStable;
	public static ItemFireballBasic item_fireBallNuke;
	public static ItemFireballBasic item_fireBallNapalm;
	
	public static ItemCrystal item_crystal;
	public static ItemCrystal item_crystalConsole;
	public static ItemCrystal item_crystalStargate;
	public static ItemCrystal item_crystalStargateInterface;
	public static ItemCrystal item_crystalStargateBlueprint;
	public static ItemCrystal item_crystalDhd;
	public static ItemCrystal item_crystalShield;
	public static ItemCrystal item_crystalTeleporter;
	public static ItemCrystal item_crystalScanner;
	public static ItemCrystal item_crystalEnchantment;
	public static ItemCrystal item_crystalStuffData;
	public static ItemCrystal item_crystalSoulData;
	public static ItemCrystal item_crystalFireballData;
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
	public static final String blockName_crystalFactory = "block_crystalFactory";
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
	public static final String itemName_naquadahLeggings = "item_naquadahLeggings";
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
	
	public static final String itemName_fireStaffBasic = "item_fireStaffBasic";
	public static final String itemName_fireStaffBoosted = "item_fireStaffBoosted";
	public static final String itemName_fireStaffExplosive = "item_fireStaffExplosive";
	public static final String itemName_fireStaffExplosiveStable = "item_fireStaffExplosiveStable";
	public static final String itemName_fireStaffNuke = "item_fireStaffNuke";
	public static final String itemName_fireStaffNapalm = "item_fireStaffNapalm";
	
	public static final String itemName_fireBallBasic = "item_fireballBasic";
	public static final String itemName_fireBallBoosted = "item_fireballBoosted";
	public static final String itemName_fireBallExplosive = "item_fireballExplosive";
	public static final String itemName_fireBallExplosiveStable = "item_fireballExplosiveStable";
	public static final String itemName_fireBallNuke = "item_fireballNuke";
	public static final String itemName_fireBallNapalm = "item_fireballNapalm";
	
	public static final String itemName_crystal = "item_crystal";
	public static final String itemName_crystalConsole = "item_crystalConsole";
	public static final String itemName_crystalStargate = "item_crystalStargate";
	public static final String itemName_crystalStargateInterface = "item_crystalStargateInterface";
	public static final String itemName_crystalStargateBlueprint = "item_crystalStargateBlueprint";
	public static final String itemName_crystalDhd = "item_crystalDhd";
	public static final String itemName_crystalShield = "item_crystalShield";
	public static final String itemName_crystalTeleporter = "item_crystalTeleporter";
	public static final String itemName_crystalScanner = "item_crystalScanner";
	public static final String itemName_crystalEnchantment = "item_crystalEnchantment";
	public static final String itemName_crystalStuffData = "item_crystalStuffData";
	public static final String itemName_crystalSoulData = "item_crystalSoulData";
	public static final String itemName_crystalFireballData = "item_crystalFireballData";
	public static final String itemName_crystalSoulEmpty = "item_crystalSoulEmpty";
	
	public static final String itemName_crystalSoul = "item_crystalSoul";
	
	// Console names :
	
	public static final String CONSOLE_DEFAULT = "DefaultConsole";
	public static final String CONSOLE_TELEPORTER = "Teleporter";
	public static final String CONSOLE_STARGATE_FACTORY = "StargateFactory";
	public static final String CONSOLE_STARGATE_DHD = "StargateDhd";
	public static final String CONSOLE_STARGATE_SHIELD = "StargateShield";
	public static final String CONSOLE_STUFF_LEVEL_UP_TABLE = "StuffLevelUpTable";
	public static final String CONSOLE_SOUL_CRYSTAL_FACTORY = "SoulCrystalFactory";
	public static final String CONSOLE_FIREBALL_FACTORY = "FireballFactory";
	
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
		block_crystalFactory = new BlockCrystalFactory(blockName_crystalFactory);
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
		
		item_fireStaffBasic = new ItemFireStaffBasic(itemName_fireStaffBasic);
		item_fireStaffBoosted = new ItemFireStaffBoosted(itemName_fireStaffBoosted);
		item_fireStaffExplosive = new ItemFireStaffExplosive(itemName_fireStaffExplosive);
		item_fireStaffExplosiveStable = new ItemFireStaffExplosiveStable(itemName_fireStaffExplosiveStable);
		item_fireStaffNuke = new ItemFireStaffNuke(itemName_fireStaffNuke);
		item_fireStaffNapalm = new ItemFireStaffNapalm(itemName_fireStaffNapalm);
		
		item_fireBallBasic = new ItemFireballBasic(itemName_fireBallBasic);
		item_fireBallBoosted = new ItemFireballBoosted(itemName_fireBallBoosted);
		item_fireBallExplosive = new ItemFireballExplosive(itemName_fireBallExplosive);
		item_fireBallExplosiveStable = new ItemFireballExplosiveStable(itemName_fireBallExplosiveStable);
		item_fireBallNuke = new ItemFireballNuke(itemName_fireBallNuke);
		item_fireBallNapalm = new ItemFireballNapalm(itemName_fireBallNapalm);
		
		item_crystal = new ItemCrystal(itemName_crystal, "Cr");
		item_crystalConsole = new ItemCrystal(itemName_crystalConsole, "Co");
		item_crystalStargate = new ItemCrystal(itemName_crystalStargate, "SGC");
		item_crystalStargateInterface = new ItemCrystal(itemName_crystalStargateInterface, "SGI");
		item_crystalStargateBlueprint = new ItemCrystal(itemName_crystalStargateBlueprint, "SGB");
		item_crystalDhd = new ItemCrystal(itemName_crystalDhd, "DHD");
		item_crystalShield = new ItemCrystal(itemName_crystalShield, "Sh");
		item_crystalTeleporter = new ItemCrystal(itemName_crystalTeleporter, "Te");
		item_crystalScanner = new ItemCrystal(itemName_crystalScanner, "Sc");
		item_crystalEnchantment = new ItemCrystal(itemName_crystalEnchantment, "En");
		item_crystalStuffData = new ItemCrystal(itemName_crystalStuffData, "DSt");
		item_crystalSoulData = new ItemCrystal(itemName_crystalSoulData, "DSo");
		item_crystalFireballData = new ItemCrystal(itemName_crystalFireballData, "DFi");
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
		item_crystalSoulBlaze = new ItemSoulCrystalFull(EntityBlaze.class, D1, D2, 75, D4);
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
		item_crystalSoulSlime = new ItemSoulCrystalFull(EntitySlime.class, D1, D2, D3, 0.3);
		item_crystalSoulMagmaCube = new ItemSoulCrystalFull(EntityMagmaCube.class, D1, D2, 75, D4);
		item_crystalSoulBat = new ItemSoulCrystalFull(EntityBat.class, 6, 12, 100, D4);
		item_crystalSoulVillager = new ItemSoulCrystalFull(EntityVillager.class, 1, D2, 50, 1);
		item_crystalSoulHorse = new ItemSoulCrystalFull(EntityHorse.class, 2, D2, 50, 1);
	}
	
	protected void registerConsoles() {
		Multiset<ItemCrystal> crystals;
		
		crystals = HashMultiset.create();
		Console.registerConsole(crystals, ConsoleDefault.class, CONSOLE_DEFAULT);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalTeleporter);
		Console.registerConsole(crystals, ConsoleTeleporter.class, CONSOLE_TELEPORTER);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalStargateBlueprint);
		Console.registerConsole(crystals, ConsoleStargateFactory.class, CONSOLE_STARGATE_FACTORY);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalDhd);
		crystals.add(item_crystalStargateInterface);
		Console.registerConsole(crystals, ConsoleStargateDhd.class, CONSOLE_STARGATE_DHD);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalShield);
		crystals.add(item_crystalStargateInterface);
		Console.registerConsole(crystals, ConsoleStargateShield.class, CONSOLE_STARGATE_SHIELD);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalStuffData);
		crystals.add(item_crystalEnchantment);
		Console.registerConsole(crystals, ConsoleStuffLevelUpTable.class, CONSOLE_STUFF_LEVEL_UP_TABLE);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalSoulData);
		crystals.add(item_crystalEnchantment);
		Console.registerConsole(crystals, ConsoleSoulCrystalFactory.class, CONSOLE_SOUL_CRYSTAL_FACTORY);
		
		crystals = HashMultiset.create();
		crystals.add(item_crystalFireballData);
		crystals.add(item_crystalEnchantment);
		Console.registerConsole(crystals, ConsoleFireballFactory.class, CONSOLE_FIREBALL_FACTORY);
	}
	
	protected void registerCrystal(ItemCrystal crystal) {
		ItemCrystal.registerCraftableCrystal(crystal);
	}
	
	protected void registerFireball(ItemFireballBasic fireball) {
		ItemFireballBasic.registerCraftableFireball(fireball);
	}
	
	protected void registerRecipes() {
		boolean canCraftConsole = false;
		boolean canCraftCrystal = false;
		boolean canCraftEnchantmentCrystal = false;
		
		this.addSmelting(new ItemStack(item_naquadahIngot), item_naquadahOre.itemID);
		this.addRecipe(new ItemStack(block_naquadahBlock), new Object[] {"NNN", "NNN", "NNN", 'N', item_naquadahIngot});
		this.addShapelessRecipe(new ItemStack(Block.redstoneLampIdle), new Object[] {block_selfPoweredRedstoneLight});
		this.addShapelessRecipe(new ItemStack(Item.fireballCharge), new Object[] {item_fireBallExplosive});
		this.registerCrystal(item_crystal);
		
		if(this.getConfig().canCraftNaquadahAlloy) {
			this.addRecipe(new ItemStack(block_naquadahAlloy), new Object[] {"NI", "IN", 'N', item_naquadahIngot, 'I', Item.ingotIron});
			
			if(this.getConfig().canCraftStargate) {
				this.addRecipe(new ItemStack(item_chevronCompound), new Object[] {"NRN", "_N_", 'N', item_naquadahIngot, 'R', Item.redstone});
				this.addShapelessRecipe(new ItemStack(block_chevronOff), new Object[] {block_naquadahAlloy, item_chevronCompound});
				this.addShapelessRecipe(new ItemStack(block_stargateControl), new Object[] {block_naquadahAlloy, item_crystalStargate});
				this.registerCrystal(item_crystalStargate);
				this.registerCrystal(item_crystalStargateInterface);
				this.registerCrystal(item_crystalDhd);
				
				if(this.getConfig().canCraftStargateShield) {
					this.addShapelessRecipe(new ItemStack(item_shieldRemote), new Object[] {item_crystalStargateInterface, item_touchScreen, Item.ingotIron, Item.redstone});
					this.registerCrystal(item_crystalShield);
				}
				
				if(this.getConfig().canCraftFastStargate) {
					this.registerCrystal(item_crystalStargateBlueprint);
				}
				
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftTeleporter) {
				this.registerCrystal(item_crystalTeleporter);
				
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftDetector) {
				this.addShapelessRecipe(new ItemStack(block_detector), new Object[] {block_naquadahAlloy, item_crystalScanner});
				this.registerCrystal(item_crystalScanner);
				
				canCraftCrystal = true;
			}
			
			if(this.getConfig().canCraftStuffLevelUpTable) {
				this.registerCrystal(item_crystalStuffData);
				
				canCraftEnchantmentCrystal = true;
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftMobGenerator) {
				this.addShapelessRecipe(new ItemStack(block_mobGenerator), new Object[] {block_naquadahAlloy, item_crystalSoulEmpty});
				this.registerCrystal(item_crystalSoulEmpty);
				this.registerCrystal(item_crystalSoulData);
				
				canCraftEnchantmentCrystal = true;
				canCraftConsole = true;
			}
			
			if(this.getConfig().canCraftFireballBasic) {
				this.registerFireball(item_fireBallBasic);
				
				if(this.getConfig().canCraftToolsAndArmors && this.getConfig().canCraftFireStaffBasic) {
					this.addShapelessRecipe(new ItemStack(item_fireStaffBasic), new Object[] {item_fireBallBasic, item_naquadahLighter, item_crystalEnchantment, block_naquadahBlock, Block.blockDiamond, Block.blockGold, Block.blockIron});
					canCraftEnchantmentCrystal = true;
				}
			}
			
			if(this.getConfig().canCraftFireballBoosted) {
				this.registerFireball(item_fireBallBoosted);
				
				if(this.getConfig().canCraftToolsAndArmors && this.getConfig().canCraftFireStaffBoosted) {
					this.addShapelessRecipe(new ItemStack(item_fireStaffBoosted), new Object[] {item_fireBallBoosted, item_naquadahLighter, item_crystalEnchantment, block_naquadahBlock, Block.blockDiamond, Block.blockGold, Block.blockIron});
					canCraftEnchantmentCrystal = true;
				}
			}
			
			if(this.getConfig().canCraftFireballExplosive) {
				this.registerFireball(item_fireBallExplosive);
				
				if(this.getConfig().canCraftToolsAndArmors && this.getConfig().canCraftFireStaffExplosive) {
					this.addShapelessRecipe(new ItemStack(item_fireStaffExplosive), new Object[] {item_fireBallExplosive, item_naquadahLighter, item_crystalEnchantment, block_naquadahBlock, Block.blockDiamond, Block.blockGold, Block.blockIron});
					canCraftEnchantmentCrystal = true;
				}
			}
			
			if(this.getConfig().canCraftFireballExplosiveStable) {
				this.registerFireball(item_fireBallExplosiveStable);
				
				if(this.getConfig().canCraftToolsAndArmors && this.getConfig().canCraftFireStaffExplosiveStable) {
					this.addShapelessRecipe(new ItemStack(item_fireStaffExplosiveStable), new Object[] {item_fireBallExplosiveStable, item_naquadahLighter, item_crystalEnchantment, block_naquadahBlock, Block.blockDiamond, Block.blockGold, Block.blockIron});
					canCraftEnchantmentCrystal = true;
				}
			}
			
			if(this.getConfig().canCraftFireballNuke) {
				this.registerFireball(item_fireBallNuke);
				
				if(this.getConfig().canCraftToolsAndArmors && this.getConfig().canCraftFireStaffNuke) {
					this.addShapelessRecipe(new ItemStack(item_fireStaffNuke), new Object[] {item_fireBallNuke, item_naquadahLighter, item_crystalEnchantment, block_naquadahBlock, Block.blockDiamond, Block.blockGold, Block.blockIron});
					canCraftEnchantmentCrystal = true;
				}
			}
			
			if(this.getConfig().canCraftFireballNapalm) {
				this.registerFireball(item_fireBallNapalm);
				
				if(this.getConfig().canCraftToolsAndArmors && this.getConfig().canCraftFireStaffNapalm) {
					this.addShapelessRecipe(new ItemStack(item_fireStaffNapalm), new Object[] {item_fireBallNapalm, item_naquadahLighter, item_crystalEnchantment, block_naquadahBlock, Block.blockDiamond, Block.blockGold, Block.blockIron});
					canCraftEnchantmentCrystal = true;
				}
			}
			
			if(ItemFireballBasic.getCraftableFireballs().size() > 1) {
				this.registerCrystal(item_crystalFireballData);
				
				canCraftEnchantmentCrystal = true;
				canCraftConsole = true;
			}
			
			if(canCraftConsole) {
				this.addShapelessRecipe(new ItemStack(item_touchScreen), new Object[] {Block.thinGlass, Item.redstone});
				this.addShapelessRecipe(new ItemStack(block_consolePanel), new Object[] {block_naquadahAlloy, item_touchScreen});
				this.addShapelessRecipe(new ItemStack(block_consoleBase), new Object[] {block_naquadahAlloy, item_crystalConsole});
				this.registerCrystal(item_crystalConsole);
				
				canCraftCrystal = true;
			}
		}
		
		if(this.getConfig().canCraftSelfPoweredRedstoneLight) {
			this.addShapelessRecipe(new ItemStack(block_selfPoweredRedstoneLight), new Object[] {Block.redstoneLampIdle});
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
			this.addRecipe(new ItemStack(item_naquadahLighter), new Object[] {"N_", "_F", 'N', item_naquadahIngot, 'F', Item.flint});
			this.addRecipe(new ItemStack(item_naquadahShears), new Object[] {"_N", "N_", 'N', item_naquadahIngot});
			this.addRecipe(new ItemStack(item_naquadahFishingRod), new Object[] {"__N", "_NS", "N_S", 'N', item_naquadahIngot, 'S', Item.silk});
		}
		
		if(canCraftEnchantmentCrystal) {
			this.registerCrystal(item_crystalEnchantment);
			canCraftCrystal = true;
		}
		
		if(canCraftCrystal) {
			this.addShapelessRecipe(new ItemStack(item_crystal), new Object[] {Item.diamond, Item.redstone});
			this.addShapelessRecipe(new ItemStack(block_crystalFactory), new Object[] {block_naquadahAlloy, item_crystal, item_touchScreen});
		}
	}
	
	protected void registerDispenserBehaviors() {
		this.registerDispenserBehavior(item_fireBallBasic, new DispenserBehaviorFireballBasic());
		this.registerDispenserBehavior(item_fireBallExplosive, new DispenserBehaviorFireballExplosive());
	}
	
	protected void registerTileEntities() {
		this.registerTileEntity(TileEntityChevron.class, "SeigneurNecron_StargateMod_Chevron");
		this.registerTileEntity(TileEntityConsoleBase.class, "SeigneurNecron_StargateMod_ConsoleBase");
		this.registerTileEntity(TileEntityDetector.class, "SeigneurNecron_StargateMod_Detector");
		this.registerTileEntity(TileEntityMobGenerator.class, "SeigneurNecron_StargateMod_MobGenerator");
		this.registerTileEntity(TileEntityStargateControl.class, "SeigneurNecron_StargateMod_StargateControl");
		this.registerTileEntity(TileEntityCrystalFactory.class, "SeigneurNecron_StargateMod_CrystalFactory");
		this.registerTileEntity(TileEntityStargatePart.class, "SeigneurNecron_StargateMod_StargatePart");
	}
	
	protected void registerEntities() {
		this.registerEntitiy(EntityCustomFishHook.class, "customFishHook", 0, 250, 5, true);
		this.registerEntitiy(EntityFireballBasic.class, "customFireBall", 1, 250, 5, true);
		this.registerEntitiy(EntityFireballExplosive.class, "customExplosiveFireBall", 2, 250, 5, true);
		this.registerEntitiy(EntityFireballNuke.class, "nuke", 3, 250, 5, true);
		this.registerEntitiy(EntityFireballNapalm.class, "napalm", 4, 250, 5, true);
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
		this.setBlockHarvestLevel(block_crystalFactory, "pickaxe", naquadahHarvestLevel);
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
		this.addName(block_stargateControl, "Stargate control unit", "Unite de controle de porte des etoiles");
		this.addName(block_vortex, "Vortex", "Vortex");
		this.addName(block_shield, "Shield", "Bouclier");
		this.addName(block_shieldedVortex, "Shielded vortex", "Vortex avec bouclier");
		this.addName(block_kawoosh, "Kawoosh", "Kawoosh");
		this.addName(block_crystalFactory, "Crystal factory", "Usine de cristaux");
		this.addName(block_consoleBase, "Console base", "Socle de console");
		this.addName(block_consolePanel, "Control panel", "Panneau de controle");
		this.addName(block_detector, "Detector", "Detecteur");
		this.addName(block_mobGenerator, "Mob generator", "Generateur de mobs");
		this.addName(block_selfPoweredRedstoneLight, "Self powered redstone light", "Lampe a redstone auto-alimentee");
		this.addName(block_fastStargate, "Fast Stargate", "Fast Stargate");
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
		
		this.addName(item_fireStaffBasic, "Fire staff", "Baguette de feu");
		this.addName(item_fireStaffBoosted, "Boosted fire staff", "Baguette de feu boostee");
		this.addName(item_fireStaffExplosive, "Explosive fire staff", "Baguette de feu explosive");
		this.addName(item_fireStaffExplosiveStable, "Stable explosive fire staff", "Baguette de feu explosive stable");
		this.addName(item_fireStaffNuke, "Nuke staff", "Baguette nucleaire");
		this.addName(item_fireStaffNapalm, "Napalm staff", "Baguette napalm");
		
		this.addName(item_fireBallBasic, "Basic fireball", "Boule de feu basique");
		this.addName(item_fireBallBoosted, "Boosted fireball", "Boule de feu boostee");
		this.addName(item_fireBallExplosive, "Explosive fireball", "Boule de feu explosive");
		this.addName(item_fireBallExplosiveStable, "Stable explosive fireball", "Boule de feu explosive stable");
		this.addName(item_fireBallNuke, "Nuke fireball", "Boule de feu nucleaire");
		this.addName(item_fireBallNapalm, "Napalm fireball", "Boule de feu napalm");
		
		this.addName(item_crystal, "Crystal", "Cristal");
		this.addName(item_crystalConsole, "Console Crystal", "Cristal de console");
		this.addName(item_crystalStargate, "Stargate control crystal", "Cristal de controle de porte des etoiles");
		this.addName(item_crystalStargateInterface, "Stargate interface crystal", "Cristal de connexion a la porte des etoiles");
		this.addName(item_crystalStargateBlueprint, "Stargate blueprint crystal", "Cristal de construction de porte des etoiles");
		this.addName(item_crystalDhd, "DHD control crystal", "Cristal de controle de DHD");
		this.addName(item_crystalShield, "Shield crystal", "Cristal de bouclier");
		this.addName(item_crystalTeleporter, "Teleporter crystal", "Cristal de teleporteur");
		this.addName(item_crystalScanner, "Scanner crystal", "Cristal de scanner");
		this.addName(item_crystalEnchantment, "Enchantment crystal", "Cristal d'enchantement");
		this.addName(item_crystalStuffData, "Data crystal - Stuff", "Cristal de donnees - equipement");
		this.addName(item_crystalSoulData, "Data crystal - Souls", "Cristal de donnees - ames");
		this.addName(item_crystalFireballData, "Data crystal - Fireballs", "Cristal de donnees - boules de feu");
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
		
		this.addName(GuiDefaultConsole.INV_NAME, "Default console", "Console par defaut");
		this.addName(GuiDefaultConsole.ANY_INVALID_SET, "Any invalid set", "Combinaison invalide");
		
		this.addName(InventoryConsoleBase.INV_NAME, "Console crystal slots", "Emplacements de cristaux");
		this.addName(GuiConsoleBase.INFO, "Insert crystals to configure the console. If you insert no crystal or an invalid crystals combination, the console panel will display the list of valid crystals combinations. Order doesn't matter.", "Inserez des crystaux pour configurer la console. Si vous n'inserez aucun cristal ou si vous inserez une combinaison de cristaux invalide, le paneaux de la console affichera la liste des combinaisons valides. L'odre n'a pas d'importance.");
		
		this.addName(InventorySoulCrystalFactory.INV_NAME, "Soul crystal factory", "Table de creation de cristaux d'ame");
		this.addName(GuiSoulCrystalFactory.CREATE, "Create", "Creer");
		this.addName(GuiSoulCrystalFactory.SOULS, "Souls", "Ames");
		this.addName(GuiSoulCrystalFactory.INSERT_CRYSTAL, "Insert an empty soul crystal.", "Inserer un crystal d'ame vide.");
		this.addName(GuiSoulCrystalFactory.CRYSTAL_READY, "Soul crystal ready.", "Cristal d'ame pret.");
		
		this.addName(InventoryMobGenerator.INV_NAME, "Mob generator", "Generateur de mobs");
		this.addName(GuiMobGenerator.INFO, "The mob generator needs a soul crystal and redstone power to function.", "Le generateur de mob a besoin d'un cristal d'ame et de courant redstone pour fonctionner.");
		
		this.addName(InventoryStuffLevelUpTable.INV_NAME, "Stuff level up table", "Table d'amelioration d'equipement");
		this.addName(GuiStuffLevelUpTable.MAGIC_POWER, "Magic power", "Puissance magique");
		this.addName(GuiStuffLevelUpTable.COST, "Cost", "Cout");
		this.addName(GuiStuffLevelUpTable.LEVELS, "Levels", "Niveaux");
		this.addName(GuiStuffLevelUpTable.ENCHANT, "Enchant", "Enchanter");
		
		this.addName(InventoryCrystalFactory.INV_NAME, "Crystal factory", "Usine de cristaux");
		this.addName(GuiCrystalFactory.CREATE, "Create", "Creer");
		this.addName(GuiCrystalFactory.INSERT_CRYSTAL, "Insert a configurable crystal.", "Inserer un crystal configurable.");
		this.addName(GuiCrystalFactory.CRYSTAL_READY, "Crystal ready.", "Cristal pret.");
		
		this.addName(InventoryFireballFactory.INV_NAME, "Fireball factory", "Usine de boules de feu");
		this.addName(GuiFireballFactory.TRANFORM, "Transform", "Tansformer");
		this.addName(GuiFireballFactory.INSERT_FIREBALL, "Insert a fireball.", "Inserer une boule de feu.");
		this.addName(GuiFireballFactory.FIREBALL_READY, "Fireball ready.", "Boule de feu prete.");
		
		this.addName(InventoryStargateFactory.INV_NAME, "Stargate factory", "Usine de porte des etoiles");
		this.addName(GuiStargateFactory.INFO, "This console allows you to craft a \"Fast stargate\" block which can instantly build a stargate. To craft the block, you need to supply the following materials :\n- " + NB_NAQUADAH_ALLOY_BLOCKS + " naquadah alloy blocks\n- " + NB_CHEVRON + " chevrons\n- 1 stargate control unit\nShift click the materials in your inventory to automatically transfer them in the crafting slots, then click the button. The \"Fast stargate\" block will appear in the last slot.", "Cette console vous permet de creer un bloc \"Fast stargate\" qui peut construire une porte des etoiles instantanement. Pour creer le bloc, vous devez fournir les materiaux suivants :\n- " + NB_NAQUADAH_ALLOY_BLOCKS + " blocs d'alliage de naquadah\n- " + NB_CHEVRON + " chevrons\n- 1 unite de controle de porte des etoiles\nShift-cliquez les materiaux dans votre inventaire pour les envoyer automatiquement dans les bons emplacements, puis cliquez sur le bouton. Le bloc \"Fast stargate\" apparaitra dans le dernier emplacement.");
		this.addName(GuiStargateFactory.CREATE, "Create", "Creer");
		this.addName(GuiStargateFactory.MATERIALS_MISSING, "Some materials are missing.", "Il manque des materiaux.");
		this.addName(GuiStargateFactory.MATERIALS_READY, "The materials are ready.", "Les materiaux sont prets.");
		
		this.addName(item_crystal.getUnlocalizedCrystalInfo(), "This is an unconfigured crystal. It is used to craft the crystal factory.", "Ce cristal n'est pas configure. Il sert a crafter l'usine de cristaux.");
		this.addName(item_crystalConsole.getUnlocalizedCrystalInfo(), "This crystal is used to craft the console base. The console allows you to access multiple interfaces depending on the inserted crystals.", "Ce cristal est utilise pour crafter le socle de console. La console vous permet d'acceder a differentes interfaces selon les cristaux qui y sont inseres.");
		this.addName(item_crystalStargate.getUnlocalizedCrystalInfo(), "This crystal is used to craft the stargate control unit, which is the main block of a stargate.", "Ce cristal est utilise pour crafter l'unite de controle de la porte des etoile, qui est le block principal de la porte.");
		this.addName(item_crystalStargateInterface.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to connect it to a stargate (to create a dhd or a shield console). It is also used to craft the shield remote.", "Ce cristal peut etre insere dans une console pour la connecter a une porte des etoiles (pour creer un dhd ou une console de bouclier). Il est aussi utilise pour crafter la telecommande de bouclier.");
		this.addName(item_crystalStargateBlueprint.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a stargate factory.", "Ce cristal peut etre insere dans une console pour creer une usine de porte des etoiles.");
		this.addName(item_crystalDhd.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a dhd.", "Ce cristal peut etre insere dans une console pour creer un dhd.");
		this.addName(item_crystalShield.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a shield console.", "Ce cristal peut etre insere dans une console pour creer une console de bouclier.");
		this.addName(item_crystalTeleporter.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a teleporter.", "Ce cristal peut etre insere dans une console pour creer un teleporteur.");
		this.addName(item_crystalScanner.getUnlocalizedCrystalInfo(), "This crystal is used to craft a detector, which can detect nearby players.", "Ce cristal est utilise pour crafter le detecteur, qui peut detecter les joueurs a proximite.");
		this.addName(item_crystalEnchantment.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a stuff level up table or a soul crystal factory.", "Ce cristal peut etre insere dans une console pour creer une table d'amelioration d'equipement ou une table de creation de cristaux d'ame.");
		this.addName(item_crystalStuffData.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a stuff level up table.", "Ce cristal peut etre insere dans une console pour creer une table d'amelioration d'equipement.");
		this.addName(item_crystalSoulData.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a soul crystal factory.", "Ce cristal peut etre insere dans une console pour creer une usine de cristaux d'ame.");
		this.addName(item_crystalFireballData.getUnlocalizedCrystalInfo(), "This crystal can be inserted in a console to create a fireball factory.", "Ce cristal peut etre insere dans une console pour creer une usine de boules de feu.");
		this.addName(item_crystalSoulEmpty.getUnlocalizedCrystalInfo(), "This crystal can be filled with mob souls (using the soul crystal factory) and inserted in a mob generator.", "Ce cristal peut etre rempli avec des ames de mobs (en utilisant la table de creation de cristaux d'ame), puis insere dans un generateur de mobs.");
		
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_DEFAULT, "Default console", "Console par defaut");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_TELEPORTER, "Teleporter", "Teleporteur");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STARGATE_FACTORY, "Stargate factory", "Usine de porte des etoiles");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STARGATE_DHD, "Dhd", "Dhd");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STARGATE_SHIELD, "Stargate Shield", "Console de bouclier");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_STUFF_LEVEL_UP_TABLE, "Stuff level up table", "Table d'amelioration d'equipement");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_SOUL_CRYSTAL_FACTORY, "Soul crystal factory", "Usine de cristaux d'ame");
		this.addName(CONSOLE_NAME_PREFIX + CONSOLE_FIREBALL_FACTORY, "Fireball factory", "Usine de boules de feu");
		
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_DEFAULT, "This console provides you a list of valid crystal sets", "Cette console vous affiche la liste des combinaisons de cristaux valides.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_TELEPORTER, "This console allows you to teleport yourself to another teleporter. It also allows you to acces the list of registered teleporters and to add/modify/delete elements in this list.", "Cette console vous permet de vous teleporter vers un autre teleporteur. Elle vous permet egalement de consulter la liste des teleporteurs enregistres, ainsi que d'y ajouter, modifier ou supprimer des elements.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STARGATE_FACTORY, "This console allows you to craft a block which can instantly build a stargate. Once you crafted the block, put it on the ground where you want to build a stargate, then right click the block while looking in the direction you want the stargate to be oriented.", "Cette console vous permet de creer un bloc qui peut construire une porte des etoiles instantanement. Une fois le bloc cree, posez le sur le sol a l'endroit ou vous voulez contruire une porte, puis faites un click droit sur le bloc tout en regardant dans la direction vers laquelle vous voulez orienter la porte.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STARGATE_DHD, "This console allows you to activate/deactivate a stargate. It also allows you to acces the list of registered stargates and to add/modify/delete elements in this list.", "Cette console vous permet d'activer ou de desactiver une porte des etoiles. Elle vous permet egalement de consulter la liste des portes enregistres, ainsi que d'y ajouter, modifier ou supprimer des elements.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STARGATE_SHIELD, "This console allows you to activate/deactivate the shield of a stargate. It also allows you to enable/disable the auto shield mode and to change the code which is used to deactivate the shield from the other side.", "Cette console vous permet d'activer ou de desactiver le bouclier d'une porte des etoiles. Elle vous permet egalement d'activer ou de desactiver le mode bouclier automatique et de changer le code permettant de desactiver le bouclier depuis l'autre cote.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_STUFF_LEVEL_UP_TABLE, "This console allows you to upgrade you stuff by choosing the enchantments you want to add.", "Cette console vous permet d'ameliorer votre equipement en choisissant les enchantements que vous vouler ajouter.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_SOUL_CRYSTAL_FACTORY, "This console allows you to spend the souls you have collected to create crystals which can spawn monsters when inserted in a mob generator.", "Cette console vous permet d'utiliser les ames que vous avez recolte pour creer des cristaux pouvant faire aparaitre des monstres s'ils sont inseres dans un generateur de mobs.");
		this.addName(CONSOLE_INFO_PREFIX + CONSOLE_FIREBALL_FACTORY, "This console allows you to transform fireballs to give them different properties.", "Cette console vous permet de transformer les boules de feu pour leur donner differentes proprietes.");
		
		// Damage sources :
		this.addName("death.attack." + CustomDamageSource.KAWOOSH.getDamageType(), "%1$s tried to swim into the kawoosh.", "%1$s a essaye de nager dans le kawoosh.");
		this.addName("death.attack." + CustomDamageSource.IRIS.getDamageType(), "%1$s couldn't exit hyperspace.", "%1$s n'a pas pu sortir de l'hyper-espace.");
	}
	
}

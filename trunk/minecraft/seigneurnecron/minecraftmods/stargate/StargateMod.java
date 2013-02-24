package seigneurnecron.minecraftmods.stargate;

import net.minecraft.block.Block;
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
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.stargate.block.BlockChevron;
import seigneurnecron.minecraftmods.stargate.block.BlockCoordDhd;
import seigneurnecron.minecraftmods.stargate.block.BlockCoordTeleporter;
import seigneurnecron.minecraftmods.stargate.block.BlockDetector;
import seigneurnecron.minecraftmods.stargate.block.BlockMasterChevron;
import seigneurnecron.minecraftmods.stargate.block.BlockMobGenerator;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahAlloy;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahMade;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahOre;
import seigneurnecron.minecraftmods.stargate.block.BlockPanelDhd;
import seigneurnecron.minecraftmods.stargate.block.BlockPanelTeleporter;
import seigneurnecron.minecraftmods.stargate.block.BlockStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.block.BlockVortex;
import seigneurnecron.minecraftmods.stargate.client.network.StargateClientPacketHandler;
import seigneurnecron.minecraftmods.stargate.item.ItemEmptySoulCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemStargate;
import seigneurnecron.minecraftmods.stargate.item.ItemStargatePlaceable;
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
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.network.StargateServerPacketHandler;
import seigneurnecron.minecraftmods.stargate.test.BlockFastStargate;
import seigneurnecron.minecraftmods.stargate.test.EntityCustomFireBall;
import seigneurnecron.minecraftmods.stargate.test.ItemFireStaff;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMasterChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityNaquadahAlloy;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityVortex;
import seigneurnecron.minecraftmods.stargate.world.NaquadahGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLRelaunchLog;

/**
 * Classe principale du mode : SeigneurNecron Stargate Mod.
 * Une classe modifiee dans le jeu de base : EntityFishHook.
 * @author Seigneur Necron
 */
@Mod(modid = "SeigneurNecron Stargate Mod", name = "SeigneurNecron Stargate Mod", version = "[1.4.7] v2.0.0")
@NetworkMod(channels = {StargateMod.chanel}, clientSideRequired = true, serverSideRequired = true, packetHandler = StargatePacketHandler.class, clientPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.chanel}, packetHandler = StargateClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.chanel}, packetHandler = StargateServerPacketHandler.class))
public class StargateMod {
	
	// Debug mod :
	private static boolean debug = true;
	private static StringBuffer buffer = new StringBuffer();
	
	@Instance("SeigneurNecron Stargate Mod")
	public static StargateMod instance;
	
	@SidedProxy(clientSide = "seigneurnecron.minecraftmods.stargate.client.network.StargateClientProxy", serverSide = "seigneurnecron.minecraftmods.stargate.network.StargateCommonProxy")
	public static StargateCommonProxy proxy;
	
	// Stargate chanel name :
	public static final String chanel = "Necron_Stargate";
	
	// Stargate texture files :
	public static final String blockTextureFile = "/seigneurnecron/minecraftmods/stargate/image/terrain.png";
	public static final String itemTextureFile = "/seigneurnecron/minecraftmods/stargate/image/items.png";
	
	// Stargate sound files :
	public static final String soundFolder = "/seigneurnecron/minecraftmods/stargate/sound/";
	public static final String[] soundFiles = {"chevron.wav", "masterChevron.wav", "gateOpen.wav", "gateClose.wav", "gateFail.wav", "enterVortex.wav", "eventHorizon.wav"};
	
	// Quelques constantes :
	public static final float resitantBlockHardness = 10.0F;
	public static final float resitantBlockResistance = 2000.0F;
	public static final int itemSoulCrystalTextureIndex = 31;
	
	// Start IDs :
	public static final int BLOCK_START_ID = 2700; // dispo : 2621-3210
	public static final int ITEM_START_ID = 9000; // dispo : 8769-11002
	
	// Materiaux :
	public static EnumToolMaterial naquadahToolMaterial = EnumHelper.addToolMaterial("Naquadah", 3, 0, 10F, 4, 25);
	public static EnumArmorMaterial naquadahArmorMaterial = EnumHelper.addArmorMaterial("Naquadah", 0, new int[] {3, 8, 6, 3}, 25);
	
	// Stargate blocks :
	public static final Block naquadahAlloy = new BlockNaquadahAlloy(BLOCK_START_ID + 0, 32, "naquadahAlloy");
	public static final Block chevron = new BlockChevron(BLOCK_START_ID + 1, 0, "chevron");
	public static final Block masterChevron = new BlockMasterChevron(BLOCK_START_ID + 2, 7, "masterChevron");
	public static final Block vortex = new BlockVortex(BLOCK_START_ID + 3, 221, "vortex");
	public static final Block dhd = new BlockPanelDhd(BLOCK_START_ID + 4, 10, "dhd");
	public static final Block dhdCoord = new BlockCoordDhd(BLOCK_START_ID + 5, 26, "dhdCoord");
	public static final Block teleporter = new BlockPanelTeleporter(BLOCK_START_ID + 6, 12, "teleporter");
	public static final Block teleporterCoord = new BlockCoordTeleporter(BLOCK_START_ID + 7, 11, "teleporterCoord");
	public static final Block naquadahOre = new BlockNaquadahOre(BLOCK_START_ID + 8, 33, "naquadahOre");
	public static final Block naquadahBlock = new BlockNaquadahMade(BLOCK_START_ID + 9, 34, "naquadahBlock");
	public static final Block detector = new BlockDetector(BLOCK_START_ID + 10, 35, "detector");
	public static final Block mobGenerator = new BlockMobGenerator(BLOCK_START_ID + 11, 37, "mobGenerator");
	public static final Block stuffLevelUpTable = new BlockStuffLevelUpTable(BLOCK_START_ID + 12, 40, "stuffLevelUpTable");
	
	// Test blocks :
	public static final Block fastStargate = new BlockFastStargate(BLOCK_START_ID + 300, 240, "fastStargate");
	
	// Stargate items :
	public static final Item itemChevron = new ItemStargatePlaceable(ITEM_START_ID + 0, 0, "chevron", chevron);
	public static final Item itemMasterChevron = new ItemStargatePlaceable(ITEM_START_ID + 1, 1, "masterChevron", masterChevron);
	public static final Item itemNaquadahOre = new ItemStargate(ITEM_START_ID + 2, 2, "naquadahOre");
	public static final Item itemNaquadahIngot = new ItemStargate(ITEM_START_ID + 3, 3, "naquadahIngot");
	public static final Item itemChevronCompound = new ItemStargate(ITEM_START_ID + 4, 4, "chevronCompound");
	public static final Item itemCrystal = new ItemStargate(ITEM_START_ID + 5, 5, "crystal");
	public static final Item itemCrystalDhd = new ItemStargate(ITEM_START_ID + 6, 6, "crystalControl");
	public static final Item itemCrystalTeleporter = new ItemStargate(ITEM_START_ID + 7, 7, "crystalTeleporter");
	public static final Item itemCrystalScanner = new ItemStargate(ITEM_START_ID + 8, 8, "crystalScanner");
	public static final Item itemCrystalSoulEmpty = new ItemEmptySoulCrystal(ITEM_START_ID + 9, 9, "crystalSoulEmpty");
	public static final Item itemCrystalCreation = new ItemStargate(ITEM_START_ID + 10, 10, "crystalCreation");
	public static final Item itemDhdPanel = new ItemStargate(ITEM_START_ID + 14, 14, "dhdPanel");
	public static final Item itemTouchScreen = new ItemStargate(ITEM_START_ID + 15, 15, "touchScreen");
	
	// Naquada tools :
	public static final Item itemNaquadahPickaxe = new ItemNaquadahPickaxe(ITEM_START_ID + 16, 16, "naquadahPickaxe");
	public static final Item itemNaquadahShovel = new ItemNaquadahShovel(ITEM_START_ID + 17, 17, "naquadahShovel");
	public static final Item itemNaquadahAxe = new ItemNaquadahAxe(ITEM_START_ID + 18, 18, "naquadahAxe");
	public static final Item itemNaquadahHoe = new ItemNaquadahHoe(ITEM_START_ID + 19, 19, "naquadahHoe");
	public static final Item itemNaquadahSword = new ItemNaquadahSword(ITEM_START_ID + 20, 20, "naquadahSword");
	public static final Item itemNaquadahHelmet = new ItemNaquadahHelmet(ITEM_START_ID + 21, 21, "naquadahHelmet");
	public static final Item itemNaquadahPlate = new ItemNaquadahPlate(ITEM_START_ID + 22, 22, "naquadahPlate");
	public static final Item itemNaquadahLegs = new ItemNaquadahLegs(ITEM_START_ID + 23, 23, "naquadahLegs");
	public static final Item itemNaquadahBoots = new ItemNaquadahBoots(ITEM_START_ID + 24, 24, "naquadahBoots");
	public static final Item itemNaquadahBow = new ItemNaquadahBow(ITEM_START_ID + 25, 25, "naquadahBow");
	public static final Item itemNaquadahLighter = new ItemNaquadahLighter(ITEM_START_ID + 26, 26, "naquadahLighter");
	public static final Item itemNaquadahShears = new ItemNaquadahShears(ITEM_START_ID + 27, 27, "naquadahShears");
	public static final Item itemNaquadahFishingRod = new ItemNaquadahFishingRod(ITEM_START_ID + 28, 28, "naquadahFishingRod");
	
	// Mob generator :
	public static final Item itemCrystalSoulChicken = new ItemSoulCrystal(ITEM_START_ID + 256, EntityChicken.class);
	public static final Item itemCrystalSoulCow = new ItemSoulCrystal(ITEM_START_ID + 257, EntityCow.class);
	public static final Item itemCrystalSoulMooshroom = new ItemSoulCrystal(ITEM_START_ID + 258, EntityMooshroom.class);
	public static final Item itemCrystalSoulPig = new ItemSoulCrystal(ITEM_START_ID + 259, EntityPig.class);
	public static final Item itemCrystalSoulSheep = new ItemSoulCrystal(ITEM_START_ID + 260, EntitySheep.class);
	public static final Item itemCrystalSoulOcelot = new ItemSoulCrystal(ITEM_START_ID + 261, EntityOcelot.class);
	public static final Item itemCrystalSoulWolf = new ItemSoulCrystal(ITEM_START_ID + 262, EntityWolf.class);
	public static final Item itemCrystalSoulIronGolem = new ItemSoulCrystal(ITEM_START_ID + 263, EntityIronGolem.class, 2, 4);
	public static final Item itemCrystalSoulSnowman = new ItemSoulCrystal(ITEM_START_ID + 264, EntitySnowman.class, 2, 6);
	public static final Item itemCrystalSoulBlaze = new ItemSoulCrystal(ITEM_START_ID + 265, EntityBlaze.class);
	public static final Item itemCrystalSoulCreeper = new ItemSoulCrystal(ITEM_START_ID + 266, EntityCreeper.class);
	public static final Item itemCrystalSoulEnderman = new ItemSoulCrystal(ITEM_START_ID + 267, EntityEnderman.class, 2, 4);
	public static final Item itemCrystalSoulGiantZombie = new ItemSoulCrystal(ITEM_START_ID + 268, EntityGiantZombie.class, 1, 2);
	public static final Item itemCrystalSoulSilverfish = new ItemSoulCrystal(ITEM_START_ID + 269, EntitySilverfish.class);
	public static final Item itemCrystalSoulSkeleton = new ItemSoulCrystal(ITEM_START_ID + 270, EntitySkeleton.class);
	public static final Item itemCrystalSoulSpider = new ItemSoulCrystal(ITEM_START_ID + 271, EntitySpider.class);
	public static final Item itemCrystalSoulCaveSpider = new ItemSoulCrystal(ITEM_START_ID + 272, EntityCaveSpider.class);
	public static final Item itemCrystalSoulWitch = new ItemSoulCrystal(ITEM_START_ID + 273, EntityWitch.class, 1, 2);
	public static final Item itemCrystalSoulWither = new ItemSoulCrystal(ITEM_START_ID + 274, EntityWither.class, 1, 1);
	public static final Item itemCrystalSoulZombie = new ItemSoulCrystal(ITEM_START_ID + 275, EntityZombie.class);
	public static final Item itemCrystalSoulPigZombie = new ItemSoulCrystal(ITEM_START_ID + 276, EntityPigZombie.class);
	public static final Item itemCrystalSoulSquid = new ItemSoulCrystal(ITEM_START_ID + 277, EntitySquid.class);
	public static final Item itemCrystalSoulDragon = new ItemSoulCrystal(ITEM_START_ID + 278, EntityDragon.class, 1, 1);
	public static final Item itemCrystalSoulGhast = new ItemSoulCrystal(ITEM_START_ID + 279, EntityGhast.class, 1, 2);
	public static final Item itemCrystalSoulSlime = new ItemSoulCrystal(ITEM_START_ID + 280, EntitySlime.class);
	public static final Item itemCrystalSoulMagmaCube = new ItemSoulCrystal(ITEM_START_ID + 281, EntityMagmaCube.class);
	public static final Item itemCrystalSoulBat = new ItemSoulCrystal(ITEM_START_ID + 282, EntityBat.class, 6, 12);
	
	// Test items :
	public static final Item itemFireStaff = new ItemFireStaff(ITEM_START_ID + 1000, 240, "itemFireStaff");
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerTextures();
		proxy.registerSounds();
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		// Register world generator :
		GameRegistry.registerWorldGenerator(new NaquadahGenerator());
		
		// Register gui handler :
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
		// Resgister Custom entities :
		EntityRegistry.registerModEntity(EntityCustomFireBall.class, "customFireBall", 1, this, 250, 5, true);
		
		// Register Stargate blocks :
		ModLoader.registerBlock(naquadahAlloy);
		ModLoader.registerBlock(chevron);
		ModLoader.registerBlock(masterChevron);
		ModLoader.registerBlock(vortex);
		ModLoader.registerBlock(dhd);
		ModLoader.registerBlock(dhdCoord);
		ModLoader.registerBlock(teleporter);
		ModLoader.registerBlock(teleporterCoord);
		ModLoader.registerBlock(naquadahOre);
		ModLoader.registerBlock(naquadahBlock);
		ModLoader.registerBlock(detector);
		ModLoader.registerBlock(mobGenerator);
		ModLoader.registerBlock(stuffLevelUpTable);
		
		ModLoader.registerBlock(fastStargate);
		
		// Give names to block and items :
		this.giveEnNames();
		this.giveFrNames();
		
		// Register Stargate crafting recipes :
		ModLoader.addSmelting(itemNaquadahOre.itemID, new ItemStack(itemNaquadahIngot));
		ModLoader.addRecipe(new ItemStack(naquadahBlock), new Object[] {"NNN", "NNN", "NNN", 'N', itemNaquadahIngot});
		ModLoader.addRecipe(new ItemStack(naquadahAlloy), new Object[] {"NFN", "FRF", "NFN", 'N', itemNaquadahIngot, 'F', Item.ingotIron, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"D", "R", 'D', Item.diamond, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalTeleporter});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalDhd});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalScanner});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalSoulEmpty});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalCreation});
		ModLoader.addRecipe(new ItemStack(itemCrystalDhd), new Object[] {"CR_", "___", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystalTeleporter), new Object[] {"C_R", "___", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystalScanner), new Object[] {"C__", "R__", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystalSoulEmpty), new Object[] {"C__", "_R_", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystalCreation), new Object[] {"C__", "__R", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemChevronCompound), new Object[] {"NCN", "_N_", 'N', itemNaquadahIngot, 'C', itemCrystal});
		ModLoader.addRecipe(new ItemStack(itemDhdPanel), new Object[] {"GGG", "NCN", 'G', Block.glass, 'N', itemNaquadahIngot, 'C', itemCrystal});
		ModLoader.addRecipe(new ItemStack(itemTouchScreen), new Object[] {"GGG", "RCR", 'G', Block.glass, 'R', Item.redstone, 'C', itemCrystal});
		ModLoader.addRecipe(new ItemStack(itemChevron), new Object[] {"N", "C", 'N', naquadahAlloy, 'C', itemChevronCompound});
		ModLoader.addRecipe(new ItemStack(itemMasterChevron), new Object[] {"C", "N", 'N', naquadahAlloy, 'C', itemChevronCompound});
		ModLoader.addRecipe(new ItemStack(dhdCoord), new Object[] {"C", "N", 'N', naquadahAlloy, 'C', itemCrystalDhd});
		ModLoader.addRecipe(new ItemStack(teleporterCoord), new Object[] {"C", "N", 'N', naquadahAlloy, 'C', itemCrystalTeleporter});
		ModLoader.addRecipe(new ItemStack(detector), new Object[] {"C", "N", 'N', naquadahAlloy, 'C', itemCrystalScanner});
		ModLoader.addRecipe(new ItemStack(dhd), new Object[] {"P", "N", 'N', naquadahAlloy, 'P', itemDhdPanel});
		ModLoader.addRecipe(new ItemStack(teleporter), new Object[] {"S", "N", 'N', naquadahAlloy, 'S', itemTouchScreen});
		ModLoader.addRecipe(new ItemStack(mobGenerator), new Object[] {"NDN", "DCD", "NDN", 'N', itemNaquadahIngot, 'D', Item.diamond, 'C', itemCrystalTeleporter});
		ModLoader.addRecipe(new ItemStack(stuffLevelUpTable), new Object[] {"C", "N", 'N', naquadahAlloy, 'C', itemCrystalCreation});
		
		ModLoader.addRecipe(new ItemStack(itemNaquadahPickaxe), new Object[] {"NNN", "_S_", "_S_", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahShovel), new Object[] {"N", "S", "S", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahAxe), new Object[] {"NN", "SN", "S_", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahHoe), new Object[] {"NN", "S_", "S_", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahSword), new Object[] {"N", "N", "S", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahHelmet), new Object[] {"NNN", "N_N", "___", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahPlate), new Object[] {"N_N", "NNN", "NNN", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahLegs), new Object[] {"NNN", "N_N", "N_N", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahBoots), new Object[] {"___", "N_N", "N_N", 'N', itemNaquadahIngot, 'S', Item.stick});
		ModLoader.addRecipe(new ItemStack(itemNaquadahBow), new Object[] {"_NS", "N_S", "_NS", 'N', itemNaquadahIngot, 'S', Item.silk});
		ModLoader.addRecipe(new ItemStack(itemNaquadahLighter), new Object[] {"D", "N", 'N', itemNaquadahIngot, 'D', Item.diamond});
		ModLoader.addRecipe(new ItemStack(itemNaquadahShears), new Object[] {"_N", "N_", 'N', itemNaquadahIngot});
		ModLoader.addRecipe(new ItemStack(itemNaquadahFishingRod), new Object[] {"__N", "_NS", "N_S", 'N', itemNaquadahIngot, 'S', Item.silk});
		
		// Register Stargate tile entities :
		ModLoader.registerTileEntity(TileEntityCoordTeleporter.class, "Teleporter");
		ModLoader.registerTileEntity(TileEntityCoordDhd.class, "DHD");
		ModLoader.registerTileEntity(TileEntityMasterChevron.class, "MasterChevron");
		ModLoader.registerTileEntity(TileEntityChevron.class, "Chevron");
		ModLoader.registerTileEntity(TileEntityNaquadahAlloy.class, "Naquadah");
		ModLoader.registerTileEntity(TileEntityVortex.class, "Vortex");
		ModLoader.registerTileEntity(TileEntityDetector.class, "Detector");
		ModLoader.registerTileEntity(TileEntityMobGenerator.class, "MobGenerator");
		ModLoader.registerTileEntity(TileEntityStuffLevelUpTable.class, "StuffLevelUpTable");
		
		// Change Stargate blocks properties :
		MinecraftForge.setBlockHarvestLevel(naquadahAlloy, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(chevron, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(masterChevron, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(dhd, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(dhdCoord, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(teleporter, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(teleporterCoord, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(naquadahOre, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(naquadahBlock, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(detector, "pickaxe", 3);
	}
	
	private void giveEnNames() {
		// Blocks :
		ModLoader.addName(naquadahAlloy, "Naquadah alloy");
		ModLoader.addName(chevron, "Chevron");
		ModLoader.addName(masterChevron, "Master chevron");
		ModLoader.addName(vortex, "Vortex");
		ModLoader.addName(dhd, "DHD panel");
		ModLoader.addName(dhdCoord, "DHD base");
		ModLoader.addName(teleporter, "Teleporter panel");
		ModLoader.addName(teleporterCoord, "Teleporter base");
		ModLoader.addName(naquadahOre, "Naquadah ore");
		ModLoader.addName(naquadahBlock, "Naquadah block");
		ModLoader.addName(detector, "Detector");
		ModLoader.addName(mobGenerator, "Mob generator");
		ModLoader.addName(stuffLevelUpTable, "Stuff levelup table");
		
		ModLoader.addName(fastStargate, "Fast Stargate");
		
		// Items :
		ModLoader.addName(itemChevron, "Chevron");
		ModLoader.addName(itemMasterChevron, "Master chevron");
		ModLoader.addName(itemNaquadahOre, "Naquadah ore");
		ModLoader.addName(itemNaquadahIngot, "Naquadah ingot");
		ModLoader.addName(itemChevronCompound, "Chevron compound");
		ModLoader.addName(itemCrystal, "Crystal");
		ModLoader.addName(itemCrystalDhd, "DHD control crystal");
		ModLoader.addName(itemCrystalTeleporter, "Teleporter crystal");
		ModLoader.addName(itemCrystalScanner, "Scanner crystal");
		ModLoader.addName(itemCrystalSoulEmpty, "Empty soul crystal");
		ModLoader.addName(itemCrystalCreation, "Materialization crystal");
		ModLoader.addName(itemDhdPanel, "Control panel");
		ModLoader.addName(itemTouchScreen, "Touch screen");
		
		ModLoader.addName(itemNaquadahPickaxe, "Naquadah pickaxe");
		ModLoader.addName(itemNaquadahShovel, "Naquadah shovel");
		ModLoader.addName(itemNaquadahAxe, "Naquadah axe");
		ModLoader.addName(itemNaquadahHoe, "Naquadah hoe");
		ModLoader.addName(itemNaquadahSword, "Naquadah sword");
		ModLoader.addName(itemNaquadahHelmet, "Naquadah helmet");
		ModLoader.addName(itemNaquadahPlate, "Naquadah plate");
		ModLoader.addName(itemNaquadahLegs, "Naquadah legs");
		ModLoader.addName(itemNaquadahBoots, "Naquadah boots");
		ModLoader.addName(itemNaquadahBow, "Naquadah bow");
		ModLoader.addName(itemNaquadahLighter, "Naquadah ligher");
		ModLoader.addName(itemNaquadahShears, "Naquadah shears");
		ModLoader.addName(itemNaquadahFishingRod, "Naquadah fishing rod");
		
		ModLoader.addName(itemCrystalSoulChicken, "Chicken soul crystal");
		ModLoader.addName(itemCrystalSoulCow, "Cow soul crystal");
		ModLoader.addName(itemCrystalSoulMooshroom, "Mooshroom soul crystal");
		ModLoader.addName(itemCrystalSoulPig, "Pig soul crystal");
		ModLoader.addName(itemCrystalSoulSheep, "Sheep soul crystal");
		ModLoader.addName(itemCrystalSoulOcelot, "Ocelot soul crystal");
		ModLoader.addName(itemCrystalSoulWolf, "Wolf soul crystal");
		ModLoader.addName(itemCrystalSoulIronGolem, "Iron golem soul crystal");
		ModLoader.addName(itemCrystalSoulSnowman, "Snowman soul crystal");
		ModLoader.addName(itemCrystalSoulBlaze, "Blaze soul crystal");
		ModLoader.addName(itemCrystalSoulCreeper, "Creeper soul crystal");
		ModLoader.addName(itemCrystalSoulEnderman, "Enderman soul crystal");
		ModLoader.addName(itemCrystalSoulGiantZombie, "Giant zombie soul crystal");
		ModLoader.addName(itemCrystalSoulSilverfish, "Silver fish soul crystal");
		ModLoader.addName(itemCrystalSoulSkeleton, "Skeleton soul crystal");
		ModLoader.addName(itemCrystalSoulSpider, "Spider soul crystal");
		ModLoader.addName(itemCrystalSoulCaveSpider, "Cave spider soul crystal");
		ModLoader.addName(itemCrystalSoulWitch, "Witch soul crystal");
		ModLoader.addName(itemCrystalSoulWither, "Wither soul crystal");
		ModLoader.addName(itemCrystalSoulZombie, "Zombie soul crystal");
		ModLoader.addName(itemCrystalSoulPigZombie, "Pig-zombie soul crystal");
		ModLoader.addName(itemCrystalSoulSquid, "Squid soul crystal");
		ModLoader.addName(itemCrystalSoulDragon, "Dragon soul crystal");
		ModLoader.addName(itemCrystalSoulGhast, "Ghast soul crystal");
		ModLoader.addName(itemCrystalSoulSlime, "Slime soul crystal");
		ModLoader.addName(itemCrystalSoulMagmaCube, "Magma cube soul crystal");
		ModLoader.addName(itemCrystalSoulBat, "Bat soul crystal");
		
		ModLoader.addName(itemFireStaff, "Fire staff");
	}
	
	private void giveFrNames() {
		// Blocks :
		ModLoader.addName(naquadahAlloy, "fr_FR", "Alliage de naquadah");
		ModLoader.addName(chevron, "fr_FR", "Chevron");
		ModLoader.addName(masterChevron, "fr_FR", "Chevron maitre");
		ModLoader.addName(vortex, "fr_FR", "Vortex");
		ModLoader.addName(dhd, "fr_FR", "DHD (panneau)");
		ModLoader.addName(dhdCoord, "fr_FR", "DHD (socle)");
		ModLoader.addName(teleporter, "fr_FR", "Teleporteur (panneau)");
		ModLoader.addName(teleporterCoord, "fr_FR", "Teleporteur (socle)");
		ModLoader.addName(naquadahOre, "fr_FR", "Minerai de naquadah");
		ModLoader.addName(naquadahBlock, "fr_FR", "Block de naquadah");
		ModLoader.addName(detector, "fr_FR", "Detecteur");
		ModLoader.addName(mobGenerator, "fr_FR", "Generateur de mobs");
		ModLoader.addName(stuffLevelUpTable, "fr_FR", "Table d'amelioration d'equipment");
		
		ModLoader.addName(fastStargate, "fr_FR", "Fast Stargate");
		
		// Items :
		ModLoader.addName(itemChevron, "fr_FR", "Chevron");
		ModLoader.addName(itemMasterChevron, "fr_FR", "Chevron maitre");
		ModLoader.addName(itemNaquadahOre, "fr_FR", "Minerai de naquadah");
		ModLoader.addName(itemNaquadahIngot, "fr_FR", "Lingot de naquadah");
		ModLoader.addName(itemChevronCompound, "fr_FR", "Composant de chevron");
		ModLoader.addName(itemCrystal, "fr_FR", "Cristal");
		ModLoader.addName(itemCrystalDhd, "fr_FR", "Cristal de controle de DHD");
		ModLoader.addName(itemCrystalTeleporter, "fr_FR", "Cristal de teleporteur");
		ModLoader.addName(itemCrystalScanner, "fr_FR", "Cristal de scanner");
		ModLoader.addName(itemCrystalSoulEmpty, "fr_FR", "Cristal d'ame vide");
		ModLoader.addName(itemCrystalCreation, "fr_FR", "Cristal de materialisation");
		ModLoader.addName(itemDhdPanel, "fr_FR", "Panneau de controle");
		ModLoader.addName(itemTouchScreen, "fr_FR", "Ecran tactile");
		
		ModLoader.addName(itemNaquadahPickaxe, "fr_FR", "Pioche de naquadah");
		ModLoader.addName(itemNaquadahShovel, "fr_FR", "Pelle de naquadah");
		ModLoader.addName(itemNaquadahAxe, "fr_FR", "Hache de naquadah");
		ModLoader.addName(itemNaquadahHoe, "fr_FR", "Houe de naquadah");
		ModLoader.addName(itemNaquadahSword, "fr_FR", "Epee de naquadah");
		ModLoader.addName(itemNaquadahHelmet, "fr_FR", "Haume de naquadah");
		ModLoader.addName(itemNaquadahPlate, "fr_FR", "Armure de naquadah");
		ModLoader.addName(itemNaquadahLegs, "fr_FR", "Jambieres de naquadah");
		ModLoader.addName(itemNaquadahBoots, "fr_FR", "Bottes de naquadah");
		ModLoader.addName(itemNaquadahBow, "fr_FR", "Arc de naquadah");
		ModLoader.addName(itemNaquadahLighter, "fr_FR", "Briquet de naquadah");
		ModLoader.addName(itemNaquadahShears, "fr_FR", "Cisailles de naquadah");
		ModLoader.addName(itemNaquadahFishingRod, "fr_FR", "Canne a peche de naquadah");
		
		ModLoader.addName(itemCrystalSoulChicken, "fr_FR", "Cristal d'ame de poulet");
		ModLoader.addName(itemCrystalSoulCow, "fr_FR", "Cristal d'ame de vache");
		ModLoader.addName(itemCrystalSoulMooshroom, "fr_FR", "Cristal d'ame de champimheu");
		ModLoader.addName(itemCrystalSoulPig, "fr_FR", "Cristal d'ame de cochon");
		ModLoader.addName(itemCrystalSoulSheep, "fr_FR", "Cristal d'ame de mouton");
		ModLoader.addName(itemCrystalSoulOcelot, "fr_FR", "Cristal d'ame de chat sauvage");
		ModLoader.addName(itemCrystalSoulWolf, "fr_FR", "Cristal d'ame de loup");
		ModLoader.addName(itemCrystalSoulIronGolem, "fr_FR", "Cristal d'ame de golem de fer");
		ModLoader.addName(itemCrystalSoulSnowman, "fr_FR", "Cristal d'ame de bonhomme de neige");
		ModLoader.addName(itemCrystalSoulBlaze, "fr_FR", "Cristal d'ame de elementaire de feu");
		ModLoader.addName(itemCrystalSoulCreeper, "fr_FR", "Cristal d'ame de creeper");
		ModLoader.addName(itemCrystalSoulEnderman, "fr_FR", "Cristal d'ame d'enderman");
		ModLoader.addName(itemCrystalSoulGiantZombie, "fr_FR", "Cristal d'ame de gean zombie");
		ModLoader.addName(itemCrystalSoulSilverfish, "fr_FR", "Cristal d'ame de poisson d'argent");
		ModLoader.addName(itemCrystalSoulSkeleton, "fr_FR", "Cristal d'ame de skelette");
		ModLoader.addName(itemCrystalSoulSpider, "fr_FR", "Cristal d'ame d'areignee");
		ModLoader.addName(itemCrystalSoulCaveSpider, "fr_FR", "Cristal d'ame d'areignee venimeuse");
		ModLoader.addName(itemCrystalSoulWitch, "fr_FR", "Cristal d'ame de sorciere");
		ModLoader.addName(itemCrystalSoulWither, "fr_FR", "Cristal d'ame de Wither");
		ModLoader.addName(itemCrystalSoulZombie, "fr_FR", "Cristal d'ame de zombie");
		ModLoader.addName(itemCrystalSoulPigZombie, "fr_FR", "Cristal d'ame de cochon-zombie");
		ModLoader.addName(itemCrystalSoulSquid, "fr_FR", "Cristal d'ame de poulpe");
		ModLoader.addName(itemCrystalSoulDragon, "fr_FR", "Cristal d'ame de EnderDragon");
		ModLoader.addName(itemCrystalSoulGhast, "fr_FR", "Cristal d'ame de ghast");
		ModLoader.addName(itemCrystalSoulSlime, "fr_FR", "Cristal d'ame de blob");
		ModLoader.addName(itemCrystalSoulMagmaCube, "fr_FR", "Cristal d'ame de blob de lave");
		ModLoader.addName(itemCrystalSoulBat, "fr_FR", "Cristal d'ame de chauve-souris");
		
		ModLoader.addName(itemFireStaff, "fr_FR", "Baguette de feu");
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt) {
		//Post-Initialization code such as mod hooks
	}
	
	/**
	 * Envoie un packet du client au server.
	 * @param packet - le packet a envoyer.
	 */
	public static void sendPacketToServer(Packet packet) {
		ModLoader.sendPacket(packet);
	}
	
	/**
	 * Envoie un packet du server a tout les joueurs.
	 * @param packet - le packet a envoyer.
	 */
	public static void sendPacketToAllPlayers(Packet packet) {
		ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
	}
	
	/**
	 * Envoie un packet du server a tout les joueurs situes dans la dimension specifiee.
	 * @param packet - le packet a envoyer.
	 * @param dimension - la dimension dans laquelle les joueurs doivent etre situes.
	 */
	public static void sendPacketToAllPlayersInDimension(Packet packet, int dimension) {
		ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayersInDimension(packet, dimension);
	}
	
	/**
	 * Log une chaine de caracteres, seulement si le mode debug est active.
	 * @param text - la chaine de caracteres a ecrire.
	 * @param logNow - indique si la chaine doit etre loguee tout de suite, ou stockee en attendant le reste de la ligne.
	 */
	public static void debug(String text, boolean logNow) {
		if(debug) {
			buffer.append(text);
			if(logNow) {
				FMLRelaunchLog.info(buffer.toString());
				buffer.delete(0, buffer.length());
			}
		}
	}
	
	/**
	 * Log n fois une chaine de caracteres, seulement si le mode debug est active.
	 * @param text - la chaine de caracteres a ecrire.
	 * @param logNow - indique si la chaine doit etre loguee tout de suite, ou stockee en attendant le reste de la ligne.
	 * @param nb - le nombre de fois qu'il faut ecrire la chaine de caractere.
	 */
	public static void debug(String text, boolean logNow, int n) {
		if(debug && n > 0) {
			for(int i = 0; i < n; ++i) {
				buffer.append(text);
			}
			if(logNow) {
				FMLRelaunchLog.info(buffer.toString());
				buffer.delete(0, buffer.length());
			}
		}
	}
	
}

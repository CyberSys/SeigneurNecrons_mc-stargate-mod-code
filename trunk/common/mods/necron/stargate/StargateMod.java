package mods.necron.stargate;

/* ########## 0_Stargate : OK ########## */
/* ########## 1_Outils_Infini : OK ########## */
/* ########## 2_Durabilite_Augmente : EnumToolMaterial, EnumArmorMaterial ########## */
/* ########## 3_Creeper_Moins_Chiant : EntityCreeper, Explosion, World ########## */
/* ########## 4_Enderman_Moins_Chiant : EntityEnderman ########## */
/* ########## 5_Keep_Inventory_On_Death : EntityPlayer, EntityPlayerMP ########## */
/* ########## 6_Le_verre_drop : BlockGlass, BlockPane ########## */
/* ########## 7_Bedrock_Plate : ChunkProviderGenerate ########## */
/* ########## 8_Liquides_dupilation : BlockFlowing ########## */
/* ########## 9_XP_Gratos : EntityLiving ########## */
/* ########## 10_MobSpawner : BlockMobSpawner ########## */
/* ########## 11_Obsidian_Moins_Resistant : OK ########## */
/* ########## 12_Caution_this_is_Sparta : TileEntitySignRenderer ########## */

import mods.necron.stargate.StargateClientPacketHandler;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLRelaunchLog;

@Mod(modid = "SgnrNecron_StargateMod", name = "Stargate Mod", version = "[1.3.2] v1.0.0")
@NetworkMod(channels = {StargateMod.chanel}, clientSideRequired = true, serverSideRequired = true, packetHandler = StargatePacketHandler.class, clientPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.chanel}, packetHandler = StargateClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.chanel}, packetHandler = StargateServerPacketHandler.class))
public class StargateMod {
	
	// Debug mod :
	private static boolean debug = true;
	private static StringBuffer buffer = new StringBuffer();
	
	@Instance
	public static StargateMod instance;
	
	@SidedProxy(clientSide = "mods.stargate.StargateClientProxy", serverSide = "mods.stargate.StargateCommonProxy")
	public static StargateCommonProxy proxy;
	
	// Stargate chanel name :
	public static final String chanel = "Stargate";
	
	// Stargate texture files :
	public static final String blockTextureFile = "/mods/stargate/terrain.png";
	public static final String itemTextureFile = "/mods/stargate/items.png";
	
	// Queques constantes :
	public static final float resitantBlockHardness = 10.0F;
	public static final float resitantBlockResistance = 2000.0F;
	public static final int itemMobSpawnerTextureIndex = 16;
	public static final int indestructibleItemsMaxDamage = 0;
	
	// Stargate blocks :
	public static final Block naquadaAlliage = new BlockNaquadaAlliage(255, 32, "naquadaAlliage");
	public static final Block chevron = new BlockChevron(254, 0, "chevron");
	public static final Block masterChevron = new BlockMasterChevron(253, 7, "masterChevron");
	public static final Block vortex = new BlockVortex(252, 221, "vortex");
	public static final Block dhd = new BlockPanelDhd(251, 10, "dhd");
	public static final Block dhdCoord = new BlockCoordDhd(250, 26, "dhdCoord");
	public static final Block teleporter = new BlockPanelTeleporter(249, 12, "teleporter");
	public static final Block teleporterCoord = new BlockCoordTeleporter(248, 11, "teleporterCoord");
	public static final Block naquadaOre = new BlockNaquadaOre(247, 33, "naquadaOre");
	public static final Block naquadaBlock = new BlockNaquadaMade(246, 34, "naquadaBlock");
	public static final Block detector = new BlockDetector(245, 35, "detector");
	
	// Stargate items :
	public static final Item itemChevron = new ItemStargatePlaceable(6000, 0, "chevron", chevron);
	public static final Item itemMasterChevron = new ItemStargatePlaceable(6001, 1, "masterChevron", masterChevron);
	public static final Item itemNaquadaOre = new ItemStargate(6002, 2, "naquadaOre");
	public static final Item itemNaquadaIngot = new ItemStargate(6003, 3, "naquadaIngot");
	public static final Item itemChevronCompound = new ItemStargate(6004, 4, "chevronCompound");
	public static final Item itemCrystal = new ItemStargate(6005, 5, "crystal");
	public static final Item itemCrystalDhd = new ItemStargate(6006, 6, "crystalControl");
	public static final Item itemCrystalTeleporter = new ItemStargate(6007, 7, "crystalTeleporter");
	public static final Item itemCrystalScanner = new ItemStargate(6008, 8, "crystalScanner");
	public static final Item itemDhdPanel = new ItemStargate(6014, 14, "dhdPanel");
	public static final Item itemTouchScreen = new ItemStargate(6015, 15, "touchScreen");
	
	// MobSpawner items :
	public static final Item itemSpawnerCreeper = new ItemMobSpawner(5002, "Creeper");
	public static final Item itemSpawnerSkeleton = new ItemMobSpawner(5003, "Skeleton");
	public static final Item itemSpawnerSpider = new ItemMobSpawner(5004, "Spider");
	public static final Item itemSpawnerGiant = new ItemMobSpawner(5005, "Giant");
	public static final Item itemSpawnerZombie = new ItemMobSpawner(5006, "Zombie");
	public static final Item itemSpawnerSlime = new ItemMobSpawner(5007, "Slime");
	public static final Item itemSpawnerGhast = new ItemMobSpawner(5008, "Ghast");
	public static final Item itemSpawnerPigZombie = new ItemMobSpawner(5009, "PigZombie");
	public static final Item itemSpawnerEnderman = new ItemMobSpawner(5010, "Enderman");
	public static final Item itemSpawnerCaveSpider = new ItemMobSpawner(5011, "CaveSpider");
	public static final Item itemSpawnerSilverfish = new ItemMobSpawner(5012, "Silverfish");
	public static final Item itemSpawnerBlaze = new ItemMobSpawner(5013, "Blaze");
	public static final Item itemSpawnerLavaSlime = new ItemMobSpawner(5014, "LavaSlime");
	public static final Item itemSpawnerEnderDragon = new ItemMobSpawner(5015, "EnderDragon");
	public static final Item itemSpawnerPig = new ItemMobSpawner(5016, "Pig");
	public static final Item itemSpawnerSheep = new ItemMobSpawner(5017, "Sheep");
	public static final Item itemSpawnerCow = new ItemMobSpawner(5018, "Cow");
	public static final Item itemSpawnerChicken = new ItemMobSpawner(5019, "Chicken");
	public static final Item itemSpawnerSquid = new ItemMobSpawner(5020, "Squid");
	public static final Item itemSpawnerWolf = new ItemMobSpawner(5021, "Wolf");
	public static final Item itemSpawnerMushroomCow = new ItemMobSpawner(5022, "MushroomCow");
	public static final Item itemSpawnerSnowMan = new ItemMobSpawner(5023, "SnowMan");
	public static final Item itemSpawnerOzelot = new ItemMobSpawner(5024, "Ozelot");
	public static final Item itemSpawnerVillagerGolem = new ItemMobSpawner(5025, "VillagerGolem");
	public static final Item itemSpawnerVillager = new ItemMobSpawner(5026, "Villager");
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Load texture files :
		proxy.registerRenderInformation();
		proxy.registerSounds();
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		// Register world generator :
		GameRegistry.registerWorldGenerator(new NaquadaGenerator());
		
		// Register gui handler :
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
		// Register Stargate blocks :
		ModLoader.registerBlock(naquadaAlliage);
		ModLoader.registerBlock(chevron);
		ModLoader.registerBlock(masterChevron);
		ModLoader.registerBlock(vortex);
		ModLoader.registerBlock(dhd);
		ModLoader.registerBlock(dhdCoord);
		ModLoader.registerBlock(teleporter);
		ModLoader.registerBlock(teleporterCoord);
		ModLoader.registerBlock(naquadaOre);
		ModLoader.registerBlock(naquadaBlock);
		ModLoader.registerBlock(detector);
		
		// Give fr names to Stargate blocks :
		ModLoader.addName(naquadaAlliage, "fr_FR", "Alliage de naquada");
		ModLoader.addName(chevron, "fr_FR", "Chevron");
		ModLoader.addName(masterChevron, "fr_FR", "Chevron maitre");
		ModLoader.addName(vortex, "fr_FR", "Vortex");
		ModLoader.addName(dhd, "fr_FR", "DHD (panneau)");
		ModLoader.addName(dhdCoord, "fr_FR", "DHD (socle)");
		ModLoader.addName(teleporter, "fr_FR", "Teleporteur (panneau)");
		ModLoader.addName(teleporterCoord, "fr_FR", "Teleporteur (socle)");
		ModLoader.addName(naquadaOre, "fr_FR", "Minerai de naquada");
		ModLoader.addName(naquadaBlock, "fr_FR", "Block de naquada");
		ModLoader.addName(detector, "fr_FR", "Detecteur");
		
		// Give fr names to Stargate items :
		ModLoader.addName(itemChevron, "fr_FR", "Chevron");
		ModLoader.addName(itemMasterChevron, "fr_FR", "Chevron maitre");
		ModLoader.addName(itemNaquadaOre, "fr_FR", "Minerai de naquada");
		ModLoader.addName(itemNaquadaIngot, "fr_FR", "Lingot de naquada");
		ModLoader.addName(itemChevronCompound, "fr_FR", "Composant de chevron");
		ModLoader.addName(itemCrystal, "fr_FR", "Cristal");
		ModLoader.addName(itemCrystalDhd, "fr_FR", "Cristal de contrôle de DHD");
		ModLoader.addName(itemCrystalTeleporter, "fr_FR", "Cristal de téléporteur");
		ModLoader.addName(itemCrystalScanner, "fr_FR", "Cristal de scanner");
		ModLoader.addName(itemDhdPanel, "fr_FR", "Panneau de contrôle");
		ModLoader.addName(itemTouchScreen, "fr_FR", "Ecran tactile");
		
		// Register Stargate crafting recipes :
		ModLoader.addSmelting(itemNaquadaOre.shiftedIndex, new ItemStack(itemNaquadaIngot));
		ModLoader.addRecipe(new ItemStack(naquadaBlock), new Object[] {"NNN", "NNN", "NNN", 'N', itemNaquadaIngot});
		ModLoader.addRecipe(new ItemStack(naquadaAlliage), new Object[] {"NFN", "FRF", "NFN", 'N', itemNaquadaIngot, 'F', Item.ingotIron, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"D", "R", 'D', Item.diamond, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalTeleporter});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalDhd});
		ModLoader.addRecipe(new ItemStack(itemCrystal), new Object[] {"C", 'C', itemCrystalScanner});
		ModLoader.addRecipe(new ItemStack(itemCrystalDhd), new Object[] {"CR_", "___", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystalTeleporter), new Object[] {"C_R", "___", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemCrystalScanner), new Object[] {"C__", "R__", "___", 'C', itemCrystal, 'R', Item.redstone});
		ModLoader.addRecipe(new ItemStack(itemChevronCompound), new Object[] {"NCN", "_N_", 'N', itemNaquadaIngot, 'C', itemCrystal});
		ModLoader.addRecipe(new ItemStack(itemDhdPanel), new Object[] {"GGG", "NCN", 'G', Block.glass, 'N', itemNaquadaIngot, 'C', itemCrystal});
		ModLoader.addRecipe(new ItemStack(itemTouchScreen), new Object[] {"GGG", "RCR", 'G', Block.glass, 'R', Item.redstone, 'C', itemCrystal});
		ModLoader.addRecipe(new ItemStack(itemChevron), new Object[] {"N", "C", 'N', naquadaAlliage, 'C', itemChevronCompound});
		ModLoader.addRecipe(new ItemStack(itemMasterChevron), new Object[] {"C", "N", 'N', naquadaAlliage, 'C', itemChevronCompound});
		ModLoader.addRecipe(new ItemStack(dhdCoord), new Object[] {"C", "N", 'N', naquadaAlliage, 'C', itemCrystalDhd});
		ModLoader.addRecipe(new ItemStack(teleporterCoord), new Object[] {"C", "N", 'N', naquadaAlliage, 'C', itemCrystalTeleporter});
		ModLoader.addRecipe(new ItemStack(dhd), new Object[] {"P", "N", 'N', naquadaAlliage, 'P', itemDhdPanel});
		ModLoader.addRecipe(new ItemStack(teleporter), new Object[] {"S", "N", 'N', naquadaAlliage, 'S', itemTouchScreen});
		
		// Register Stargate tile entities :
		ModLoader.registerTileEntity(TileEntityCoordTeleporter.class, "Teleporter");
		ModLoader.registerTileEntity(TileEntityCoordDhd.class, "DHD");
		ModLoader.registerTileEntity(TileEntityMasterChevron.class, "MasterChevron");
		ModLoader.registerTileEntity(TileEntityChevron.class, "Chevron");
		ModLoader.registerTileEntity(TileEntityNaquadaAlliage.class, "Naquada");
		ModLoader.registerTileEntity(TileEntityVortex.class, "Vortex");
		ModLoader.registerTileEntity(TileEntityDetector.class, "Detector");
		
		// Change Stargate blocks properties :
		MinecraftForge.setBlockHarvestLevel(naquadaAlliage, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(chevron, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(masterChevron, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(dhd, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(dhdCoord, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(teleporter, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(teleporterCoord, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(naquadaOre, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(naquadaBlock, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(detector, "pickaxe", 3);
		
		// Call IndestructibleTools initialisation :
		this.initIndestructibleTools();
		
		// Call ObsidianResistance initialisation :
		this.initObsidianResistance();
		
		// Call MobSpawner initialisation :
		this.initMobSpawner();
	}
	
	private void initIndestructibleTools() {
		// Cheat some tools :
		ModLoader.setPrivateValue(Item.class, Item.pickaxeDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.shovelDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.axeDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.hoeDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.swordDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.helmetDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.plateDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.legsDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.bootsDiamond, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.bow, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.flintAndSteel, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.fishingRod, "maxDamage", indestructibleItemsMaxDamage);
		ModLoader.setPrivateValue(Item.class, Item.shears, "maxDamage", indestructibleItemsMaxDamage);
	}
	
	private void initObsidianResistance() {
		// Lower obsidian block hardness :
		Block.obsidian.setHardness(resitantBlockHardness);
	}
	
	private void initMobSpawner() {
		// Give fr names to MobSpawner items :
		ModLoader.addName(itemSpawnerCreeper, "fr_FR", "Générateur de creepers");
		ModLoader.addName(itemSpawnerSkeleton, "fr_FR", "Générateur de squelettes");
		ModLoader.addName(itemSpawnerSpider, "fr_FR", "Générateur d'araignées");
		ModLoader.addName(itemSpawnerGiant, "fr_FR", "Générateur de géants");
		ModLoader.addName(itemSpawnerZombie, "fr_FR", "Générateur de zombies");
		ModLoader.addName(itemSpawnerSlime, "fr_FR", "Générateur de blobs");
		ModLoader.addName(itemSpawnerGhast, "fr_FR", "Générateur de poulpes-chat-volants");
		ModLoader.addName(itemSpawnerPigZombie, "fr_FR", "Générateur de cochons-zombies");
		ModLoader.addName(itemSpawnerEnderman, "fr_FR", "Générateur d'endermen");
		ModLoader.addName(itemSpawnerCaveSpider, "fr_FR", "Générateur d'araignées-venimeuses");
		ModLoader.addName(itemSpawnerSilverfish, "fr_FR", "Générateur de poissons d'argent");
		ModLoader.addName(itemSpawnerBlaze, "fr_FR", "Générateur de piromans");
		ModLoader.addName(itemSpawnerLavaSlime, "fr_FR", "Générateur de blobs de lave");
		ModLoader.addName(itemSpawnerEnderDragon, "fr_FR", "Générateur d'EnderDragon");
		ModLoader.addName(itemSpawnerPig, "fr_FR", "Générateur de cochons");
		ModLoader.addName(itemSpawnerSheep, "fr_FR", "Générateur de moutons");
		ModLoader.addName(itemSpawnerCow, "fr_FR", "Générateur de vaches-ninja");
		ModLoader.addName(itemSpawnerChicken, "fr_FR", "Générateur de poulets");
		ModLoader.addName(itemSpawnerSquid, "fr_FR", "Générateur de poulpes");
		ModLoader.addName(itemSpawnerWolf, "fr_FR", "Générateur de loups");
		ModLoader.addName(itemSpawnerMushroomCow, "fr_FR", "Générateur de champimeuh");
		ModLoader.addName(itemSpawnerSnowMan, "fr_FR", "Générateur de bonhommes de neige");
		ModLoader.addName(itemSpawnerOzelot, "fr_FR", "Générateur de nyaaa");
		ModLoader.addName(itemSpawnerVillagerGolem, "fr_FR", "Générateur de golems");
		ModLoader.addName(itemSpawnerVillager, "fr_FR", "Générateur de péons");
		
		// Register MobSpawner crafting recipes :
		ModLoader.addRecipe(new ItemStack(itemSpawnerCreeper, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.gunpowder});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSkeleton, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.bone});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSpider, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.silk});
		ModLoader.addRecipe(new ItemStack(itemSpawnerGiant, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Block.netherStalk});
		ModLoader.addRecipe(new ItemStack(itemSpawnerZombie, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.rottenFlesh});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSlime, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.slimeBall});
		ModLoader.addRecipe(new ItemStack(itemSpawnerGhast, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.ghastTear});
		ModLoader.addRecipe(new ItemStack(itemSpawnerPigZombie, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.goldNugget});
		ModLoader.addRecipe(new ItemStack(itemSpawnerEnderman, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.enderPearl});
		ModLoader.addRecipe(new ItemStack(itemSpawnerCaveSpider, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.spiderEye});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSilverfish, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Block.sand});
		ModLoader.addRecipe(new ItemStack(itemSpawnerBlaze, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.blazeRod});
		ModLoader.addRecipe(new ItemStack(itemSpawnerLavaSlime, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.magmaCream});
		ModLoader.addRecipe(new ItemStack(itemSpawnerEnderDragon, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.eyeOfEnder});
		ModLoader.addRecipe(new ItemStack(itemSpawnerPig, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.porkRaw});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSheep, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Block.cloth});
		ModLoader.addRecipe(new ItemStack(itemSpawnerCow, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.beefRaw});
		ModLoader.addRecipe(new ItemStack(itemSpawnerChicken, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.chickenRaw});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSquid, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.dyePowder});
		ModLoader.addRecipe(new ItemStack(itemSpawnerWolf, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.wheat});
		ModLoader.addRecipe(new ItemStack(itemSpawnerMushroomCow, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Block.mushroomCapRed});
		ModLoader.addRecipe(new ItemStack(itemSpawnerSnowMan, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.snowball});
		ModLoader.addRecipe(new ItemStack(itemSpawnerOzelot, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.fishRaw});
		ModLoader.addRecipe(new ItemStack(itemSpawnerVillagerGolem, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Block.blockSteel});
		ModLoader.addRecipe(new ItemStack(itemSpawnerVillager, 1), new Object[] {"MDM", "DXD", "MDM", 'M', Block.cobblestoneMossy, 'D', Item.diamond, 'X', Item.appleRed});
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt) {
		//Post-Initialization code such as mod hooks
	}
	
	/**
	 * Log une chaine de caractère, seulement si le mode debug est activé.
	 * @param text - la chaine de caractère à écrire.
	 * @param logNow - indique si la chaine doit être loguée tout de suite, ou stockée en attendant le reste de la ligne.
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
	 * Log n fois une chaine de caractère, seulement si le mode debug est activé.
	 * @param text - la chaine de caractère à écrire.
	 * @param logNow - indique si la chaine doit être loguée tout de suite, ou stockée en attendant le reste de la ligne.
	 * @param nb - le nombre de fois qu'il faut écrire la chaine de caractère.
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

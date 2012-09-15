package mods.necron.stargate;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLRelaunchLog;

/**
 * Cette classe est la classe principale du mode Necron_StagateMod.<br /><br />
 * Autres classes ajoutées (common - 39) :<br />
 * BlockChevron, BlockCoord, BlockCoordDhd, BlockCoordTeleporter, BlockDetector, BlockGui, BlockMasterChevron, BlockNaquadaAlliage, BlockNaquadaMade, BlockNaquadaOre, BlockPanel, BlockPanelDhd, BlockPanelTeleporter, BlockStargate, BlockStargatePart, BlockStargateSolidPart, BlockVortex,
 * ContainerGui, GateActivationState, GateActivationType, GateState, ItemStargate, ItemStargatePlaceable, NaquadaGenerator, StargateCommonProxy, StargatePacketHandler, StargateServerPacketHandler,
 * TileEntityChevron, TileEntityCoord, TileEntityCoordDhd, TileEntityCoordTeleporter, TileEntityDetector, TileEntityGui, TileEntityMasterChevron, TileEntityNaquadaAlliage, TileEntityStargate, TileEntityStargatePart, TileEntityVortex.<br /><br />
 * Autres classes ajoutées (client - 7) :<br />
 * GuiDetector, GuiDhd, GuiIntegerField, GuiTeleporter, StargateClientEvents, StargateClientPacketHandler, StargateClientProxy.<br /><br />
 * Aucune classe du jeu de base n'est modifiée !
 * @author Seigneur Necron
 */
@Mod(modid = "SeigneurNecron_StargateMod", name = "Necron_StargateMod", version = "[1.3.2] v1.0.0")
@NetworkMod(channels = {StargateMod.chanel}, clientSideRequired = true, serverSideRequired = true, packetHandler = StargatePacketHandler.class, clientPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.chanel}, packetHandler = StargateClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {StargateMod.chanel}, packetHandler = StargateServerPacketHandler.class))
public class StargateMod {
	
	// Debug mod :
	private static boolean debug = true;
	private static StringBuffer buffer = new StringBuffer();
	
	@Instance("StargateMod")
	public static StargateMod instance;
	
	@SidedProxy(clientSide = "mods.necron.stargate.StargateClientProxy", serverSide = "mods.necron.stargate.StargateCommonProxy")
	public static StargateCommonProxy proxy;
	
	// Stargate chanel name :
	public static final String chanel = "Necron_Stargate";
	
	// Stargate texture files :
	public static final String blockTextureFile = "/mods/necron/stargate/terrain.png";
	public static final String itemTextureFile = "/mods/necron/stargate/items.png";
	
	// Quelques constantes :
	public static final float resitantBlockHardness = 10.0F;
	public static final float resitantBlockResistance = 2000.0F;
	
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
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Load texture files :
		proxy.registerRenderInformation();
		// Load sounds files :
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
		ModLoader.addRecipe(new ItemStack(detector), new Object[] {"C", "N", 'N', naquadaAlliage, 'C', itemCrystalScanner});
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

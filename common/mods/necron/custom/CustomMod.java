package mods.necron.custom;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
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
import cpw.mods.fml.relauncher.FMLRelaunchLog;

/**
 * Cette classe est la classe principale du mode Necron_CustomMod.<br /><br />
 * Autres classes ajoutees (common - 6) :<br />
 * CustomCommonProxy, CustomPacketHandler, CustomServerPacketHandler, ItemCustom, ItemCustomPlaceable, ItemMobSpawner.<br /><br />
 * Autres classes ajoutees (client - 2) :<br />
 * CustomClientPacketHandler, CustomClientProxy.<br /><br />
 * Classes du jeu de base modifiees (common - 16) :<br />
 * BlockFlowing, BlockGlass, BlockMobSpawner, BlockPane, ChunkProviderGenerate, EntityCreeper, EntityEnderman, EntityLiving, EntityPlayer, EntityPlayerMP, EnumArmorMaterial, EnumToolMaterial, Explosion, World.<br /><br />
 * Classes du jeu de base modifiees (client - 1) :<br />
 * TileEntitySignRenderer.<br />
 * @author Seigneur Necron
 */
@Mod(modid = "SeigneurNecron_CustomMod", name = "Necron_CustomMod", version = "[1.3.2] v1.0.0")
@NetworkMod(channels = {CustomMod.chanel}, clientSideRequired = true, serverSideRequired = true, packetHandler = CustomPacketHandler.class, clientPacketHandlerSpec = @SidedPacketHandler(channels = {CustomMod.chanel}, packetHandler = CustomClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {CustomMod.chanel}, packetHandler = CustomServerPacketHandler.class))
public class CustomMod {
	
	// Debug mod :
	private static boolean debug = true;
	private static StringBuffer buffer = new StringBuffer();
	
	@Instance("CustomMod")
	public static CustomMod instance;
	
	@SidedProxy(clientSide = "mods.necron.custom.CustomClientProxy", serverSide = "mods.necron.custom.CustomCommonProxy")
	public static CustomCommonProxy proxy;
	
	// Custom chanel name :
	public static final String chanel = "Necron_Custom";
	
	// Custom texture files :
	public static final String blockTextureFile = "/mods/necron/custom/terrain.png";
	public static final String itemTextureFile = "/mods/necron/custom/items.png";
	
	// Quelques constantes :
	public static final float resitantBlockHardness = 10.0F;
	public static final float resitantBlockResistance = 2000.0F;
	public static final int itemMobSpawnerTextureIndex = 0;
	public static final int indestructibleItemsMaxDamage = 0;
	
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
		// Load sounds files :
		proxy.registerSounds();
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		// Register gui handler :
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		
		// Mod 1 - Les outils/armures en diaments (et quelques autres outils) sont indestructibles.
		this.initIndestructibleTools();
		
		// Mod 2 - Change les proprietes des materiaux.
		// Classes modifiees dans le jeu de base : EnumArmorMaterial, EnumToolMaterial.
		
		// Mod 3 - Les creepers ne detruisent plus le decor.
		// Classes modifiees dans le jeu de base : EntityCreeper, Explosion, World.
		
		// Mod 4 - Les enderman ne ramassent plus les blocs.
		// Classes modifiees dans le jeu de base : EntityEnderman.
		
		// Mod 5 - Les joueurs conservent leur inventaire quand ils meurent.
		// Classes modifiees dans le jeu de base : EntityPlayer, EntityPlayerMP.
		
		// Mod 6 - Le verre et les vitres dropent.
		// Classes modifiees dans le jeu de base : BlockGlass, BlockPane.
		
		// Mod 7 - La bedrock est generee uniquement sur la premiere couche (Y = 0).
		// Classes modifiees dans le jeu de base : ChunkProviderGenerate.
		
		// Mod 8 - Tout les liquides se dupliquent.
		// Classes modifiees dans le jeu de base : BlockFlowing.
		
		// Mod 9 - Les monstres dropent de l'xp meme quand ils se sont suicide.
		// Classes modifiees dans le jeu de base : EntityLiving.
		
		// Mod 10 - L'obsidian est moins long a miner.
		this.initObsidianResistance();
		
		// Mod 11 - Les mobSpawners dropent un item permettant de replacer le meme type de mobSpawner.
		// Classes modifiees dans le jeu de base : BlockMobSpawner.
		this.initMobSpawner();
		
		// Mod 12 - Caution this is Sparta.
		// Classes modifiees dans le jeu de base : TileEntitySignRenderer.
		// Classes ajoutees : SignRendererHelper.
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
		ModLoader.addName(itemSpawnerCreeper, "fr_FR", "Generateur de creepers");
		ModLoader.addName(itemSpawnerSkeleton, "fr_FR", "Generateur de squelettes");
		ModLoader.addName(itemSpawnerSpider, "fr_FR", "Generateur d'araignees");
		ModLoader.addName(itemSpawnerGiant, "fr_FR", "Generateur de geants");
		ModLoader.addName(itemSpawnerZombie, "fr_FR", "Generateur de zombies");
		ModLoader.addName(itemSpawnerSlime, "fr_FR", "Generateur de blobs");
		ModLoader.addName(itemSpawnerGhast, "fr_FR", "Generateur de poulpes-chat-volants");
		ModLoader.addName(itemSpawnerPigZombie, "fr_FR", "Generateur de cochons-zombies");
		ModLoader.addName(itemSpawnerEnderman, "fr_FR", "Generateur d'endermen");
		ModLoader.addName(itemSpawnerCaveSpider, "fr_FR", "Generateur d'araignees-venimeuses");
		ModLoader.addName(itemSpawnerSilverfish, "fr_FR", "Generateur de poissons d'argent");
		ModLoader.addName(itemSpawnerBlaze, "fr_FR", "Generateur de piromans");
		ModLoader.addName(itemSpawnerLavaSlime, "fr_FR", "Generateur de blobs de lave");
		ModLoader.addName(itemSpawnerEnderDragon, "fr_FR", "Generateur d'EnderDragon");
		ModLoader.addName(itemSpawnerPig, "fr_FR", "Generateur de cochons");
		ModLoader.addName(itemSpawnerSheep, "fr_FR", "Generateur de moutons");
		ModLoader.addName(itemSpawnerCow, "fr_FR", "Generateur de vaches-ninja");
		ModLoader.addName(itemSpawnerChicken, "fr_FR", "Generateur de poulets");
		ModLoader.addName(itemSpawnerSquid, "fr_FR", "Generateur de poulpes");
		ModLoader.addName(itemSpawnerWolf, "fr_FR", "Generateur de loups");
		ModLoader.addName(itemSpawnerMushroomCow, "fr_FR", "Generateur de champimeuh");
		ModLoader.addName(itemSpawnerSnowMan, "fr_FR", "Generateur de bonhommes de neige");
		ModLoader.addName(itemSpawnerOzelot, "fr_FR", "Generateur de nyaaa");
		ModLoader.addName(itemSpawnerVillagerGolem, "fr_FR", "Generateur de golems");
		ModLoader.addName(itemSpawnerVillager, "fr_FR", "Generateur de peons");
		
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

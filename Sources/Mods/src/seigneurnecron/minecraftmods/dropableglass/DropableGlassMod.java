package seigneurnecron.minecraftmods.dropableglass;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.dropableglass.block.BlockGlassDropable;
import seigneurnecron.minecraftmods.dropableglass.block.BlockGlassPaneDropable;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * <br />
 * SeigneurNecron's Dropable Glass Mod main class.
 * @author Seigneur Necron
 */
@Mod(modid = DropableGlassMod.MOD_ID, name = DropableGlassMod.MOD_NAME, version = DropableGlassMod.VERSION, dependencies = "required-after:" + SeigneurNecronMod.MOD_ID)
public class DropableGlassMod extends ModBase<DropableGlassMod, DropableGlassModConfig> {
	
	// Seigneur Necron mod core basic informations :
	
	public static final String MOD_ID = "seigneur_necron_dropable_glass_mod";
	public static final String MOD_NAME = "SeigneurNecron's Dropable Glass Mod";
	public static final String VERSION = "[1.6.4] v1.0.1 [core 1.1.1]";
	
	@Override
	protected String getModId() {
		return MOD_ID;
	}
	
	// Instance :
	
	@Instance(DropableGlassMod.MOD_ID)
	public static DropableGlassMod instance;
	
	// Configuration :
	
	@Override
	protected DropableGlassModConfig createModConfig(FMLPreInitializationEvent event) {
		return new DropableGlassModConfig(this, new Configuration(event.getSuggestedConfigurationFile()));
	}
	
	// Blocks :
	
	public static Block block_glassDropable;
	public static Block block_thinGlassDropable;
	
	// Initialization :
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		if(this.getConfig().glassBlocksDropable) {
			Block.blocksList[Block.glass.blockID] = null;
			block_glassDropable = new BlockGlassDropable();
		}
		
		if(this.getConfig().glassPanesDropable) {
			Block.blocksList[Block.thinGlass.blockID] = null;
			block_thinGlassDropable = new BlockGlassPaneDropable();
		}
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
}

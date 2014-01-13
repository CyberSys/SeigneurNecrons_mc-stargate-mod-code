package seigneurnecron.minecraftmods.customsigns;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.customsigns.proxy.CustomSignsCommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

// #### TODOs ############################################################################################### //
//                                                                                                            //
// TODO - Find a way to get the list of the texture files in the "sign" folder.                               //
// This would make very easy to add new custom textures for signs.                                            //
// The list of textures must be updated when the user changes the texture pack.                               //
//                                                                                                            //
// ########################################################################################################## //

/**
 * SeigneurNecron's Custom Signs Mod main class.
 * @author Seigneur Necron
 */
@Mod(modid = CustomSignsMod.MOD_ID, name = CustomSignsMod.MOD_NAME, version = CustomSignsMod.VERSION, dependencies = "required-after:" + SeigneurNecronMod.MOD_ID)
public class CustomSignsMod extends ModBase<CustomSignsMod, CustomSignsModConfig> {
	
	// Seigneur Necron custom signs mod basic informations :
	
	public static final String MOD_ID = "seigneur_necron_custom_signs_mod";
	public static final String MOD_NAME = "SeigneurNecron's Custom Signs Mod";
	public static final String VERSION = "[1.6.4] v1.0.0 [core v.1.1.0]";
	
	@Override
	protected String getModId() {
		return MOD_ID;
	}
	
	// Instance :
	
	@Instance(CustomSignsMod.MOD_ID)
	public static CustomSignsMod instance;
	
	@SidedProxy(clientSide = "seigneurnecron.minecraftmods.customsigns.proxy.CustomSignsClientProxy", serverSide = "seigneurnecron.minecraftmods.customsigns.proxy.CustomSignsCommonProxy")
	public static CustomSignsCommonProxy proxy;
	
	// Configuration :
	
	@Override
	protected CustomSignsModConfig createModConfig(FMLPreInitializationEvent event) {
		return new CustomSignsModConfig(this, new Configuration(event.getSuggestedConfigurationFile()));
	}
	
	// Initialization :
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		proxy.registerRenderers();
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
}

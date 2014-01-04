package seigneurnecron.minecraftmods.core;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.gui.GuiContainerBasic;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * SeigneurNecron's Mod Core main class.
 * @author Seigneur Necron
 */
@Mod(modid = SeigneurNecronMod.MOD_ID, name = SeigneurNecronMod.MOD_NAME, version = SeigneurNecronMod.VERSION)
public class SeigneurNecronMod extends ModBase<SeigneurNecronMod, SeigneurNecronModConfig> {
	
	// Seigneur Necron mod core basic informations :
	
	public static final String MOD_ID = "seigneur_necron_mod_core";
	public static final String MOD_NAME = "SeigneurNecron's Mod Core";
	public static final String VERSION = "[1.6.2] v1.1.0 [forge 9.10.0.845]";
	
	@Override
	protected String getModId() {
		return MOD_ID;
	}
	
	// Instance :
	
	@Instance(SeigneurNecronMod.MOD_ID)
	public static SeigneurNecronMod instance;
	
	// Configuration :
	
	@Override
	protected SeigneurNecronModConfig createModConfig(FMLPreInitializationEvent event) {
		return new SeigneurNecronModConfig(this, new Configuration(event.getSuggestedConfigurationFile()));
	}
	
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
		this.registerNames();
	}
	
	protected void registerNames() {
		this.addName(GuiContainerBasic.INVENTORY, "Inventory", "Inventaire");
		this.addName(GuiContainerBasic.TOOL_BAR, "Tool bar", "Bare d'outils");
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
}

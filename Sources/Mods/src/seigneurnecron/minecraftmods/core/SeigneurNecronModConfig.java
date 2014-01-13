package seigneurnecron.minecraftmods.core;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.mod.ModConfig;

/**
 * @author Seigneur Necron
 */
public class SeigneurNecronModConfig extends ModConfig<SeigneurNecronMod> {
	
	// Constructors :
	
	protected SeigneurNecronModConfig(SeigneurNecronMod mod, Configuration config) {
		super(mod, config, 3000, 9000);
	}
	
	// Configuration fields :
	
	@Config(comment = "THIS MUST BE TRUE or the mod will not work correctly. \"false\" is only used to test the mod in Eclipse while developing.")
	public boolean obfuscated = true;
	
	// Obfuscated name fields :
	
	@ObfName(notObfuscatedName = "selectedButton")
	public String guiScreen_selectedButton = "field_73883_a";
	
	@ObfName(notObfuscatedName = "isEnabled")
	public String guiTextField_isEnabled = "field_73819_m";
	
	@ObfName(notObfuscatedName = "dropContentsWhenDead")
	public String entityMinecartContainer_dropContentWhenDead = "field_94112_b";
	
	@ObfName(notObfuscatedName = "xTile")
	public String entityFishHook_xTile = "field_70202_d";
	
	@ObfName(notObfuscatedName = "yTile")
	public String entityFishHook_yTile = "field_70203_e";
	
	@ObfName(notObfuscatedName = "zTile")
	public String entityFishHook_zTile = "field_70200_f";
	
	@ObfName(notObfuscatedName = "inTile")
	public String entityFishHook_inTile = "field_70201_g";
	
	@ObfName(notObfuscatedName = "inGround")
	public String entityFishHook_inGround = "field_70214_h";
	
	@ObfName(notObfuscatedName = "ticksInGround")
	public String entityFishHook_ticksInGround = "field_70216_i";
	
	@ObfName(notObfuscatedName = "ticksInAir")
	public String entityFishHook_ticksInAir = "field_70211_j";
	
	@ObfName(notObfuscatedName = "ticksCatchable")
	public String entityFishHook_ticksCatchable = "field_70219_an";
	
	@ObfName(notObfuscatedName = "fishPosRotationIncrements")
	public String entityFishHook_fishPosRotationIncrements = "field_70217_ao";
	
	@ObfName(notObfuscatedName = "fishX")
	public String entityFishHook_fishX = "field_70218_ap";
	
	@ObfName(notObfuscatedName = "fishY")
	public String entityFishHook_fishY = "field_70210_aq";
	
	@ObfName(notObfuscatedName = "fishZ")
	public String entityFishHook_fishZ = "field_70209_ar";
	
	@ObfName(notObfuscatedName = "fishYaw")
	public String entityFishHook_fishYaw = "field_70208_as";
	
	@ObfName(notObfuscatedName = "fishPitch")
	public String entityFishHook_fishPitch = "field_70207_at";
	
}

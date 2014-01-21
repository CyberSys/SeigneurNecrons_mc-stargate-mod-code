package seigneurnecron.minecraftmods.stargate;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.mod.ModConfig;

/**
 * @author Seigneur Necron
 */
public class StargateModConfig extends ModConfig<StargateMod> {
	
	// Constructors :
	
	public StargateModConfig(StargateMod mod, Configuration config) {
		super(mod, config, 3000, 9000);
	}
	
	// Configuration :
	
	@Config(comment = "Determines if players can craft naquadah alloy.")
	public boolean canCraftNaquadahAlloy = true;
	
	@Config(comment = "Determines if players can craft stargates ! (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftStargate = true;
	
	@Config(comment = "Determines if players can craft stargate shield consoles. (Requires canCraftNaquadahAlloy & canCraftStargate = true)")
	public boolean canCraftStargateShield = true;
	
	@Config(comment = "Determines if players can craft the fast stargate block. (Requires canCraftNaquadahAlloy & canCraftStargate = true)")
	public boolean canCraftFastStargate = true;
	
	@Config(comment = "Determines if players can craft teleporters. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftTeleporter = true;
	
	@Config(comment = "Determines if players can craft detectors. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftDetector = true;
	
	@Config(comment = "Determines if players can craft mob generators. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftMobGenerator = true;
	
	@Config(comment = "Determines if players can craft stuff level up tables. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftStuffLevelUpTable = true;
	
	@Config(comment = "Determines if players can craft self powered redstone lights.")
	public boolean canCraftSelfPoweredRedstoneLight = true;
	
	@Config(comment = "Determines if players can craft naquadah tools and armors.")
	public boolean canCraftToolsAndArmors = true;
	
	@Config(comment = "Determines if players can craft fireballs.")
	public boolean canCraftFireBalls = true;
	
	@Config(comment = "Determines if players can craft the (cheated) fire staff. (Requires canCraftToolsAndArmors = true)")
	public boolean canCraftFireStaff = true;
	
	@Config(comment = "Determines if players can craft explosive fireballs.")
	public boolean canCraftExplosiveFireBalls = true;
	
	@Config(comment = "Determines if players can craft the (over-cheated) explosive fire staff. (Requires canCraftToolsAndArmors & canCraftFireStaff & canCraftExplosiveFireBalls = true)")
	public boolean canCraftExplosiveFireStaff = true;
	
	@Config(comment = "Determines if explosive fireballs can destroy blocks.")
	public boolean canExplosiveFireBallsDestroyBlocks = true;
	
	@Config(comment = "Determines if bedrock blocks can be mined (by right-clicking) with a naquadah pickaxe (only if Y != 0).")
	public boolean canNaquadahPickaxeMineBedrock = true;
	
	@Config(comment = "Determines the gate sounds : flase => MilkyWay gate sounds, true => Pegasus gate sounds.")
	public boolean atlantisSounds = false;
	
}

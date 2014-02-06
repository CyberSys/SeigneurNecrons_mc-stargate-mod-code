package seigneurnecron.minecraftmods.stargate;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.mod.ModConfig;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
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
	
	@Config(comment = "Determines if players can craft basic fireballs. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftFireballBasic = true;
	
	@Config(comment = "Determines if players can craft boosted fireballs. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftFireballBoosted = true;
	
	@Config(comment = "Determines if players can craft explosive fireballs. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftFireballExplosive = true;
	
	@Config(comment = "Determines if players can craft stable explosive fireballs. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftFireballExplosiveStable = true;
	
	@Config(comment = "Determines if players can craft nuke fireballs. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftFireballNuke = true;
	
	@Config(comment = "Determines if players can craft napalm fireballs. (Requires canCraftNaquadahAlloy = true)")
	public boolean canCraftFireballNapalm = true;
	
	@Config(comment = "Determines if players can craft the basic fire staff. (Requires canCraftNaquadahAlloy & canCraftToolsAndArmors & canCraftFireballBasic = true)")
	public boolean canCraftFireStaffBasic = true;
	
	@Config(comment = "Determines if players can craft the boosted fire staff. (Requires canCraftNaquadahAlloy & canCraftToolsAndArmors & canCraftFireballBoosted = true)")
	public boolean canCraftFireStaffBoosted = true;
	
	@Config(comment = "Determines if players can craft the explosive fire staff. (Requires canCraftNaquadahAlloy & canCraftToolsAndArmors & canCraftFireballExplosive = true)")
	public boolean canCraftFireStaffExplosive = true;
	
	@Config(comment = "Determines if players can craft the stable explosive fire staff. (Requires canCraftNaquadahAlloy & canCraftToolsAndArmors & canCraftFireballExplosiveStable = true)")
	public boolean canCraftFireStaffExplosiveStable = true;
	
	@Config(comment = "Determines if players can craft the nuke staff. (Requires canCraftNaquadahAlloy & canCraftToolsAndArmors & canCraftFireballNuke = true)")
	public boolean canCraftFireStaffNuke = true;
	
	@Config(comment = "Determines if players can craft the napalm staff. (Requires canCraftNaquadahAlloy & canCraftToolsAndArmors & canCraftFireballNapalm = true)")
	public boolean canCraftFireStaffNapalm = true;
	
	@Config(comment = "Determines if explosive fireballs can destroy blocks.")
	public boolean canExplosiveFireBallsDestroyBlocks = true;
	
	@Config(comment = "Determines if bedrock blocks can be mined (by right-clicking) with a naquadah pickaxe (only if Y != 0).")
	public boolean canNaquadahPickaxeMineBedrock = true;
	
	@Config(comment = "Determines the gate sounds : flase => MilkyWay gate sounds, true => Pegasus gate sounds.")
	public boolean atlantisSounds = false;
	
}

package seigneurnecron.minecraftmods.dropableglass;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.mod.ModConfig;

/**
 * @author Seigneur Necron
 */
public class DropableGlassModConfig extends ModConfig<DropableGlassMod> {
	
	// Constructors :
	
	protected DropableGlassModConfig(DropableGlassMod mod, Configuration config) {
		super(mod, config, 3000, 9000);
	}
	
	// Configuration fields :
	
	@Config(comment = "Determines if glass blocks are droppped without silk touch.")
	public boolean glassBlocksDropable = true;
	
	@Config(comment = "Determines if glass panes are droppped without silk touch.")
	public boolean glassPanesDropable = true;
	
}

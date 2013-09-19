package seigneurnecron.minecraftmods.customsigns;

import net.minecraftforge.common.Configuration;
import seigneurnecron.minecraftmods.core.mod.ModConfig;

/**
 * @author Seigneur Necron
 */
public class CustomSignsModConfig extends ModConfig<CustomSignsMod> {
	
	// Constructors :
	
	protected CustomSignsModConfig(CustomSignsMod mod, Configuration config) {
		super(mod, config, 3000, 9000);
	}
	
	// Configuration fields :
	
	@Config(comment = "This is list of the words you can write on the first line of a sign to display a custom image. The name of the image file corresponding to a message must be that same word followed by \".png\".")
	public String[] messages = {"CAUTION", "TROLL", "TRAP"};
	
}

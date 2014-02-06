package seigneurnecron.minecraftmods.core.font;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.ReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * <br />
 * This class allows to easily create a new font renderer.
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class CustomFontRenderer extends FontRenderer {
	
	// Constructors :
	
	public CustomFontRenderer(String textureFile) {
		super(FMLClientHandler.instance().getClient().gameSettings, new ResourceLocation(textureFile), FMLClientHandler.instance().getClient().renderEngine, false);
		ReloadableResourceManager resourceManager = (ReloadableResourceManager) FMLClientHandler.instance().getClient().getResourceManager();
		resourceManager.registerReloadListener(this);
	}
	
}

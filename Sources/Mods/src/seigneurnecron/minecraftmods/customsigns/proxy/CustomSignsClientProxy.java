package seigneurnecron.minecraftmods.customsigns.proxy;

import net.minecraft.tileentity.TileEntitySign;
import seigneurnecron.minecraftmods.customsigns.render.TileEntitySignCustomRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;

/**
 * @author Seigneur Necron
 */
public class CustomSignsClientProxy extends CustomSignsCommonProxy {
	
	// Methods :
	
	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySignCustomRenderer());
	}
	
}

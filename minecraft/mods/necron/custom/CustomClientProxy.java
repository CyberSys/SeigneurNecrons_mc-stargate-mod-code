package mods.necron.custom;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomClientProxy extends CustomCommonProxy {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture(CustomMod.blockTextureFile);
		MinecraftForgeClient.preloadTexture(CustomMod.itemTextureFile);
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomFireBall.class, new RenderCustomFireBall());
	}
	
	@Override
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		// vide pour l'instant...
	}
	
	@Override
	public void registerSounds() {
		// vide pour l'instant...
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
}

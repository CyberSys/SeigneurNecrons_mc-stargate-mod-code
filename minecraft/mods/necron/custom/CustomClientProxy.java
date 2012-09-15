package mods.necron.custom;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;

public class CustomClientProxy extends CustomCommonProxy {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture(CustomMod.blockTextureFile);
		MinecraftForgeClient.preloadTexture(CustomMod.itemTextureFile);
	}
	
	@Override
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		// vide pour l'instant...
	}
	
	public void registerSounds() {
		// vide pour l'instant...
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
}

package mods.necron.custom;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CustomCommonProxy implements IGuiHandler {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public void registerRenderInformation() {
		// Vide cote server.
	}
	
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		// Vide cote server
	}
	
	public void registerSounds() {
		// Vide cote server.
	}
	
	public World getClientWorld() {
		return null;
	}
	
}

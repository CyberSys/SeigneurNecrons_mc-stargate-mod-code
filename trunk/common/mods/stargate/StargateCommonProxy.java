package mods.stargate;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class StargateCommonProxy implements IGuiHandler {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && tileEntity instanceof TileEntityGui) {
			return new ContainerGui(player.inventory, (TileEntityGui) tileEntity);
		}
		
		return null;
	}
	
	public void registerRenderInformation() {
		// Vide côté server.
	}
	
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		// Vide côté server
	}
	
	public void registerSounds() {
		// Vide côté server.
	}
	
	public World getClientWorld() {
		return null;
	}
	
}

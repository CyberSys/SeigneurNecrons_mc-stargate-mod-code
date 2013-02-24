package seigneurnecron.minecraftmods.stargate.network;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.common.network.IGuiHandler;

public class StargateCommonProxy implements IGuiHandler {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityMobGenerator) {
				return new ContainerMobGenerator(player.inventory, (TileEntityMobGenerator) tileEntity);
			}
		}
		
		return null;
	}
	
	public void registerTextures() {
		// Vide cote server.
	}
	
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		// Vide cote server
	}
	
	public void registerSounds() {
		// Vide cote server.
	}
	
	public WorldClient getClientWorld() {
		return null;
	}
	
	public EntityClientPlayerMP getClientPlayer() {
		return null;
	}
	
}

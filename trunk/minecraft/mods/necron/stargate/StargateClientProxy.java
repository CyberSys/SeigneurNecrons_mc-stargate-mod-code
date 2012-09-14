package mods.necron.stargate;

import mods.necron.stargate.StargateCommonProxy;
import mods.necron.stargate.StargateMod;
import mods.necron.stargate.TileEntityCoordDhd;
import mods.necron.stargate.TileEntityCoordTeleporter;
import mods.necron.stargate.TileEntityDetector;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;

public class StargateClientProxy extends StargateCommonProxy {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityCoordTeleporter){
				return new GuiTeleporter((TileEntityCoordTeleporter) tileEntity);
			}
			else if(tileEntity instanceof TileEntityCoordDhd) { 
				return new GuiDhd((TileEntityCoordDhd) tileEntity);
			}
			else if(tileEntity instanceof TileEntityDetector) { 
				return new GuiDetector((TileEntityDetector) tileEntity);
			}
		}
		
		return null;
	}
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture(StargateMod.blockTextureFile);
		MinecraftForgeClient.preloadTexture(StargateMod.itemTextureFile);
	}
	
	@Override
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		
	}
	
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new StargateClientEvents());
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
}

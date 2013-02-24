package seigneurnecron.minecraftmods.stargate.client.network;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiMobGenerator;
import seigneurnecron.minecraftmods.stargate.network.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StargateClientProxy extends StargateCommonProxy {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityMobGenerator) {
				return new GuiMobGenerator(player.inventory, (TileEntityMobGenerator) tileEntity);
			}
		}
		
		return null;
	}
	
	@Override
	public void registerTextures() {
		MinecraftForgeClient.preloadTexture(StargateMod.blockTextureFile);
		MinecraftForgeClient.preloadTexture(StargateMod.itemTextureFile);
	}
	
	@Override
	public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/) {
		// vide pour l'instant...
	}
	
	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new StargateClientEvents());
	}
	
	@Override
	public WorldClient getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public EntityClientPlayerMP getClientPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	
}

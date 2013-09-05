package seigneurnecron.minecraftmods.stargate.client.network;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiMobGenerator;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.client.render.RenderCustomFireBall;
import seigneurnecron.minecraftmods.stargate.client.render.TileEntitySignCustomRenderer;
import seigneurnecron.minecraftmods.stargate.client.sound.StargateSounds;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityNapalm;
import seigneurnecron.minecraftmods.stargate.entity.EntityNuke;
import seigneurnecron.minecraftmods.stargate.network.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientProxy extends StargateCommonProxy {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityMobGenerator) {
				return new GuiMobGenerator(player.inventory, (TileEntityMobGenerator) tileEntity);
			}
			if(tileEntity instanceof TileEntityStuffLevelUpTable) {
				return new GuiStuffLevelUpTable(player.inventory, (TileEntityStuffLevelUpTable) tileEntity);
			}
		}
		
		return null;
	}
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomFireBall.class, new RenderCustomFireBall(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomExplosiveFireBall.class, new RenderCustomFireBall(0.6F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNuke.class, new RenderCustomFireBall(1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNapalm.class, new RenderCustomFireBall(1.5F));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySignCustomRenderer());
	}
	
	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new StargateSounds());
	}
	
	@Override
	public int addArmor(String name) {
		return RenderingRegistry.addNewArmourRendererPrefix(name);
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

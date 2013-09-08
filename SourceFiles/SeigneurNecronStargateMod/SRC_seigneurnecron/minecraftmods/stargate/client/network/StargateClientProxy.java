package seigneurnecron.minecraftmods.stargate.client.network;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.stargate.StargateMod;
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
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientProxy extends StargateCommonProxy {
	
	private static final ResourceLocation STARGATE_FONT = new ResourceLocation(StargateMod.ASSETS_PREFIX + "textures/font/glyphs.png");
	
	private FontRenderer stargateFontRenderer;
	
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
	public FontRenderer getStargateFontRender() {
		//		if(this.stargateFontRenderer == null) {
		//			this.stargateFontRenderer = new FontRenderer(ModLoader.getMinecraftInstance().gameSettings, STARGATE_FONT, ModLoader.getMinecraftInstance().renderEngine, false);
		//		}
		//		
		//		return this.stargateFontRenderer;
		
		// FIXME - remplacer par la vrai font.
		return ModLoader.getMinecraftInstance().fontRenderer;
	}
	
}

package seigneurnecron.minecraftmods.stargate.proxy;

import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.core.font.CustomFontRenderer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomExplosiveFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;
import seigneurnecron.minecraftmods.stargate.entity.EntityNapalm;
import seigneurnecron.minecraftmods.stargate.entity.EntityNuke;
import seigneurnecron.minecraftmods.stargate.gui.GuiMobGenerator;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.render.RenderCustomFireBall;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientProxy extends StargateCommonProxy {
	
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
		this.registerFontRenderer();
	}
	
	protected void registerFontRenderer() {
		try {
			this.stargateFontRenderer = new CustomFontRenderer(StargateMod.instance.getAssetPrefix() + "textures/font/glyphs.png");
		}
		catch(Exception argh) {
			StargateMod.instance.log("Error while creating the custom stargate font renderer. DHD symbols will not render correctly. Replaced by default font.", Level.SEVERE);
			argh.printStackTrace();
			this.stargateFontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
		}
	}
	
	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(StargateMod.getSounds());
	}
	
	@Override
	public int addArmor(String name) {
		return RenderingRegistry.addNewArmourRendererPrefix(name);
	}
	
	@Override
	public FontRenderer getStargateFontRender() {
		return this.stargateFontRenderer;
	}
	
}

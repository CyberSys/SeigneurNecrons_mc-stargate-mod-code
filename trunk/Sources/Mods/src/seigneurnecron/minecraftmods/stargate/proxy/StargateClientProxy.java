package seigneurnecron.minecraftmods.stargate.proxy;

import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import seigneurnecron.minecraftmods.core.font.CustomFontRenderer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.block.BlockGuiScreen;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireBallBoosted;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballBasic;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballExplosive;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballExplosiveStable;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNapalm;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNuke;
import seigneurnecron.minecraftmods.stargate.gui.GuiConsoleBase;
import seigneurnecron.minecraftmods.stargate.gui.GuiCrystalFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiFireballFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiMobGenerator;
import seigneurnecron.minecraftmods.stargate.gui.GuiShieldRemote;
import seigneurnecron.minecraftmods.stargate.gui.GuiSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStargateFactory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerFireballFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerMobGenerator;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStargateFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.item.ItemShieldRemote;
import seigneurnecron.minecraftmods.stargate.render.RenderFireball;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleFireballFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleScreen;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStuffLevelUpTable;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientProxy extends StargateCommonProxy {
	
	// Fields :
	
	private FontRenderer stargateFontRenderer;
	
	// GuiContainer methods :
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityMobGenerator) {
				return new GuiMobGenerator(new ContainerMobGenerator(player, ((TileEntityMobGenerator) tileEntity).getInventory()));
			}
			else if(tileEntity instanceof TileEntityCrystalFactory) {
				return new GuiCrystalFactory(new ContainerCrystalFactory(player, ((TileEntityCrystalFactory) tileEntity).getInventory()));
			}
			else if(tileEntity instanceof TileEntityConsoleBase) {
				TileEntityConsoleBase tileEntityConsoleBase = (TileEntityConsoleBase) tileEntity;
				
				if(id == NOT_A_CONSOLE) {
					return new GuiConsoleBase(new ContainerConsoleBase(player, tileEntityConsoleBase.getInventory()));
				}
				else {
					Console console = tileEntityConsoleBase.getConsole();
					
					if(console != null) {
						if(console instanceof ConsoleStuffLevelUpTable) {
							return new GuiStuffLevelUpTable(new ContainerStuffLevelUpTable(player, ((ConsoleStuffLevelUpTable) console).getInventory()));
						}
						else if(console instanceof ConsoleSoulCrystalFactory) {
							return new GuiSoulCrystalFactory(new ContainerSoulCrystalFactory(player, ((ConsoleSoulCrystalFactory) console).getInventory()));
						}
						else if(console instanceof ConsoleFireballFactory) {
							return new GuiFireballFactory(new ContainerFireballFactory(player, ((ConsoleFireballFactory) console).getInventory()));
						}
						else if(console instanceof ConsoleStargateFactory) {
							return new GuiStargateFactory(new ContainerStargateFactory(player, ((ConsoleStargateFactory) console).getInventory()));
						}
					}
				}
			}
		}
		
		return null;
	}
	
	// GuiScreen methods :
	
	@Override
	public void openGui(BlockGuiScreen block, EntityPlayer player, TileEntity tileEntity) {
		FMLClientHandler.instance().displayGuiScreen(player, block.getGuiScreen(tileEntity, player));
	}
	
	@Override
	public void openGui(ItemShieldRemote item, World world, EntityPlayer player) {
		TileEntityStargateControl stargate = item.getNearestActivatedGate(world, (int) player.posX, (int) player.posY, (int) player.posZ);
		
		if(stargate != null) {
			FMLClientHandler.instance().displayGuiScreen(player, new GuiShieldRemote(stargate, player));
		}
	}
	
	@Override
	public void openGui(ConsoleScreen console, EntityPlayer player) {
		FMLClientHandler.instance().displayGuiScreen(player, console.getGui(player));
	}
	
	// Registering methods :
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityFireballBasic.class, new RenderFireball(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBallBoosted.class, new RenderFireball(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireballExplosive.class, new RenderFireball(0.6F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireballExplosiveStable.class, new RenderFireball(0.6F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireballNuke.class, new RenderFireball(1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireballNapalm.class, new RenderFireball(1.5F));
		
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

package seigneurnecron.minecraftmods.stargate.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.block.BlockGuiScreen;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerFireballFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerMobGenerator;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStargateFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.item.ItemShieldRemote;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleFireballFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleScreen;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStuffLevelUpTable;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class StargateCommonProxy implements IGuiHandler {
	
	// Constants :
	
	public static final int NOT_A_CONSOLE = 0;
	public static final int A_CONSOLE = 1;
	
	// Static part :
	
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	public static void storeEntityData(String key, NBTTagCompound data) {
		extendedEntityData.put(key, data);
	}
	
	public static NBTTagCompound getEntityData(String key) {
		return extendedEntityData.remove(key);
	}
	
	// GuiContainer methods :
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityMobGenerator) {
				return new ContainerMobGenerator(player, ((TileEntityMobGenerator) tileEntity).getInventory());
			}
			else if(tileEntity instanceof TileEntityCrystalFactory) {
				return new ContainerCrystalFactory(player, ((TileEntityCrystalFactory) tileEntity).getInventory());
			}
			else if(tileEntity instanceof TileEntityConsoleBase) {
				TileEntityConsoleBase tileEntityConsoleBase = (TileEntityConsoleBase) tileEntity;
				
				if(id == NOT_A_CONSOLE) {
					return new ContainerConsoleBase(player, tileEntityConsoleBase.getInventory());
				}
				else {
					Console console = tileEntityConsoleBase.getConsole();
					
					if(console != null) {
						if(console instanceof ConsoleStuffLevelUpTable) {
							return new ContainerStuffLevelUpTable(player, ((ConsoleStuffLevelUpTable) console).getInventory());
						}
						else if(console instanceof ConsoleSoulCrystalFactory) {
							return new ContainerSoulCrystalFactory(player, ((ConsoleSoulCrystalFactory) console).getInventory());
						}
						else if(console instanceof ConsoleFireballFactory) {
							return new ContainerFireballFactory(player, ((ConsoleFireballFactory) console).getInventory());
						}
						else if(console instanceof ConsoleStargateFactory) {
							return new ContainerStargateFactory(player, ((ConsoleStargateFactory) console).getInventory());
						}
					}
				}
			}
		}
		
		return null;
	}
	
	// GuiScreen methods :
	
	public void openGui(BlockGuiScreen block, EntityPlayer player, TileEntity tileEntity) {
		// Nothing here.
	}
	
	public void openGui(ItemShieldRemote item, World world, EntityPlayer player) {
		// Nothing here.
	}
	
	public void openGui(ConsoleScreen console, EntityPlayer player) {
		// Nothing here.
	}
	
	// Registering methods :
	
	public void registerRenderers() {
		// Empty server side.
	}
	
	public void registerSounds() {
		// Empty server side.
	}
	
	public int addArmor(String name) {
		return 0;
	}
	
	public FontRenderer getStargateFontRender() {
		return null;
	}
	
}

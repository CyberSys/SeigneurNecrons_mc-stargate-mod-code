package seigneurnecron.minecraftmods.stargate.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerMobGenerator;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStuffLevelUpTable;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author Seigneur Necron
 */
public class StargateCommonProxy implements IGuiHandler {
	
	public static final int NOT_A_CONSOLE = 0;
	public static final int A_CONSOLE = 1;
	
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	public static void storeEntityData(String key, NBTTagCompound data) {
		extendedEntityData.put(key, data);
	}
	
	public static NBTTagCompound getEntityData(String key) {
		return extendedEntityData.remove(key);
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			if(tileEntity instanceof TileEntityMobGenerator) {
				return new ContainerMobGenerator(player.inventory, ((TileEntityMobGenerator) tileEntity).getInventory());
			}
			else if(tileEntity instanceof TileEntityConsoleBase) {
				TileEntityConsoleBase tileEntityConsoleBase = (TileEntityConsoleBase) tileEntity;
				
				if(id == NOT_A_CONSOLE) {
					return new ContainerConsoleBase(player.inventory, tileEntityConsoleBase.getInventory());
				}
				else {
					Console console = tileEntityConsoleBase.getConsole();
					
					if(console != null) {
						if(console instanceof ConsoleStuffLevelUpTable) {
							ConsoleStuffLevelUpTable consoleStuffLevelUpTable = (ConsoleStuffLevelUpTable) console;
							return new ContainerStuffLevelUpTable(player.inventory, consoleStuffLevelUpTable.getInventory());
						}
						else if(console instanceof ConsoleCrystalFactory) {
							ConsoleCrystalFactory consoleCrystalFactory = (ConsoleCrystalFactory) console;
							return new ContainerCrystalFactory(player.inventory, consoleCrystalFactory.getInventory());
						}
					}
				}
			}
		}
		
		return null;
	}
	
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

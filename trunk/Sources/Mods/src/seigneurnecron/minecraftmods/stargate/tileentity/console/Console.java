package seigneurnecron.minecraftmods.stargate.tileentity.console;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public abstract class Console {
	
	// Static part :
	
	public static final String CONSOLE_NAMES_PREFIX = "container.console.";
	
	private static Map<ArrayList<ItemCrystal>, Class<? extends Console>> crystalsToConsoleMap = new HashMap<ArrayList<ItemCrystal>, Class<? extends Console>>();
	private static Map<Class<? extends Console>, String> consoleToNameMap = new HashMap<Class<? extends Console>, String>();
	
	public static void registerConsole(ArrayList<ItemCrystal> crystals, Class<? extends Console> console, String name) {
		if(!crystalsToConsoleMap.containsKey(crystals)) {
			crystalsToConsoleMap.put(crystals, console);
			
			if(!consoleToNameMap.containsKey(console)) {
				consoleToNameMap.put(console, name);
			}
		}
		else {
			StargateMod.instance.log("Error : this crystal set is already registered in the console map.", Level.SEVERE);
		}
	}
	
	public static Class<? extends Console> getConsoleClass(ArrayList<ItemCrystal> crystals) {
		Class<? extends Console> clazz = crystalsToConsoleMap.get(crystals);
		
		if(clazz == null) {
			clazz = ConsoleDefault.class;
		}
		
		return clazz;
	}
	
	public static Console getConsole(Class<? extends Console> clazz, TileEntityConsoleBase tileEntity) {
		if(clazz != null) {
			try {
				Constructor<? extends Console> constructor = clazz.getConstructor(TileEntityConsoleBase.class);
				Console console = constructor.newInstance(tileEntity);
				return console;
			}
			catch(Exception argh) {
				argh.printStackTrace();
			}
		}
		
		StargateMod.instance.log("Error : can't instanciate the console \"" + (clazz == null ? "null" : clazz.getSimpleName()) + "\".", Level.SEVERE);
		return null;
	}
	
	public static Console getConsole(ArrayList<ItemCrystal> crystals, TileEntityConsoleBase tileEntity) {
		return getConsole(getConsoleClass(crystals), tileEntity);
	}
	
	public static String getConsoleName(Class<? extends Console> clazz) {
		return consoleToNameMap.get(clazz);
	}
	
	public static Set<Entry<ArrayList<ItemCrystal>, Class<? extends Console>>> getValidCristalSets() {
		return crystalsToConsoleMap.entrySet();
	}
	
	public static Set<Entry<Class<? extends Console>, String>> getConsoles() {
		return consoleToNameMap.entrySet();
	}
	
	// Fields :
	
	protected final TileEntityConsoleBase tileEntity;
	
	// Constructors :
	
	protected Console(TileEntityConsoleBase tileEntity) {
		this.tileEntity = tileEntity;
	}
	
	// Methods :
	
	/**
	 * Indicates whether the console is valid or has been replaced.
	 * @return true if the console is valid, false if it has been replaced.
	 */
	public boolean isValid() {
		return this.tileEntity.getConsole() == this;
	}
	
	/**
	 * Indicates whether the console is active.
	 * @return true if the console is active, else false.
	 */
	public boolean isActive() {
		return true;
	}
	
	/**
	 * Open the gui of this console.
	 * @return true if the gui was succesfully opened, else false.
	 */
	public abstract boolean openGui(World world, int x, int y, int z, EntityPlayer player);
	
	/**
	 * Defines what happen when the console is destroyed.
	 */
	public void onConsoleDestroyed() {
		// Nothing here.
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		// Nothing here.
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		// Nothing here.
	}
	
}

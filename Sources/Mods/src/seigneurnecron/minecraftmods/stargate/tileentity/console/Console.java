package seigneurnecron.minecraftmods.stargate.tileentity.console;

import static seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase.NB_CRYSTALS;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class Console {
	
	// Static part :
	
	private static final String CONSOLE_PREFIX = "container.console.";
	public static final String CONSOLE_NAME_PREFIX = CONSOLE_PREFIX + "name.";
	public static final String CONSOLE_INFO_PREFIX = CONSOLE_PREFIX + "info.";
	
	private static Map<Multiset<ItemCrystal>, Class<? extends Console>> crystalsToConsoleMap = new HashMap<Multiset<ItemCrystal>, Class<? extends Console>>();
	private static Map<Class<? extends Console>, String> consoleToNameMap = new HashMap<Class<? extends Console>, String>();
	
	public static void registerConsole(Multiset<ItemCrystal> crystals, Class<? extends Console> console, String name) {
		if(crystals != null && crystals.size() <= NB_CRYSTALS) {
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
		else {
			StargateMod.instance.log("Error : the crystal set must be not null and contain at most " + NB_CRYSTALS + " crystals.", Level.SEVERE);
		}
	}
	
	public static Class<? extends Console> getConsoleClass(List<ItemCrystal> crystals) {
		Multiset<ItemCrystal> set = HashMultiset.create();
		
		for(ItemCrystal crystal : crystals) {
			set.add(crystal);
		}
		
		return getConsoleClass(set);
	}
	
	public static Class<? extends Console> getConsoleClass(Multiset<ItemCrystal> crystals) {
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
	
	public static Console getConsole(List<ItemCrystal> crystals, TileEntityConsoleBase tileEntity) {
		return getConsole(getConsoleClass(crystals), tileEntity);
	}
	
	public static String getConsoleName(Class<? extends Console> clazz) {
		return consoleToNameMap.get(clazz);
	}
	
	@SideOnly(Side.CLIENT)
	public static String getTranslatedConsoleName(Class<? extends Console> clazz) {
		String consoleName = getConsoleName(clazz);
		
		if(consoleName != null) {
			return I18n.getString(CONSOLE_NAME_PREFIX + consoleName);
		}
		else {
			return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static String getTranslatedConsoleInfo(Class<? extends Console> clazz) {
		String consoleName = getConsoleName(clazz);
		
		if(consoleName != null) {
			return I18n.getString(CONSOLE_INFO_PREFIX + consoleName);
		}
		else {
			return null;
		}
	}
	
	public static ImmutableList<Entry<Multiset<ItemCrystal>, Class<? extends Console>>> getValidCristalSets() {
		return ImmutableList.copyOf(crystalsToConsoleMap.entrySet());
	}
	
	public static ImmutableList<Entry<Class<? extends Console>, String>> getConsoles() {
		return ImmutableList.copyOf(consoleToNameMap.entrySet());
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

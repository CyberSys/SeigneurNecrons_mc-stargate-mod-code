package seigneurnecron.minecraftmods.stargate.item;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.minecraft.client.resources.I18n;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemCrystal extends ItemStargate implements Comparable<ItemCrystal> {
	
	// Static part :
	
	public static final String CRYSTAL_INFO_SUFFIX = ".info";
	
	private static final SortedSet<ItemCrystal> craftableCrystals = new TreeSet<ItemCrystal>();
	
	public static List<ItemCrystal> getCraftableCristals() {
		return Lists.newArrayList(craftableCrystals);
	}
	
	public static void registerCraftableCrystal(ItemCrystal crystal) {
		craftableCrystals.add(crystal);
	}
	
	// Fields :
	
	public final String shortcut;
	
	// Constructors :
	
	public ItemCrystal(String name, String shortcut) {
		super(name);
		this.shortcut = shortcut;
	}
	
	// Comparable interface :
	
	@Override
	public int compareTo(ItemCrystal other) {
		return this.getStatName().compareTo(other.getStatName());
	}
	
	// Methods :
	
	public String getUnlocalizedCrystalInfo() {
		return this.getUnlocalizedName() + CRYSTAL_INFO_SUFFIX;
	}
	
	@SideOnly(Side.CLIENT)
	public String getTranslatedCrystalInfo() {
		return I18n.getString(this.getUnlocalizedCrystalInfo());
	}
	
}

package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.core.loadable.Loadable;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class PowerUp implements Loadable<PowerUp> {
	
	// Static part :
	
	private static final ArrayList<Enchantment> tab = new ArrayList<Enchantment>(22);
	
	static {
		tab.add(Enchantment.protection);
		tab.add(Enchantment.fireProtection);
		tab.add(Enchantment.blastProtection);
		tab.add(Enchantment.projectileProtection);
		tab.add(Enchantment.thorns);
		tab.add(Enchantment.respiration);
		tab.add(Enchantment.aquaAffinity);
		tab.add(Enchantment.featherFalling);
		tab.add(Enchantment.power);
		tab.add(Enchantment.punch);
		tab.add(Enchantment.flame);
		tab.add(Enchantment.infinity);
		tab.add(Enchantment.sharpness);
		tab.add(Enchantment.smite);
		tab.add(Enchantment.baneOfArthropods);
		tab.add(Enchantment.knockback);
		tab.add(Enchantment.fireAspect);
		tab.add(Enchantment.looting);
		tab.add(Enchantment.efficiency);
		tab.add(Enchantment.silkTouch);
		tab.add(Enchantment.fortune);
		tab.add(Enchantment.unbreaking);
	}
	
	// NBTTags names :
	
	private static final String ENCHANT = "enchant";
	private static final String LEVEL = "level";
	private static final String COST = "cost";
	
	// Fields :
	
	public Enchantment enchant;
	public int level;
	public int cost;
	
	// Constructors :
	
	public PowerUp(Enchantment enchant, int level, int cost) {
		this.enchant = enchant;
		this.level = level;
		this.cost = cost;
	}
	
	public PowerUp(NBTTagCompound tag) {
		this.loadNBTData(tag);
	}
	
	public PowerUp(DataInputStream input) throws IOException {
		this.readData(input);
	}
	
	// Methods :
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		PowerUp other = (PowerUp) obj;
		return (this.enchant == other.enchant) && (this.level == other.level);
	}
	
	// Comparable interface :
	
	@Override
	public int compareTo(PowerUp other) {
		return tab.indexOf(this.enchant) - tab.indexOf(other.enchant);
	}
	
	// Loadable interface :
	
	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setInteger(ENCHANT, tab.indexOf(this.enchant));
		tag.setInteger(LEVEL, this.level);
		tag.setInteger(COST, this.cost);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag) {
		this.enchant = tab.get(tag.getInteger(ENCHANT));
		this.level = tag.getInteger(LEVEL);
		this.cost = tag.getInteger(COST);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(tab.indexOf(this.enchant));
		output.writeInt(this.level);
		output.writeInt(this.cost);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		this.enchant = tab.get(input.readInt());
		this.level = input.readInt();
		this.cost = input.readInt();
	}
	
	@Override
	public NBTTagCompound getCompound() {
		NBTTagCompound compound = new NBTTagCompound();
		this.saveNBTData(compound);
		return compound;
	}
	
}

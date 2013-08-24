package seigneurnecron.minecraftmods.stargate.inventory;

import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;

/**
 * @author Seigneur Necron
 */
public class PowerUp implements Comparable<PowerUp> {
	
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
	
	public Enchantment enchant;
	public int level;
	public int cost;
	
	public PowerUp(Enchantment enchant, int level, int cost) {
		this.enchant = enchant;
		this.level = level;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(PowerUp other) {
		return tab.indexOf(this.enchant) - tab.indexOf(other.enchant);
	}
	
}

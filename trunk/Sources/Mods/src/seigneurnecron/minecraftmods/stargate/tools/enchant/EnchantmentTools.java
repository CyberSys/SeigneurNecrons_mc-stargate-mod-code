package seigneurnecron.minecraftmods.stargate.tools.enchant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemCarrotOnAStick;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;

/**
 * @author Seigneur Necron
 */
public class EnchantmentTools {
	
	// Constants :
	
	/**
	 * The number of bookcases required to use the enchantment table.
	 */
	public static final int MIN_NB_BOOKS = 18;
	
	// Constructors :
	
	private EnchantmentTools() {
		// This class don't have to be instanciated, it only contains static methods.
	}
	
	// Methods :
	
	/**
	 * Returns the list of applicable enchantments for an item.
	 * @param itemStack - the item that we want to know the possible enchantments.
	 * @return the list of applicable enchantments.
	 */
	public static List<Enchantment> getPossibleEnchantments(ItemStack itemStack) {
		LinkedList<Enchantment> result = new LinkedList<Enchantment>();
		Item item = itemStack.getItem();
		
		// If item is an armor.
		if(item instanceof ItemArmor) {
			ItemArmor itemArmor = (ItemArmor) item;
			
			result.add(Enchantment.protection);
			result.add(Enchantment.fireProtection);
			result.add(Enchantment.blastProtection);
			result.add(Enchantment.projectileProtection);
			result.add(Enchantment.thorns);
			
			// If item is an helmet.
			if(itemArmor.armorType == 0) {
				result.add(Enchantment.respiration);
				result.add(Enchantment.aquaAffinity);
			}
			// If item is boots.
			else if(itemArmor.armorType == 3) {
				result.add(Enchantment.featherFalling);
			}
			
			// If item is not unbreakable.
			if(itemArmor.getArmorMaterial() != StargateMod.naquadahArmorMaterial) {
				result.add(Enchantment.unbreaking);
			}
		}
		// If item is a bow.
		else if(item instanceof ItemBow) {
			result.add(Enchantment.power);
			result.add(Enchantment.punch);
			result.add(Enchantment.flame);
			result.add(Enchantment.infinity);
			
			// If item is not unbreakable.
			if(item != StargateMod.item_naquadahBow) {
				result.add(Enchantment.unbreaking);
			}
		}
		// If item is a sword.
		else if(item instanceof ItemSword) {
			result.add(Enchantment.sharpness);
			result.add(Enchantment.smite);
			result.add(Enchantment.baneOfArthropods);
			result.add(Enchantment.knockback);
			result.add(Enchantment.fireAspect);
			result.add(Enchantment.looting);
			
			// If item is not unbreakable.
			if(item != StargateMod.item_naquadahSword) {
				result.add(Enchantment.unbreaking);
			}
		}
		// If item is an axe.
		else if(item instanceof ItemAxe) {
			result.add(Enchantment.sharpness);
			result.add(Enchantment.smite);
			result.add(Enchantment.baneOfArthropods);
			result.add(Enchantment.knockback);
			result.add(Enchantment.fireAspect);
			result.add(Enchantment.looting);
			result.add(Enchantment.efficiency);
			result.add(Enchantment.silkTouch);
			result.add(Enchantment.fortune);
			
			// If item is not unbreakable.
			if(item != StargateMod.item_naquadahAxe) {
				result.add(Enchantment.unbreaking);
			}
		}
		// If item is a pickaxe or a shovel.
		else if(item instanceof ItemPickaxe || item instanceof ItemSpade) {
			ItemTool itemTool = (ItemTool) item;
			
			result.add(Enchantment.efficiency);
			result.add(Enchantment.silkTouch);
			result.add(Enchantment.fortune);
			
			// If item is not unbreakable.
			if(itemTool.getToolMaterialName() != StargateMod.naquadahMaterialName) {
				result.add(Enchantment.unbreaking);
			}
		}
		// If item is shears.
		else if(item instanceof ItemShears) {
			result.add(Enchantment.efficiency);
			result.add(Enchantment.silkTouch);
			
			// If item is not unbreakable.
			if(item != StargateMod.item_naquadahShears) {
				result.add(Enchantment.unbreaking);
			}
		}
		// If item is a hoe, a lighter, a fishing rod or a carrot on a stick, and item is not unbreakable.
		else if((item instanceof ItemHoe && item != StargateMod.item_naquadahHoe) || (item instanceof ItemFlintAndSteel && item != StargateMod.item_naquadahLighter) || (item instanceof ItemFishingRod && item != StargateMod.item_naquadahFishingRod) || item instanceof ItemCarrotOnAStick) {
			result.add(Enchantment.unbreaking);
		}
		
		return result;
	}
	
	/**
	 * Returns the list of the enchantments that are already applied on an item, with their levels.
	 * @param itemStack - the item that we want to know the applied enchantments.
	 * @return a map of the enchantments that are already applied on an item and their levels.
	 */
	@SuppressWarnings("unchecked")
	public static Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
		Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
		
		for(Entry<Integer, Integer> entry : map.entrySet()) {
			int id = entry.getKey();
			if(id >= 0 && id < Enchantment.enchantmentsList.length) {
				enchantments.put(Enchantment.enchantmentsList[id], entry.getValue());
			}
		}
		
		return enchantments;
	}
	
	/**
	 * Returns the list of the enchantments that can be added to an item, with thier levels and costs.
	 * @param itemStack - the item that we want to enchant.
	 * @return the list of the enchantments that can be added to an item, with thier levels and costs.
	 */
	public static LinkedList<PowerUp> getAvailableEnchantments(ItemStack itemStack) {
		LinkedList<PowerUp> available = new LinkedList<PowerUp>();
		
		if(itemStack != null) {
			Map<Enchantment, Integer> current = getEnchantments(itemStack);
			List<Enchantment> possible = getPossibleEnchantments(itemStack);
			
			// For each possible enchantment.
			for(Enchantment enchantment : possible) {
				boolean ok = true;
				int level = 1;
				int cost = 0;
				
				// For each current enchantment.
				for(Entry<Enchantment, Integer> entry : current.entrySet()) {
					cost += entry.getValue();
					
					// If enchantment already applied.
					if(entry.getKey() == enchantment) {
						// If enchantment not at max level.
						if(entry.getValue() < enchantment.getMaxLevel()) {
							level = entry.getValue() + 1;
						}
						// If enchantment already at max level.
						else {
							ok = false;
							break;
						}
					}
					// If enchantment can't be applied together.
					else if(!enchantment.canApplyTogether(entry.getKey())) {
						ok = false;
						break;
					}
				}
				
				// If enchantment finally available.
				if(ok) {
					cost += level * 5;
					available.add(new PowerUp(enchantment, level, cost));
				}
			}
		}
		
		return available;
	}
	
	public static boolean canPayEnchantCost(EntityPlayer player, PowerUp powerUp) {
		return (powerUp != null) && (player.capabilities.isCreativeMode || player.experienceLevel >= powerUp.cost);
	}
	
	public static boolean isThereEnoughtBookCase(EntityPlayer player, int nbBooks) {
		return player.capabilities.isCreativeMode || nbBooks >= MIN_NB_BOOKS;
	}
	
	public static boolean hasEnoughtSoul(EntityPlayer player, SoulCount soulCount) {
		if(soulCount == null) {
			return false;
		}
		
		ItemSoulCrystalFull crystal = ItemSoulCrystalFull.getCrystalFromMonsterId(soulCount.id);
		return (crystal != null) && (player.capabilities.isCreativeMode || soulCount.count >= crystal.neededSouls);
	}
	
}

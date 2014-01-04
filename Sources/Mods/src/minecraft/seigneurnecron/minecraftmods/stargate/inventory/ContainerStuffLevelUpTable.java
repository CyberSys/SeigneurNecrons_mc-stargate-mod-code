package seigneurnecron.minecraftmods.stargate.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
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
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ContainerStuffLevelUpTable extends ContainerOneLine<InventoryStuffLevelUpTable> {
	
	/**
	 * The number of bookcases required to use the enchantment table.
	 */
	public static final int MIN_NB_BOOKS = 18;
	
	/**
	 * The number of nearby bookcases.
	 */
	private int nbBooks = 0;
	
	/**
	 * The list of applicable enchantments, with thier levels and costs.
	 */
	private LinkedList<PowerUp> enchantments = new LinkedList<PowerUp>();
	
	public ContainerStuffLevelUpTable(InventoryPlayer inventoryPlayer, InventoryStuffLevelUpTable inventory) {
		super(inventoryPlayer, inventory);
		this.inventory.container = this;
		this.onCraftMatrixChanged(this.inventory);
	}
	
	/**
	 * Returns the number of nearby bookcases.
	 * @return the number of nearby bookcases.
	 */
	public int getNbBooks() {
		return this.nbBooks;
	}
	
	/**
	 * Returns the list of applicable enchantments, with thier levels and costs.
	 * @return the list of applicable enchantments, with thier levels and costs.
	 */
	public LinkedList<PowerUp> getEnchantments() {
		return this.enchantments;
	}
	
	/**
	 * Updates the number of nearby bookcases.
	 * @param nbBooks - the number of nearby bookcases.
	 */
	private void setNbBooks(int nbBooks) {
		this.nbBooks = nbBooks;
	}
	
	/**
	 * Updates the list of applicable enchantments, with thier levels and costs.
	 * @param enchantments - the list of applicable enchantments, with thier levels and costs.
	 */
	private void setEnchantments(LinkedList<PowerUp> enchantments) {
		Collections.sort(enchantments);
		this.enchantments = enchantments;
	}
	
	/**
	 * Returns the list of applicable enchantments for an item.
	 * @param itemStack - the item that we want to know the possible enchantments.
	 * @return the list of applicable enchantments.
	 */
	private static List<Enchantment> getPossibleEnchantments(ItemStack itemStack) {
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
	private static Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
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
	private static LinkedList<PowerUp> getAvailableEnchantments(ItemStack itemStack) {
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
	
	@Override
	public void onContainerClosed(EntityPlayer entityPlayer) {
		super.onContainerClosed(entityPlayer);
		
		if(!this.inventory.getTileEntity().worldObj.isRemote) {
			ItemStack itemStack = this.inventory.getStackInSlotOnClosing(0);
			
			if(itemStack != null) {
				entityPlayer.dropPlayerItem(itemStack);
			}
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafter) {
		super.addCraftingToCrafters(crafter);
		crafter.sendProgressBarUpdate(this, 0, this.getNbBooks());
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); ++i) {
			ICrafting crafter = (ICrafting) this.crafters.get(i);
			crafter.sendProgressBarUpdate(this, 0, this.getNbBooks());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int index, int value) {
		if(index == 0) {
			this.setNbBooks(value);
		}
		else {
			super.updateProgressBar(index, value);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean enchantItem(EntityPlayer entityPlayer, int index) {
		ItemStack itemStack = this.inventory.getStackInSlot(0);
		
		if(itemStack != null && index >= 0 && index < this.getEnchantments().size()) {
			PowerUp powerUp = this.getEnchantments().get(index);
			
			if(entityPlayer.capabilities.isCreativeMode || (entityPlayer.experienceLevel >= powerUp.cost && this.getNbBooks() >= MIN_NB_BOOKS)) {
				if(!this.inventory.getTileEntity().worldObj.isRemote) {
					entityPlayer.addExperienceLevel(-powerUp.cost);
					
					Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
					enchantments.put(powerUp.enchant.effectId, powerUp.level);
					EnchantmentHelper.setEnchantments(enchantments, itemStack);
					
					this.onCraftMatrixChanged(this.inventory);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if(inventory == this.inventory) {
			TileEntityConsoleBase tileEntity = this.inventory.getTileEntity();
			
			World world = tileEntity.worldObj;
			
			if(!world.isRemote) {
				int x = tileEntity.xCoord;
				int y = tileEntity.yCoord;
				int z = tileEntity.zCoord;
				ItemStack itemStack = inventory.getStackInSlot(0);
				int nbBooks = 0;
				
				for(int i = -2; i <= 2; i++) {
					for(int j = -2; j <= 2; j++) {
						if((Math.abs(i) == 2) ^ (Math.abs(j) == 2)) {
							for(int k = 0; k <= 1; k++) {
								if(world.getBlockId(x + i, y + k, z + j) == Block.bookShelf.blockID && world.isAirBlock(x + ((Math.abs(i) == 2) ? (i / 2) : i), y + k, z + ((Math.abs(j) == 2) ? (j / 2) : j))) {
									nbBooks++;
								}
							}
						}
					}
				}
				
				this.setNbBooks(nbBooks);
				this.setEnchantments(getAvailableEnchantments(itemStack));
				this.updateEnchantments();
				
				this.detectAndSendChanges();
			}
		}
	}
	
	public void updateEnchantments() {
		if(this.inventory.getTileEntity().worldObj.isRemote) {
			ItemStack itemStack = this.inventory.getStackInSlot(1);
			LinkedList<PowerUp> enchantments = new LinkedList<PowerUp>();
			
			if(itemStack != null) {
				Map<Enchantment, Integer> map = getEnchantments(itemStack);
				
				for(Entry<Enchantment, Integer> entry : map.entrySet()) {
					enchantments.add(new PowerUp(entry.getKey(), entry.getValue(), 0));
				}
			}
			
			this.setEnchantments(enchantments);
		}
		else {
			ItemStack itemStack = new ItemStack(Item.enchantedBook);
			Map<Integer, Integer> enchantments = new HashMap<Integer, Integer>();
			
			for(PowerUp powerUp : this.getEnchantments()) {
				enchantments.put(powerUp.enchant.effectId, powerUp.level);
			}
			
			EnchantmentHelper.setEnchantments(enchantments, itemStack);
			this.inventory.setInventorySlotContents(1, itemStack);
		}
	}
	
	public int getCurrentItemEnchantLevelSum() {
		int sum = 0;
		
		ItemStack itemStack = this.inventory.getStackInSlot(0);
		
		if(itemStack != null) {
			Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
			
			for(int level : enchantments.values()) {
				sum += level;
			}
		}
		
		return sum;
	}
	
}

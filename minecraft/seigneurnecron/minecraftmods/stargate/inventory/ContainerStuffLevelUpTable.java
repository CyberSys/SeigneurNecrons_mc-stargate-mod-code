package seigneurnecron.minecraftmods.stargate.inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;

public class ContainerStuffLevelUpTable extends ContainerStargate<TileEntityStuffLevelUpTable> {
	
	public static void test() {
		LinkedList<ItemStack> items = new LinkedList<ItemStack>();
		items.add(new ItemStack(Item.swordDiamond));
		items.add(new ItemStack(Item.axeDiamond));
		items.add(new ItemStack(Item.pickaxeDiamond));
		items.add(new ItemStack(Item.shovelDiamond));
		items.add(new ItemStack(Item.hoeDiamond));
		items.add(new ItemStack(Item.helmetDiamond));
		items.add(new ItemStack(Item.plateDiamond));
		items.add(new ItemStack(Item.legsDiamond));
		items.add(new ItemStack(Item.bootsDiamond));
		items.add(new ItemStack(Item.bow));
		items.add(new ItemStack(Item.shears));
		items.add(new ItemStack(Item.flintAndSteel));
		items.add(new ItemStack(Item.fishingRod));
		items.add(new ItemStack(Item.carrotOnAStick));
		
		StargateMod.debug("#############################################################", true);
		for(ItemStack item : items) {
			StargateMod.debug(item.getItemName() + " : ", false);
			for(Enchantment enchantment : getPossibleEnchantments(item)) {
				StargateMod.debug(enchantment.getName() + "; ", false);
			}
			StargateMod.debug("", true);
		}
		StargateMod.debug("#############################################################", true);
	}
	
	public ContainerStuffLevelUpTable(InventoryPlayer inventoryPlayer, TileEntityStuffLevelUpTable tileEntity) {
		super(inventoryPlayer, tileEntity);
	}
	
	private boolean isItemEnchantable(ItemStack itemStack) {
		return itemStack != null; // FIXME - a completer.
	}
	
	private static Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
		HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
		
		// FIXME - trouver le meilleur moyen de recuperer la liste des enchantements qui sont deja sur l'item.
		Map<Integer, Integer> pwet = EnchantmentHelper.getEnchantments(itemStack);
		
		return enchantments;
	}
	
	/**
	 * Retourne la liste de tous les enchantements pouvant etre mis sur un item.
	 * @param itemStack - l'item dont on veut connaitre les enchantements possibles.
	 * @return la liste des enchantements possibles.
	 */
	private static List<Enchantment> getPossibleEnchantments(ItemStack itemStack) {
		LinkedList<Enchantment> result = new LinkedList<Enchantment>();
		Item item = itemStack.getItem();
		
		if(item instanceof ItemArmor) {
			ItemArmor itemArmor = (ItemArmor) item;
			
			result.add(Enchantment.protection);
			result.add(Enchantment.fireProtection);
			result.add(Enchantment.blastProtection);
			result.add(Enchantment.projectileProtection);
			result.add(Enchantment.field_92039_k); // Thorns
			result.add(Enchantment.unbreaking);
			
			// If item is an helmet.
			if(itemArmor.armorType == 0) {
				result.add(Enchantment.respiration);
				result.add(Enchantment.aquaAffinity);
			}
			// If item is boots.
			else if(itemArmor.armorType == 3) {
				result.add(Enchantment.featherFalling);
			}
		}
		
		// If item is a sword or an axe.
		if(item instanceof ItemSword || item instanceof ItemAxe) {
			// FIXME - la suite.
		}
		
		// FIXME - ne pas proposer unbreaking pour les outils en naquadah.
		
		return result;
	}
	
	@Override
	protected void init() {
		this.addSlotToContainer(new Slot(this.tileEntity, 0, 25, 47));
	}
	
	@Override
	public void onCraftGuiClosed(EntityPlayer entityPlayer) {
		super.onCraftGuiClosed(entityPlayer);
		
		if(!this.tileEntity.worldObj.isRemote) {
			ItemStack itemStack = this.tileEntity.getStackInSlotOnClosing(0);
			
			if(itemStack != null) {
				entityPlayer.dropPlayerItem(itemStack);
			}
		}
	}
	
	//	@Override
	//	public boolean enchantItem(EntityPlayer entityPlayer, int index) {
	//		ItemStack itemStack = this.tileEntity.getStackInSlot(0);
	//		itemStack.;
	//		
	//		if(this.enchantLevels[index] > 0 && itemStack != null && (entityPlayer.experienceLevel >= this.enchantLevels[index] || entityPlayer.capabilities.isCreativeMode)) {
	//			if(!this.tileEntity.worldObj.isRemote) {
	//				List var4 = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, this.enchantLevels[index]);
	//				boolean var5 = itemStack.itemID == Item.book.itemID;
	//				
	//				if(var4 != null) {
	//					entityPlayer.addExperienceLevel(-this.enchantLevels[index]);
	//					
	//					if(var5) {
	//						itemStack.itemID = Item.field_92053_bW.itemID;
	//					}
	//					
	//					int var6 = var5 ? this.rand.nextInt(var4.size()) : -1;
	//					
	//					for(int var7 = 0; var7 < var4.size(); ++var7) {
	//						EnchantmentData var8 = (EnchantmentData) var4.get(var7);
	//						
	//						if(!var5 || var7 == var6) {
	//							if(var5) {
	//								Item.field_92053_bW.func_92060_a(itemStack, var8);
	//							}
	//							else {
	//								itemStack.addEnchantment(var8.enchantmentobj, var8.enchantmentLevel);
	//							}
	//						}
	//					}
	//					
	//					this.onCraftMatrixChanged(this.tileEntity);
	//				}
	//			}
	//			
	//			return true;
	//		}
	//		else {
	//			return false;
	//		}
	//	}
	
	@Override
	public void onCraftMatrixChanged(IInventory iInventory) {
		if(iInventory == this.tileEntity) {
			World world = this.tileEntity.worldObj;
			int x = this.tileEntity.xCoord;
			int y = this.tileEntity.yCoord;
			int z = this.tileEntity.zCoord;
			
			ItemStack itemStack = iInventory.getStackInSlot(0);
			
			if(this.isItemEnchantable(itemStack)) {
				if(!world.isRemote) {
					int nbBook = 0;
					
					for(int i = -2; i <= 2; i++) {
						for(int j = -2; j <= 2; j++) {
							if((Math.abs(i) == 2) ^ (Math.abs(j) == 2)) {
								for(int k = 0; k <= 1; k++) {
									if(world.getBlockId(x + i, y + k, z + j) == Block.bookShelf.blockID && world.isAirBlock(x + (i / i), y + k, z + (j / j))) {
										nbBook++;
									}
								}
							}
						}
					}
					
					// FIXME - a terminer.
					
					for(int i = 0; i < 3; ++i) {
						//this.enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var4, nbBook, itemStack);
					}
					
					this.detectAndSendChanges();
				}
			}
			else {
				for(int i = 0; i < 3; ++i) {
					//this.enchantLevels[i] = 0;
				}
			}
		}
	}
	
}

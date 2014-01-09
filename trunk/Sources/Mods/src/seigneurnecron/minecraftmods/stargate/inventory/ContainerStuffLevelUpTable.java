package seigneurnecron.minecraftmods.stargate.inventory;

import static seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools.MIN_NB_BOOKS;

import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;

/**
 * @author Seigneur Necron
 */
public class ContainerStuffLevelUpTable extends ContainerConsolePanel<InventoryStuffLevelUpTable> {
	
	// Constructors :
	
	public ContainerStuffLevelUpTable(EntityPlayer player, InventoryStuffLevelUpTable inventory) {
		super(player, inventory);
		this.inventory.console.updateEnchantInfo();
	}
	
	// Methods :
	
	@Override
	protected Slot getNewSlot(int index, int xPos, int yPos) {
		return new Slot(this.inventory, index, xPos, yPos);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean enchantItem(EntityPlayer entityPlayer, int index) {
		ItemStack itemStack = this.inventory.getStackInSlot(0);
		
		if(itemStack != null && index >= 0 && index < this.inventory.console.getEnchantments().size()) {
			PowerUp powerUp = this.inventory.console.getEnchantments().get(index);
			
			if(entityPlayer.capabilities.isCreativeMode || (entityPlayer.experienceLevel >= powerUp.cost && this.inventory.console.getNbBooks() >= MIN_NB_BOOKS)) {
				if(!this.inventory.tileEntity.worldObj.isRemote) {
					entityPlayer.addExperienceLevel(-powerUp.cost);
					
					Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
					enchantments.put(powerUp.enchant.effectId, powerUp.level);
					EnchantmentHelper.setEnchantments(enchantments, itemStack);
					
					this.inventory.console.updateEnchantInfo();
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer entityPlayer) {
		super.onContainerClosed(entityPlayer);
		
		if(!this.inventory.tileEntity.worldObj.isRemote) {
			ItemStack itemStack = this.inventory.getStackInSlotOnClosing(0);
			
			if(itemStack != null) {
				entityPlayer.dropPlayerItem(itemStack);
			}
		}
	}
	
}

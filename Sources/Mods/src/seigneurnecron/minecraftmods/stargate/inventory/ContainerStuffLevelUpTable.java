package seigneurnecron.minecraftmods.stargate.inventory;

import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
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
	@SuppressWarnings("unchecked")
	public boolean enchantItem(EntityPlayer player, int index) {
		ItemStack itemStack = this.inventory.getStuff();
		
		if(itemStack != null && index >= 0 && index < this.inventory.console.getEnchantments().size()) {
			PowerUp powerUp = this.inventory.console.getEnchantments().get(index);
			
			if(EnchantmentTools.isThereEnoughtBookCase(player, this.inventory.console.getNbBooks()) && EnchantmentTools.canPayEnchantCost(player, powerUp)) {
				if(!this.inventory.tileEntity.worldObj.isRemote) {
					if(!player.capabilities.isCreativeMode) {
						player.addExperienceLevel(-powerUp.cost);
					}
					
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
	
	//	@Override
	//	public void onContainerClosed(EntityPlayer entityPlayer) {
	//		super.onContainerClosed(entityPlayer);
	//		
	//		if(!this.inventory.tileEntity.worldObj.isRemote) {
	//			ItemStack itemStack = this.inventory.getStackInSlotOnClosing(0);
	//			
	//			if(itemStack != null) {
	//				entityPlayer.dropPlayerItem(itemStack);
	//			}
	//		}
	//	}
	
}

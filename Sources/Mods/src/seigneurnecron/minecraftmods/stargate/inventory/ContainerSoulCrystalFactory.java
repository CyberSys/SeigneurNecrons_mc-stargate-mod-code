package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerSoulCountData;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ContainerSoulCrystalFactory extends ContainerConsolePanel<InventorySoulCrystalFactory> {
	
	// Constructors :
	
	public ContainerSoulCrystalFactory(EntityPlayer player, InventorySoulCrystalFactory inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	public boolean enchantItem(EntityPlayer player, int index) {
		if(this.inventory.isCrystalValid()) {
			PlayerSoulCountData playerData = PlayerSoulCountData.get(player);
			SoulCount soulCount = null;
			
			for(SoulCount soul : playerData.getDataList()) {
				if(soul.id == index) {
					soulCount = soul;
					break;
				}
			}
			
			if(soulCount != null) {
				ItemSoulCrystalFull crystal = ItemSoulCrystalFull.getCrystalFromMonsterId(soulCount.id);
				
				if(crystal != null) {
					boolean creativeMode = player.capabilities.isCreativeMode;
					boolean hasEnoughtSoul = soulCount.count >= crystal.neededSouls;
					
					if(creativeMode || hasEnoughtSoul) {
						if(!this.inventory.tileEntity.worldObj.isRemote) {
							this.inventory.setCrystal(new ItemStack(crystal));
							
							if(!creativeMode) {
								soulCount.id -= crystal.neededSouls;
								playerData.syncProperties();
							}
						}
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}

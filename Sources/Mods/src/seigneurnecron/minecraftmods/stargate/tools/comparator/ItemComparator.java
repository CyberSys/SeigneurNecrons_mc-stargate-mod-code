package seigneurnecron.minecraftmods.stargate.tools.comparator;

import java.util.Comparator;

import net.minecraft.item.Item;

/**
 * @author Seigneur Necron
 */
public class ItemComparator implements Comparator<Item> {
	
	@Override
	public int compare(Item item1, Item item2) {
		if(item1 == null) {
			if(item2 == null) {
				return 0;
			}
			else {
				return -1;
			}
		}
		else {
			if(item2 == null) {
				return 1;
			}
			else {
				return item1.getUnlocalizedName().compareTo(item2.getUnlocalizedName());
			}
		}
	}
	
}

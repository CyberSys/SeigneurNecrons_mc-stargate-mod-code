package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemEmptySoulCrystal extends ItemStargate {
	
	public ItemEmptySoulCrystal(int id, int iconIdex, String name) {
		super(id, iconIdex, name);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityLiving entity) {
		World world = entity.worldObj;
		if(!world.isRemote) {
			if(entity.getHealth() <= 20) {
				int monsterId = EntityList.getEntityID(entity);
				ItemSoulCrystal crystal = ItemSoulCrystal.getCrystalFromMonsterId(monsterId);
				
				if(crystal != null) {
					ItemStack stack = new ItemStack(crystal);
					EntityItem drop = new EntityItem(world, entity.posX, entity.posY, entity.posZ, stack);
					drop.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(drop);
					entity.attackEntityFrom(DamageSource.causePlayerDamage(null), 25);
					itemstack.stackSize--;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
}

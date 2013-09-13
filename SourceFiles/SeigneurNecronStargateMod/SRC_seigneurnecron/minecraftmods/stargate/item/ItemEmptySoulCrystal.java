package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * @author Seigneur Necron
 */
public class ItemEmptySoulCrystal extends ItemStargate {
	
	private static final int MAX_HEALTH = 10;
	
	public ItemEmptySoulCrystal(String name) {
		super(name);
	}
	
	// itemInteractionForEntity(ItemStack, EntityPlayer, EntityLivingBase)
	@Override
	public boolean func_111207_a(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity) {
		World world = entity.worldObj;
		if(!world.isRemote) {
			if(entity.func_110143_aJ() <= MAX_HEALTH) { // getHealth()
				int monsterId = EntityList.getEntityID(entity);
				ItemSoulCrystal crystal = ItemSoulCrystal.getCrystalFromMonsterId(monsterId);
				
				if(crystal != null) {
					ItemStack stack = new ItemStack(crystal);
					EntityItem drop = new EntityItem(world, entity.posX, entity.posY, entity.posZ, stack);
					drop.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(drop);
					entity.attackEntityFrom(DamageSource.causePlayerDamage(null), MAX_HEALTH);
					itemstack.stackSize--;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
}

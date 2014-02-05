package seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballBasic;

/**
 * @author Seigneur Necron
 */
public class DispenserBehaviorFireballBasic extends BehaviorDefaultDispenseItem {
	
	// Methods :
	
	@Override
	public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
		World world = iBlockSource.getWorld();
		world.spawnEntityInWorld(getProjectile(world, iBlockSource));
		itemStack.splitStack(1);
		return itemStack;
	}
	
	protected Entity getProjectile(World world, IBlockSource iBlockSource) {
		return new EntityFireballBasic(world, iBlockSource);
	}
	
	@Override
	protected void playDispenseSound(IBlockSource iBlockSource) {
		iBlockSource.getWorld().playAuxSFX(1009, iBlockSource.getXInt(), iBlockSource.getYInt(), iBlockSource.getZInt(), 0);
	}
	
}

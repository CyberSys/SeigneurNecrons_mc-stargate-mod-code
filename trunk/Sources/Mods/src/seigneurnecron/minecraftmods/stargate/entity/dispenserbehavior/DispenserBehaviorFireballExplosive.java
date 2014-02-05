package seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballExplosive;

/**
 * @author Seigneur Necron
 */
public class DispenserBehaviorFireballExplosive extends DispenserBehaviorFireballBasic {
	
	// Methods :
	
	@Override
	protected Entity getProjectile(World world, IBlockSource iBlockSource) {
		return new EntityFireballExplosive(world, iBlockSource);
	}
	
}

package seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireBallBoosted;

/**
 * @author Seigneur Necron
 */
public class DispenserBehaviorFireballBoosted extends DispenserBehaviorFireballBasic {
	
	// Methods :
	
	@Override
	protected Entity getProjectile(World world, IBlockSource iBlockSource) {
		return new EntityFireBallBoosted(world, iBlockSource);
	}
	
}

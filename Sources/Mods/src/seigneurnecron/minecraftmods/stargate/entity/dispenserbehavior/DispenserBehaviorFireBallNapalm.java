package seigneurnecron.minecraftmods.stargate.entity.dispenserbehavior;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballNapalm;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class DispenserBehaviorFireBallNapalm extends DispenserBehaviorFireballBasic {
	
	// Methods :
	
	@Override
	protected Entity getProjectile(World world, IBlockSource iBlockSource) {
		return new EntityFireballNapalm(world, iBlockSource);
	}
	
}

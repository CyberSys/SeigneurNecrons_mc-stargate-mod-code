package seigneurnecron.minecraftmods.stargate.item.fireball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireBallBoosted;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ItemFireballBoosted extends ItemFireballBasic {
	
	// Constructors :
	
	public ItemFireballBoosted(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityFireBallBoosted(world, entityPlayer);
	}
	
}

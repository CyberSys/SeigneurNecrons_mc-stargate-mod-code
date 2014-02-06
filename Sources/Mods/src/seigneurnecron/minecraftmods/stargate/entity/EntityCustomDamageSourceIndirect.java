package seigneurnecron.minecraftmods.stargate.entity;

import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballBasic;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class EntityCustomDamageSourceIndirect extends EntityDamageSourceIndirect {
	
	// Constants :
	
	public static final String ON_FIRE = "onFire";
	
	// Constructors :
	
	public EntityCustomDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity) {
		super(par1Str, par2Entity, par3Entity);
	}
	
	// Methods :
	
	public static DamageSource causeFireballDamage(EntityFireballBasic entityCustomFireBall, Entity entity) {
		Entity shootingEntity = (entity == null) ? entityCustomFireBall : entity;
		return (new EntityCustomDamageSourceIndirect(ON_FIRE, entityCustomFireBall, shootingEntity)).setFireDamage().setProjectile();
	}
	
}

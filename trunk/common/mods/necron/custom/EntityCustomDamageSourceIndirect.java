package mods.necron.custom;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityDamageSourceIndirect;

public class EntityCustomDamageSourceIndirect extends EntityDamageSourceIndirect {
	
	public EntityCustomDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity) {
		super(par1Str, par2Entity, par3Entity);
	}
	
	/**
	 * returns EntityDamageSourceIndirect of a fireball
	 */
	public static DamageSource causeFireballDamage(EntityCustomFireBall entityCustomFireBall, Entity entity) {
		Entity shootingEntity = (entity == null) ? entityCustomFireBall : entity;
		return (new EntityCustomDamageSourceIndirect("onFire", entityCustomFireBall, shootingEntity)).setFireDamage().setProjectile();
	}
	
	/**
	 * Define the damage type as fire based.
	 */
	protected DamageSource setFireDamage() {
		return super.setFireDamage();
	}
	
}

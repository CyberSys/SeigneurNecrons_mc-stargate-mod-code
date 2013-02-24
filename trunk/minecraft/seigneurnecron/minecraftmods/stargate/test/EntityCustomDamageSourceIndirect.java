package seigneurnecron.minecraftmods.stargate.test;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class EntityCustomDamageSourceIndirect extends EntityDamageSourceIndirect {
	
	public EntityCustomDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity) {
		super(par1Str, par2Entity, par3Entity);
	}
	
	public static DamageSource causeFireballDamage(EntityCustomFireBall entityCustomFireBall, Entity entity) {
		Entity shootingEntity = (entity == null) ? entityCustomFireBall : entity;
		return (new EntityCustomDamageSourceIndirect("onFire", entityCustomFireBall, shootingEntity)).setFireDamage().setProjectile();
	}
	
	@Override
	protected DamageSource setFireDamage() {
		return super.setFireDamage();
	}
	
}

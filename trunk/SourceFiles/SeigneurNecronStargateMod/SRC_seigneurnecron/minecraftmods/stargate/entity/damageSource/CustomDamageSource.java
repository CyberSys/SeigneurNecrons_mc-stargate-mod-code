package seigneurnecron.minecraftmods.stargate.entity.damageSource;

import net.minecraft.util.DamageSource;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class CustomDamageSource extends DamageSource {
	
	public static DamageSource kawoosh = (new CustomDamageSource(StargateMod.MOD_ID + ".kawoosh")).setDamageBypassesArmor();
	public static DamageSource iris = (new CustomDamageSource(StargateMod.MOD_ID + ".iris")).setDamageBypassesArmor();
	
	protected CustomDamageSource(String name) {
		super(name);
	}
	
}

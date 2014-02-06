package seigneurnecron.minecraftmods.stargate.entity.damagesource;

import net.minecraft.util.DamageSource;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class CustomDamageSource extends DamageSource {
	
	// Constants :
	
	public static final DamageSource KAWOOSH = (new CustomDamageSource(StargateMod.MOD_ID + ".kawoosh")).setDamageBypassesArmor();
	public static final DamageSource IRIS = (new CustomDamageSource(StargateMod.MOD_ID + ".iris")).setDamageBypassesArmor();
	
	// Constructors :
	
	protected CustomDamageSource(String name) {
		super(name);
	}
	
}

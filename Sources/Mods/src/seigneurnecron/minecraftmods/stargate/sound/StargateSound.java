package seigneurnecron.minecraftmods.stargate.sound;

import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.sound.Sound;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class StargateSound extends Sound {
	
	// Fields :
	
	private boolean dependsOnGateType;
	
	// Constructors :
	
	public StargateSound(String name, String ext, boolean dependsOnGateType) {
		super(name, ext);
		this.setDependsOnGateType(dependsOnGateType);
	}
	
	public StargateSound(String name, boolean dependsOnGateType) {
		this(name, "wav", dependsOnGateType);
	}
	
	public StargateSound(String name, String ext) {
		this(name, ext, false);
	}
	
	public StargateSound(String name) {
		this(name, "wav", false);
	}
	
	// Getters :
	
	public boolean isDependsOnGateType() {
		return this.dependsOnGateType;
	}
	
	// Setters :
	
	protected void setDependsOnGateType(boolean dependsOnGateType) {
		this.dependsOnGateType = dependsOnGateType;
	}
	
	// Methods :
	
	@Override
	public String toString() {
		if(this.dependsOnGateType) {
			return super.toString() + (StargateMod.instance.getConfig().atlantisSounds ? "_pegasus" : "_milkyway");
		}
		else {
			return super.toString();
		}
	}
	
	@Override
	protected ModBase getMod() {
		return StargateMod.instance;
	}
	
}

package seigneurnecron.minecraftmods.stargate.client.sound;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;

/**
 * @author Seigneur Necron
 */
public class Sound {
	
	// Fields :
	
	private String name;
	private String ext;
	private boolean dependsOnGateType;
	
	// Constructors :
	
	public Sound(String name) {
		this(name, "wav", false);
	}
	
	public Sound(String name, String ext) {
		this(name, ext, false);
	}
	
	public Sound(String name, boolean dependsOnGateType) {
		this(name, "wav", dependsOnGateType);
	}
	
	public Sound(String name, String ext, boolean dependsOnGateType) {
		this.setName(name);
		this.setExt(ext);
		this.setDependsOnGateType(dependsOnGateType);
	}
	
	// Getters :
	
	public String getName() {
		return this.name;
	}
	
	public String getExt() {
		return this.ext;
	}
	
	public boolean isDependsOnGateType() {
		return this.dependsOnGateType;
	}
	
	// Setters :
	
	protected void setName(String name) {
		this.name = name;
	}
	
	protected void setExt(String ext) {
		this.ext = ext;
	}
	
	protected void setDependsOnGateType(boolean dependsOnGateType) {
		this.dependsOnGateType = dependsOnGateType;
	}
	
	// Methods :
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(StargateMod.ASSETS_PREFIX);
		builder.append(this.name);
		
		if(this.dependsOnGateType) {
			builder.append(StargateModConfig.atlantisSounds ? "_pegasus" : "_milkyway");
		}
		
		return builder.toString();
	}
	
	public String getFileName() {
		return this.toString() + "." + this.getExt();
	}
	
}

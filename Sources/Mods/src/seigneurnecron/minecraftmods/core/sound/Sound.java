package seigneurnecron.minecraftmods.core.sound;

import seigneurnecron.minecraftmods.core.mod.ModBase;

/**
 * @author Seigneur Necron
 */
public abstract class Sound {
	
	// Fields :
	
	private String prefix;
	private String name;
	private String ext;
	
	// Constructors :
	
	public Sound(String name, String ext) {
		this.setPrefix(this.getMod().getAssetPrefix());
		this.setName(name);
		this.setExt(ext);
	}
	
	public Sound(String name) {
		this(name, "wav");
	}
	
	// Getters :
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getExt() {
		return this.ext;
	}
	
	// Setters :
	
	protected void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	protected void setExt(String ext) {
		this.ext = ext;
	}
	
	// Methods :
	
	/**
	 * Returns the string that you need to play a sound.
	 */
	@Override
	public String toString() {
		return this.prefix + this.name;
	}
	
	/**
	 * Return the name of the file for the sound.
	 * @return the name of the file for the sound.
	 */
	public String getFileName() {
		return this.toString() + "." + this.getExt();
	}
	
	/**
	 * You need to know the mod adding this sound to get the file name.
	 * @return the mod adding this sound.
	 */
	protected abstract ModBase getMod();
	
}

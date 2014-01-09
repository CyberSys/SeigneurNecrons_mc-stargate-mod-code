package seigneurnecron.minecraftmods.stargate.block;

import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockShield extends BlockPortal {
	
	// Constructors :
	
	public BlockShield(String name) {
		super(name);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
	}
	
}

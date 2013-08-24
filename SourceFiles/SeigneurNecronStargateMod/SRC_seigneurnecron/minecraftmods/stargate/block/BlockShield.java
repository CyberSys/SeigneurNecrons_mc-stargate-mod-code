package seigneurnecron.minecraftmods.stargate.block;

import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockShield extends BlockPortal {
	
	public BlockShield(String name) {
		super(name);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
	}
	
}

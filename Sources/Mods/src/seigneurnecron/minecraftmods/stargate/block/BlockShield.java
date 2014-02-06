package seigneurnecron.minecraftmods.stargate.block;

import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class BlockShield extends BlockPortal {
	
	// Constructors :
	
	public BlockShield(String name) {
		super(name);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
	}
	
}

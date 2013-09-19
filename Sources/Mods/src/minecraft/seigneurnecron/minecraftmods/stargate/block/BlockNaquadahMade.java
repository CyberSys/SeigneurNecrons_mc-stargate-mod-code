package seigneurnecron.minecraftmods.stargate.block;

import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockNaquadahMade extends BlockStargate {
	
	public BlockNaquadahMade(String name) {
		super(name);
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
	}
	
}

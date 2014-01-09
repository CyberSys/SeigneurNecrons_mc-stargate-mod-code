package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public abstract class BlockContainerNaquadah extends BlockContainerStargate {
	
	// Constructors :
	
	protected BlockContainerNaquadah(String name) {
		super(name, Material.rock);
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
	}
	
}

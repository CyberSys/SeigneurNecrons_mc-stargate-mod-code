package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class BlockStargateSolidPart extends BlockStargatePart {
	
	// Constructors :
	
	protected BlockStargateSolidPart(String name) {
		super(name, Material.rock);
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
	}
	
	// Methods :
	
	/**
	 * Warns the gate that one of the naquada blocks was destroyed.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		if(!world.isRemote && this.breakGate(world, x, y, z)) {
			TileEntityStargateControl gate = this.getControlUnit(world, x, y, z);
			if(gate != null) {
				gate.setBroken();
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	/**
	 * Indicates if the gate must be broken when the block is destroyed.
	 * @return true if the gate must be broken when the block is destroyed, else false.
	 */
	protected boolean breakGate(World world, int x, int y, int z) {
		return true;
	}
	
}

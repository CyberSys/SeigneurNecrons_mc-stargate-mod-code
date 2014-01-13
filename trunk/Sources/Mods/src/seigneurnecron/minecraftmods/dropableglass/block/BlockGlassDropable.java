package seigneurnecron.minecraftmods.dropableglass.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;

/**
 * @author Seigneur Necron
 */
public class BlockGlassDropable extends BlockGlass {
	
	// Constructors :
	
	public BlockGlassDropable() {
		super(Block.glass.blockID, Block.glass.blockMaterial, false);
		this.setHardness(Block.glass.blockHardness);
		this.setStepSound(Block.glass.stepSound);
		this.setUnlocalizedName(Block.glass.getUnlocalizedName().substring(5));
		this.setTextureName("glass");
	}
	
	// Methods :
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
}

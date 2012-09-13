package mods.stargate;

import java.util.Random;

import net.minecraft.src.BlockOre;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockNaquadaOre extends BlockOre {
	
	public BlockNaquadaOre(int par1, int par2, String name) {
		super(par1, par2);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setBlockName(name);
	}
	
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return StargateMod.itemNaquadaOre.shiftedIndex;
	}
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	/**
	 * Drops the block items with a specified chance of dropping the specified items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		
		if(this.idDropped(par5, par1World.rand, par7) != this.blockID) {
			int xp = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
			this.dropXpOnBlockBreak(par1World, par2, par3, par4, xp);
		}
	}
	
	/**
	 * Grabs the current texture file used for this block.
	 */
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

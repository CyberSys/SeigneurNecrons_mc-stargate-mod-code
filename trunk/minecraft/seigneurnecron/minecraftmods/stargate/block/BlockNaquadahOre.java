package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;

public class BlockNaquadahOre extends BlockOre {
	
	public BlockNaquadahOre(int par1, int par2, String name) {
		super(par1, par2);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setBlockName(name);
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return StargateMod.itemNaquadahOre.itemID;
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		
		if(this.idDropped(par5, par1World.rand, par7) != this.blockID) {
			int xp = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
			this.dropXpOnBlockBreak(par1World, par2, par3, par4, xp);
		}
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

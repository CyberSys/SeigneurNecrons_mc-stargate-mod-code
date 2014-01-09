package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockChevronOn extends BlockChevron {
	
	// Constructors :
	
	public BlockChevronOn(String name) {
		super(name);
		this.setCreativeTab(null);
	}
	
	// Methods :
	
	@Override
	public int idDropped(int par1, Random random, int par3) {
		return StargateMod.block_chevronOff.blockID;
	}
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return StargateMod.block_chevronOff.blockID;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		Block block = blocksList[world.getBlockId(x, y, z)];
		
		if(block != null) {
			if(block == this) {
				return 9;
			}
			
			return block.getLightValue(world, x, y, z);
		}
		
		return lightValue[this.blockID];
	}
	
}

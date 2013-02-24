package net.minecraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * ########## Classe modifiee (1 ligne modifiee) ##########<br />
 * Le verre et les vitres dropent.<br />
 * Ensemble : BlockGlass, BlockPane.
 */
public class BlockGlass extends BlockBreakable {
	
	public BlockGlass(int par1, int par2, Material par3Material, boolean par4) {
		super(par1, par2, par3Material, par4);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random) {
		return 1; /* ########## ligne modifiee ########## */
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass() {
		return 0;
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	/**
	 * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
	 */
	@Override
	protected boolean canSilkHarvest() {
		return true;
	}
}

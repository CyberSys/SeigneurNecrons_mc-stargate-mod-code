package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockNaquadahOre extends BlockOre {
	
	// Constructors :
	
	public BlockNaquadahOre(String name) {
		super(StargateMod.instance.getConfig().getBlockId(name));
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
		this.setUnlocalizedName(name);
		this.func_111022_d(name); // setIconName()
		this.setCreativeTab(StargateMod.stargateBlocksTab);
		StargateMod.instance.registerBlock(this);
	}
	
	// Methods :
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return StargateMod.item_naquadahOre.itemID;
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.func_111023_E()); // getIconName()
	}
	
}

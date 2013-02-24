package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMasterChevron;

public abstract class BlockStargateSolidPart extends BlockStargatePart {
	
	protected BlockStargateSolidPart(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock, name);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	/**
	 * Previent la porte qu'un block la consituant a ete detruit.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		if(!world.isRemote) {
			TileEntityMasterChevron gate = this.getMasterChevron(world, x, y, z);
			if(gate != null) {
				gate.setBroken();
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

package mods.stargate;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public abstract class BlockStargateSolidPart extends BlockStargatePart {
	
	protected BlockStargateSolidPart(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock, name);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	/**
	 * Previent la porte qu'un block la consituant a été détruit.
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
	
	/**
	 * Grabs the current texture file used for this block.
	 */
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

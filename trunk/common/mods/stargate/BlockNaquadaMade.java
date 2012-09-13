package mods.stargate;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;

public class BlockNaquadaMade extends Block {
	
	public BlockNaquadaMade(int id, int textureIndex, String name, CreativeTabs tab) {
		super(id, textureIndex, Material.rock);
		this.setBlockName(name);
		this.setCreativeTab(tab);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
	}
	
	public BlockNaquadaMade(int id, int textureIndex, String name) {
		this(id, textureIndex, name, CreativeTabs.tabBlock);
	}
	
	/**
	 * Grabs the current texture file used for this block.
	 */
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

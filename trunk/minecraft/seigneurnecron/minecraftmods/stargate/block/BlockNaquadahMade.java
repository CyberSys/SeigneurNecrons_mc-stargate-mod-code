package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import seigneurnecron.minecraftmods.stargate.StargateMod;

public class BlockNaquadahMade extends Block {
	
	public BlockNaquadahMade(int id, int textureIndex, String name, CreativeTabs tab) {
		super(id, textureIndex, Material.rock);
		this.setBlockName(name);
		this.setCreativeTab(tab);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
	}
	
	public BlockNaquadahMade(int id, int textureIndex, String name) {
		this(id, textureIndex, name, CreativeTabs.tabBlock);
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

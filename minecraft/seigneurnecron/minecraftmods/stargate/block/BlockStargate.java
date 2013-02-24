package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockStargate extends BlockContainer {
	
	protected BlockStargate(int id, int textureIndex, Material material, String name) {
		super(id, textureIndex, material);
		this.setBlockName(name);
	}
	
}

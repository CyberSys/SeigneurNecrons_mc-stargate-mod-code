package mods.stargate;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.Material;

public abstract class BlockStargate extends BlockContainer {
	
	protected BlockStargate(int id, int textureIndex, Material material, String name) {
		super(id, textureIndex, material);
		this.setBlockName(name);
	}
	
}

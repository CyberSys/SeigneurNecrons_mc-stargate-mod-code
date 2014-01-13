package seigneurnecron.minecraftmods.dropableglass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;

/**
 * @author Seigneur Necron
 */
public class BlockGlassPaneDropable extends BlockPane {
	
	// Constructors :
	
	public BlockGlassPaneDropable() {
		super(Block.thinGlass.blockID, "glass", "glass_pane_top", Block.thinGlass.blockMaterial, true);
		this.setHardness(Block.thinGlass.blockHardness);
		this.setStepSound(Block.thinGlass.stepSound);
		this.setUnlocalizedName(Block.thinGlass.getUnlocalizedName().substring(5));
	}
	
}

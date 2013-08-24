package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockPanelDhd extends BlockPanelStargateConsole {
	
	public BlockPanelDhd(String name) {
		super(name);
	}
	
	@Override
	protected Block baseBlock() {
		return StargateMod.block_dhdBase;
	}
	
}

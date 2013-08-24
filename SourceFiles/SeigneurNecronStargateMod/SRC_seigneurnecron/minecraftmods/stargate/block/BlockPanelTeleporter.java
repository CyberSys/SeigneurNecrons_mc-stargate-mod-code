package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class BlockPanelTeleporter extends BlockPanel {
	
	public BlockPanelTeleporter(String name) {
		super(name);
	}
	
	@Override
	protected Block baseBlock() {
		return StargateMod.block_teleporterBase;
	}
	
}

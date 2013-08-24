package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import seigneurnecron.minecraftmods.stargate.StargateMod;

public class BlockPanelShieldConsole extends BlockPanelStargateConsole {
	
	public BlockPanelShieldConsole(String name) {
		super(name);
	}
	
	@Override
	protected Block baseBlock() {
		return StargateMod.block_shieldConsoleBase;
	}
	
}

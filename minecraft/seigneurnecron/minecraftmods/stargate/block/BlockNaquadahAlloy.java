package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityNaquadahAlloy;

public class BlockNaquadahAlloy extends BlockStargateSolidPart {
	
	public BlockNaquadahAlloy(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNaquadahAlloy();
	}
	
}

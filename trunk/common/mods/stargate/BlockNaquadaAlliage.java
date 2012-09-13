package mods.stargate;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockNaquadaAlliage extends BlockStargateSolidPart {
	
	public BlockNaquadaAlliage(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	/**
	 * each class overrdies this to return a new tileEntity
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNaquadaAlliage();
	}
	
}

package mods.necron.stargate;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class BlockChevron extends BlockStargateSolidPart {
	
	public BlockChevron(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
		this.setCreativeTab(null);
	}
	
	/**
	 * each class overrdies this to return a new tileEntity
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChevron();
	}
	
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntityChevron tileEntity = (TileEntityChevron) iBlockAccess.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null) {
			int gateOrientation = tileEntity.getGateOrientation();
			
			if(gateOrientation == 0) {
				return this.blockIndexInTexture;
			}
			
			if(side == gateOrientation) {
				return (tileEntity.isChevronActivated() ? tileEntity.getNo() + 16 : tileEntity.getNo());
			}
			
			return StargateMod.naquadaAlliage.blockIndexInTexture;
		}
		
		return this.blockIndexInTexture;
	}
	
}

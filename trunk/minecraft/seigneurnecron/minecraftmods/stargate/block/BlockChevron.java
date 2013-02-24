package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChevron extends BlockStargateSolidPart {
	
	public BlockChevron(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
		this.setCreativeTab(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChevron();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
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
			
			return StargateMod.naquadahAlloy.blockIndexInTexture;
		}
		
		return this.blockIndexInTexture;
	}
	
}

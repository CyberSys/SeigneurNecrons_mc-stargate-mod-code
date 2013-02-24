package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMobGenerator extends BlockGuiContainer {
	
	public BlockMobGenerator(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityMobGenerator();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		if(!world.isRemote) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityMobGenerator) {
				((TileEntityMobGenerator) tileEntity).setPowered(world.isBlockIndirectlyGettingPowered(x, y, z));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityMobGenerator) {
			TileEntityMobGenerator tileEntityMG = (TileEntityMobGenerator) tileEntity;
			if(tileEntityMG.isCrystalInserted()) {
				return this.blockIndexInTexture + (tileEntityMG.isPowered() ? 2 : 1);
			}
		}
		return this.blockIndexInTexture;
	}
	
}

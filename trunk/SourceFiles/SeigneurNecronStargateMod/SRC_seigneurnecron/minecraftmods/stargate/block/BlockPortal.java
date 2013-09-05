package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateOrientation;

public class BlockPortal extends BlockStargatePart {
	
	private static final float WIDTH = 0.5F;
	private static final float THICKNESS = 0.125F;
	
	public BlockPortal(String name) {
		super(name, StargateMod.material_vortex);
		this.setBlockUnbreakable();
		this.setLightValue(0.75F);
		this.setStepSound(soundGlassFootstep);
		this.setCreativeTab(null);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z) {
		float xWidth = THICKNESS;
		float zWidth = THICKNESS;
		
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && tileEntity instanceof TileEntityStargatePart) {
			GateOrientation gateOrientation = ((TileEntityStargatePart) tileEntity).getGateOrientation();
			
			if(gateOrientation == GateOrientation.X_AXIS) {
				xWidth = WIDTH;
			}
			else if(gateOrientation == GateOrientation.Z_AXIS) {
				zWidth = WIDTH;
			}
		}
		
		this.setBlockBounds(0.5F - xWidth, 0.0F, 0.5F - zWidth, 0.5F + xWidth, 1.0F, 0.5F + zWidth);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		return iBlockAccess.getBlockId(x, y, z) != this.blockID && super.shouldSideBeRendered(iBlockAccess, x, y, z, side);
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return 0;
	}
	
}

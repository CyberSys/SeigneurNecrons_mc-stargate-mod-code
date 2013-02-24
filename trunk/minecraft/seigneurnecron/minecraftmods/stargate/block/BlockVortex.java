package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.enums.GateState;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMasterChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityVortex;

public class BlockVortex extends BlockStargatePart {
	
	public BlockVortex(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.portal, name);
		this.setHardness(-1.0F);
		this.setLightValue(0.75F);
		this.setStepSound(soundGlassFootstep);
		this.setTickRandomly(true);
	}
	
	/**
	 * Previent la porte qu'un des block constituant le vortex a ete detruit.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		if(!world.isRemote) {
			TileEntityMasterChevron gate = this.getMasterChevron(world, x, y, z);
			if(gate != null) {
				gate.close();
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z) {
		float var5;
		float var6;
		
		if(iBlockAccess.getBlockId(x - 1, y, z) != this.blockID && iBlockAccess.getBlockId(x + 1, y, z) != this.blockID) {
			var5 = 0.125F;
			var6 = 0.5F;
			this.setBlockBounds(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
		}
		else {
			var5 = 0.5F;
			var6 = 0.125F;
			this.setBlockBounds(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
		}
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
		if(iBlockAccess.getBlockId(x, y, z) == this.blockID) {
			return false;
		}
		else {
			boolean var6 = iBlockAccess.getBlockId(x - 1, y, z) == this.blockID && iBlockAccess.getBlockId(x - 2, y, z) != this.blockID;
			boolean var7 = iBlockAccess.getBlockId(x + 1, y, z) == this.blockID && iBlockAccess.getBlockId(x + 2, y, z) != this.blockID;
			boolean var8 = iBlockAccess.getBlockId(x, y, z - 1) == this.blockID && iBlockAccess.getBlockId(x, y, z - 2) != this.blockID;
			boolean var9 = iBlockAccess.getBlockId(x, y, z + 1) == this.blockID && iBlockAccess.getBlockId(x, y, z + 2) != this.blockID;
			boolean var10 = var6 || var7;
			boolean var11 = var8 || var9;
			return var10 && side == 4 ? true : (var10 && side == 5 ? true : (var11 && side == 2 ? true : var11 && side == 3));
		}
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
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(!world.isRemote) {
			TileEntityMasterChevron gate = this.getMasterChevron(world, x, y, z);
			
			if(gate != null && gate.getState() == GateState.INPUT && entity.ridingEntity == null && entity.riddenByEntity == null) {
				gate.teleportEntity(entity);
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityVortex();
	}
	
}

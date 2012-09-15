package mods.necron.stargate;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockVortex extends BlockStargatePart {
	
	public BlockVortex(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.portal, name);
		this.setHardness(-1.0F);
		this.setLightValue(0.75F);
		this.setStepSound(soundGlassFootstep);
		this.setTickRandomly(true);
	}
	
	/**
	 * Prévient la porte qu'un des block constituant le vortex a été detruit.
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
	
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
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
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
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
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
	
	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	/**
	 * only called by clickMiddleMouseButton, and passed to inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return 0;
	}
	
	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(!world.isRemote) {
			TileEntityMasterChevron gate = this.getMasterChevron(world, x, y, z);
			
			if(gate != null && gate.getState() == GateState.INPUT && entity.ridingEntity == null && entity.riddenByEntity == null) {
				gate.teleportEntity(entity);
			}
		}
	}
	
	/**
	 * each class overrdies this to return a new tileEntity
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityVortex();
	}
	
}

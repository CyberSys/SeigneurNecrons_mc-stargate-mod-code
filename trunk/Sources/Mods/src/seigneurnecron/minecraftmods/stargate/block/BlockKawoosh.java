package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.entity.damagesource.CustomDamageSource;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class BlockKawoosh extends BlockStargate {
	
	// Constructors :
	
	public BlockKawoosh(String name) {
		super(name, StargateMod.material_vortex);
		this.setHardness(-1.0F);
		this.setLightValue(0.75F);
		this.setStepSound(soundGlassFootstep);
		this.setCreativeTab(null);
	}
	
	// Methods :
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
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
		return !(iBlockAccess.getBlockId(x, y, z) == this.blockID || iBlockAccess.isBlockOpaqueCube(x, y, z));
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
		if(entity instanceof EntityLivingBase) {
			entity.attackEntityFrom(CustomDamageSource.KAWOOSH, Integer.MAX_VALUE);
		}
		else {
			entity.setDead();
		}
	}
	
}

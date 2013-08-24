package seigneurnecron.minecraftmods.stargate.dispenserBehavior;

import java.util.Random;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;

public class DispenserBehaviorCustomFireBall extends BehaviorDefaultDispenseItem {
	
	@Override
	public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
		EnumFacing enumfacing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
		IPosition iposition = BlockDispenser.getIPositionFromBlockSource(iBlockSource);
		
		double x = iposition.getX() + enumfacing.getFrontOffsetX() * 0.3F;
		double y = iposition.getY() + enumfacing.getFrontOffsetX() * 0.3F;
		double z = iposition.getZ() + enumfacing.getFrontOffsetZ() * 0.3F;
		
		World world = iBlockSource.getWorld();
		Random random = world.rand;
		
		double motionX = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetX();
		double motionY = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetY();
		double motionZ = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetZ();
		
		world.spawnEntityInWorld(new EntityCustomFireBall(world, x, y, z, motionX, motionY, motionZ));
		itemStack.splitStack(1);
		return itemStack;
	}
	
	protected Entity getProjectile(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		return new EntityCustomFireBall(world, x, y, z, motionX, motionY, motionZ);
	}
	
	@Override
	protected void playDispenseSound(IBlockSource par1IBlockSource) {
		par1IBlockSource.getWorld().playAuxSFX(1009, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
	}
	
}

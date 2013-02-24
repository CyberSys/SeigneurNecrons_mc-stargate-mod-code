package seigneurnecron.minecraftmods.stargate.test;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMasterChevron;

public class BlockFastStargate extends Block {
	
	public BlockFastStargate(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock);
		this.setBlockName(name);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		if(!world.isRemote) {
			int angle = MathHelper.floor_double(entityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			int side = (angle == 0) ? 2 : (angle == 1) ? 5 : (angle == 2) ? 3 : 4;
			world.setBlockWithNotify(x, y, z, 0);
			this.addDecoration(world, x, y, z, side);
			TileEntityMasterChevron.constructGate(world, x, y + this.getHauteur(), z, side);
		}
		return true;
	}
	
	protected int getHauteur() {
		return 11;
	}
	
	protected void addDecoration(World world, int xCoord, int yCoord, int zCoord, int side) {
		int axeX = (side == 2 || side == 3) ? 1 : 0;
		int axeZ = (side == 4 || side == 5) ? 1 : 0;
		int lenght = 8;
		int width = 2;
		
		for(int i = -3; i <= -1; i++) {
			int y = yCoord + i;
			for(int j = -lenght; j <= lenght; j++) {
				for(int k = -width; k <= width; k++) {
					int x = xCoord + (axeX * j) + (axeZ * k);
					int z = zCoord + (axeX * k) + (axeZ * j);
					world.setBlockWithNotify(x, y, z, Block.stoneBrick.blockID);
				}
			}
		}
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

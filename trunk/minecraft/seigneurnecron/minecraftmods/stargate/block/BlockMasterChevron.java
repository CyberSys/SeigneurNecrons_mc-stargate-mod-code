package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMasterChevron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMasterChevron extends BlockContainer {
	
	public BlockMasterChevron(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(null);
		this.setBlockName(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		if(!world.isRemote) {
			((TileEntityMasterChevron) world.getBlockTileEntity(x, y, z)).createGate();
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
		int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		switch(angle) {
			case 0:
				world.setBlockMetadataWithNotify(x, y, z, 2);
				break;
			case 1:
				world.setBlockMetadataWithNotify(x, y, z, 5);
				break;
			case 2:
				world.setBlockMetadataWithNotify(x, y, z, 3);
				break;
			case 3:
				world.setBlockMetadataWithNotify(x, y, z, 4);
				break;
		}
	}
	
	/**
	 * Supprime les TileEntity des autres blocks de la porte avant de supprimer ce block.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityMasterChevron) {
			((TileEntityMasterChevron) tileEntity).setBroken();
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMasterChevron();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		int metadata = iBlockAccess.getBlockMetadata(x, y, z);
		
		if(side == metadata) {
			TileEntityMasterChevron tileEntity = (TileEntityMasterChevron) iBlockAccess.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity.isChevronActivated()) {
				return 23; // chevron 7 active.
			}
			return this.blockIndexInTexture;
		}
		
		return StargateMod.naquadahAlloy.blockIndexInTexture;
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

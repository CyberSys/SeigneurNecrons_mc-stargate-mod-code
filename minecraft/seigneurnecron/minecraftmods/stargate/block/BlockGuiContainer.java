package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiContainer;

public abstract class BlockGuiContainer extends BlockStargate {
	
	public BlockGuiContainer(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock, name);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity == null || !(tileEntity instanceof TileEntityGuiContainer)) {
			return false;
		}
		
		if(!world.isRemote) {
			player.openGui(StargateMod.instance, this.blockID, world, x, y, z);
		}
		
		return true;
	}
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

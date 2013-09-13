package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateGuiContainer;

/**
 * @author Seigneur Necron
 */
public abstract class BlockGuiContainer extends BlockStargateContainer {
	
	public BlockGuiContainer(String name) {
		super(name, Material.rock);
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity == null || !(tileEntity instanceof TileEntityStargateGuiContainer)) {
			return false;
		}
		
		if(!world.isRemote) {
			player.openGui(StargateMod.instance, this.blockID, world, x, y, z);
		}
		
		return true;
	}
	
}

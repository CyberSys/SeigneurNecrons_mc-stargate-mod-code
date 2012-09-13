package mods.stargate;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;


public abstract class BlockGui extends BlockStargate {
	
	public BlockGui(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock, name);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		
		if(!world.isRemote) {
			TileEntityGui tileEntity = (TileEntityGui) world.getBlockTileEntity(x, y, z);
			
			if(tileEntity.isEditable()) {
				tileEntity.setEditable(false);
				player.openGui(StargateMod.instance, this.blockID, world, x, y, z);
			}
		}
		return true;
	}
	
	/**
	 * Grabs the current texture file used for this block.
	 */
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}

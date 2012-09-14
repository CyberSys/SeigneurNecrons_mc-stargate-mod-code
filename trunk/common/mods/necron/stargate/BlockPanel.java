package mods.necron.stargate;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockPanel extends BlockNaquadaMade {
	
	public BlockPanel(int id, int textureIndex, String name) {
		super(id, textureIndex, name, CreativeTabs.tabRedstone);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
	}
	
	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
		int angle = MathHelper.floor_double((double) (entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
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
	
}

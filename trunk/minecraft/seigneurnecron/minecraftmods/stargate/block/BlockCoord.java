package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BlockCoord extends BlockGuiScreen {
	
	public BlockCoord(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
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
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
}

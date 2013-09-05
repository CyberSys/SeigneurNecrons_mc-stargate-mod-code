package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseStargateConsole;

/**
 * @author Seigneur Necron
 */
public abstract class BlockBaseStargateConsole extends BlockBase {
	
	protected BlockBaseStargateConsole(String name) {
		super(name);
	}
	
	@Override
	public void activate(World world, int x, int y, int z, EntityPlayer player) {
		if(!world.isRemote) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			
			if(tileEntity != null && tileEntity instanceof TileEntityBaseStargateConsole) {
				((TileEntityBaseStargateConsole) tileEntity).getStargateControl();
			}
		}
		
		super.activate(world, x, y, z, player);
	}
	
}

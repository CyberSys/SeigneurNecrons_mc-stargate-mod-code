package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.tileentity.TileEntityContainer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;

/**
 * @author Seigneur Necron
 */
public abstract class BlockGuiContainer extends BlockContainerNaquadah {
	
	protected BlockGuiContainer(String name) {
		super(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof TileEntityContainer) {
			if(!world.isRemote) {
				player.openGui(StargateMod.instance, StargateCommonProxy.NOT_A_CONSOLE, world, x, y, z);
			}
			
			return true;
		}
		
		return false;
	}
	
}

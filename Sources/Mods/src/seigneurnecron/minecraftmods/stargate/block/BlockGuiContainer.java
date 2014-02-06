package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.tileentity.TileEntityContainer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityContainerStargate;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class BlockGuiContainer extends BlockContainerNaquadah {
	
	// Constructors :
	
	protected BlockGuiContainer(String name) {
		super(name);
	}
	
	// Methods :
	
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
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		
		if(tileentity instanceof TileEntityContainerStargate) {
			((TileEntityContainerStargate) tileentity).getInventory().dropContent();
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
}

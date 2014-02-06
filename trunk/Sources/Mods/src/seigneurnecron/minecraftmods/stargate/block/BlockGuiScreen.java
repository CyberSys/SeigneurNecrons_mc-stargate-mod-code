package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class BlockGuiScreen extends BlockContainerNaquadah {
	
	// Constructors :
	
	public BlockGuiScreen(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && this.tileEntityOk(tileEntity)) {
			StargateMod.proxy.openGui(this, player, tileEntity);
			return true;
		}
		
		return false;
	}
	
	protected abstract boolean tileEntityOk(TileEntity tileEntity);
	
	@SideOnly(Side.CLIENT)
	public abstract GuiScreen getGuiScreen(TileEntity tileEntity, EntityPlayer player);
	
}

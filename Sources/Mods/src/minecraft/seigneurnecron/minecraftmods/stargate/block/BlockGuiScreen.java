package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class BlockGuiScreen extends BlockContainerNaquadah {
	
	public BlockGuiScreen(String name) {
		super(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && this.tileEntityOk(tileEntity)) {
			if(world.isRemote) {
				ModLoader.openGUI(player, this.getGuiScreen(tileEntity, player));
			}
			
			return true;
		}
		
		return false;
	}
	
	protected abstract boolean tileEntityOk(TileEntity tileEntity);
	
	@SideOnly(Side.CLIENT)
	protected abstract GuiScreen getGuiScreen(TileEntity tileEntity, EntityPlayer player);
	
}

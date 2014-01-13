package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Seigneur Necron
 */
public abstract class ConsoleScreen extends Console {
	
	// Constructors :
	
	protected ConsoleScreen(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(world.isRemote) {
			FMLClientHandler.instance().displayGuiScreen(player, this.getGui(player));
		}
		
		return true;
	}
	
	protected abstract GuiScreen getGui(EntityPlayer player);
	
}

package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.gui.GuiDefaultConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public class ConsoleDefault extends Console {
	
	// Constructors :
	
	public ConsoleDefault(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(world.isRemote) {
			ModLoader.openGUI(player, new GuiDefaultConsole(this.tileEntity, player, this));
		}
		
		return true;
	}
	
}

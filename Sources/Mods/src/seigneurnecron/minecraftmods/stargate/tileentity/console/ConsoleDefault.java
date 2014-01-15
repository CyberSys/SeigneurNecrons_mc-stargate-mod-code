package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.gui.GuiDefaultConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ConsoleDefault extends ConsoleScreen {
	
	// Constructors :
	
	public ConsoleDefault(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer player) {
		return new GuiDefaultConsole(this.tileEntity, player, this);
	}
	
}

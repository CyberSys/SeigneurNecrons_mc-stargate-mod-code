package seigneurnecron.minecraftmods.stargate.gui;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleDefault;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDefaultConsole extends GuiScreenConsolePanel<ConsoleDefault> {
	
	// FIXME - faire cette interface.
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiDefaultConsole(TileEntityConsoleBase tileEntity, EntityPlayer player, ConsoleDefault console) {
		super(tileEntity, player, console);
	}
	
}

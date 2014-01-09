package seigneurnecron.minecraftmods.stargate.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateShield;

/**
 * @author Seigneur Necron
 */
public class ShieldCommand implements Command {
	
	// Methods :
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityConsoleBase) {
			Console console = ((TileEntityConsoleBase) tileEntity).getConsole();
			
			if(console instanceof ConsoleStargateShield) {
				boolean activate = input.readBoolean();
				((ConsoleStargateShield) console).activateShield(activate);
			}
		}
	}
	
}

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
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ShieldCodeCommand implements Command {
	
	// Methods :
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityConsoleBase) {
			Console console = ((TileEntityConsoleBase) tileEntity).getConsole();
			
			if(console instanceof ConsoleStargateShield) {
				int code = input.readInt();
				((ConsoleStargateShield) console).changeShieldCode(code);
			}
		}
	}
	
}

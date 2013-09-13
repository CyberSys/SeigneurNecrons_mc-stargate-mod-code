package seigneurnecron.minecraftmods.stargate.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;

/**
 * @author Seigneur Necron
 */
public class ShieldCodeCommand implements Command {
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityBaseShieldConsole) {
			int code = input.readInt();
			((TileEntityBaseShieldConsole) tileEntity).changeShieldCode(code);
		}
	}
	
}

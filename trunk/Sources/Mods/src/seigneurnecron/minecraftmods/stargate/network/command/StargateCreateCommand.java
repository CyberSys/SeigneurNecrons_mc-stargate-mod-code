package seigneurnecron.minecraftmods.stargate.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;

/**
 * @author Seigneur Necron
 */
public class StargateCreateCommand implements Command {
	
	// Methods :
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityStargateControl) {
			String address = input.readUTF();
			((TileEntityStargateControl) tileEntity).createGate(address);
		}
	}
	
}

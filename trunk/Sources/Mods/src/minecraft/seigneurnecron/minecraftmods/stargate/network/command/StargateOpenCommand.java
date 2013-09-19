package seigneurnecron.minecraftmods.stargate.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;

/**
 * @author Seigneur Necron
 */
public class StargateOpenCommand implements Command {
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityBaseDhd) {
			String address = input.readUTF();
			((TileEntityBaseDhd) tileEntity).activateStargate(address);
		}
	}
	
}

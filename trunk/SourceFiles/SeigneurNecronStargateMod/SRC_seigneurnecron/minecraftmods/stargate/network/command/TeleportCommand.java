package seigneurnecron.minecraftmods.stargate.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;

/**
 * @author Seigneur Necron
 */
public class TeleportCommand implements Command {
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityBaseTeleporter) {
			int xTp = input.readInt();
			int yTp = input.readInt();
			int zTp = input.readInt();
			((TileEntityBaseTeleporter) tileEntity).teleportPlayer((EntityPlayerMP) player, xTp, yTp, zTp);
		}
	}
	
}

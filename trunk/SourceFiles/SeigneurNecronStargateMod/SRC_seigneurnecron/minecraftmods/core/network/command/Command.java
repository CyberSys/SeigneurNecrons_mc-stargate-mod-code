package seigneurnecron.minecraftmods.core.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Seigneur Necron
 */
public interface Command {
	
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException;
	
}

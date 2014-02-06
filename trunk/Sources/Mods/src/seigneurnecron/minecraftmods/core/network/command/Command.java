package seigneurnecron.minecraftmods.core.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public interface Command {
	
	// Methids :
	
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException;
	
}

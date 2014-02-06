package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.core.loadable.Coordinates;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ChunkCoordinates extends Coordinates<ChunkCoordinates> {
	
	// Constructors :
	
	public ChunkCoordinates(int dim, int x, int y, int z) {
		super(dim, x, y, z);
	}
	
	public ChunkCoordinates(NBTTagCompound tag) {
		super(tag);
	}
	
	public ChunkCoordinates(DataInputStream input) throws IOException {
		super(input);
	}
	
}

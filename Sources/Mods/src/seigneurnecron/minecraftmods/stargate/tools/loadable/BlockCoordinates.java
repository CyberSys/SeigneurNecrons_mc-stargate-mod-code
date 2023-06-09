package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.core.loadable.Coordinates;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargate;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class BlockCoordinates<T extends BlockCoordinates> extends Coordinates<T> {
	
	// Constants :
	
	public static final int CHUNK_SIZE = 16;
	public static final int ZONE_X_SIZE = 3 * CHUNK_SIZE;
	public static final int ZONE_Y_SIZE = 3 * CHUNK_SIZE;
	
	// Constructors :
	
	public BlockCoordinates(int dim, int x, int y, int z) {
		super(dim, x, y, z);
	}
	
	public BlockCoordinates(TileEntityStargate tileEntity) {
		super(tileEntity.getDimension(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
	}
	
	public BlockCoordinates(NBTTagCompound tag) {
		super(tag);
	}
	
	public BlockCoordinates(DataInputStream input) throws IOException {
		super(input);
	}
	
	// Methods :
	
	public ChunkCoordinates getChunkCoordinates() {
		return new ChunkCoordinates(this.dim, getCoord(this.x, CHUNK_SIZE), getCoord(this.y, CHUNK_SIZE), getCoord(this.z, CHUNK_SIZE));
	}
	
	public StargateZoneCoordinates getStargateZoneCoordinates() {
		return new StargateZoneCoordinates(this.dim, getCoord(this.x, ZONE_X_SIZE), getCoord(this.y, ZONE_Y_SIZE), getCoord(this.z, ZONE_X_SIZE));
	}
	
	private static int getCoord(int coord, int base) {
		return (coord < 0) ? (coord / base - 1) : (coord / base);
	}
	
}

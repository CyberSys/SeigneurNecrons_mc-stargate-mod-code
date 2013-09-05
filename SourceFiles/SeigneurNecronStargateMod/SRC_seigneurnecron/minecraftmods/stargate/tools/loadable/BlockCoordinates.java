package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class BlockCoordinates extends Coordinates<BlockCoordinates> {
	
	public static final int CHUNK_SIZE = 16;
	public static final int ZONE_X_SIZE = 3;
	public static final int ZONE_Y_SIZE = 3;
	
	// Builders :
	
	public BlockCoordinates(int dim, int x, int y, int z) {
		super(dim, x, y, z);
	}
	
	public BlockCoordinates(NBTTagCompound tag) {
		super(tag);
	}
	
	// Methods :
	
	public StargateZoneCoordinates getStargateZoneCoordinates() {
		return new StargateZoneCoordinates(this.dim, getXCoord(this.x), getYCoord(this.y), getXCoord(this.z));
	}
	
	private static int getXCoord(int coord) {
		return getCoord(coord, CHUNK_SIZE * ZONE_X_SIZE);
	}
	
	private static int getYCoord(int coord) {
		return getCoord(coord, CHUNK_SIZE * ZONE_Y_SIZE);
	}
	
	private static int getCoord(int coord, int base) {
		return (coord < 0) ? (coord / base - 1) : (coord / base);
	}
	
	public BlockCoordinates(DataInputStream input) throws IOException {
		super(input);
	}
	
}

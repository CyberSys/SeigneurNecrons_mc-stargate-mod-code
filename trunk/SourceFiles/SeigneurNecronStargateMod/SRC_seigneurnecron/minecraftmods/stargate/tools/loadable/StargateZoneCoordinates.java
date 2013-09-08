package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public class StargateZoneCoordinates extends Coordinates<StargateZoneCoordinates> {
	
	// Constructors :
	
	public StargateZoneCoordinates(int dim, int x, int y, int z) {
		super(dim, x, y, z);
	}
	
	public StargateZoneCoordinates(NBTTagCompound tag) {
		super(tag);
	}
	
	public StargateZoneCoordinates(DataInputStream input) throws IOException {
		super(input);
	}
	
}

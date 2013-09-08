package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public interface Loadable<T> extends Comparable<T> {
	
	public void saveNBTData(NBTTagCompound compound);
	
	public void loadNBTData(NBTTagCompound compound);
	
	public void writeData(DataOutputStream output) throws IOException;
	
	public void readData(DataInputStream input) throws IOException;
	
}

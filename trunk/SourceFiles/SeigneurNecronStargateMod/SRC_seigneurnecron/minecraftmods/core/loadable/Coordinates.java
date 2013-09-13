package seigneurnecron.minecraftmods.core.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public abstract class Coordinates<T extends Coordinates> implements Loadable<T> {
	
	// NBTTags names :
	
	private static final String DIM = "dim";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String Z = "z";
	
	// Fields :
	
	public int dim;
	public int x;
	public int y;
	public int z;
	
	// Constructors :
	
	protected Coordinates(int dim, int x, int y, int z) {
		this.dim = dim;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	protected Coordinates(NBTTagCompound tag) {
		this.loadNBTData(tag);
	}
	
	protected Coordinates(DataInputStream input) throws IOException {
		this.readData(input);
	}
	
	// Methods :
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.dim == 0 ? "Earth" : this.dim == -1 ? "Hell" : this.dim == 1 ? "End" : "Unknown");
		builder.append(" (");
		builder.append(this.x);
		builder.append(", ");
		builder.append(this.y);
		builder.append(", ");
		builder.append(this.z);
		builder.append(")");
		
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Coordinates other = (Coordinates) obj;
		return (this.dim == other.dim) && (this.x == other.x) && (this.y == other.y) && (this.z == other.z);
	}
	
	// Comparable interface :
	
	@Override
	public int compareTo(T other) {
		if(other == null) {
			return 1;
		}
		
		if(this.dim != other.dim) {
			return this.dim - other.dim;
		}
		
		if(this.x != other.x) {
			return this.x - other.x;
		}
		
		if(this.z != other.z) {
			return this.z - other.z;
		}
		
		if(this.y != other.y) {
			return this.y - other.y;
		}
		
		return 0;
	}
	
	// Loadable interface :
	
	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setInteger(DIM, this.dim);
		tag.setInteger(X, this.x);
		tag.setInteger(Y, this.y);
		tag.setInteger(Z, this.z);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag) {
		this.dim = tag.getInteger(DIM);
		this.x = tag.getInteger(X);
		this.y = tag.getInteger(Y);
		this.z = tag.getInteger(Z);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(this.dim);
		output.writeInt(this.x);
		output.writeInt(this.y);
		output.writeInt(this.z);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		this.dim = input.readInt();
		this.x = input.readInt();
		this.y = input.readInt();
		this.z = input.readInt();
	}
	
	@Override
	public NBTTagCompound getCompound() {
		NBTTagCompound compound = new NBTTagCompound();
		this.saveNBTData(compound);
		return compound;
	}
	
}

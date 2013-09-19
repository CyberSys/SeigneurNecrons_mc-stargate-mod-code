package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.core.loadable.Loadable;

/**
 * @author Seigneur Necron
 */
public class Stargate implements Comparable<Stargate>, Loadable<Stargate> {
	
	// NBTTags names :
	
	private static final String ADDRESS = "address";
	private static final String NAME = "name";
	private static final String CODE = "code";
	
	// Fields :
	
	public String address;
	public String name;
	public int code;
	
	// Constructors :
	
	public Stargate(String address, String name, int code) {
		this.address = address;
		this.name = name;
		this.code = code;
	}
	
	public Stargate(NBTTagCompound tag) {
		this.loadNBTData(tag);
	}
	
	public Stargate(DataInputStream input) throws IOException {
		this.readData(input);
	}
	
	// Methods :
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Stargate other = (Stargate) obj;
		return this.address == null ? other.address == null : this.address.equals(other.address);
	}
	
	// Comparable interface :
	
	@Override
	public int compareTo(Stargate other) {
		if(other == null) {
			return 1;
		}
		
		if(this.address == null) {
			return (other.address == null) ? 0 : -1;
		}
		
		if(other.address == null) {
			return 1;
		}
		
		return this.address.compareTo(other.address);
	}
	
	// Loadable interface :
	
	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setString(ADDRESS, this.address);
		tag.setString(NAME, this.name);
		tag.setInteger(CODE, this.code);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag) {
		this.address = tag.getString(ADDRESS);
		this.name = tag.getString(NAME);
		this.code = tag.getInteger(CODE);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeUTF(this.address);
		output.writeUTF(this.name);
		output.writeInt(this.code);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		this.address = input.readUTF();
		this.name = input.readUTF();
		this.code = input.readInt();
	}
	
	@Override
	public NBTTagCompound getCompound() {
		NBTTagCompound compound = new NBTTagCompound();
		this.saveNBTData(compound);
		return compound;
	}
	
}

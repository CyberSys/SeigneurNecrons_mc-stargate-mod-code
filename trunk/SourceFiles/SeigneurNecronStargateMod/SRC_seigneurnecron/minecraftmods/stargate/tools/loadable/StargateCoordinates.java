package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class StargateCoordinates extends Coordinates<StargateCoordinates> {
	
	// NBTTags names :
	
	private static final String ADDRESS = "address";
	
	// Fields :
	
	public String address;
	
	// Builders :
	
	public StargateCoordinates(String address, int dim, int x, int y, int z) {
		super(dim, x, y, z);
		this.address = address;
	}
	
	public StargateCoordinates(NBTTagCompound tag) {
		super(tag);
	}
	
	public StargateCoordinates(DataInputStream input) throws IOException {
		super(input);
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
		
		StargateCoordinates other = (StargateCoordinates) obj;
		return this.address == null ? other.address == null : this.address.equals(other.address);
	}
	
	@Override
	public int compareTo(StargateCoordinates other) {
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
		super.saveNBTData(tag);
		tag.setString(ADDRESS, this.address);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag) {
		super.loadNBTData(tag);
		this.address = tag.getString(ADDRESS);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		output.writeUTF(this.address);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		this.address = input.readUTF();
	}
	
}

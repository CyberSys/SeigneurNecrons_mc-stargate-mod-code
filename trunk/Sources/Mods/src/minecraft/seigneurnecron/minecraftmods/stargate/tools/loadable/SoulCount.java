package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.core.loadable.Loadable;

/**
 * @author Seigneur Necron
 */
public class SoulCount implements Loadable<SoulCount> {
	
	// NBTTags names :
	
	private static final String ID = "id";
	private static final String COUNT = "count";
	
	// Fields :
	
	public int id;
	public int count;
	
	// Constructors :
	
	public SoulCount(int id, int count) {
		this.id = id;
		this.count = count;
	}
	
	public SoulCount(NBTTagCompound tag) {
		this.loadNBTData(tag);
	}
	
	public SoulCount(DataInputStream input) throws IOException {
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
		
		SoulCount other = (SoulCount) obj;
		return this.id == other.id;
	}
	
	// Comparable interface :
	
	@Override
	public int compareTo(SoulCount other) {
		if(other == null) {
			return 1;
		}
		
		return this.id - other.id;
	}
	
	// Loadable interface :
	
	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setInteger(ID, this.id);
		tag.setInteger(COUNT, this.count);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag) {
		this.id = tag.getInteger(ID);
		this.count = tag.getInteger(COUNT);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(this.id);
		output.writeInt(this.count);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		this.id = input.readInt();
		this.count = input.readInt();
	}
	
	@Override
	public NBTTagCompound getCompound() {
		NBTTagCompound compound = new NBTTagCompound();
		this.saveNBTData(compound);
		return compound;
	}
	
}

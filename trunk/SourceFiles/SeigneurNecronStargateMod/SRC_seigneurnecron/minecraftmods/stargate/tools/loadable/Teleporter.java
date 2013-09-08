package seigneurnecron.minecraftmods.stargate.tools.loadable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public class Teleporter extends Coordinates<Teleporter> {
	
	// NBTTags names :
	
	private static final String NAME = "name";
	
	// Fields :
	
	public String name;
	
	// Constructors :
	
	public Teleporter(int dim, int x, int y, int z, String name) {
		super(dim, x, y, z);
		this.name = name;
	}
	
	public Teleporter(NBTTagCompound tag) {
		super(tag);
	}
	
	public Teleporter(DataInputStream input) throws IOException {
		super(input);
	}
	
	// Loadable interface :
	
	@Override
	public void saveNBTData(NBTTagCompound tag) {
		super.saveNBTData(tag);
		tag.setString(NAME, this.name);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag) {
		super.loadNBTData(tag);
		this.name = tag.getString(NAME);
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		output.writeUTF(this.name);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		this.name = input.readUTF();
	}
	
}

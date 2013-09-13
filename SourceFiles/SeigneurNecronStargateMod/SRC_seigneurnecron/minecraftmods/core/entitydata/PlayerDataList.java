package seigneurnecron.minecraftmods.core.entitydata;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import seigneurnecron.minecraftmods.core.loadable.Loadable;

/**
 * @author Seigneur Necron
 */
public abstract class PlayerDataList<T extends Loadable> extends PlayerData {
	
	// NBTTags names :
	
	protected static final String LIST = "dataList";
	
	// Fields :
	
	/**
	 * A list of registered data.
	 */
	protected List<T> dataList;
	
	// Constructors :
	
	protected PlayerDataList(EntityPlayer player) {
		super(player);
		this.initList();
	}
	
	// Getters :
	
	public List<T> getDataList() {
		return this.dataList;
	}
	
	// Interface :
	
	public void addElementAndSync(T element) {
		this.addElement(element);
		this.syncProperties();
	}
	
	public void removeElementAndSync(T element) {
		this.removeElement(element);
		this.syncProperties();
	}
	
	public void overwriteElementAndSync(T oldElement, T newElement) {
		this.removeElement(oldElement);
		this.addElement(newElement);
		this.syncProperties();
	}
	
	// Methods :
	
	private void initList() {
		this.dataList = new LinkedList<T>();
	}
	
	protected final void addElement(T element) {
		this.dataList.remove(element);
		
		int i = 0;
		while(i < this.dataList.size() && element.compareTo(this.dataList.get(i)) >= 0) {
			i++;
		}
		
		this.dataList.add(i, element);
	}
	
	protected final void removeElement(T element) {
		this.dataList.remove(element);
	}
	
	// Packet system :
	
	@Override
	protected void saveProperties(DataOutputStream output) throws IOException {
		output.writeInt(this.dataList.size());
		
		for(T element : this.dataList) {
			element.writeData(output);
		}
	}
	
	@Override
	public void loadProperties(DataInputStream input) throws IOException {
		int nbElements = input.readInt();
		List<T> list = new LinkedList<T>();
		
		for(int i = 0; i < nbElements; i++) {
			list.add(this.getElement(input));
		}
		
		this.dataList = list;
	}
	
	protected abstract T getElement(DataInputStream input) throws IOException;
	
	// NBTTags system :
	
	@Override
	protected void saveToNBT(NBTTagCompound propertyTag) {
		NBTTagList list = new NBTTagList();
		propertyTag.setTag(LIST, list);
		
		for(T element : this.dataList) {
			NBTTagCompound tag = new NBTTagCompound();
			element.saveNBTData(tag);
			list.appendTag(tag);
		}
	}
	
	@Override
	protected void loadFromNBT(NBTTagCompound propertyTag) {
		NBTTagList list = propertyTag.getTagList(LIST);
		this.initList();
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			T element = this.getElement(tag);
			this.dataList.add(element);
		}
	}
	
	protected abstract T getElement(NBTTagCompound tag);
	
}

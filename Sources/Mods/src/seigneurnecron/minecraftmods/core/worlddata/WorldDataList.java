package seigneurnecron.minecraftmods.core.worlddata;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import seigneurnecron.minecraftmods.core.loadable.Loadable;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class WorldDataList<T extends Loadable> extends WorldSavedData {
	
	// NBTTags names :
	
	protected static final String LIST = "dataList";
	
	// Fields :
	
	/**
	 * A list of registered data.
	 */
	protected List<T> dataList;
	
	// Constructors :
	
	/**
	 * You must have a constructor of this form, else you will get a NoSuchMethodException.
	 * @param indentifier - the property indentifier.
	 */
	protected WorldDataList(String indentifier) {
		super(indentifier);
		this.initList();
	}
	
	// Getters :
	
	public List<T> getDataList() {
		return this.dataList;
	}
	
	// Methods :
	
	private void initList() {
		this.dataList = new LinkedList<T>();
	}
	
	public void addElement(T element) {
		this.dataList.remove(element);
		this.dataList.add(element);
		this.markDirty();
	}
	
	public void removeElement(T element) {
		this.dataList.remove(element);
		this.markDirty();
	}
	
	// NBTTags system :
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		compound.setTag(LIST, list);
		
		for(T element : this.dataList) {
			NBTTagCompound tag = new NBTTagCompound();
			element.saveNBTData(tag);
			list.appendTag(tag);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.initList();
		NBTTagList list = compound.getTagList(LIST);
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			T element = this.getElement(tag);
			this.dataList.add(element);
		}
	}
	
	protected abstract T getElement(NBTTagCompound tag);
	
}

package mods.necron.stargate;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;

public abstract class TileEntityStargate extends TileEntity {
	
	// Partie mapping :
	
	public static int packetId_CloseGuiTeleporter = 100;
	public static int packetId_CloseGuiDhd = 101;
	public static int packetId_CloseGuiDetector = 102;
	
	/**
	 * Une map permettant d'obtenir une classe h�ritant de TileEntityStargate � partir de son id.
	 */
	private static final HashMap<Integer, Class<? extends TileEntityStargate>> idToClassMap = new HashMap<Integer, Class<? extends TileEntityStargate>>();
	
	/**
	 * Une map permettant d'obtenir l'id d'une classe h�ritant de TileEntityStargate.
	 */
	private static final HashMap<Class<? extends TileEntityStargate>, Integer> classToIdMap = new HashMap<Class<? extends TileEntityStargate>, Integer>();
	
	static {
		addToMapping(0, TileEntityMasterChevron.class);
		addToMapping(1, TileEntityChevron.class);
		addToMapping(2, TileEntityNaquadaAlliage.class);
		addToMapping(3, TileEntityVortex.class);
		addToMapping(4, TileEntityCoordTeleporter.class);
		addToMapping(5, TileEntityCoordDhd.class);
		addToMapping(6, TileEntityDetector.class);
	}
	
	/**
	 * Ajoute un couple id/classe au mapping.
	 * @param id - l'id � ajouter.
	 * @param clazz - la classe � ajouter.
	 */
	private static void addToMapping(int id, Class<? extends TileEntityStargate> clazz) {
		if(!idToClassMap.containsKey(id) && !classToIdMap.containsKey(clazz)) {
			idToClassMap.put(id, clazz);
			classToIdMap.put(clazz, id);
		}
	}
	
	/**
	 * Permet d'obtenir l'id d'une classe h�ritant de TileEntityStargate.
	 * @param clazz - la classe dont on cherche l'id.
	 * @return l'id de la classe.
	 */
	public static int getIdFromClass(Class<? extends TileEntityStargate> clazz) {
		Integer id = classToIdMap.get(clazz);
		return (id != null ? id : -1);
	}
	
	/**
	 * Permet d'obtenir une classe h�ritant de TileEntityStargate � partir de son id.
	 * @param id - l'id de la classe recherch�e.
	 * @return la classe recherch�e.
	 */
	public static Class<? extends TileEntityStargate> getClassFromId(int id) {
		return idToClassMap.get(id);
	}
	
	/**
	 * Indique si l'id fourni correspond � une classe enregistr�e.
	 * @param id - l'id de la classe recherch�e.
	 * @return true si l'id correspond � une classe enregistr�e, false sinon.
	 */
	public static boolean isMapped(int id) {
		return idToClassMap.containsKey(id);
	}
	
	/**
	 * Indique si la classe fournie correspond � un id enregistr�.
	 * @param id - la classe dont on cherche l'id.
	 * @return true si la classe correspond � un id enregistr�, false sinon.
	 */
	public static boolean isMapped(Class<? extends TileEntityStargate> clazz) {
		return classToIdMap.containsKey(clazz);
	}
	
	// Partie conversion :
	
	/**
	 * Transforme un tableau de byte en LinkedList de Byte.
	 * @param array - un tableau de byte.
	 * @return une LinkedList de Byte.
	 */
	protected static LinkedList<Byte> arrayToList(byte[] array) {
		LinkedList<Byte> list = new LinkedList<Byte>();
		for(int i = 0; i < array.length; ++i) {
			list.add(array[i]);
		}
		return list;
	}
	
	/**
	 * Transforme une LinkedList de Byte en tableau de byte.
	 * @param list - une LinkedList de Byte.
	 * @return un tableau de byte.
	 */
	protected static byte[] listToArray(LinkedList<Byte> list) {
		byte[] array = new byte[list.size()];
		for(int i = 0; i < list.size(); ++i) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * Ecrit l'entier fourni sous la forme de 4 Byte, � la fin de la List de Byte fournie.
	 * @param list - la List de Byte o� �crire l'entier.
	 * @param value - l'entier � �crire.
	 */
	protected static void writeInt(LinkedList<Byte> list, int value) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Lit un entier dans les 4 premiers �l�ments d'une List de Byte.<br />
	 * (Les �l�ments lus sont supprim�s de la List)
	 * @param list - la List de Byte o� lire l'entier.
	 * @return l'entier lu.
	 */
	protected static int readInt(LinkedList<Byte> list) {
		ByteBuffer tmp = ByteBuffer.allocate(4);
		for(int i = 0; i < tmp.capacity(); ++i) {
			tmp.put(i, list.remove(0));
		}
		return tmp.getInt(0);
	}
	
	/**
	 * Ecrit le booleen fourni sous la forme d'un Byte, au d�but de la List de Byte fournie.
	 * @param list - la List de Byte o� �crire le booleen.
	 * @param value - le booleen � �crire.
	 */
	protected static void writeBoolean(LinkedList<Byte> list, boolean value) {
		byte tmp = (byte) (value ? 1 : 0);
		list.add(tmp);
	}
	
	/**
	 * Lit un booleen dans le premier �l�ment d'une List de Byte.<br />
	 * (L'�l�ment lu est supprim� de la List)
	 * @param list - la List de Byte o� lire le booleen.
	 * @return le booleen lu.
	 */
	protected static boolean readBoolean(LinkedList<Byte> list) {
		byte tmp = list.remove(0);
		return (tmp != 0);
	}
	
	/**
	 * Ecrit l'entier fourni sur les 4 premiers Byte de la List fournie.<br />
	 * Le but �tant de changer l'id du packet.
	 * @param list - la List de Byte o� �crire l'entier.
	 * @param id - l'id � attribuer au packet.
	 */
	protected static void changeId(LinkedList<Byte> list, int id) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(id).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.set(i, tmp[i]);
		}
	}
	
	// Partie interface externe :
	
	/**
	 * Retourne un pack contenant les informations de cette tileEntity.
	 * @return un pack contenant les informations de cette tileEntity.
	 */
	@Override
	public Packet getAuxillaryInfoPacket() {
		return new Packet250CustomPayload(StargateMod.chanel, listToArray(this.getEntityData()));
	}
	
	/**
	 * Retourne un pack contenant les informations de cette tileEntity, avec l'id choisit.
	 * @param id - l'id � attribuer au packet.
	 * @return un pack contenant les informations de cette tileEntity, avec l'id choisit.
	 */
	public Packet getAuxillaryInfoPacketWhithId(int id) {
		LinkedList<Byte> list = this.getEntityData();
		changeId(list, id);
		return new Packet250CustomPayload(StargateMod.chanel, listToArray(list));
	}
	
	/**
	 * Charge les donn�es de la tileEntity depuis le packet.
	 * @param networkManager - le NetworkManager d'o� provient le packet.
	 * @param packet - le packet contenant les donn�es � charger.
	 */
	public void onDataPacket(NetworkManager networkManager, Packet250CustomPayload packet) {
		if(packet != null) {
			this.loadEntityData(arrayToList(packet.data));
		}
	}
	
	// Partie lecture/�criture :
	
	/**
	 * Enregistre les donn�es de la tileEntity dans une List de Byte, dans le but de cr�er un packet.
	 * @return les donn�es de la tileEntity sous la forme d'une List de Byte.
	 */
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = new LinkedList<Byte>();
		
		writeInt(list, getIdFromClass(this.getClass()));
		writeInt(list, this.worldObj.getWorldInfo().getDimension());
		writeInt(list, this.xCoord);
		writeInt(list, this.yCoord);
		writeInt(list, this.zCoord);
		
		return list;
	}
	
	/**
	 * Charge les donn�es de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les donn�es � charger.
	 * @return true si le chargement est un succes, false sinon.
	 */
	protected boolean loadEntityData(LinkedList<Byte> list) {
		int id = readInt(list);
		if(this.isCorrectId(id)) {
			int dim = readInt(list);
			int x = readInt(list);
			int y = readInt(list);
			int z = readInt(list);
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie que l'id fournie est une id correcte pour un packet destin� � cette tile entity.
	 * @param id - l'id � tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	protected boolean isCorrectId(int id) {
		return id == getIdFromClass(this.getClass());
	}
	
	// Partie mise � jour :
	
	/**
	 * Transmet les modifications aux clients.
	 */
	protected void updateClients() {
		if(!this.worldObj.isRemote) {
			ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayersInDimension(this.getAuxillaryInfoPacket(), this.worldObj.getWorldInfo().getDimension());
		}
	}
	
	/**
	 * Signal au renderer que le block li� � cette tile entity a besoin d'�tre mit � jour.
	 */
	protected void updateBlockTexture() {
		if(this.worldObj.isRemote) {
			this.worldObj.markBlockNeedsUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
}

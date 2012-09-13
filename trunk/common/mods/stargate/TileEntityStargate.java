package mods.stargate;

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
	 * Une map permettant d'obtenir une classe héritant de TileEntityStargate à partir de son id.
	 */
	private static final HashMap<Integer, Class<? extends TileEntityStargate>> idToClassMap = new HashMap<Integer, Class<? extends TileEntityStargate>>();
	
	/**
	 * Une map permettant d'obtenir l'id d'une classe héritant de TileEntityStargate.
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
	 * @param id - l'id à ajouter.
	 * @param clazz - la classe à ajouter.
	 */
	private static void addToMapping(int id, Class<? extends TileEntityStargate> clazz) {
		if(!idToClassMap.containsKey(id) && !classToIdMap.containsKey(clazz)) {
			idToClassMap.put(id, clazz);
			classToIdMap.put(clazz, id);
		}
	}
	
	/**
	 * Permet d'obtenir l'id d'une classe héritant de TileEntityStargate.
	 * @param clazz - la classe dont on cherche l'id.
	 * @return l'id de la classe.
	 */
	public static int getIdFromClass(Class<? extends TileEntityStargate> clazz) {
		Integer id = classToIdMap.get(clazz);
		return (id != null ? id : -1);
	}
	
	/**
	 * Permet d'obtenir une classe héritant de TileEntityStargate à partir de son id.
	 * @param id - l'id de la classe recherchée.
	 * @return la classe recherchée.
	 */
	public static Class<? extends TileEntityStargate> getClassFromId(int id) {
		return idToClassMap.get(id);
	}
	
	/**
	 * Indique si l'id fourni correspond à une classe enregistrée.
	 * @param id - l'id de la classe recherchée.
	 * @return true si l'id correspond à une classe enregistrée, false sinon.
	 */
	public static boolean isMapped(int id) {
		return idToClassMap.containsKey(id);
	}
	
	/**
	 * Indique si la classe fournie correspond à un id enregistré.
	 * @param id - la classe dont on cherche l'id.
	 * @return true si la classe correspond à un id enregistré, false sinon.
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
	 * Ecrit l'entier fourni sous la forme de 4 Byte, à la fin de la List de Byte fournie.
	 * @param list - la List de Byte où écrire l'entier.
	 * @param value - l'entier à écrire.
	 */
	protected static void writeInt(LinkedList<Byte> list, int value) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Lit un entier dans les 4 premiers éléments d'une List de Byte.<br />
	 * (Les éléments lus sont supprimés de la List)
	 * @param list - la List de Byte où lire l'entier.
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
	 * Ecrit le booleen fourni sous la forme d'un Byte, au début de la List de Byte fournie.
	 * @param list - la List de Byte où écrire le booleen.
	 * @param value - le booleen à écrire.
	 */
	protected static void writeBoolean(LinkedList<Byte> list, boolean value) {
		byte tmp = (byte) (value ? 1 : 0);
		list.add(tmp);
	}
	
	/**
	 * Lit un booleen dans le premier élément d'une List de Byte.<br />
	 * (L'élément lu est supprimé de la List)
	 * @param list - la List de Byte où lire le booleen.
	 * @return le booleen lu.
	 */
	protected static boolean readBoolean(LinkedList<Byte> list) {
		byte tmp = list.remove(0);
		return (tmp != 0);
	}
	
	/**
	 * Ecrit l'entier fourni sur les 4 premiers Byte de la List fournie.<br />
	 * Le but étant de changer l'id du packet.
	 * @param list - la List de Byte où écrire l'entier.
	 * @param id - l'id à attribuer au packet.
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
	 * @param id - l'id à attribuer au packet.
	 * @return un pack contenant les informations de cette tileEntity, avec l'id choisit.
	 */
	public Packet getAuxillaryInfoPacketWhithId(int id) {
		LinkedList<Byte> list = this.getEntityData();
		changeId(list, id);
		return new Packet250CustomPayload(StargateMod.chanel, listToArray(list));
	}
	
	/**
	 * Charge les données de la tileEntity depuis le packet.
	 * @param networkManager - le NetworkManager d'où provient le packet.
	 * @param packet - le packet contenant les données à charger.
	 */
	public void onDataPacket(NetworkManager networkManager, Packet250CustomPayload packet) {
		if(packet != null) {
			this.loadEntityData(arrayToList(packet.data));
		}
	}
	
	// Partie lecture/écriture :
	
	/**
	 * Enregistre les données de la tileEntity dans une List de Byte, dans le but de créer un packet.
	 * @return les données de la tileEntity sous la forme d'une List de Byte.
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
	 * Charge les données de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les données à charger.
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
	 * Verifie que l'id fournie est une id correcte pour un packet destiné à cette tile entity.
	 * @param id - l'id à tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	protected boolean isCorrectId(int id) {
		return id == getIdFromClass(this.getClass());
	}
	
	// Partie mise à jour :
	
	/**
	 * Transmet les modifications aux clients.
	 */
	protected void updateClients() {
		if(!this.worldObj.isRemote) {
			ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayersInDimension(this.getAuxillaryInfoPacket(), this.worldObj.getWorldInfo().getDimension());
		}
	}
	
	/**
	 * Signal au renderer que le block lié à cette tile entity a besoin d'être mit à jour.
	 */
	protected void updateBlockTexture() {
		if(this.worldObj.isRemote) {
			this.worldObj.markBlockNeedsUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
}

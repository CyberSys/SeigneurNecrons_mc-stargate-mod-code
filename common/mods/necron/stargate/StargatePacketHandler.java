package mods.necron.stargate;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class StargatePacketHandler implements IPacketHandler {
	
	// Id des packets Stagate :
	
	public static int packetId_CloseGuiTeleporter = 100;
	public static int packetId_CloseGuiDhd = 101;
	public static int packetId_CloseGuiDetector = 102;
	public static int packetId_TeleportEntity = 150;
	
	// Partie mapping des tile entity :
	
	static {
		idToClassMap = new HashMap<Integer, Class<? extends TileEntityStargate>>();
		classToIdMap = new HashMap<Class<? extends TileEntityStargate>, Integer>();
		addToMapping(0, TileEntityMasterChevron.class);
		addToMapping(1, TileEntityChevron.class);
		addToMapping(2, TileEntityNaquadaAlliage.class);
		addToMapping(3, TileEntityVortex.class);
		addToMapping(4, TileEntityCoordTeleporter.class);
		addToMapping(5, TileEntityCoordDhd.class);
		addToMapping(6, TileEntityDetector.class);
	}
	
	/**
	 * Une map permettant d'obtenir une classe heritant de TileEntityStargate a partir de son id.
	 */
	private static final HashMap<Integer, Class<? extends TileEntityStargate>> idToClassMap;
	
	/**
	 * Une map permettant d'obtenir l'id d'une classe heritant de TileEntityStargate.
	 */
	private static final HashMap<Class<? extends TileEntityStargate>, Integer> classToIdMap;
	
	/**
	 * Ajoute un couple id/classe au mapping.
	 * @param id - l'id a ajouter.
	 * @param clazz - la classe a ajouter.
	 */
	private static void addToMapping(int id, Class<? extends TileEntityStargate> clazz) {
		if(!idToClassMap.containsKey(id) && !classToIdMap.containsKey(clazz)) {
			idToClassMap.put(id, clazz);
			classToIdMap.put(clazz, id);
		}
	}
	
	/**
	 * Permet d'obtenir l'id d'une classe heritant de TileEntityStargate.
	 * @param clazz - la classe dont on cherche l'id.
	 * @return l'id de la classe.
	 */
	public static int getIdFromClass(Class<? extends TileEntityStargate> clazz) {
		Integer id = classToIdMap.get(clazz);
		return (id != null ? id : -1);
	}
	
	/**
	 * Permet d'obtenir une classe heritant de TileEntityStargate a partir de son id.
	 * @param id - l'id de la classe recherchee.
	 * @return la classe recherchee.
	 */
	public static Class<? extends TileEntityStargate> getClassFromId(int id) {
		return idToClassMap.get(id);
	}
	
	/**
	 * Indique si l'id fourni correspond a une classe enregistree.
	 * @param id - l'id de la classe recherchee.
	 * @return true si l'id correspond a une classe enregistree, false sinon.
	 */
	public static boolean isMapped(int id) {
		return idToClassMap.containsKey(id);
	}
	
	/**
	 * Indique si la classe fournie correspond a un id enregistre.
	 * @param id - la classe dont on cherche l'id.
	 * @return true si la classe correspond a un id enregistre, false sinon.
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
	 * Ecrit l'entier fourni sous la forme de 4 Byte, a la fin de la List de Byte fournie.
	 * @param list - la List de Byte ou ecrire l'entier.
	 * @param value - l'entier a ecrire.
	 */
	protected static void writeInt(LinkedList<Byte> list, int value) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Ecrit le float fourni sous la forme de 4 Byte, a la fin de la List de Byte fournie.
	 * @param list - la List de Byte ou ecrire le float.
	 * @param value - le float a ecrire.
	 */
	protected static void writeFloat(LinkedList<Byte> list, float value) {
		byte[] tmp = ByteBuffer.allocate(4).putFloat(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Ecrit le double fourni sous la forme de 8 Byte, a la fin de la List de Byte fournie.
	 * @param list - la List de Byte ou ecrire le double.
	 * @param value - le double a ecrire.
	 */
	protected static void writeDouble(LinkedList<Byte> list, double value) {
		byte[] tmp = ByteBuffer.allocate(8).putDouble(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Ecrit le booleen fourni sous la forme d'un Byte, au debut de la List de Byte fournie.
	 * @param list - la List de Byte ou ecrire le booleen.
	 * @param value - le booleen a ecrire.
	 */
	protected static void writeBoolean(LinkedList<Byte> list, boolean value) {
		byte tmp = (byte) (value ? 1 : 0);
		list.add(tmp);
	}
	
	/**
	 * Lit un entier dans les 4 premiers elements d'une List de Byte.<br />
	 * (Les elements lus sont supprimes de la List)
	 * @param list - la List de Byte ou lire l'entier.
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
	 * Lit un float dans les 4 premiers elements d'une List de Byte.<br />
	 * (Les elements lus sont supprimes de la List)
	 * @param list - la List de Byte ou lire le float.
	 * @return le float lu.
	 */
	protected static float readFloat(LinkedList<Byte> list) {
		ByteBuffer tmp = ByteBuffer.allocate(4);
		for(int i = 0; i < tmp.capacity(); ++i) {
			tmp.put(i, list.remove(0));
		}
		return tmp.getFloat(0);
	}
	
	/**
	 * Lit un double dans les 8 premiers elements d'une List de Byte.<br />
	 * (Les elements lus sont supprimes de la List)
	 * @param list - la List de Byte ou lire le double.
	 * @return le double lu.
	 */
	protected static double readDouble(LinkedList<Byte> list) {
		ByteBuffer tmp = ByteBuffer.allocate(8);
		for(int i = 0; i < tmp.capacity(); ++i) {
			tmp.put(i, list.remove(0));
		}
		return tmp.getDouble(0);
	}
	
	/**
	 * Lit un booleen dans le premier element d'une List de Byte.<br />
	 * (L'element lu est supprime de la List)
	 * @param list - la List de Byte ou lire le booleen.
	 * @return le booleen lu.
	 */
	protected static boolean readBoolean(LinkedList<Byte> list) {
		byte tmp = list.remove(0);
		return (tmp != 0);
	}
	
	/**
	 * Ecrit l'entier fourni sur les 4 premiers Byte de la List fournie.<br />
	 * Le but etant de changer l'id du packet.
	 * @param list - la List de Byte ou ecrire l'entier.
	 * @param id - l'id a attribuer au packet.
	 */
	protected static void changeId(LinkedList<Byte> list, int id) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(id).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.set(i, tmp[i]);
		}
	}
	
	// Partie traitement des packets :
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		// Packet handling in client or server version.
	}
	
}

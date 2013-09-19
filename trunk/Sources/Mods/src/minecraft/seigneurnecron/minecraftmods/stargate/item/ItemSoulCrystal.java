package seigneurnecron.minecraftmods.stargate.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class ItemSoulCrystal extends ItemStargate {
	
	private static final Map<Integer, ItemSoulCrystal> monsterIdToCrystalMapping = new HashMap<Integer, ItemSoulCrystal>();
	
	private static final int DEFAULT_SPAWN_COUNT = 4;
	private static final int DEFAULT_MAX_MOB = 8;
	
	private final int monsterId;
	private final int spawnCount;
	private final int maxMob;
	
	public ItemSoulCrystal(Class<? extends EntityLiving> clazz) {
		this(clazz, DEFAULT_SPAWN_COUNT, DEFAULT_MAX_MOB);
	}
	
	public ItemSoulCrystal(Class<? extends EntityLiving> clazz, int spawnCount) {
		this(clazz, spawnCount, DEFAULT_MAX_MOB);
	}
	
	@SuppressWarnings("unchecked")
	public ItemSoulCrystal(Class<? extends EntityLiving> clazz, int spawnCount, int maxMob) {
		super(StargateMod.itemName_crystalSoul + EntityList.classToStringMapping.get(clazz));
		this.func_111206_d(StargateMod.itemName_crystalSoul); // setIconName(name)
		
		Integer tmpMonsterId = null;
		for(Entry entry : (Set<Entry>) EntityList.IDtoClassMapping.entrySet()) {
			if(entry.getValue() == clazz) {
				tmpMonsterId = (Integer) entry.getKey();
				break;
			}
		}
		
		if(tmpMonsterId == null) {
			throw new RuntimeException(new IllegalArgumentException("Can't find the entity id for the class \"" + clazz.getSimpleName() + "\". This is a bug !"));
		}
		
		this.monsterId = tmpMonsterId.intValue();
		this.spawnCount = spawnCount;
		this.maxMob = maxMob;
		
		addToMapping(this.monsterId, this);
	}
	
	public int getMonsterId() {
		return this.monsterId;
	}
	
	public String getMonsterName() {
		return EntityList.getStringFromID(this.monsterId);
	}
	
	public int getSpawnCount() {
		return this.spawnCount;
	}
	
	public int getMaxMob() {
		return this.maxMob;
	}
	
	public static ItemSoulCrystal getCrystalFromMonsterId(int monsterId) {
		return monsterIdToCrystalMapping.get(monsterId);
	}
	
	private static void addToMapping(int monsterId, ItemSoulCrystal crystal) {
		monsterIdToCrystalMapping.put(monsterId, crystal);
	}
	
}

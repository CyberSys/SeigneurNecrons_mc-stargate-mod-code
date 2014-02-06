package seigneurnecron.minecraftmods.stargate.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ItemSoulCrystalFull extends ItemSoulCrystal {
	
	// Static part :
	
	private static final Map<Integer, ItemSoulCrystalFull> monsterIdToCrystalMapping = new HashMap<Integer, ItemSoulCrystalFull>();
	
	public static ItemSoulCrystalFull getCrystalFromMonsterId(int monsterId) {
		return monsterIdToCrystalMapping.get(monsterId);
	}
	
	private static void addToMapping(int monsterId, ItemSoulCrystalFull crystal) {
		monsterIdToCrystalMapping.put(monsterId, crystal);
	}
	
	// Constants :
	
	public static final int DEFAULT_SPAWN_COUNT = 4;
	public static final int DEFAULT_MAX_MOB = 8;
	public static final int DEFAULT_NEEDED_SOUL = 200;
	public static final double DEFAULT_SOUL_DROP_PROBA = 0.5;
	
	// Fields :
	
	public final int monsterId;
	public final String monsterName;
	public final int spawnCount;
	public final int maxMob;
	public final int neededSouls;
	public final double soulDropProba;
	
	// Constructors :
	
	public ItemSoulCrystalFull(Class<? extends EntityLiving> clazz) {
		this(clazz, DEFAULT_SPAWN_COUNT, DEFAULT_MAX_MOB, DEFAULT_NEEDED_SOUL, DEFAULT_SOUL_DROP_PROBA);
	}
	
	@SuppressWarnings("unchecked")
	public ItemSoulCrystalFull(Class<? extends EntityLiving> clazz, int spawnCount, int maxMob, int neededSouls, double soulDropProba) {
		super(StargateMod.itemName_crystalSoul + EntityList.classToStringMapping.get(clazz), "SF");
		this.setTextureName(StargateMod.itemName_crystalSoul);
		
		if(spawnCount <= 0 || maxMob <= 0 || neededSouls <= 0 || soulDropProba <= 0 || soulDropProba > 1) {
			throw new IllegalArgumentException("0 < spawnCount, 0 < maxMob, 0 < neededSouls, 0 < soulDropProba <= 1");
		}
		
		Map<Integer, Class> entityMapping = EntityList.IDtoClassMapping;
		Integer tmpMonsterId = null;
		
		for(Entry<Integer, Class> entry : entityMapping.entrySet()) {
			if(entry.getValue() == clazz) {
				tmpMonsterId = entry.getKey();
				break;
			}
		}
		
		if(tmpMonsterId == null) {
			throw new RuntimeException(new IllegalArgumentException("Can't find the entity id for the class \"" + clazz.getSimpleName() + "\". This is a bug !"));
		}
		
		this.monsterId = tmpMonsterId.intValue();
		this.monsterName = EntityList.getStringFromID(this.monsterId);
		this.spawnCount = spawnCount;
		this.maxMob = maxMob;
		this.neededSouls = neededSouls;
		this.soulDropProba = soulDropProba;
		
		addToMapping(this.monsterId, this);
	}
	
}

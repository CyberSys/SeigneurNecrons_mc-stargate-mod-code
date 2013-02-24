package seigneurnecron.minecraftmods.stargate.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;

public class ItemSoulCrystal extends ItemStargate {
	
	private static final Map<Integer, ItemSoulCrystal> monsterIdToCrystalMapping = new HashMap<Integer, ItemSoulCrystal>();
	
	private static final int DEFAULT_SPAWN_COUNT = 4;
	private static final int DEFAULT_MAX_MOB = 8;
	
	private final int monsterId;
	private final String monsterName;
	private final int spawnCount;
	private final int maxMob;
	
	public ItemSoulCrystal(int id, Class<? extends EntityLiving> clazz) {
		this(id, clazz, DEFAULT_SPAWN_COUNT, DEFAULT_MAX_MOB);
	}
	
	public ItemSoulCrystal(int id, Class<? extends EntityLiving> clazz, int spawnCount) {
		this(id, clazz, spawnCount, DEFAULT_MAX_MOB);
	}
	
	public ItemSoulCrystal(int id, Class<? extends EntityLiving> clazz, int spawnCount, int maxMob) {
		super(id, StargateMod.itemSoulCrystalTextureIndex, "");
		this.spawnCount = spawnCount;
		this.maxMob = maxMob;
		
		EntityLiving entity = getEntityFromClass(clazz);
		int monsterId = EntityList.getEntityID(entity);
		String monsterName = EntityList.getEntityString(entity);
		
		this.monsterId = monsterId;
		this.monsterName = monsterName;
		this.setItemName("crystalSoul" + monsterName);
		
		addToMapping(monsterId, this);
	}
	
	public int getMonsterId() {
		return this.monsterId;
	}
	
	public String getMonsterName() {
		return this.monsterName;
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
	
	private static EntityLiving getEntityFromClass(Class<? extends EntityLiving> clazz) {
		EntityLiving entity = null;
		
		try {
			entity = clazz.getConstructor(new Class[] {World.class}).newInstance(new Object[] {null});
		}
		catch(Exception argh) {
			entity = new EntityZombie(null);
            throw new RuntimeException(clazz.getSimpleName() + " could not be instantiated ! This is a bug !");
		}
		
		return entity;
	}
	
}

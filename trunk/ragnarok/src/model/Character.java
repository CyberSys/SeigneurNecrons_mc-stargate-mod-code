package model;

import java.util.HashMap;
import java.util.Map.Entry;

import model.exception.LvlOutOfRangeException;

public abstract class Character{
	protected static final int RESET_STAT_VALUE		= 1;
	protected static final int FIRST_LVL			= 1;
	protected static final int STAT_POINT_RATIO		= 5;
	protected static final int STAT_POINT_CONSTANT	= 2;
	
	protected String name;
	protected int lvl;
	protected int statPoints;
	protected HashMap<String,Integer> stats;
	
	protected Character(String name){
		this.name	= name;
		this.lvl	= 1;
		this.stats	= new HashMap<String, Integer>();
		resetAllStats();
	}
	
	// getters:
	public String getName(){
		return this.name;
	}
	
	public int getLvl(){
		return this.lvl;
	}
	
	public int getStatPoints(){
		return this.statPoints;
	}
	
	public int getStat(String statName){
		return this.stats.get(statName);
	}
	
	public abstract String getClassName();
	
	
	// setters:
	public void setLvl(int lvl) throws LvlOutOfRangeException{
		if(lvl <= 0 || lvl > 99) throw new LvlOutOfRangeException(lvl);
		int diff = this.lvl - lvl;
		if(diff <= 0){
			for(; diff < 0 ; ++diff){
				++this.lvl;
				this.statPoints += getStatPointsForCurrentLevel();
			}
		}else{
			this.lvl = lvl;
			resetAllStats();
		}
	}
	
	public void setStatPoint(int statPoints){
		this.statPoints = statPoints;
	}
	
	//others:
	protected void resetAllStats(){
		this.statPoints = 0;
		for(int i = getLvl() ; i >= Character.FIRST_LVL ; --i)
			this.statPoints += getStatPointsForLevel(i);
		this.stats.put("Str", Character.RESET_STAT_VALUE);
		this.stats.put("Agi", Character.RESET_STAT_VALUE);
		this.stats.put("Vit", Character.RESET_STAT_VALUE);
		this.stats.put("Int", Character.RESET_STAT_VALUE);
		this.stats.put("Dex", Character.RESET_STAT_VALUE);
		this.stats.put("Luk", Character.RESET_STAT_VALUE);
	}

	protected int getStatPointsForCurrentLevel(){
		return getStatPointsForLevel(getLvl());
	}
	
	protected abstract int getStatPointsForLevel(int lvl);
	
	public String toString(){
		StringBuilder str = new StringBuilder("<" + getName() + ", " + getClassName() + " Lvl: " + getLvl() + ">\n");
		for(Entry<String, Integer> stat : this.stats.entrySet())
			str.append("	" + stat.getKey() + " : " + stat.getValue() + "\n");
		str.append("	--------\n");
		str.append("	stat points : " + getStatPoints() + "\n");
		str.append("________________________\n");
		return str.toString();
	}
}

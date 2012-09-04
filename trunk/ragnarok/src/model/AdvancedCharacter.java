package model;

public abstract class AdvancedCharacter extends Character{
	private static final int STAT_POINTS_OF_FIRST_LV = 100;
	
	protected AdvancedCharacter(String name){
		super(name);
	}
	
	@Override
	protected int getStatPointsForLevel(int lvl){
		return lvl == Character.FIRST_LVL?AdvancedCharacter.STAT_POINTS_OF_FIRST_LV:((int)lvl/Character.STAT_POINT_RATIO) + Character.STAT_POINT_CONSTANT;
	}
}

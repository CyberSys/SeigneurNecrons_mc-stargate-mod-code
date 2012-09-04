package model;

public abstract class NormalCharacter extends Character{
	private static final int STAT_POINTS_OF_FIRST_LV = 48;
	
	protected NormalCharacter(String name){
		super(name);
	}
	
	@Override
	protected int getStatPointsForLevel(int lvl){
		return lvl == Character.FIRST_LVL?NormalCharacter.STAT_POINTS_OF_FIRST_LV:((int)lvl/Character.STAT_POINT_RATIO) + Character.STAT_POINT_CONSTANT;
	}
}

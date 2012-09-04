package model.exception;

public class LvlOutOfRangeException extends Exception{
	private static final long serialVersionUID = -3669373927415455582L;
	
	public LvlOutOfRangeException(int lvl){
		super("Level " + lvl + " is illegal.");
	}
}

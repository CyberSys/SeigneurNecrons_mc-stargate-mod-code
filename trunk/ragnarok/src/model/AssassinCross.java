package model;

public class AssassinCross extends AdvancedCharacter{
	private static final String CLASS_NAME = "Assassin Cross";
	
	public AssassinCross(String name){
		super(name);
	}

	@Override
	public String getClassName(){
		return AssassinCross.CLASS_NAME;
	}
}

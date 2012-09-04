package sandbox;

import model.AssassinCross;
import model.exception.LvlOutOfRangeException;

public class Test01{
	public Test01() throws LvlOutOfRangeException{
		AssassinCross loki = new AssassinCross("Loki");
		System.out.println(loki.toString());
		loki.setLvl(99);
		System.out.println(loki.toString());
		loki.setLvl(20);
		System.out.println(loki.toString());
		loki.setLvl(99);
		System.out.println(loki.toString());
		loki.setLvl(1);
		System.out.println(loki.toString());
	}
	
	public static void main(String... args) throws LvlOutOfRangeException{
		new Test01();
	}
}

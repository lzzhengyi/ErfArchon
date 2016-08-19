package archon;

public class UClass {

	final static boolean female=true;
	final static boolean male=!female;
	
	public boolean gender;
	public String name,attack;
	
	public UClass(String n, String a){
		name=n;
		attack=a;
		gender=female;
	}
	public UClass(String n, String a, boolean g){
		name=n;
		attack=a;
		gender=g;
	}
}

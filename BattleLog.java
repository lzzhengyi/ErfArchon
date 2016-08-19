package archon;

public class BattleLog {

	int day, year;
	String log;
	
	public BattleLog(int d, int y, String s){
		day=d;
		year=y;
		log=s;
	}
	public String getName(){
		return "Log:"+year+"."+day;
	}
	public String getLog(){
		return log;
	}
}

package archon;

import static archon.AConst.*;
import java.util.ArrayList;

public class City {
	
	int lvl, UClassID;
	String name;
	int progress=0;//progress to making next unit
	int growth=0;//progress to next level
	int growrate;
	int orientation;
	
	public City(String n, int ucid){
		name=n;
		UClassID=ucid;
		initCity();
	}
	public City(){
		name=AConst.nf.getLastName();
		UClassID=AConst.random.nextInt(AConst.UCLASSES.length-1)+1;
		initCity();
	}
	private void initCity(){
		orientation=AConst.random.nextInt(ORIENTS.length);
		lvl=1;
		growrate=AConst.random.nextInt(4)+2;
	}
	public ArrayList<Archon> getGarrison(){
		ArrayList<Archon>g=new ArrayList<Archon>();
		for (int i=0;i<lvl*3;i++){
			Archon a =new Archon(UClassID);
			g.add(a);
			a.isConstruct=true;
		}
		return g;
	}
	/**
	 * perform this every tick
	 */
	public boolean grow (){
		boolean b=false;
		growth+=1;
		if (growth>=getAmtToGrow()){
			growth=0;
			lvl++;
			b=true;
		}
		return b;
	}
	private int getAmtToGrow(){
		return (int) Math.pow(growrate, lvl);
	}
	/**
	 * returns an arraylist containing any units generated during the turn
	 */
	public ArrayList<Archon> popUnit(){
		ArrayList<Archon>popped=new ArrayList<Archon>();
		progress+=lvl;
		while (progress>5){
			progress-=5;
			popped.add(new Archon(UClassID));
		}
		return popped;
	}
	public String toString(){
		return name+"("+lvl+", "+AConst.UCLASSES[UClassID].name+")";
	}
	public ArrayList<String> getSummary(){
		ArrayList<String>summary=new ArrayList<String>();
		summary.add("Name: "+name);
		summary.add("Units: "+AConst.UCLASSES[UClassID].name);
		summary.add("Level: "+lvl);
		summary.add("Progress");
		summary.add("To next unit: "+progress+"/5");
		summary.add("To next level: "+growth+"/"+getAmtToGrow());
		return summary;
	}
}

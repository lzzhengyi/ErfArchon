package archon;

import static archon.AConst.*;
import java.util.ArrayList;

public class Faction {

	String name;
	public ArrayList<City>cities=new ArrayList<City>();
	public ArrayList<Archon>archons=new ArrayList<Archon>();
	int unittype=AConst.random.nextInt(AConst.UCLASSES.length-1)+1;
	int treasury=0;
	Archon leader;
	
	private ArrayList<Archon>east=new ArrayList<Archon>();
	private ArrayList<Archon>west=new ArrayList<Archon>();
	private ArrayList<Archon>north=new ArrayList<Archon>();
	private ArrayList<Archon>south=new ArrayList<Archon>();
	private ArrayList<Archon>center=new ArrayList<Archon>();
	ArrayList<Archon>[] armies=new ArrayList[5];
	
	public Faction(){
		name=AConst.nf.getLastName();
		unittype=AConst.random.nextInt(AConst.UCLASSES.length-1)+1;
		armies[EAST]=east;
		armies[WEST]=west;
		armies[NORTH]=north;
		armies[SOUTH]=south;
		armies[CENTER]=center;
		generateLeader();
	}
	public void generateArchon(){
		archons.add(new Archon(unittype));
	}
	public void generateLeader(){
		//if armies are greater than zero, promote the one with leadership
		//else make one from scratch
		leader=new Archon(unittype);
		leader.promoteElite();
		leader.promoteElite();
		
		leader.grade="Ruler";
	}
	/**
	 * Proportionally allocate armies to the five general defensive regions of a faction
	 * This allocation is reset every turn
	 */
	private void allocateArmies(){
		//clear current armies
		for (ArrayList<Archon> al:armies){
			al.clear();
		}
		//get proportion of cities in each area
		int[] citycount=new int[5];
		for (int i=0;i<citycount.length;i++){
			citycount[i]=0;
		}
		for (City c:cities){
			citycount[c.orientation]++;
		}
//		for (int i=0;i<citycount.length;i++){
//			System.out.println(citycount[i]);
//		}		
		//assign archons
		ArrayList<Archon>unassigned=(ArrayList<Archon>) archons.clone();
		while (unassigned.size()>0){
			int destination=random.nextInt(cities.size());
			if (destination<
					citycount[EAST]){
				armies[EAST].add(unassigned.remove(random.nextInt(unassigned.size())));
			} else if (destination<
					citycount[EAST]+citycount[WEST]
					){
				armies[WEST].add(unassigned.remove(random.nextInt(unassigned.size())));
			} else if (destination<
					citycount[EAST]+citycount[WEST]+citycount[NORTH]
					){
				armies[NORTH].add(unassigned.remove(random.nextInt(unassigned.size())));
			} else if (destination<
					citycount[EAST]+citycount[WEST]+citycount[NORTH]+citycount[SOUTH]
					){
				armies[SOUTH].add(unassigned.remove(random.nextInt(unassigned.size())));
			} else if (destination<
					citycount[EAST]+citycount[WEST]+citycount[NORTH]+citycount[SOUTH]+citycount[CENTER]
					){
				armies[CENTER].add(unassigned.remove(random.nextInt(unassigned.size())));
			} 
		}
	}
	public void tickCycle(boolean isPlayer){
		if (leader==null || leader.checkIfDead()){
			generateLeader();
		}
		for (City c : cities){
			//progress towards next city level
			c.grow();
			//gather all popped archons to city
			if (!isPlayer)
				archons.addAll(c.popUnit());
		}
		treasury+=calcIncome();
		allocateArmies();
		//prepare invasions
		pruneDead();
	}
	/**
	 * Income of a faction's subdivision
	 */
	public int calcIncome (int or){
		int inc=0;
		for (int i=0;i<cities.size();i++){
			if (cities.get(i).orientation==or){
				inc+=cities.get(i).lvl;
			}
		}
		return inc;
	}
	/**
	 * Total faction income
	 */
	public int calcIncome (){
		int inc=0;
		for (int i=0;i<cities.size();i++){
			inc+=cities.get(i).lvl;
		}
		return inc;
	}
	/**
	 * Clears both the archon list and the army lists
	 */
	public void pruneDead(){
		for (int i=archons.size()-1;i>=0;i--){
			if (archons.get(i).checkIfDead()){
				archons.remove(i);
			}
		}
		for (int i=0;i<armies.length;i++){
			for (int j=armies[i].size()-1;j>=0;j--){
				if (armies[i].get(j).checkIfDead()){
					armies[i].remove(j);
				}
			}
		}
	}
	public ArrayList<String> getSummary(){
		ArrayList<String>summary=new ArrayList<String>();
		summary.add("Name: "+name);
		summary.add("Leader: "+leader.summarize());
		summary.add("Units: "+AConst.UCLASSES[unittype].name);
		summary.add("Cities: "+cities.size());
		summary.add("Treasury: "+treasury);
		summary.add("Army: "+archons.size());
		return summary;
	}
}

package archon;

import java.util.ArrayList;

public class World {

	int turncount=0;
	public Faction player=new Faction();
	ArrayList<BattleLog> battlelogs=new ArrayList<BattleLog>();
	public ArrayList<Faction> factions=new ArrayList<Faction>();
	ArrayList<Contract> contracts=new ArrayList<Contract>();
	
	public World(){
		player.name="DarkComm";
		player.unittype=0;
		player.cities.add(new City());
		player.cities.get(0).name="DarkHQ";
		player.cities.get(0).lvl=5;
		
		//add npc factions
		for (int i=0;i<100;i++){
			factions.add(new Faction());
			factions.get(i).cities.add(new City());
		}
	}
	/**
	 * Part 1 in the world life cycle
	 * Do this after a player attack
	 * or
	 * After the player decides to complete contract selection
	 */
	public void tickCycle(){
		turncount++;
		//tick cycle for all factions
		for (int i=0;i<factions.size();i++){
			factions.get(i).tickCycle(false);
		}
		player.tickCycle(true);
		//prune dead for all factions
		//generate contracts		
	}
	/**
	 * Resolve all battles in the world
	 * after this, give control back to the player
	 */
	public void resolveContracts(){
		//resolve all contracts
		//prunedead for all factions
	}
	/**
	 * Adds a new player to the player object
	 */
	public void addPlayerArchon(){
		player.generateArchon();
	}
	public void addPlayerArchon(Archon a){
		player.archons.add(a);
	}
	public String[] getFactionString(){
		String[] fnames=new String[factions.size()];
		for (int i=0;i<fnames.length;i++){
			fnames[i]=factions.get(i).name;
		}
		return fnames;
	}
}

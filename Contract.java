package archon;

import java.util.ArrayList;

/**
 * You can see which factions are involved in the contract,
 * and the size of the armies involved
 * 
 * Current contract thoughts:
 * If the timer is kept, a contract is opened by a weaker civ
 * The contract randomly closes, or when the current civ becomes the strongest
 * 
 */
public class Contract {

	boolean proattack=false;
	Faction attacker, defender;
	City city; //the defender holds this city
	ArrayList<Archon>mercs=new ArrayList<Archon>();
	
	/**
	 * Dont use this constructions
	 */
	public Contract(Faction a, Faction d, City c){
		attacker=a;
		defender=d;
		city=c;
	}
	/**
	 * a is the client who will use the archons in any relevant battles
	 */
	public Contract(Faction a){
		attacker=a;
		defender=null;
		city=null;
	}
	/**
	 * Returns true if the archon was added and false if it was not
	 */
	public boolean addArchon (Archon a){
		if (mercs.size()<6){
			mercs.add(a);
			return true;
		} else {
			return false;
		}
	}
//	public void acceptContract(ArrayList<Archon> archons, boolean side){
//		mercs.addAll(archons);
//		proattack=side;
//	}
	public ArrayList<String> resolveContract(boolean choice){
		ArrayList<Archon> attackers=(ArrayList<Archon>) attacker.archons.clone();
		ArrayList<Archon> defenders=(ArrayList<Archon>) defender.archons.clone();
		defenders.addAll(city.getGarrison());
		ArcBattle ab=new ArcBattle(attackers,defenders);
		return ab.resolveArcBattle();
	}
}

package archon;
import static archon.AConst.*;

import java.util.ArrayList;

public class Archon implements Comparable{
	
	String name, grade;
	int[]stats;
	int[]specials;
	String soloact, groupact;
	boolean isConstruct=false; //garrison units, or golems
	boolean isDilligent=false;
	boolean isAmbitious=false;
	boolean isRepulsive=false;
	ArrayList<MCard> spells=new ArrayList<MCard>();
	ArrayList<MCard> readyspells=new ArrayList<MCard>();
	UClass uclass;
	
	int lvl, exp, fear, loyalty;
	int damage, shield, wall, evade, poison;
	boolean isDefending=false;
	
	public Archon(){
		uclass=UCLASSES[0];
		if (uclass.gender){
			name=nf.getFemaleName();
		} else{
			name=nf.getMaleName();
		}
		initNovice();
	}
	public Archon(int ucid){
		uclass=UCLASSES[ucid];
		if (uclass.gender){
			name=nf.getFemaleName();
		} else{
			name=nf.getMaleName();
		}
		initNovice();
	}
	private void initNovice(){
		grade="";
		soloact=SOLOACTS[random.nextInt(SOLOACTS.length)];
		groupact=GROUPACTS[random.nextInt(GROUPACTS.length)];
		lvl=1;
		exp=0;
		fear=0;
		stats=new int [STATNAMES.length];
		for (int i=0;i<stats.length;i++){
			stats[i]=random.nextInt(5)+4;
		}
		stats[HP]+=15;
		specials=new int[SPECNAMES.length];
		for (int i=0;i<specials.length;i++){
			specials[i]=0;
		}
		if (random.nextDouble()<0.1){
			if (random.nextDouble()<0.5){
				isDilligent=true;
			} else {
				if (random.nextDouble()<0.75){
					isAmbitious=true;
				} else {
					isRepulsive=true;
				}
			}
		}
		clearStatus();
	}
	/**
	 * Used to promote generic faction units to elite level
	 */
	public void promoteElite(){
		grade="Elite";
		stats[HP]+=10;
		for (int i=0;i<stats.length;i++){
			stats[i]+=random.nextInt(4);
		}
		gainRandomSpecial();
		gainRandomSpecial();
		gainRandomSpecial();
	}
	public String levelup(){
		lvl++;
		for (int i=0;i<stats.length;i++){
			stats[i]+=random.nextInt(2);
		}
		stats[HP]++;
		if (lvl>1){
			gainRandomSpecial();			
		}
		damage=0;
		readySpells();
		return name+" gains a level!";
	}
	public void gainRandomSpecial(){
		int sb=random.nextInt(specials.length);
		if (sb==MA){
			gainSpells();
		}
		specials[sb]++;
	}
	/**
	 * Adds two random spells to the spell repetoire
	 */
	public void gainSpells(){
		for (int i=0;i<2;i++){
			spells.add(ATTACKS[random.nextInt(ATTACKS.length)]);
		}
	}
	/**
	 * Returns a string describing exp gain and any levels gained
	 */
	public String gainEXP(int i){
		String s=name+" gains "+i+"experience.";
		exp+=i;
		while (exp>getEXPtoLevel()){
			exp=Math.max(0, exp-getEXPtoLevel());
			s+="\n"+levelup();
		}
		return s;
	}
	public int getEXPtoLevel(){
		return (lvl+1)*(1+lvl)*(1+lvl);
	}
	public String getSoloAct(){
		return name+soloact;
	}
	/**
	 * randomly boosts a stat or fails
	 * returns a string describing it as such
	 */
	public String getRandTraining(){
		int train=random.nextInt(stats.length*10);
		if (train==HP){
			stats[HP]++;
			return name+" goes for a workout! +HP";
		} else if (train==AT){
			stats[AT]++;
			return name+" performs combat drills. +AT";			
		} else if (train==SP){
			stats[SP]++;
			return name+" goes for a cross-country jog. +SP";
		} else {
			return getSoloAct();
		}
	}
	public String getGroupAct(Archon[]othernames){
		String s="";
		for (Archon ss:othernames){
			s+=ss.name+", ";
		}
		return s+"and "+name+groupact;
	}
	/**
	 * returns health if passed statid is invalid
	 */
	public int getStats(int s){
		if (s<stats.length)
			return stats[s];
		else
			return stats[HP];
	}
	/**
	 * returns magic if passed statid is invalid
	 */
	public int getSpecial(int s){
		if (s<specials.length)
			return specials[s];
		else
			return specials[MA];
	}
	public String summarize(){
		String s="";
		s+=name+" the "+uclass.name+" "+grade+" lv"+lvl+"("+exp+"/"+getEXPtoLevel()+")";
		return s;
	}
	public String shortsumm(){
		String s="";
		s+=name+"(lv"+lvl+" "+uclass.name+" "+grade+")";
		return s;
	}
	/**
	 * Sets up the internal arraylist of ready spells to be used in combat
	 */
	public void readySpells(){
		readyspells.clear();
		readyspells.addAll(spells);
		shuffleReadySpells();
	}
	private void shuffleReadySpells(){
		if (readyspells.size()>0){
			for (int i=readyspells.size()-1;i>=0;i--){
				int swap=random.nextInt(readyspells.size());
				MCard temp=readyspells.get(swap);
				readyspells.set(swap, readyspells.get(i));
				readyspells.set(i, temp);
			}			
		}
	}
	public MCard getNextSpell(){
		if (readyspells.size()>0){
			return readyspells.remove(0);
		} else {
			return null;
		}
	}
	/**
	 * Remove any temporary battle statuses
	 */
	public void clearStatus(){
		isDefending=false;
		damage=0;
		shield=0;
		wall=0;
		evade=0;
		poison=0;
	}
	/**
	 * Resolve any increases to stats each turn
	 * resulting from specials (IL and GO)
	 */
	public ArrayList<String> gainBoosts(){
		ArrayList<String>s=new ArrayList<String>();
		if (evade<specials[IL]){
			int gain=random.nextInt(specials[IL]+1);
			if (gain>0){
				evade+=gain;
				s.add(name+" regenerates a veil!");				
			}

		}
		if (specials[GO]>wall){
			int gain=random.nextInt(specials[GO]+1);
			if (gain>0){
				wall+=gain;
				s.add(name+" regenerates construct armor!");	
			}
		}
		return s;
	}
	public ArrayList<String> takeDamage(int d){
		ArrayList<String>s=new ArrayList<String>();
		if (shield>0){
			shield--;
			s.add(name+"'s shield flickers!");
			d=0;
		} else if (wall>0){
			wall--;
			s.add(name+"'s armor cracks!");
			d=d/2;
		}
		int reduction=0;
		if (evade>0 && d>0){
			s.add(name+" is merely grazed by the attack!");
			reduction=evade+0;
			evade=Math.max(evade-d, 0);		
		}
		d=Math.max(d-reduction, 0);
		damage+=d;
		s.add(name+" takes "+d+" damage! ("+(stats[HP]-damage)+"/"+stats[HP]+")");
		if (poison>0){
			damage+=poison;
			s.add(name+" suffers the lingering effects of a curse!");
		}
		return s;
	}
	public String healDamage(int d){
		if (damage<=d){
			d=damage+0;
		}
		damage=Math.max(0, damage-d);
		return name+" is healed for "+d+" damage!("+(stats[HP]-damage)+"/"+stats[HP]+")";
	}
	public String gainShield(int a){
		shield=Math.min(a+shield, 2);
		if (shield-a==0){
			return name+" generates a shield.";
		} else {
			return name+" boosts shield strength.";			
		}
	}
	public String gainArmor(int a){
		wall+=a;
		if (wall-a==0){
			return name+" generates construct armor.";
		} else {
			return name+" boosts armor strength.";			
		}
	}
	public String gainEvade(int a){
		evade+=a;
		if (evade-a==0){
			return name+" is shrouded by a veil.";
		} else {
			return name+" boosts veil strength.";			
		}
	}
	public String gainCurse(int a){
		poison+=a;
		if (poison-a==0){
			return name+" is afflicted by a curse!";
		} else {
			return "The curse on "+name+" grows stronger!";			
		}		
	}
	public boolean checkIfDead(){
		return damage>=stats[HP];
	}
	public int compareTo(Object obj) {
		return ((Archon)obj).stats[SP]-stats[SP];
	}
	public String toString(){
		return name+" Lv"+lvl+"("+exp+"/"+this.getEXPtoLevel()+")";
	}
	public ArrayList<String> getSummary(){
		ArrayList<String> ss=new ArrayList<String>();
		ss.add(toString());
		for (int i=0;i<STATNAMES.length;i++){
			if (i==HP){
				ss.add(STATNAMES[HP]+": "+(stats[HP]-damage)+"/"+stats[HP]);
			} else {
				ss.add(STATNAMES[i]+": "+stats[i]);
			}
		}
		for (int i=0;i<SPECNAMES.length;i++){
			ss.add(SPECNAMES[i]+": "+specials[i]);
		}
		for (int i=0;i<spells.size();i++){
			ss.add(spells.get(i).name);
		}
		return ss;
	}
}

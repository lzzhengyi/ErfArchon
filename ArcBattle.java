package archon;

import static archon.AConst.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ArcBattle {

	ArrayList <Archon> alpha, beta, alphatemp, betatemp, alphapair, betapair;
	int turncount=0;
	ArrayList <Archon []> battles=new ArrayList <Archon []>();
	
	public ArcBattle(ArrayList <Archon> o, ArrayList <Archon> t){
		alpha=o;
		beta=t;
		//alphatemp stores the living characters that can be attacked
		alphatemp=new ArrayList <Archon>();
		betatemp=new ArrayList <Archon>();
		alphatemp.addAll(alpha);
		betatemp.addAll(beta);
		//alphapair stores the unpaired characters
		alphapair=new ArrayList <Archon>();
		alphapair.addAll(alphatemp);
		betapair=new ArrayList <Archon>();
		betapair.addAll(betatemp);
	}
	/**
	 * Remove dead from the temp arrays
	 */
	public void pruneDead(){
		
		for (int i=alphapair.size()-1;i>=0;i--){
			if (alphapair.get(i).checkIfDead()){
				alphapair.remove(i);
			}
		}
		for (int i=betapair.size()-1;i>=0;i--){
			if (betapair.get(i).checkIfDead()){
				betapair.remove(i);
			}			
		}
		for (int i=alphatemp.size()-1;i>=0;i--){
			if (alphatemp.get(i).checkIfDead()){
				alphatemp.remove(i);
			}
		}
		for (int i=betatemp.size()-1;i>=0;i--){
			if (betatemp.get(i).checkIfDead()){
				betatemp.remove(i);
			}			
		}
	}
	/**
	 * Pair up active warriors 1 on 1,
	 * then pair up the pairs randomly to get flanks
	 * 
	 * Call this before each round
	 */
	public void pairOff(){
		pruneDead();
		while (!alphapair.isEmpty() && !betapair.isEmpty()){
			battles.add(new Archon[]{
					alphapair.remove(AConst.random.nextInt(alphapair.size())),
					betapair.remove(AConst.random.nextInt(betapair.size()))
					});
		}
		while (alphapair.size()>0 && betatemp.size()>0){
			battles.add(new Archon[]{
					alphapair.remove(AConst.random.nextInt(alphapair.size())),
					betatemp.get(AConst.random.nextInt(betatemp.size()))					
			});
		}
		while (betapair.size()>0 && alphatemp.size()>0){
			battles.add(new Archon[]{
					alphatemp.get(AConst.random.nextInt(alphatemp.size())),
					betapair.remove(AConst.random.nextInt(betapair.size()))					
			});
		}
	}
	public ArrayList<String> removeResolvedBattles(){
		ArrayList<String> s=new ArrayList<String>();
		for (int i=battles.size()-1;i>=0;i--){
			if (battles.get(i)[0].checkIfDead() || battles.get(i)[1].checkIfDead()){
				if (!battles.get(i)[0].checkIfDead()){
					alphapair.add(battles.get(i)[0]);
					s.add(battles.get(i)[0].name+" wins!"+battles.get(i)[1].name);
				}
				if (!battles.get(i)[1].checkIfDead()){
					betapair.add(battles.get(i)[1]);
					s.add(battles.get(i)[1].name+" wins!"+battles.get(i)[0].name);
				}
				battles.remove(i);
			}
		}
		return s;
	}
	/**
	 * Intends to resolve the entire arcbattle and return a log
	 * 
	 * Procedure:
	 * resolve 100 turns, if defender still alive, defender wins
	 * each turn check to see if one side flees
	 */
	public ArrayList<String> resolveArcBattle(){
		ArrayList<String> s=new ArrayList<String>();
		
		return s;
	}
	/**
	 * Resolve one round in all battles in the arrays
	 */
	public ArrayList<String> resolveBattleRound(){
		pairOff();
		ArrayList<String> s=new ArrayList<String>();
		s.add("~Turn "+(++turncount)+"~ ("+alphatemp.size()+" vs "+betatemp.size()+")");
		for (Archon a: alphatemp){
			s.addAll(a.gainBoosts());
		}
		for (Archon b: betatemp){
			s.addAll(b.gainBoosts());
		}
		for (int i=battles.size()-1;i>=0;i--){
			if (!battles.get(i)[0].checkIfDead() && !battles.get(i)[1].checkIfDead()){
				s.add("---");
				s.addAll(resolveDuelRound(battles.get(i)[0],battles.get(i)[1]));
				//add back victors to the pool to be paired up
				if (battles.get(i)[0].checkIfDead() || battles.get(i)[1].checkIfDead()){
					if (!battles.get(i)[0].checkIfDead()){
						alphapair.add(battles.get(i)[0]);
						s.add(battles.get(i)[0].name+" wins!"+battles.get(i)[1].name);
					}
					if (!battles.get(i)[1].checkIfDead()){
						betapair.add(battles.get(i)[1]);
						s.add(battles.get(i)[1].name+" wins!"+battles.get(i)[0].name);
					}
					battles.remove(i);
				}
			}
		}
		removeResolvedBattles();
		pruneDead();
		return s;
	}
	public ArrayList<String> resolveDuelRound(Archon one, Archon two){
		ArrayList<String> s=new ArrayList<String>();
		if (!one.checkIfDead() && !two.checkIfDead()){
			PriorityQueue<Archon> pq=new PriorityQueue<Archon>();
			pq.add(one);
			pq.add(two);
			while (pq.size()>0){
				Archon a=pq.poll();
				if (!a.checkIfDead()){
					if (a==one){
						s.addAll(attack (one, two));
					} else if (a==two){
						s.addAll(attack (two,one));
					}				
				}
			}
			if (one.checkIfDead()){
				s.add(one.name+" has fallen!");
			}
			if (two.checkIfDead()){
				s.add(two.name+" has fallen!");
			}			
		}
		return s;
	}
	private ArrayList<String> attack(Archon a, Archon d){
		ArrayList<String> s=new ArrayList<String>();
		MCard nextspell=a.getNextSpell();
		int damage=AConst.random.nextInt(a.stats[AT]+1)+1;
		if (nextspell!=null){
			s.add(a.shortsumm()+" casts a "+nextspell.name+" spell!");
			a.isDefending=!nextspell.isOffensive;
			damage+=a.specials[MA];
			if (nextspell.cardID==0){
				//storm
				damage=a.stats[AT];
				if (d.wall>0){
					damage*=2;
				}
				s.addAll(d.takeDamage(damage));
				a.isDefending=false;
			} else if (nextspell.cardID==1){
				//pulse
				if(d.shield>0){
					d.shield=0;
					s.add(d.name+"'s shield shatters!");
				}
				if (d.isDefending){
					damage*=2;
				}
				s.addAll(d.takeDamage(damage));
				a.isDefending=false;
			} else if (nextspell.cardID==2){
				//flare
				damage+=turncount;
				if (d.wall>0){
					damage*=2;
				}
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==3){
				//drain
				s.addAll(d.takeDamage(damage));
				if (damage/2>a.damage){
					s.add(a.gainEvade(damage/2-a.damage));
				}
				s.add(a.healDamage(damage/2));
			} else if (nextspell.cardID==4){
				//lance
				if(d.evade>0){
					d.evade=0;
					s.add(d.name+"'s veil is blown away!");
				}
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==5){
				//beam
				if (!d.isDefending){
					damage*=2;
				}
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==6){
				//bolt
				if(d.shield>0){
					d.shield=0;
					s.add(d.shortsumm()+"'s shield shatters!");
				}
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==7){
				//restore
				if (damage>a.damage){
					s.add(a.gainEvade(damage-a.damage));
				}
				s.add(a.healDamage(damage));
			} else if (nextspell.cardID==8){
				//shield
				s.add(a.gainShield(a.specials[MA]));
			} else if (nextspell.cardID==9){
				//armor
				s.add(a.gainArmor(a.specials[MA]));
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==10){
				//dazzle
				s.add(a.gainEvade(a.specials[MA]));
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==11){
				//curse
				s.add(d.gainCurse(a.specials[MA]));
				s.addAll(d.takeDamage(damage));
			} else if (nextspell.cardID==12){
				//meteor
				s.addAll(d.takeDamage(AConst.random.nextInt(damage/2+1)+1));
				s.addAll(d.takeDamage(AConst.random.nextInt(damage/2+1)+1));
				s.addAll(d.takeDamage(AConst.random.nextInt(damage/2+1)+1));
			}
		} else {
			damage+=a.specials[GO];
			s.add(a.shortsumm()+" uses a "+a.uclass.attack+" attack!");
			s.addAll(d.takeDamage(damage));
			a.isDefending=false;
		}
		return s;
	}
}

package archon;

import java.util.ArrayList;

public class ADriver {

	public static void main (String[] args){
//		NameFactory nf=new NameFactory();
		ArchonFrameBuilder afb=new ArchonFrameBuilder();
		afb.buildFrame();
		
		Archon[]ga=new Archon[]{
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon()
		};
		Archon[]gb=new Archon[]{
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon(),
				new Archon()
		};
//		gb[0].promoteElite();
//		ga[2].promoteElite();
//		for (int i=0;i<gb.length;i++){
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//			ga[i].levelup();
//			gb[i].levelup();
//		}
		Archon a=new Archon();
		a.gainSpells();
		a.gainSpells();
		Archon b=new Archon();
		b.gainSpells();
		b.gainSpells();
		System.out.println(a.stats[0]);
		System.out.println(a.getSoloAct());
		System.out.println(a.getGroupAct(ga));
		a.specials[0]=2;
		b.specials[0]=2;
		a.levelup();
		for (int i=0;i<8;i++){
			a.levelup();
			b.levelup();
		}
		a.readySpells();
		b.readySpells();
		System.out.println(a.summarize());
		System.out.println(b.summarize());
		System.out.println(a.name+a.stats[2]+" "+b.name+b.stats[2]);
		
		ArrayList<Archon> aa=new ArrayList<Archon>();
		for (Archon aaa : ga){
			aaa.promoteElite();
			aa.add(aaa);
		}
		ArrayList<Archon> bb=new ArrayList<Archon>();
		bb.add(a);
		bb.add(b);
		b.promoteElite();
		a.promoteElite();
		b.promoteElite();
		a.promoteElite();
//		for (Archon bbb : gb){
//			bb.add(bbb);
//		}
		ArcBattle ab=new ArcBattle(aa,bb);
		for (int i=0;i<10;i++){
			ArrayList<String>s=ab.resolveBattleRound();
			for (String ss:s){
				System.out.println(ss);
			}
		}
//		Faction f1=new Faction();
//		Faction f2=new Faction();
//		for (int i=0;i<10;i++){
//			f1.cities.add(new City());
//			f2.cities.add(new City());
//		}
//		for (int i=0;i<10;i++){
//			f1.tickCycle();
//			f2.tickCycle();
//		}
//		System.out.println(f1.archons.size()+" "+f2.archons.size());
//		for (int i=0;i<f1.armies.length;i++){
//			System.out.println(f1.armies[i].size()+" "+f2.armies[i].size());
//		}
//		ArcBattle fb=new ArcBattle(f1.east,f2.east);
//		for (int i=0;i<10;i++){
//			ArrayList<String>s=fb.resolveBattleRound();
//			for (String ss:s){
//				System.out.println(ss);
//			}
//		}
	}
}

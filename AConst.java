package archon;

import java.util.Random;

public class AConst {

	final static Random random=new Random();
	final static NameFactory nf=new NameFactory();
	
	final static String[] SOLOACTS={
		" is reading magazines.",
		" is reading really trashy magazines.",
		" is reading magazines 'for the stories'.",
		" is humming a tune.",
		" is whistling a tune.",
		" is trying to whistle, but failing badly.",
		" is watering a potted plant.",
		" is watering a dessicated potted plant.",
		" is guiltily sneaking some candy.",
		" is sneaking some delicious cake.",
		" is watching the horizon.",
		" is in a cloud-gazing reverie.",
		" is lost in stargazing.",
		" is baking sweet treats",
		" is baking something that smells lovely!",
		" is pulling something charred from an oven.",
		" is reading a romance novel.",
		" has gone for a pony ride.",
		" is playing with a puppy.",
		" is playing with a kitten.",
		" is playing solitaire.",
		" is watching a movie.",
		" is avidly watching a movie",
		" is lost in a good book.",
		" is singing softly, hoping no one will hear.",
		" is practicing an aria for anyone who will listen.",
		" is simply watching the others.",
		" is TOTALLY BORED! >_<'",
		" is totally lost in a daydream.",
		" is preening in front of a mirror.",
		" is riffling through the wardrobe.",
		" is shopping for new attire.",
		" is shopping for new accessories.",
		" is shopping for new shoes.",
		" is hunting for bargains.",
		" is wishing for more money to go shopping.",
		" is buying something unaffordable.",
		" is idly browsing the store windows.",
		" is drooling while windowgazing.",
		" is eavesdropping on conversations.",
		" is hiding under the covers, pretending to sleep.",
		" is practicing card tricks to impress others with later.",
		" is meditating to dispel worries and fears.",
		" is meditating in search of peace of mind.",
		" is concentrating hard on sewing.",
		" is ranting about one thing or another.",
		" is complaining to anyone who will listen...about nothing.",
		" is yelling into the wind about jilted love and bad romance.",
		" is hunting in dark places for ghosts - then running scared.",
	};
	final static String[] GROUPACTS={
			" are having a pillow fight!",
			" are throwing a sweet dance party!",
			" are throwing a happening dance party!",
			" are throwing a sophisticated soiree!",
			" are throwing a sweet dance party!",
			" are having a chill baking party.",
			" are having a singing competition.",
			" are discussing favorite magazines.",
			" are discussing favorite celebrities.",
			" are discussing favorite novels.",
			" are having a slumber party.",
			" are sharing hot chocolate while stargazing.",
			" are having a horse race!",
			" are sharing a laugh on some trivial matter.",
			" are discussing politics in heated tones.",
			" are exchanging gossip in hushed voices.",
			" are gossiping idly.",
			" are swapping juicy rumors.",
		};	
	
	
	final static UClass[] UCLASSES={
		new UClass("Archon","Shock"),
		new UClass("Pirate","Cutlass"),
		new UClass("Valkyrie","Sacred Spear"),
		new UClass("Knight","Charge"),
		new UClass("Ninja","Shuriken"),
		new UClass("Dragoon","Saber"),
		new UClass("Lancer","Lance"),
		new UClass("Lycanthrope","Fury"),
		new UClass("Musketeer","Barrage"),
		new UClass("Vampire","Darkness"),
		new UClass("Catfolk","CatScratch"),
		new UClass("Demonspawn","Rend"),
		new UClass("Magician","Missile"),
		new UClass("Druid","Slash"),
		new UClass("Cleric","Holy"),
		new UClass("Dark Cleric","Profane"),
		new UClass("High Elf","Dagger"),
		new UClass("Dark Elf","Blade"),
		new UClass("Pixie","Dust"),
		new UClass("Night Pixie","Dust"),
		new UClass("Sun Pixie","Dust"),
		new UClass("Harpy","Talon"),
	};
	final static int HP=0;
	final static int AT=1;
	final static int SP=2;
	
	final static int MA=0;
	final static int DA=1;
	final static int LA=2;
	final static int GO=3;
	final static int IL=4;
	
	final static String[] STATNAMES={
		"Health",
		"Attack",
		"Speed",
	};
	final static String[]SPECNAMES={
		"Magic",
		"Dance",
		"Leadership",
		"Golemancy",
		"Illusion"
	};
	final static MCard[]ATTACKS={
		new MCard("Storm",true,0),
		new MCard("Pulse",true,1),
		new MCard("Flare",true,2),
		new MCard("Drain",true,3),
		new MCard("Ray",true,4),
		new MCard("Beam",true,5),
		new MCard("Bolt",true,6),
		new MCard("Restore",false,7),
		new MCard("Shield",false,8),
		new MCard("Force",false,9),
		new MCard("Dazzle",false,10),
		new MCard("Curse",true,11),
		new MCard("Meteor",true,12),
	};
	
	final static String[]ORIENTS={
		"East","West","North","South","Center"	
	};
	
	final static int EAST=0;
	final static int WEST=1;
	final static int NORTH=2;
	final static int SOUTH=3;
	final static int CENTER=4;
}

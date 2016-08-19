package archon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameFactory {

	static String[] fn, ln, malen,femalen;
	final static String con = "bcdfghjklmnpqrstvwxy";
	final static String vow = "aeiou";
	
	String [] firstNames, lastNames, syllables;
	
	public NameFactory (){
		firstNames = new String [100];
		lastNames = new String [200];
		initializeSyllables ();
		initializeNames();
		try {
			BufferedReader br=new BufferedReader (new FileReader("CSV_Database_of_First_Names.csv"));
			BufferedReader br2=new BufferedReader (new FileReader("CSV_Database_of_Last_Names.csv"));
			BufferedReader br3=new BufferedReader (new FileReader("male only.csv"));
			BufferedReader br4=new BufferedReader (new FileReader("female only.csv"));
			List <String> l=new ArrayList <String>();
			List <String> l2=new ArrayList <String>();
			List <String> l3=new ArrayList <String>();
			List <String> l4=new ArrayList <String>();
			String line=null;
			try {
				while ((line=br.readLine())!=null){
					l.add(line);
				}
				while ((line=br2.readLine())!=null){
					l2.add(line);
				}
				while ((line=br3.readLine())!=null){
					l3.add(line);
				}
				while ((line=br4.readLine())!=null){
					l4.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
				br2.close();
				br3.close();
				br4.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fn=l.toArray(new String[l.size()]);
			ln=l2.toArray(new String[l2.size()]);
			malen=l3.toArray(new String[l3.size()]);
			femalen=l4.toArray(new String[l4.size()]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Names loaded:"+fn.length+" "+malen.length+" "+femalen.length+" "+ln.length);
	}
	public void initializeSyllables (){
		syllables = new String [100];
		for (int i=0;i<syllables.length;i++){
			syllables[i]=genSyllable();
		}
	}
	public void initializeNames (){
		Random rand = new Random ();
		for (int i=0;i<firstNames.length;i++){
			firstNames[i]="";
			firstNames[i]+=getSyllable();
			if (rand.nextBoolean()){
				firstNames[i]+=getSyllable();
			}
			firstNames[i]=firstNames[i].substring(0, 1).toUpperCase()+firstNames[i].substring(1);
		}
		for (int i=0;i<lastNames.length;i++){
			lastNames[i]="";
			lastNames[i]+=getSyllable();
			if (rand.nextBoolean()){
				lastNames[i]+=getSyllable();
				if (rand.nextBoolean()){
					lastNames[i]+=getSyllable();
				}
			}
			if (rand.nextBoolean()){
				lastNames[i]+=getSyllable();
			}
			lastNames[i]=lastNames[i].substring(0, 1).toUpperCase()+lastNames[i].substring(1);
		}
	}
	/*
	 * returns a random syllable from an initialized syllabary
	 */
	public String getSyllable (){
		Random rand = new Random ();
		return syllables[rand.nextInt(syllables.length)];
	}
	public String genSyllable (){
		Random rand = new Random ();
		String s = "";
		boolean constart = false;
		boolean conend = false;
		if (rand.nextBoolean()){
			s+=con.charAt(rand.nextInt(con.length()));
			constart = true;
		}
		if (rand.nextBoolean()&&!constart){
			s+=vow.charAt(rand.nextInt(vow.length()));
			s+=con.charAt(rand.nextInt(con.length()));
			constart = true;
		}
		s+=vow.charAt(rand.nextInt(vow.length()));
		if (rand.nextBoolean()||!constart){
			s+=con.charAt(rand.nextInt(con.length()));
			conend = true;
		}
		if (rand.nextBoolean()&& conend){
			s+=vow.charAt(rand.nextInt(vow.length()));
		}
		return s;
	}
	public String getFirstName (){
		Random rand = new Random ();
//		return firstNames[rand.nextInt(firstNames.length)];
		return fn[rand.nextInt(fn.length)];
	}
	public String getMaleName (){
		Random rand = new Random ();
//		return firstNames[rand.nextInt(firstNames.length)];
		return toSentenceCase(malen[rand.nextInt(malen.length)]);
	}
	public String getFemaleName (){
		Random rand = new Random ();
//		return firstNames[rand.nextInt(firstNames.length)];
		return toSentenceCase(femalen[rand.nextInt(femalen.length)]);
	}
	public String getLastName (){
		Random rand = new Random ();
//		return lastNames[rand.nextInt(lastNames.length)];
		return ln[rand.nextInt(ln.length)];
	}
	private String toSentenceCase(String s){
		return s.charAt(0)+s.substring(1).toLowerCase();
	}
}

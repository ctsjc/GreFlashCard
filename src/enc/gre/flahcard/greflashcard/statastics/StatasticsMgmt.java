package enc.gre.flahcard.greflashcard.statastics;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import enc.gre.flahcard.greflashcard.dataType.Statastics;



/**
 * when particular word has seen till date.
 * Every day a new stat object should be created.
 * When hit count = 0 
 * 		Add to newWord
 * else
 * 		Add to allWord
 * 
 * 	Total time spent on the 
 * 		One Word ------------> another Word
 * 							Time spent on the word
 * 		condition
 * 		App is running as a foreground 
 * 		while screen is alive (but thats not a necessary)
 * 	
 * */
public class StatasticsMgmt {
	ArrayList<Statastics> stats =new ArrayList<Statastics>();
	private static StatasticsMgmt mgmt;
	public static  StatasticsMgmt getInstance(){
		if(mgmt == null)
			mgmt = new StatasticsMgmt();
		return mgmt;
	}
	//  how to know the new day is started 
	//  
	void addNewStats(){
		Statastics statastics=new Statastics();
		statastics.setDate(GregorianCalendar.getInstance());
		stats.add(statastics);
	}//end of addNewStats
	
	public void addNewWord(){
		
	}
}//end of class



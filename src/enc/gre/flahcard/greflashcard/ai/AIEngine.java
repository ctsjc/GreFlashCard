package enc.gre.flahcard.greflashcard.ai;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import enc.gre.flahcard.greflashcard.dataType.Dict;
import enc.gre.flahcard.greflashcard.deck.DeckEngine;

public class AIEngine {
	DeckEngine deckEngine = DeckEngine.getInstance();
	static AIEngine aiEngine;
	private AIEngine(){}

	public static AIEngine getInstance(){
		if(aiEngine == null)
			aiEngine=new AIEngine();
		return aiEngine;
	}
	public void methodEMD(){
		String curDeck = deckEngine.getCurrDeckKey();
		// find the word who has misscount/hitCount is high means word should repeat 
		ArrayList<String> decknames = (ArrayList<String>) deckEngine.getDeckname();
		for(String deckname : decknames){
			deckEngine.setCurrDeckKey(deckname);
			ArrayList<Dict> dicts = deckEngine.getDictionary();
			for(Dict d : dicts){
				method1(d);
			}//end of for
		}
		deckEngine.setCurrDeckKey(curDeck);
	}//end of method1

	private void method1(Dict d) {
		if(d.getHitCount() > 2 && d.getMissCount() > 2){
			String deckname=null;
			long density;
			density = d.getMissCount() / d.getHitCount();
			if(density > 0 && density <= 1){
				deckname="easy";
				addToDeck(deckname, d);
			}

			else if(density >1 && density <=5){
				deckname="medium";
				 removeFrmDeck("easy",d);
				addToDeck(deckname, d);
			}

			else if(density >5){
				deckname="difficult";
				removeFrmDeck("medium",d);
				addToDeck(deckname, d);
			}
			
		}//end of if
	}

	public void methodEMD(Dict d){
		method1(d);
	}
	/**
	 * This method create a deck for each day
	 * */
	public void methodForgotten(){
		String curDeck = deckEngine.getCurrDeckKey();
		// find the word who has misscount/hitCount is high means word should repeat 
		ArrayList<String> decknames = (ArrayList<String>) deckEngine.getDeckname();
		for(String deckname : decknames){
			deckEngine.setCurrDeckKey(deckname);
			ArrayList<Dict> dicts = deckEngine.getDictionary();
			for(Dict d : dicts){
				methodForgottenBase(d);
			}//end of for	
		}
		deckEngine.setCurrDeckKey(curDeck);
	}//end of method2

	public void methodForgotten(Dict d){
		methodForgottenBase(d);
	}//end of methodForgotten
	/**
	 * @param d
	 */
	private void methodForgottenBase(Dict d) {
		String deckname;
		Calendar currDate = GregorianCalendar.getInstance();
		Calendar olderDate=d.getAccessTime();
		int diffInDays = (int) ((currDate.getTime().getTime() - olderDate.getTime().getTime())   / (1000 * 60 * 60 * 24));
		switch (diffInDays) {
		case 5:
			deckname="last 5 days";
			addToDeck(deckname, d);
			break;
		case 15:
			deckname="last 15 days";
			removeFrmDeck("last 5 days",d);
			addToDeck(deckname, d);
			break;
		case 35:
			deckname="last month";
			removeFrmDeck("last 15 days",d);
			addToDeck(deckname, d);
			break;
		default:
			break;
		}
	}

	private void addToDeck(String deckname,Dict dict){
		deckEngine.addNewDeck(deckname);
		deckEngine.addNewWordtoDeck(deckname, dict);
	}

	private void removeFrmDeck(String deckname,Dict dict){
				deckEngine.removeWordFromDeck(deckname, dict);
	}
	/**
	 * this method calls all the ai methods'
	 */
	public void initialize(){
		DeckEngine.getInstance().addNewDeck("easy");
		DeckEngine.getInstance().addNewDeck("medium");
		DeckEngine.getInstance().addNewDeck("difficult");

		DeckEngine.getInstance().addNewDeck("last 5 days");
		DeckEngine.getInstance().addNewDeck("last 15 days");
		DeckEngine.getInstance().addNewDeck("last month");

		
		
		methodEMD();
		methodForgotten();
	}

	public void tellAi(Dict dict){
		methodEMD(dict);
		methodForgotten(dict);
	}

}//end of AI Engine
/*
 * 1 		always failed 
 * 0.7
 * 0.5 	50% failed
 * 0.3
 * 0		Never failed
 * 
 * */
